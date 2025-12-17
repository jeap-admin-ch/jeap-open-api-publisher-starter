package ch.admin.bit.jeap.openapi.publisher;

import ch.admin.bit.jeap.openapi.OpenApiSpecPublisherTestApplication;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.Duration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest(classes = OpenApiSpecPublisherTestApplication.class, properties = {
        "jeap.archrepo.service-fqdn-property=test.fqdn",
        "test.fqdn=test-app-fqdn.bit.admin.ch",
        "server.servlet.context-path=/test-app"
})
@ActiveProfiles("test")
@AutoConfigureObservability // To test the timed annotation on the publisher method
@DirtiesContext
class OpenApiSpecUploadBaseUrlReplacementIntegrationTest {

    private static final String FILE_CONTENT = "{\"openapi\":\"3.1.0\",\"info\":{\"title\":\"OpenAPI definition\",\"version\":\"v0\"},\"servers\":[{\"url\":\"https://test-app-fqdn.bit.admin.ch/test-app\",\"description\":\"Generated server url\"}],\"paths\":{},\"components\":{}}";

    @Autowired
    private MeterRegistry meterRegistry;

    @MockitoSpyBean(name = OpenApiSpecPublisher.OPEN_API_SPEC_PUBLISHER_TASK_EXECUTOR)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    static WireMockServer wireMockServer = new WireMockServer(wireMockConfig()
            .dynamicPort()
            .http2PlainDisabled(true));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        wireMockServer.start();

        mockOAuthTokenResponse();

        // Set up mock API endpoint before Spring Boot starts
        wireMockServer.stubFor(post(urlPathEqualTo("/api/openapi/test-app"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")));

        registry.add("wiremock.port", () -> wireMockServer.port());
        registry.add("jeap.archrepo.url", () -> "http://localhost:" + wireMockServer.port());
    }

    @AfterAll
    static void tearDown() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    @SneakyThrows
    void shouldUploadOpenApiSpecOnStartup() {
        // Wait for async publication to complete (since it's triggered by ApplicationReadyEvent)
        await()
                .atMost(Duration.ofSeconds(60))
                .untilAsserted(() -> {
                    var requests = wireMockServer.findAll(postRequestedFor(urlPathEqualTo("/api/openapi/test-app")));
                    assertThat(requests)
                            .withFailMessage("Expected at least one API call to /api/openapi/test-app")
                            .hasSizeGreaterThan(0);
                });

        // Verify the request was made
        var requests = wireMockServer.findAll(postRequestedFor(urlPathEqualTo("/api/openapi/test-app")));
        assertThat(requests)
                .withFailMessage("Expected one API call to /api/openapi/test-app")
                .hasSize(1);

        LoggedRequest request = requests.getFirst();

        // Verify that the request has a bearer token in the auth header
        assertThat(request.getHeader("Authorization"))
                .withFailMessage("Request should contain Authorization header with Bearer token")
                .isNotNull()
                .contains("Bearer test-token");

        assertThat(request.getBodyAsString())
                .contains("Content-Disposition: form-data; name=\"file\"; filename=\"test-app-open-api-spec.json\"")
                .contains("test-app-open-api-spec.json")
                .contains(FILE_CONTENT);


        // Verify that the task was indeed executed asynchronously
        Mockito.verify(threadPoolTaskExecutor, Mockito.times(1))
                .execute(Mockito.any(Runnable.class));

        Timer timer = (Timer) meterRegistry.getMeters().stream().filter(t -> t.getId().getName().contains("jeap-publish-open-api"))
                .toList().getFirst();
        assertThat(timer.count())
                .withFailMessage("Expected timer for jeap-publish-open-api to be recorded")
                .isOne();
    }

    private static void mockOAuthTokenResponse() {
        // Mock OAuth2 token endpoint
        wireMockServer.stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"access_token\":\"test-token\",\"token_type\":\"Bearer\",\"expires_in\":3600}")));
    }
}
