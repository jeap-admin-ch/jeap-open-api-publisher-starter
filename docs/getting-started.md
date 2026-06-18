# Getting started

This page shows how to add the OpenAPI Specification Publisher to a Spring Boot service so that its
OpenAPI specification is uploaded to the jEAP Architecture Repository Service (archrepo) on startup. For
the upload flow see [How it works](how-it-works.md).

## 1. Add the dependency

Add the starter to the Maven module that contains your Spring Boot application:

```xml
<dependency>
    <groupId>ch.admin.bit.jeap</groupId>
    <artifactId>jeap-open-api-publisher-starter</artifactId>
</dependency>
```

The version is managed by the jEAP Spring Boot parent. The starter pulls in
`springdoc-openapi-starter-webmvc-ui`, so the service exposes an OpenAPI document that the publisher can
read. The publisher only activates when an `OpenApiResource` bean is present (i.e. springdoc is on the
classpath and enabled).

## 2. Enable publishing

Publishing is activated by setting the archrepo URL. Without it, the starter stays inert and does
nothing at startup.

```yaml
jeap:
  archrepo:
    url: https://internal-csp.applicationplatform-{env}.mycompany.ch/applicationplatform-archrepo-service
```

See the [Configuration reference](configuration.md) for all `jeap.archrepo.*` properties and their
defaults.

## 3. Configure authentication

The archrepo API is protected by OAuth2 (client-credentials grant). Configure an OAuth2 client
registration named `archrepo-client` (the default) under `spring.security.oauth2.client.registration`:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          archrepo-client:
            client-id: ${your-keycloak-system-name}-archrepo-client
            client-secret: ${your-secret}
            scope: openid
            authorization-grant-type: client_credentials
            provider: archrepo-client
        provider:
          archrepo-client:
            token-uri: https://internal.keycloak.mycompany.ch/realms/bazg-applicationplatform/protocol/openid-connect/token
```

The OAuth2 client must be granted the archrepo write role. See [Authentication](authentication.md) for
the required role and per-environment token endpoints.

## 4. Start the service

That is all. On the next start, once the application is ready, the publisher reads the OpenAPI
specification and uploads it to the archrepo in the background. A successful upload logs
`Published OpenAPI specification successfully`; failures are logged but do not affect the running
application.

> When configuring many microservices, place the shared `spring.security.oauth2.*` and
> `jeap.archrepo.url` configuration in a common location (for example the
> `conf/aws-jeap-base/app-config-common.yml` AWS AppConfig file) to avoid duplication.

## Related

- [How it works](how-it-works.md)
- [Configuration reference](configuration.md)
- [Authentication](authentication.md)
- [jeap-open-api-publisher-starter](../README.md)
