package ch.admin.bit.jeap.openapi.publisher;

import ch.admin.bit.jeap.openapi.archrepo.client.OpenApiArchitectureRepositoryService;
import ch.admin.bit.jeap.openapi.reader.OpenApiSpecReader;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.scheduling.annotation.Async;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class OpenApiSpecPublisher {

    static final String OPEN_API_SPEC_PUBLISHER_TASK_EXECUTOR = "openApiSpecPublisherTaskExecutor";

    private static final String TIMER_NAME = "jeap-publish-open-api-spec";
    private static final String SPAN_NAME = "publish-open-api-spec";

    private final String applicationName;
    private final OpenApiArchitectureRepositoryService openApiArchitectureRepositoryService;
    private final OpenApiSpecReader openApiSpecReader;
    private final BuildProperties buildProperties;
    private final GitProperties gitProperties;
    private final TracingTimer tracingTimer;

    OpenApiSpecPublisher(String applicationName,
                         OpenApiArchitectureRepositoryService openApiArchitectureRepositoryService,
                         OpenApiSpecReader openApiSpecReader,
                         BuildProperties buildProperties,
                         GitProperties gitProperties,
                         TracingTimer tracingTimer) {
        this.applicationName = applicationName;
        this.openApiArchitectureRepositoryService = openApiArchitectureRepositoryService;
        this.openApiSpecReader = openApiSpecReader;
        this.buildProperties = buildProperties;
        this.gitProperties = gitProperties;
        this.tracingTimer = tracingTimer;
    }

    @Async(OPEN_API_SPEC_PUBLISHER_TASK_EXECUTOR)
    public CompletableFuture<Void> publishOpenApiSpecAsync() {
        return tracingTimer.traceAndTime(SPAN_NAME, TIMER_NAME, () -> {
            try {
                publishOpenApiSpec();
                return CompletableFuture.completedFuture(null);
            } catch (Exception e) {
                log.error("Failed to publish OpenAPI spec", e);
                return CompletableFuture.failedFuture(e);
            }
        });
    }

    void publishOpenApiSpec() throws JsonProcessingException {
        String openApiSpec = openApiSpecReader.readOpenApiSpec();
        ByteArrayResource resource = getByteArrayResource(openApiSpec);
        openApiArchitectureRepositoryService.publishOpenApiSpec(applicationName, getAppVersion(), resource);
        log.info("Published OpenAPI specification successfully");
    }

    private ByteArrayResource getByteArrayResource(String openApiSpec) {
        return new ByteArrayResource(openApiSpec.getBytes(StandardCharsets.UTF_8)) {
            @Override
            public String getFilename() {
                return applicationName + "-open-api-spec.json";
            }
        };
    }

    private String getAppVersion() {
        if (buildProperties != null) {
            return buildProperties.getVersion();
        }
        if (gitProperties != null) {
            String gitBuildVersion = gitProperties.get("git.build.version");
            if (gitBuildVersion != null) {
                return gitBuildVersion;
            }
        }
        return "na";
    }
}
