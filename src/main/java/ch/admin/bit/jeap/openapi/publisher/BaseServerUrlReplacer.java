package ch.admin.bit.jeap.openapi.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

@Slf4j
@RequiredArgsConstructor
public class BaseServerUrlReplacer {

    private static final String ORIGINAL_BASE_SERVER_URL = "\"url\":\"http://localhost:8080/\"";

    private final Environment environment;
    private final String contextPath;
    private final ArchRepoProperties archRepoProperties;

    public String replaceServerUrl(String openApiSpec) {
        if (!archRepoProperties.isReplaceBaseServerUrl()) {
            return openApiSpec;
        }
        log.debug("Replacing server base URL in OpenAPI spec if a FQDN is configured");
        String fqdn = this.environment.getProperty(archRepoProperties.getServiceFqdnProperty());
        log.info("Found FQDN '{}' for attribute '{}'", fqdn, archRepoProperties.getServiceFqdnProperty());
        if (fqdn != null) {
            String baseServerUrl = "https://" + fqdn + contextPath;
            log.info("Replacing base server URL in OpenAPI spec with '{}'", baseServerUrl);
            return openApiSpec.replace(ORIGINAL_BASE_SERVER_URL, "\"url\":\"" + baseServerUrl + "\"");
        }
        return openApiSpec;
    }

}
