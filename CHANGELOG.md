# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres
to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [7.20.0] - 2026-08-22

### Changed
- Update parent from 9.0.0 to 9.0.1
- update jeap-spring-boot-security-client-starter from 24.19.0 to 24.20.0

## [7.19.0] - 2026-08-20

### Changed
- Update parent from 8.13.0 to 9.0.0
- update jeap-spring-boot-security-client-starter from 24.18.0 to 24.19.0
- Fix failing token introspection when a client id contains colons by URL-encoding the client id and secret before
  using them as basic auth credentials (see RFC 6749).

## [7.18.0] - 2026-08-19

### Changed
- Update parent from 8.12.1 to 8.13.0
- update jeap-spring-boot-security-client-starter from 24.17.0 to 24.18.0

## [7.17.0] - 2026-08-19

### Changed
- Update parent from 8.12.0 to 8.12.1
- update jeap-spring-boot-security-client-starter from 24.16.0 to 24.17.0

## [7.16.0] - 2026-08-18

### Changed
- Update parent from 8.11.0 to 8.12.0
- update jeap-spring-boot-security-client-starter from 24.15.0 to 24.16.0

## [7.15.0] - 2026-08-17

### Changed
- Update parent from 8.10.0 to 8.11.0
- update jeap-spring-boot-security-client-starter from 24.14.0 to 24.15.0

## [7.14.0] - 2026-08-13

### Changed
- Update parent from 8.9.1 to 8.10.0
- update jeap-spring-boot-security-client-starter from 24.12.0 to 24.14.0

## [7.13.0] - 2026-08-12

### Changed
- Update parent from 8.8.0 to 8.9.1
- update jeap-spring-boot-security-client-starter from 24.11.0 to 24.12.0

## [7.12.0] - 2026-08-11

### Changed
- Update parent from 8.7.1 to 8.8.0
- update jeap-spring-boot-security-client-starter from 24.10.0 to 24.11.0

## [7.11.0] - 2026-08-10

### Changed
- Update parent from 8.7.0 to 8.7.1
- update jeap-spring-boot-security-client-starter from 24.9.0 to 24.10.0
- update aws-advanced-jdbc-wrapper from 4.0.1 to 4.3.0
- update springdoc-openapi from 3.0.3 to 3.1.0

## [7.10.0] - 2026-08-08

### Changed
- Update parent from 8.6.1 to 8.7.0
- update jeap-spring-boot-security-client-starter from 24.8.0 to 24.9.0

## [7.9.0] - 2026-08-04

### Changed
- Update parent from 8.6.0 to 8.6.1
- update jeap-spring-boot-security-client-starter from 24.7.0 to 24.8.0

## [7.8.0] - 2026-08-01

### Changed
- Update parent from 8.5.6 to 8.6.0
- update jeap-spring-boot-security-client-starter from 24.6.1 to 24.7.0

## [7.7.1] - 2026-07-30
### Changed
- update jeap-spring-boot-security-client-starter from 24.6.0 to 24.6.1
- `ReadReplicaAwareTransactionManager`: Fixed a race condition in the lazy creation of the transaction counters.
  Transactions started concurrently while the counters were being created could observe a partially initialized
  state and fail with a `NullPointerException`, e.g. when kafka messages are consumed right after startup. Both
  counters are now published together. In addition, a failure to resolve the `MeterRegistry` no longer fails the
  transaction: it is logged once, and the counters are created on a subsequent transaction.

## [7.7.0] - 2026-07-28

### Changed
- Update parent from 8.5.5 to 8.5.6
- update jeap-spring-boot-security-client-starter from 24.5.0 to 24.6.0

## [7.6.0] - 2026-07-28
### Changed
- update jeap-spring-boot-security-client-starter from 24.4.0 to 24.5.0
- Load the existing monitoring and Actuator defaults early through
  `SpringBootActuatorEndpointActivator`, while retaining lower precedence than application
  configuration. Our working assumption is that loading these defaults later via
  `@PropertySource` allowed Spring Boot 4 to evaluate the Prometheus auto-configuration before the
  endpoint was enabled, so `/actuator/prometheus` was not registered and requests fell through to
  the application's OAuth security chain. The existing `management.endpoint.<id>.enabled`
  properties remain unchanged for backwards compatibility.

## [7.5.0] - 2026-07-25

### Changed
- Update parent from 8.5.4 to 8.5.5
- update jeap-spring-boot-security-client-starter from 24.3.0 to 24.4.0

## [7.4.0] - 2026-07-23

### Changed
- Update parent from 8.5.3 to 8.5.4
- update jeap-spring-boot-security-client-starter from 24.2.0 to 24.3.0

## [7.3.0] - 2026-07-23

### Changed
- Update parent from 8.5.2 to 8.5.3
- update jeap-spring-boot-security-client-starter from 24.1.0 to 24.2.0

## [7.2.1] - 2026-07-22

### Changed
- Throw a dedicated OpenApiPublishingException on any publish failure instead of an UncheckedIOException on JSON errors only

## [7.2.0] - 2026-07-22

### Changed
- Update parent from 8.5.0 to 8.5.2
- update jeap-spring-boot-security-client-starter from 24.0.0 to 24.1.0

## [7.1.0] - 2026-07-20

### Changed
- Complete the migration to the standalone WireMock Spring Boot integration.


## [7.0.0] - 2026-07-17
### Changed
- update jeap-spring-boot-security-client-starter from 23.15.0 to 24.0.0
- Provide the official WireMock Spring Boot integration without exposing WireMock's Jetty dependencies, replacing direct WireMock standalone dependencies across all modules.

## [6.16.0] - 2026-07-15

### Changed
- Update parent from 8.4.0 to 8.5.0
- update jeap-spring-boot-security-client-starter from 23.14.0 to 23.15.0

## [6.15.0] - 2026-07-13

### Changed
- Update parent from 8.3.4 to 8.4.0
- update jeap-spring-boot-security-client-starter from 23.13.0 to 23.14.0

## [6.14.0] - 2026-07-09
### Changed
- update jeap-spring-boot-security-client-starter from 23.12.0 to 23.13.0
- `jeap-spring-boot-security-starter-test`: add named role profiles in `OidcAuthorizationMockServer` via `withRoleProfile(...)`, plus profile switching via `setActiveProfile(...)`.
- `jeap-spring-boot-security-starter-test`: add convenience identity-claim setters in `OidcAuthorizationMockServer` (`withGivenName(...)`, `withFamilyName(...)`, `withName(...)`, `withLocale(...)`) for access token, ID token and userinfo responses.
- `jeap-spring-boot-security-starter-test`: `OidcAuthorizationMockServer.reset()` now restores the default profile and clears runtime OAuth state without rotating the JWKS key.

## [6.13.0] - 2026-07-09
### Changed
- update jeap-spring-boot-security-client-starter from 23.11.0 to 23.12.0
- Add OIDC Authorization mock server.

## [6.12.0] - 2026-07-09
### Changed
- update jeap-spring-boot-security-client-starter from 23.10.2 to 23.11.0
- `jeap-spring-boot-swagger`: translate the actuator OpenAPI group's title and description from German to English ("Monitoring Endpunkte" → "Monitoring Endpoints")
- Update documentation 

## [6.11.2] - 2026-07-06
### Changed
- update jeap-spring-boot-security-client-starter from 23.10.1 to 23.10.2
- Fix deprecated `@Valid` container annotation on `authServers` in `ResourceServerProperties` (Hibernate Validator warning HV000271)

## [6.11.1] - 2026-07-01
### Changed
- update jeap-spring-boot-security-client-starter from 23.10.0 to 23.10.1
- Add missing `test` scope to test/mock dependencies (`spring-boot-webmvc-test` in swagger starter, `wiremock-standalone` in security starter)

## [6.11.0] - 2026-06-30

### Changed
- Update parent from 8.3.3 to 8.3.4
- update jeap-spring-boot-security-client-starter from 23.9.0 to 23.10.0

## [6.10.0] - 2026-06-23

### Changed
- Update parent from 8.3.2 to 8.3.3
- update jeap-spring-boot-security-client-starter from 23.8.0 to 23.9.0

## [6.9.0] - 2026-06-22

### Changed
- Update parent from 8.3.1 to 8.3.2
- update jeap-spring-boot-security-client-starter from 23.7.0 to 23.8.0

## [6.8.0] - 2026-06-18

### Changed
- Update parent from 8.3.0 to 8.3.1
- update jeap-spring-boot-security-client-starter from 23.6.0 to 23.7.0

## [6.7.0] - 2026-06-17
### Changed
- update jeap-spring-boot-security-client-starter from 23.5.2 to 23.6.0
- Update parent from 8.2.0 to 8.3.0

## [6.6.0] - 2026-06-17

### Changed
- Update parent from 8.2.0 to 8.3.0
- Deprecated spring boot starter
- Sonar issues
- update jeap-spring-boot-security-client-starter from 23.5.0 to 23.5.2

## [6.5.1] - 2026-06-16

### Fixed
- Sonar issues
- Deprecated spring boot starter

## [6.5.0] - 2026-06-12

### Changed
- Update parent from 8.1.0 to 8.2.0
- Remove logstash version because it is managed by the internal parent now
- update jeap-spring-boot-security-client-starter from 23.4.0 to 23.5.0

## [6.4.0] - 2026-06-11
### Changed
  responses (e.g. Spring Boot's welcome page forwarding `/` to `index.html`). ETag content-caching is now disabled
  for FORWARD/INCLUDE dispatches so forwarded responses are served with their full body (without an ETag); regular
  requests keep their ETag unchanged.
- update jeap-spring-boot-security-client-starter from 23.3.0 to 23.4.0
- `jeap-spring-boot-web-config-starter`: the ShallowEtag filter no longer swallows the body of `forward:`-ed

## [6.3.0] - 2026-06-09
### Changed
- update jeap-spring-boot-security-client-starter from 23.2.0 to 23.3.0
- Update logstash-logback-encoder from 8.1 to 9.0 (migrates to Jackson 3)
- Update aws-advanced-jdbc-wrapper version to 4.0.1

## [6.2.0] - 2026-06-04

### Changed
- Update parent from 8.0.1 to 8.1.0
- update jeap-spring-boot-security-client-starter from 23.1.0 to 23.2.0

## [6.1.0] - 2026-06-01

### Changed
- Update parent from 7.0.0 to 8.0.1
- update jeap-spring-boot-security-client-starter from 23.0.0 to 23.1.0

## [6.0.0] - 2026-05-26
### Changed
- Official release with spring boot 4

## [4.3.0] - 2026-04-16

### Changed
- Update parent from 6.0.2 to 6.0.3
- update jeap-spring-boot-security-client-starter from 21.2.0 to 21.3.0

## [4.2.0] - 2026-04-13

### Changed
- Update parent from 6.0.0 to 6.0.2
- update jeap-spring-boot-security-client-starter from 21.1.0 to 21.2.0

## [4.1.0] - 2026-04-02

### Changed
- Update parent from 5.20.0 to 6.0.0
- update jeap-spring-boot-security-client-starter from 21.0.0 to 21.1.0

## [4.0.0] - 2026-03-30
### Changed
  only (without resource/tenant) now have distinct names to avoid confusion with the role-based overloads:
  | Old method                                  | New method                                              |
  |---------------------------------------------|---------------------------------------------------------|
  | `hasRoleForPartner(operation, partner)`     | `hasOperationForPartner(operation, partner)`            |
  | `hasRoleForAllPartners(operation)`          | `hasOperationForAllPartners(operation)`                 |
  | `getAllRoles(operation)`                    | `getAllRolesForOperation(operation)`                    |
  | `getAllRolesForPartner(operation, partner)` | `getAllRolesForOperationAndPartner(operation, partner)` |
  | `getAllRolesForAllPartners(operation)`      | `getAllRolesForOperationForAllPartners(operation)`      |
  | `getPartnersForRole(operation)`             | `getPartnersForOperation(operation)`                    |
  separator characters (`@`, `%`, `#`, `:`, `!`) are passed as expression parameters instead of decomposed values.
  Access is denied and an error is logged.
- update jeap-spring-boot-security-client-starter from 20.5.0 to 21.0.0
- **Breaking:** Renamed operation-only methods in `SemanticRoleRepository` for clarity. Methods that query by operation
- Added input validation to `SemanticRoleRepository` that detects misuse where full token role strings containing

## [3.5.0] - 2026-03-26

### Changed
- Update parent from 5.19.4 to 5.20.0
- update jeap-spring-boot-security-client-starter from 20.4.0 to 20.5.0

## [3.4.0] - 2026-03-23

### Changed
- Update parent from 5.19.3 to 5.19.4
- update jeap-spring-boot-security-client-starter from 20.3.0 to 20.4.0

## [3.3.0] - 2026-03-18
### Changed
- update jeap-spring-boot-security-client-starter from 20.2.0 to 20.3.0
- Added an eIAM claim set converter that can adapt eIAM-issued access tokens for jeap security.

## [3.2.0] - 2026-03-17
### Changed
- update jeap-spring-boot-security-client-starter from 20.1.0 to 20.2.0
- Added support for a different set of semantic role parts separators.

## [3.1.0] - 2026-03-12

### Changed
- Update parent from 5.19.2 to 5.19.3
- update jeap-spring-boot-security-client-starter from 20.0.0 to 20.1.0

## [3.0.0] - 2026-03-11
### Changed
  - **Removed**
    - Support for reactive/webflux
    - Support removed from monitoring, tracing, swagger, security web-config starters
- update jeap-spring-boot-security-client-starter from 19.16.0 to 20.0.0
-  Breaking Change

## [2.16.0] - 2026-03-10

### Changed
- Update parent from 5.19.0 to 5.19.2
- update jeap-spring-boot-security-client-starter from 19.15.0 to 19.16.0

## [2.15.0] - 2026-03-02

### Changed
- Update parent from 5.18.0 to 5.19.0
- update jeap-spring-boot-security-client-starter from 19.14.0 to 19.15.0

## [2.14.0] - 2026-02-25

### Changed
- Update parent from 5.17.1 to 5.18.0
- update jeap-spring-boot-security-client-starter from 19.13.0 to 19.14.0

## [2.13.0] - 2026-01-27

### Changed
- Update parent from 5.17.0 to 5.17.1
- update jeap-spring-boot-security-client-starter from 19.12.0 to 19.13.0

## [2.12.0] - 2026-01-21
### Changed
- update jeap-spring-boot-security-client-starter from 19.11.0 to 19.12.0
- Removed X-XSS-Protection header as recommended in https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/X-XSS-Protection

## [2.11.0] - 2026-01-20
### Changed
- update jeap-spring-boot-security-client-starter from 19.10.0 to 19.11.0
- Default server.forward-headers-strategy to NATIVE

## [2.10.0] - 2026-01-16
### Changed
  Enable via the `jeap.health.metric.contributor-metrics.enabled` property.
- update jeap-spring-boot-security-client-starter from 19.9.0 to 19.10.0
- Added support for exposing additional metrics about application health contributors.

## [2.9.0] - 2026-01-14

### Changed
- Update parent from 5.16.8 to 5.17.0
- update jeap-spring-boot-security-client-starter from 19.8.0 to 19.9.0
- update springdoc-openapi from 2.8.13 to 2.8.15

## [2.8.0] - 2026-01-07

### Changed
- Update parent from 5.16.7 to 5.16.8
- update jeap-spring-boot-security-client-starter from 19.7.0 to 19.8.0

## [2.7.0] - 2025-12-22

### Changed
- Update parent from 5.16.6 to 5.16.7
- update jeap-spring-boot-security-client-starter from 19.6.0 to 19.7.0

## [2.6.0] - 2025-12-19

### Changed
- Update parent from 5.16.5 to 5.16.6
- update jeap-spring-boot-security-client-starter from 19.5.0 to 19.6.0

## [2.5.1] - 2025-12-17

### Changed
- read the base server uri from the common configuration

## [2.5.0] - 2025-12-17

### Changed
- Update parent from 5.16.4 to 5.16.5
- update jeap-spring-boot-security-client-starter from 19.4.1 to 19.5.0

## [2.4.1] - 2025-12-16

### Changed
- update jeap-spring-boot-security-client-starter from 19.4.0 to 19.4.1
- Fix logback warnings due to deprecated features being used in the configuration

## [2.4.0] - 2025-12-15

### Changed
- Update parent from 5.16.3 to 5.16.4
- update jeap-spring-boot-security-client-starter from 19.3.0 to 19.4.0

## [2.3.0] - 2025-12-08

### Changed
- Update parent from 5.16.2 to 5.16.3
- update jeap-spring-boot-security-client-starter from 19.2.0 to 19.3.0

## [2.2.0] - 2025-12-08

### Changed
- Update parent from 5.16.1 to 5.16.2
- update jeap-spring-boot-security-client-starter from 19.1.0 to 19.2.0

## [2.1.0] - 2025-12-04

### Changed
- Update parent from 5.16.0 to 5.16.1
- update jeap-spring-boot-security-client-starter from 19.0.0 to 19.1.0

## [2.0.0] - 2025-12-03
### Changed
- update jeap-spring-boot-security-client-starter from 18.5.0 to 19.0.0
-  Breaking Change
    - **Removed**
      - jeap-spring-boot-cloud-autoconfig-starter
      - jeap-spring-boot-config-starter
      - other cloudfoundry specifics


## [1.4.0] - 2025-11-28

### Changed
- Update parent from 5.15.0 to 5.16.0
- Update parent from 5.15.1 to 5.16.0
- update jeap-spring-boot-security-client-starter from 18.4.0 to 18.5.0

## [1.3.0] - 2025-11-14
### Changed
- update jeap-spring-boot-security-client-starter from 18.2.0 to 18.4.0
- Update aws-advanced-jdbc-wrapper from 2.5.4 to 2.6.6


## [1.2.0] - 2025-10-02

### Changed

- Update parent from 5.14.0 to 5.15.0
- update starter from 18.0.0 to 18.2.0
- update springdoc-openapi from 2.8.9 to 2.8.13

## [1.1.0] - 2025-09-19

### Changed

- Update parent from 5.13.0 to 5.14.0

## [1.0.0] - 2025-09-08

### Added

- Initial release
