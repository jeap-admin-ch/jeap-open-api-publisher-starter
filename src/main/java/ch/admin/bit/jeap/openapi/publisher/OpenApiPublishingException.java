package ch.admin.bit.jeap.openapi.publisher;

public class OpenApiPublishingException extends RuntimeException {

    public OpenApiPublishingException(String failedToPublishOpenAPISpec, Exception e) {
        super(failedToPublishOpenAPISpec, e);
    }
}
