package ch.admin.bit.jeap.openapi.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.webmvc.api.OpenApiResource;

import java.util.Locale;

@Slf4j
public record OpenApiSpecReader(OpenApiResource openApiResource) {

    public String readOpenApiSpec() throws JsonProcessingException {
        String spec = new String(openApiResource.openapiJson(HttpServletRequestFactory.getHttpServletRequest(), "api-docs", Locale.getDefault()));
        log.trace("Found OpenAPI specification content: {}", spec);
        log.info("Found OpenAPI specification with size: {}", spec.length());
        return spec;
    }
}
