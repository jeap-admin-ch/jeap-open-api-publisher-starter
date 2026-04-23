# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres
to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [5.1.0-alpha-springboot4] - 2026-04-23

### Changed

- Update parent from 7.0.4-alpha-springboot4 to 7.0.5-alpha-springboot4

## [5.0.0] - 2026-xx-xx

### Changed
- Update parent from 6.0.3 to 7.0.0 (Spring Boot 4)

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
