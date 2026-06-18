# Configuration reference

All properties use the prefix `jeap.archrepo` and are bound by `ArchRepoProperties`. The only property
you normally need to set is `url`; the rest have sensible defaults.

| Name                                      | Default                                   | Description                                                                                                                                  |
|-------------------------------------------|-------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| `jeap.archrepo.url`                       | —                                         | URL of the archrepo service. If unset, the publisher does nothing at startup. Setting it enables the upload                                  |
| `jeap.archrepo.enabled`                   | `true`                                    | Master switch. Set to `false` to disable the auto-configuration entirely (for example in tests)                                             |
| `jeap.archrepo.oauth-client`              | `archrepo-client`                         | Id of the OAuth2 client registration used to authenticate with the archrepo (see [Authentication](authentication.md))                       |
| `jeap.archrepo.replace-base-server-url`   | `true`                                    | Replace the OpenAPI base server URL with the service FQDN before publishing                                                                  |
| `jeap.archrepo.service-fqdn-property`     | `aws.services.route53.internal_csp_fqdn`  | Name of the environment property holding the service FQDN, used when `replace-base-server-url` is `true`                                     |

## Activation conditions

The auto-configuration (`OpenApiSpecPublisherAutoConfiguration`) only kicks in when:

- an `OpenApiResource` bean is present (springdoc is on the classpath and enabled), and
- `jeap.archrepo.enabled` is `true` (the default).

The archrepo HTTP client and the publisher beans are only created when `jeap.archrepo.url` is set, so
configuring the URL is what actually turns publishing on.

## Example

```yaml
jeap:
  archrepo:
    url: https://internal-csp.applicationplatform-{env}.mycompany.ch/applicationplatform-archrepo-service
    # The following are shown with their defaults and only need to be set to override them:
    enabled: true
    oauth-client: archrepo-client
    replace-base-server-url: true
    service-fqdn-property: aws.services.route53.internal_csp_fqdn
```

## Related

- [Getting started](getting-started.md)
- [How it works](how-it-works.md)
- [Authentication](authentication.md)
- [jeap-open-api-publisher-starter](../README.md)
