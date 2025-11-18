package io.github.marceltanuri.security.commons.oauth.token.internal;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.marceltanuri.security.commons.oauth.token.api.TokenService;
import io.github.marceltanuri.security.commons.oauth.token.api.TokenServiceException;

import java.io.IOException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcel Tanuri
 *
 * An implementation of {@link TokenService} that is configurable and uses a cache.
 */
@Component(immediate = true, service = TokenService.class)
public class ConfigurableTokenService implements TokenService {

    /**
     * Retrieves an access token using the client credentials flow.
     * It first checks the cache for a valid token, and if not found, fetches a new one.
     * @param settings The client credentials settings.
     * @return The access token.
     * @throws TokenServiceException If an error occurs while fetching the token.
     */
    @Override
    public String getAccessToken(
        TokenService.ClientCredentialsSettings settings) {

        String scope = settings.scope();

        String cacheKey = settings.clientId() + ":" + scope;

        TokenResponse token = _tokenCacheService.get(cacheKey);

        if (token != null) {
            return token.getAccessToken();
        }

        token = _fetchAccessToken(settings);

        _tokenCacheService.put(cacheKey, token);

        return token.getAccessToken();
    }

    /**
     * Activates the component, initializing the HTTP client.
     * @param properties The component properties.
     */
    @Activate
    protected void activate(Map<String, Object> properties) {
        _httpClient = HttpClient.newHttpClient();
    }

    /**
     * Fetches a new access token from the token endpoint.
     * @param settings The client credentials settings.
     * @return The {@link TokenResponse}.
     * @throws TokenServiceException If an error occurs while fetching the token.
     */
    private TokenResponse _fetchAccessToken(
        ClientCredentialsSettings settings) {

        Map<String, String> formData = new HashMap<>();

        formData.put("client_id", settings.clientId());
        formData.put("client_secret", settings.clientSecret());
        formData.put("grant_type", "client_credentials");

        String scope = settings.scope();

        if ((scope != null) && !scope.isBlank()) {
            formData.put("scope", scope);
        }

        String audience = settings.audience();

        if ((audience != null) && !audience.isBlank()) {
            formData.put("audience", audience);
        }

        String form = formData.entrySet(
        ).stream(
        ).map(
            e -> e.getKey() + "=" + e.getValue()
        ).collect(
            Collectors.joining("&")
        );

        HttpRequest httpRequest = HttpRequest.newBuilder(
        ).uri(
            URI.create(settings.tokenEndpoint())
        ).header(
            "Content-Type", "application/x-www-form-urlencoded"
        ).POST(
            HttpRequest.BodyPublishers.ofString(form, StandardCharsets.UTF_8)
        ).build();

        try {
            HttpResponse<String> response = _httpClient.send(
                httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return _objectMapper.readValue(
                    response.body(), TokenResponse.class);
            }

            throw new TokenServiceException(
                "Failed to retrieve access token. Status code: " +
                    response.statusCode());
        }
        catch (InterruptedException | IOException exception) {
            throw new TokenServiceException(
                "Error fetching access token", exception);
        }
    }

    private HttpClient _httpClient;
    private final ObjectMapper _objectMapper = new ObjectMapper();

    @Reference
    private TokenCacheService _tokenCacheService;

}