package ch.admin.bit.jeap.openapi.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.webmvc.api.OpenApiResource;

import java.util.Locale;

@Slf4j
public record OpenApiSpecReader(OpenApiResource openApiResource) {

    public String readOpenApiSpec() throws JsonProcessingException {
        HttpServletRequest req = HttpServletRequestFactory.getHttpServletRequest();
        String spec = new String(openApiResource.openapiJson(req, "api-docs", Locale.getDefault()));
        log.info("Found OpenApiSpec: {}", spec);
        return spec;
    }


}



