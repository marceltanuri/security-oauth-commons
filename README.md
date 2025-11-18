# Security OAuth Commons

A Java library for handling the OAuth 2.0 Client Credentials Grant flow, designed for OSGi environments.

## Description

This library provides a `TokenService` that simplifies the process of obtaining and caching OAuth 2.0 access tokens. It is designed to be used in an OSGi environment, with components and configurations managed by the OSGi framework.

## Features

-   **OAuth 2.0 Client Credentials Flow:** Implements the client credentials grant type to fetch access tokens.
-   **Token Caching:** Includes an in-memory, configurable LRU cache for access tokens to reduce redundant requests.
-   **OSGi Integration:** Designed as an OSGi bundle with declarative services and Metatype configuration.
-   **Fluent Configuration:** Provides a builder pattern for easy and immutable configuration of client settings.

## Core Components

### `TokenService` (Interface)

The main entry point for the library. It defines the contract for obtaining an access token.

-   `String getAccessToken(ClientCredentialsSettings settings)`: Retrieves a valid access token, either from the cache or by fetching a new one.

### `TokenService.ClientCredentialsSettings` (Interface)

Defines the settings required for the OAuth 2.0 client. An immutable instance can be created using the `builder()`:

```java
TokenService.ClientCredentialsSettings settings = TokenService.ClientCredentialsSettings.builder()
    .tokenEndpoint("https://your-auth-server.com/oauth/token")
    .clientId("your-client-id")
    .clientSecret("your-client-secret")
    .scope("api:read")
    .audience("your-api-identifier")
    .build();
```

### `ConfigurableTokenService` (Component)

The OSGi component implementing the `TokenService` interface. It handles the logic of fetching tokens from the authorization server and interacts with the `TokenCacheService`.

### `InMemoryTokenCacheService` (Component)

An in-memory implementation of the `TokenCacheService`. It uses a `LinkedHashMap` to create an LRU (Least Recently Used) cache for tokens.

### `TokenCacheConfiguration` (Metatype)

Provides OSGi Metatype configuration for the `InMemoryTokenCacheService`.

-   **max-entries**: The maximum number of tokens to store in the cache (default: `100`).

To configure this service, create a file named `io.github.marceltanuri.security.commons.oauth.token.configuration.TokenCacheConfiguration.config` in your OSGi configuration directory (e.g., `[Liferay Home]/osgi/configs`) with the desired properties. For example:

```properties
max-entries=200
```

## Usage Example

To use the `TokenService`, you would typically inject it as a dependency in your OSGi component:

```java
import io.github.marceltanuri.security.commons.oauth.token.api.TokenService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component
public class MyApiClient {

    private TokenService tokenService;

    @Reference
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public void callApi() {
        TokenService.ClientCredentialsSettings settings = TokenService.ClientCredentialsSettings.builder()
            .tokenEndpoint("https://your-auth-server.com/oauth/token")
            .clientId("your-client-id")
            .clientSecret("your-client-secret")
            .build();

        String accessToken = tokenService.getAccessToken(settings);

        // Use the access token to make an API call
        // ...
    }
}
```

## Exception Handling

-   `TokenServiceException`: A `RuntimeException` thrown if the service fails to fetch or parse an access token.
