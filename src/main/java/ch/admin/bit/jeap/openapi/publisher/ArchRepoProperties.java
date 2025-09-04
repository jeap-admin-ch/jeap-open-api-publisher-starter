package ch.admin.bit.jeap.openapi.publisher;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static ch.admin.bit.jeap.openapi.publisher.ArchRepoProperties.PREFIX;

@ConfigurationProperties(prefix = PREFIX)
@Data
public class ArchRepoProperties {

    public static final String PREFIX = "jeap.archrepo";

    /**
     * The URL of the archrepo to which the Open API specification will be published. If no URL is configured, the publisher will not
     * publish the Open API specification to the archrepo at startup.
     */
    private String url;
    /**
     * The OAuth client to use for authentication with the archrepo. A client registration with this name
     * must be configured under spring.security.oauth2.client.registration.
     */
    private String oauthClient = "archrepo-client";
    /**
     * If true (default), the publisher will send the Open API specification to the archrepo as long as an archrepo URL is set.
     */
    private boolean enabled = true;

}
