package ch.admin.bit.jeap.openapi.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClient;

@Slf4j
public record OpenApiSpecReader(String portNumber, String contextPath, RestClient restClient, boolean sslEnabled) {

    public String readOpenApiSpec() {
        final String protocol = sslEnabled ? "https://" : "http://";
        final String uri = protocol + "localhost:" + portNumber + contextPath + "/api-docs";
        log.info("Reading OpenApiSpec from uri '{}'", uri);
        String spec = restClient.get()
                .uri(uri)
                .retrieve()
                .body(String.class);

        log.info("Found OpenApiSpec: {}", spec);
        return spec;
    }
}



