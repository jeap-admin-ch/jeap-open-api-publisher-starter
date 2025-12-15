package ch.admin.bit.jeap.openapi.publisher;

import brave.Tracer;
import ch.admin.bit.jeap.openapi.archrepo.client.OpenApiArchitectureRepositoryService;
import ch.admin.bit.jeap.openapi.reader.OpenApiSpecReader;
import ch.admin.bit.jeap.security.restclient.OAuth2ClientCredentialsRestClientInitializer;
import io.micrometer.core.instrument.MeterRegistry;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.webmvc.api.OpenApiResource;
import org.springdoc.webmvc.core.configuration.SpringDocWebMvcConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * Enabling the OpenAPI upload to the architecture repository (archrepo) requires setting the property
 * <pre>jeap.archrepo.url</pre> to the URL of the archrepo service.
 * This autoconfiguration is can be completely disabled (for example in tests) by setting the property <pre>jeap.archrepo.enabled=false</pre>.
 */
@AutoConfiguration(after = {SpringDocConfiguration.class, SpringDocWebMvcConfiguration.class})
@EnableConfigurationProperties(ArchRepoProperties.class)
@ConditionalOnBean(OpenApiResource.class)
@ConditionalOnProperty(prefix = ArchRepoProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableAsync
public class OpenApiSpecPublisherAutoConfiguration {

    @Bean
    public OpenApiSpecReader openApiSpecReader(OpenApiResource openApiResource) {
        return new OpenApiSpecReader(openApiResource);
    }

    @Bean
    public BaseServerUrlReplacer baseServerUrlReplacer(Environment environment, @Value("${server.servlet.context-path:}") String contextPath, ArchRepoProperties archRepoProperties) {
        return new BaseServerUrlReplacer(environment, contextPath, archRepoProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = ArchRepoProperties.PREFIX, name = "url")
    public OpenApiArchitectureRepositoryService openApiArchitectureRepositoryService(ClientRegistrationRepository clientRegistrationRepository,
                                                                                     OAuth2AuthorizedClientService clientService,
                                                                                     RestClient.Builder builder,
                                                                                     ArchRepoProperties properties) {

        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(properties.getOauthClient());
        if (clientRegistration == null) {
            throw new IllegalStateException("No OAuth2 client registration found with id: " + properties.getOauthClient() +
                                            ". Please ensure that the client registration is configured correctly at jeap.archrepo.oauth-client and that " +
                                            "an oauth client has been registered in the spring security configuration at spring.security.oauth2.client.registration." + properties.getOauthClient());
        }

        RestClient restClient = builder.clone()
                .baseUrl(properties.getUrl())
                .requestInitializer(new OAuth2ClientCredentialsRestClientInitializer(authorizedClientManager(clientRegistrationRepository, clientService), clientRegistration, false))
                .build();

        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build()
                .createClient(OpenApiArchitectureRepositoryService.class);
    }

    private OAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository clientRegistrationRepository,
                                                                  OAuth2AuthorizedClientService clientService) {
        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, clientService);
        OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        return authorizedClientManager;
    }

    @Bean
    @ConditionalOnBean({OpenApiArchitectureRepositoryService.class})
    public OpenApiSpecPublisher openApiSpecPublisher(OpenApiArchitectureRepositoryService openApiArchitectureRepositoryService,
                                                     OpenApiSpecReader openApiSpecReader,
                                                     @Value("${spring.application.name}") String applicationName,
                                                     @Autowired(required = false) BuildProperties buildProperties,
                                                     @Autowired(required = false) GitProperties gitProperties,
                                                     @Autowired(required = false) Tracer tracer,
                                                     @Autowired(required = false) MeterRegistry meterRegistry,
                                                     BaseServerUrlReplacer baseServerUrlReplacer) {
        return new OpenApiSpecPublisher(
                applicationName,
                openApiArchitectureRepositoryService,
                openApiSpecReader,
                buildProperties,
                gitProperties,
                new TracingTimer(tracer, meterRegistry),
                baseServerUrlReplacer);
    }

    @Bean
    @ConditionalOnBean(OpenApiSpecPublisher.class)
    public OpenApiSpecPublisherEventListener openApiSpecPublisherEventListener(OpenApiSpecPublisher openApiSpecPublisher) {
        return new OpenApiSpecPublisherEventListener(openApiSpecPublisher);
    }

    @Bean(OpenApiSpecPublisher.OPEN_API_SPEC_PUBLISHER_TASK_EXECUTOR)
    @ConditionalOnBean(OpenApiSpecPublisher.class)
    public ThreadPoolTaskExecutor openApiSpecPublisherTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(0);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(1);
        executor.setThreadNamePrefix("open-api-publisher-");
        executor.initialize();
        return executor;
    }

}
