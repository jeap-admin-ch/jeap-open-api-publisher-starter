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
        when(archRepoProperties.getServiceFqdnAttribute()).thenReturn("internal.fqdn");
        when(environment.getProperty("internal.fqdn")).thenReturn("example.com");
        String openApiSpec = "{\"openapi\":\"3.1.0\",\"info\":{},\"servers\":[{\"url\":\"http://localhost:8080/\"}]}";

        String result = baseServerUrlReplacer.replaceServerUrl(openApiSpec);

        assertThat(result).contains("\"url\":\"https://example.com/test-context\"");
    }

    @Test
    void doesNotReplaceServerUrlWhenFqdnIsNotConfigured() {
        when(archRepoProperties.getServiceFqdnAttribute()).thenReturn("internal.fqdn");
        when(environment.getProperty("internal.fqdn")).thenReturn(null);
        String openApiSpec = "{\"openapi\":\"3.1.0\",\"info\":{},\"servers\":[{\"url\":\"http://localhost:8080/\"}]}";

        String result = baseServerUrlReplacer.replaceServerUrl(openApiSpec);

        assertThat(result).isEqualTo(openApiSpec);
    }

    @Test
    void doesNotReplaceServerUrlWhenOriginalUrlIsNotPresent() {
        when(archRepoProperties.getServiceFqdnAttribute()).thenReturn("internal.fqdn");
        when(environment.getProperty("internal.fqdn")).thenReturn("example.com");
        String openApiSpec = "{\"openapi\":\"3.1.0\",\"info\":{},\"servers\":[]}";

        String result = baseServerUrlReplacer.replaceServerUrl(openApiSpec);

        assertThat(result).isEqualTo(openApiSpec);
    }

    @Test
    void replacesServerUrlWithEmptyContextPath() {
        when(archRepoProperties.getServiceFqdnAttribute()).thenReturn("internal.fqdn");
        baseServerUrlReplacer = new BaseServerUrlReplacer(environment, "", archRepoProperties);
        when(environment.getProperty("internal.fqdn")).thenReturn("example.com");
        String openApiSpec = "{\"openapi\":\"3.1.0\",\"info\":{},\"servers\":[{\"url\":\"http://localhost:8080/\"}]}";

        String result = baseServerUrlReplacer.replaceServerUrl(openApiSpec);

        assertThat(result).contains("\"url\":\"https://example.com\"");
    }

    @Test
    void doesNotReplaceServerUrlWhenReplaceBaseServerUrlIsFalse() {
        when(archRepoProperties.isReplaceBaseServerUrl()).thenReturn(false);
        String openApiSpec = "{\"openapi\":\"3.1.0\",\"info\":{},\"servers\":[{\"url\":\"http://localhost:8080/\"}]}";

        String result = baseServerUrlReplacer.replaceServerUrl(openApiSpec);

        assertThat(result).isEqualTo(openApiSpec);
    }
}
