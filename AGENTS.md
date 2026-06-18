# AGENTS.md

Guidance for AI coding agents working **in this repository**. For how to *use* the library in a
consuming service, read [README.md](README.md) and the [docs/](docs/) folder instead.

## Project

jEAP OpenAPI Specification Publisher is a single-module Spring Boot starter that publishes a service's
OpenAPI specification to the jEAP Architecture Repository Service (archrepo). On the Spring
`ApplicationReadyEvent` it reads the OpenAPI document produced by `springdoc-openapi`, optionally
rewrites its base server URL, and uploads it to the archrepo over an OAuth2-authenticated HTTP client.
The upload runs asynchronously and is best-effort: failures are logged and never break startup.

## Repository layout

```
pom.xml                                                  # Single-module Spring Boot starter (parent: jeap-internal-spring-boot-parent)
src/main/java/ch/admin/bit/jeap/openapi/
  publisher/OpenApiSpecPublisherAutoConfiguration.java   # @AutoConfiguration wiring all beans
  publisher/ArchRepoProperties.java                      # @ConfigurationProperties(prefix = "jeap.archrepo")
  publisher/OpenApiSpecPublisherEventListener.java       # Listens for ApplicationReadyEvent, triggers the upload
  publisher/OpenApiSpecPublisher.java                    # @Async publish: read, replace URL, upload
  publisher/BaseServerUrlReplacer.java                   # Replaces the OpenAPI base server URL with the service FQDN
  publisher/TracingTimer.java                            # Optional Micrometer span + timer around the publish
  reader/OpenApiSpecReader.java                          # Reads the spec from springdoc's OpenApiResource
  reader/HttpServletRequestFactory.java                  # Synthesises a fake HttpServletRequest for the reader
  archrepo/client/OpenApiArchitectureRepositoryService.java  # HTTP interface (@PostExchange) to the archrepo
src/main/resources/META-INF/spring/...AutoConfiguration.imports  # Registers the auto-configuration
src/test/...                                             # Spring Boot integration tests with WireMock + Awaitility
Jenkinsfile, publiccode.yml, CHANGELOG.md, LICENSE
```

## Build & test

```bash
./mvnw install        # build incl. tests
./mvnw test           # run tests
```

- Parent: `ch.admin.bit.jeap:jeap-internal-spring-boot-parent` (Spring Boot 4 aligned).
- Integration tests boot a `@SpringBootTest` against a WireMock archrepo and OAuth2 token endpoint and
  assert the upload happens asynchronously after `ApplicationReadyEvent` (`await(...)` on the publish timer).
- Spring Boot 3 maintenance happens on the `release/springboot3` branch; `master` targets Spring Boot 4.

## jEAP conventions

- Java packages live under `ch.admin.bit.jeap.openapi.*`.
- Configuration properties use the prefix `jeap.archrepo.*` (see `ArchRepoProperties`).
- Auto-configuration is registered via `@AutoConfiguration` and
  `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`.
- The publisher only activates when an `OpenApiResource` bean is present (springdoc) and
  `jeap.archrepo.enabled` is not `false`; the archrepo HTTP client is only created when
  `jeap.archrepo.url` is set.
- The upload must stay best-effort and asynchronous: never let a publish failure break startup or block
  the main thread.

## Docs

When changing public behaviour, update the matching focused file under [docs/](docs/) (one topic per
file) and the documentation index in the README.

## Versioning

- Semantic Versioning; all changes documented in [CHANGELOG.md](./CHANGELOG.md) (Keep a Changelog format).
- `setPomVersions.sh <version>` updates the version in the POM.
- When working on a feature branch, increase the version to `x.y.z-SNAPSHOT` in the POM.
- Always keep the -SNAPSHOT postfix in the POM, CI will remove it when releasing a version. Do not use the
  SNAPSHOT postfix in other places (CHANGELOG, publiccode.yml etc).
- Keep changelog entries concise and to the point, follow existing patterns.
- Keep commit messages short, use the JIRA ID from the branch name as a prefix, do not use conventional
  commits (for example: "JEAP-1234 Added feature X").
- When bumping the version, also update the changelog, and update version/date in `publiccode.yml`.
