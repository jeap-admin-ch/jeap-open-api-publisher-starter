package ch.admin.bit.jeap.openapi.archrepo.client;

import ch.admin.bit.jeap.openapi.OpenApiSpecPublisherTestApplication;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = OpenApiSpecPublisherTestApplication.class)
@EnableAutoConfiguration()
@ActiveProfiles("test")
class OpenApiArchitectureRepositoryServiceTest {

    private static final String FILE_CONTENT = "my file content";
    private static final String COMPONENT_NAME = "test-system-component";
    private static final String COMPONENT_VERSION = "1.0.0";
    private static final String API_PATH = "/api/openapi/test-system-component";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String EXPECTED_ONE_API_CALL = "Expected exactly one API call";

    static WireMockServer wireMockServer = new WireMockServer(wireMockConfig()
            .dynamicPort()
            .http2PlainDisabled(true));

    private final OpenApiArchitectureRepositoryService openApiArchitectureRepositoryService;

    OpenApiArchitectureRepositoryServiceTest(@Autowired OpenApiArchitectureRepositoryService openApiArchitectureRepositoryService) {
        this.openApiArchitectureRepositoryService = openApiArchitectureRepositoryService;
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        wireMockServer.start();

        // Set up mock API endpoint before Spring Boot starts
        wireMockServer.stubFor(post(urlPathEqualTo(API_PATH))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        registry.add("wiremock.port", () -> wireMockServer.port());
        registry.add("jeap.archrepo.url", () -> "http://localhost:" + wireMockServer.port());
    }

    @BeforeEach
    void resetWireMock() {
        wireMockServer.resetAll();
        mockOAuthTokenResponse();
    }

    @AfterAll
    static void tearDown() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    void shouldPublishOpenApiSpecSuccessfully() {
        //Given
        ByteArrayResource byteArrayResource = getByteArrayResource();

        // Set up WireMock stub
        wireMockServer.stubFor(post(urlPathEqualTo(API_PATH))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        // When
        assertDoesNotThrow(() -> openApiArchitectureRepositoryService.publishOpenApiSpec(COMPONENT_NAME, COMPONENT_VERSION, byteArrayResource));

        // Then
        var requests = wireMockServer.findAll(postRequestedFor(urlPathEqualTo(API_PATH)));
        assertThat(requests)
                .withFailMessage(EXPECTED_ONE_API_CALL)
                .hasSize(1);

        var request = requests.getFirst();
        assertThat(request.getHeader(CONTENT_TYPE)).startsWith(MediaType.MULTIPART_FORM_DATA_VALUE);
        assertThat(request.getQueryParams().get("version").getValues().getFirst()).isEqualTo(COMPONENT_VERSION);

        assertThat(request.getBodyAsString())
                .contains("Content-Disposition: form-data; name=\"file\"; filename=\"unit-test-open-api-spec.json\"")
                .contains("unit-test-open-api-spec.json")
                .contains(FILE_CONTENT);

    }


    @Test
    void shouldHandleServerError() {
        //Given
        ByteArrayResource byteArrayResource = getByteArrayResource();

        // Set up WireMock stub for server error
        wireMockServer.stubFor(post(urlPathEqualTo(API_PATH))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)));

        // When & Then
        assertThrows(HttpServerErrorException.class,
                () -> openApiArchitectureRepositoryService.publishOpenApiSpec(COMPONENT_NAME, COMPONENT_VERSION, byteArrayResource));

        // Verify request was made
        var requests = wireMockServer.findAll(postRequestedFor(urlPathEqualTo(API_PATH)));
        assertThat(requests)
                .withFailMessage(EXPECTED_ONE_API_CALL)
                .hasSize(1);
    }

    @Test
    void shouldHandleClientError() {
        //Given
        ByteArrayResource byteArrayResource = getByteArrayResource();

        // Set up WireMock stub for client error
        wireMockServer.stubFor(post(urlPathEqualTo(API_PATH))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)));

        // When & Then
        assertThrows(HttpClientErrorException.class, () -> openApiArchitectureRepositoryService.publishOpenApiSpec(COMPONENT_NAME, COMPONENT_VERSION, byteArrayResource));

        // Verify request was made
        var requests = wireMockServer.findAll(postRequestedFor(urlPathEqualTo(API_PATH)));
        assertThat(requests)
                .withFailMessage(EXPECTED_ONE_API_CALL)
                .hasSize(1);
    }

    private static void mockOAuthTokenResponse() {
        // Mock OAuth2 token endpoint
        wireMockServer.stubFor(post(urlEqualTo("/oauth/token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody("{\"access_token\":\"test-token\",\"token_type\":\"Bearer\",\"expires_in\":3600}")));
    }


    private static ByteArrayResource getByteArrayResource() {
        return new ByteArrayResource(FILE_CONTENT.getBytes(StandardCharsets.UTF_8)) {
            @Override
            public String getFilename() {
                return "unit-test-open-api-spec.json";
            }
        };
    }
}
