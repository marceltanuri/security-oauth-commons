package io.github.marceltanuri.security.commons.oauth.token.api;

/**
 * @author Marcel Tanuri
 *
 * A service for obtaining OAuth2 access tokens.
 * Implementations of this interface are responsible for managing the
 * client credentials flow and caching tokens.
 */
public interface TokenService {

	/**
	 * Retrieves a valid OAuth2 access token.
	 * <p>
	 * This method will first try to retrieve a token from the cache. If a valid
	 * token is not available, it will fetch a new one from the configured token endpoint.
	 *
	 * @param settings The specific OAuth client settings.
	 * @return A valid access token as a String.
	 */
	public String getAccessToken(ClientCredentialsSettings settings);

	/**
	 * @author Marcel Tanuri
	 *
	 * Defines the settings required for an OAuth2 client.
	 * Consumers should use the static {@code builder()} method
	 * to create immutable instances.
	 */
	public interface ClientCredentialsSettings {

		/**
		 * Returns a builder for creating a new instance of
		 * OAuthClientSettings in a fluent and immutable way.
		 * @return A Builder for the OAuth client configuration.
		 */
		public static DefaultClientCredentialsSettings.Builder builder() {

			// This method exposes the Builder class of the concrete implementation.

			return new DefaultClientCredentialsSettings.Builder();
		}

		/**
		 * Returns the audience for which the access token is intended. The audience
		 * is typically a logical name or identifier for the resource server that
		 * will be consuming the token.
		 *
		 * @return The audience as a String.
		 */
		public String audience();

		/**
		 * Returns the client identifier, a public string used by the authorization
		 * server to identify the client.
		 *
		 * @return The client ID as a String.
		 */
		public String clientId();

		/**
		 * Returns the client secret, a confidential key used to authenticate the
		 * client with the authorization server.
		 *
		 * @return The client secret as a String.
		 */
		public String clientSecret();

		/**
		 * Returns the scope of the access request. The scope defines the level of
		 * access that the client is requesting.
		 *
		 * @return The scope as a String.
		 */
		public String scope();

		/**
		 * Returns the URL of the authorization server's token endpoint, where the
		 * client exchanges its credentials for an access token.
		 *
		 * @return The token endpoint URL as a String.
		 */
		public String tokenEndpoint();

	}

}