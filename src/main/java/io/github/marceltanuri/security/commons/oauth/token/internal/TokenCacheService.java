package io.github.marceltanuri.security.commons.oauth.token.internal;

/**
 * Defines the contract for a service that caches access tokens. Implementations
 * are responsible for storing and retrieving {@link TokenResponse} objects.
 *
 * @author Marcel Tanuri
 */
public interface TokenCacheService {

	/**
	 * Retrieves a token from the cache based on the provided key.
	 *
	 * @param key The unique key associated with the token, typically derived
	 *            from the client credentials and scope.
	 * @return The cached {@link TokenResponse} if a valid token is found,
	 *         otherwise {@code null}.
	 */
	public TokenResponse get(String key);

	/**
	 * Stores a token in the cache with a specified key.
	 *
	 * @param key   The unique key under which the token will be stored.
	 * @param token The {@link TokenResponse} to be cached.
	 */
	public void put(String key, TokenResponse token);

}