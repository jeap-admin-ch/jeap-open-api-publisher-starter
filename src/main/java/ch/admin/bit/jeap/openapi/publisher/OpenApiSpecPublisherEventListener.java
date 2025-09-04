package ch.admin.bit.jeap.openapi.publisher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Slf4j
public record OpenApiSpecPublisherEventListener(OpenApiSpecPublisher openApiSpecPublisher) {

    @EventListener(ApplicationReadyEvent.class)
    public void publishOpenApiSpecOnStartup() {
        openApiSpecPublisher.publishOpenApiSpecAsync();
    }
}
