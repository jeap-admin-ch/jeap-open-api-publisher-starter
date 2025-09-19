package ch.admin.bit.jeap.openapi.archrepo.client;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.service.annotation.PostExchange;

public interface OpenApiArchitectureRepositoryService {

    @PostExchange(value = "/api/openapi/{systemComponentName}", contentType = MediaType.MULTIPART_FORM_DATA_VALUE)
    void publishOpenApiSpec(@PathVariable String systemComponentName, @RequestParam String version, @RequestPart("file") Resource file);
}
