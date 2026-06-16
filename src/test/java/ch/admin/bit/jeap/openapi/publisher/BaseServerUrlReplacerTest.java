package ch.admin.bit.jeap.openapi.publisher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BaseServerUrlReplacerTest {

    private static final String INTERNAL_FQDN_PROPERTY = "internal.fqdn";
    private static final String EXAMPLE_COM = "example.com";
    private static final String OPENAPI_WITH_LOCALHOST_SERVER = "{\"openapi\":\"3.1.0\",\"info\":{},\"servers\":[{\"url\":\"http://localhost:8080/\"}]}";

    @Mock
    private Environment environment;
    @Mock
    private ArchRepoProperties archRepoProperties;
    private BaseServerUrlReplacer baseServerUrlReplacer;

    @BeforeEach
    void setUp() {
        when(archRepoProperties.isReplaceBaseServerUrl()).thenReturn(true);
        baseServerUrlReplacer = new BaseServerUrlReplacer(environment, "/test-context", archRepoProperties);
    }

    @Test
    void replacesServerUrlWhenFqdnIsConfigured() {
        when(archRepoProperties.getServiceFqdnProperty()).thenReturn(INTERNAL_FQDN_PROPERTY);
        when(environment.getProperty(INTERNAL_FQDN_PROPERTY)).thenReturn(EXAMPLE_COM);
        String openApiSpec = OPENAPI_WITH_LOCALHOST_SERVER;

        String result = baseServerUrlReplacer.replaceServerUrl(openApiSpec);

        assertThat(result).contains("\"url\":\"https://example.com/test-context\"");
    }

    @Test
    void doesNotReplaceServerUrlWhenFqdnIsNotConfigured() {
        when(archRepoProperties.getServiceFqdnProperty()).thenReturn(INTERNAL_FQDN_PROPERTY);
        when(environment.getProperty(INTERNAL_FQDN_PROPERTY)).thenReturn(null);
        String openApiSpec = OPENAPI_WITH_LOCALHOST_SERVER;

        String result = baseServerUrlReplacer.replaceServerUrl(openApiSpec);

        assertThat(result).isEqualTo(openApiSpec);
    }

    @Test
    void doesNotReplaceServerUrlWhenOriginalUrlIsNotPresent() {
        when(archRepoProperties.getServiceFqdnProperty()).thenReturn(INTERNAL_FQDN_PROPERTY);
        when(environment.getProperty(INTERNAL_FQDN_PROPERTY)).thenReturn(EXAMPLE_COM);
        String openApiSpec = "{\"openapi\":\"3.1.0\",\"info\":{},\"servers\":[]}";

        String result = baseServerUrlReplacer.replaceServerUrl(openApiSpec);

        assertThat(result).isEqualTo(openApiSpec);
    }

    @Test
    void replacesServerUrlWithEmptyContextPath() {
        when(archRepoProperties.getServiceFqdnProperty()).thenReturn(INTERNAL_FQDN_PROPERTY);
        baseServerUrlReplacer = new BaseServerUrlReplacer(environment, "", archRepoProperties);
        when(environment.getProperty(INTERNAL_FQDN_PROPERTY)).thenReturn(EXAMPLE_COM);
        String openApiSpec = OPENAPI_WITH_LOCALHOST_SERVER;

        String result = baseServerUrlReplacer.replaceServerUrl(openApiSpec);

        assertThat(result).contains("\"url\":\"https://example.com\"");
    }

    @Test
    void doesNotReplaceServerUrlWhenReplaceBaseServerUrlIsFalse() {
        when(archRepoProperties.isReplaceBaseServerUrl()).thenReturn(false);
        String openApiSpec = OPENAPI_WITH_LOCALHOST_SERVER;

        String result = baseServerUrlReplacer.replaceServerUrl(openApiSpec);

        assertThat(result).isEqualTo(openApiSpec);
    }
}
