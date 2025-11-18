package io.github.marceltanuri.security.commons.oauth.token.api;

/**
 * Default immutable implementation of the {@link TokenService.ClientCredentialsSettings}
 * interface. This class holds the configuration required for the OAuth 2.0
 * Client Credentials Grant flow.
 * <p>
 * Instances of this class should be created using the nested {@link Builder}.
 *
 * @author Marcel Tanuri
 */
public class DefaultClientCredentialsSettings
	implements TokenService.ClientCredentialsSettings {

	/**
	 * Returns the audience for which the access token is intended.
	 *
	 * @return The audience as a String.
	 */
	@Override
	public String audience() {
		return _audience;
	}

	/**
	 * Returns the client identifier.
	 *
	 * @return The client ID as a String.
	 */
	@Override
	public String clientId() {
		return _clientId;
	}

	/**
	 * Returns the client secret.
	 *
	 * @return The client secret as a String.
	 */
	@Override
	public String clientSecret() {
		return _clientSecret;
	}

	/**
	 * Returns the scope of the access request.
	 *
	 * @return The scope as a String.
	 */
	@Override
	public String scope() {
		return _scope;
	}

	/**
	 * Returns the URL of the token endpoint.
	 *
	 * @return The token endpoint URL as a String.
	 */
	@Override
	public String tokenEndpoint() {
		return _tokenEndpoint;
	}

	@Override
	public String toString() {
		return "DefaultClientCredentialsSettings{clientId='" + _clientId +
			'\'' + ", clientSecret='" + "[PROTECTED]" + '\'' +
				", tokenEndpoint='" + _tokenEndpoint + '\'' + ", scope='" +
					_scope + '\'' + ", audience='" + _audience + '\'' + '}';
	}

	/**
	 * A builder for creating immutable instances of {@link DefaultClientCredentialsSettings}.
	 */
	public static class Builder {

		/**
		 * Sets the audience for which the token is intended.
		 *
		 * @param audience The audience, typically a URI identifying the resource server.
		 * @return This builder instance for chaining.
		 */
		public Builder audience(String audience) {
			_audience = audience;

			return this;
		}

		/**
		 * Builds and returns an immutable {@link DefaultClientCredentialsSettings}
		 * instance with the configured properties.
		 *
		 * @return A new instance of {@link DefaultClientCredentialsSettings}.
		 */
		public DefaultClientCredentialsSettings build() {

			return new DefaultClientCredentialsSettings(this);
		}

		/**
		 * Sets the client identifier.
		 *
		 * @param clientId The client ID.
		 * @return This builder instance for chaining.
		 */
		public Builder clientId(String clientId) {
			_clientId = clientId;

			return this;
		}

		/**
		 * Sets the client secret.
		 *
		 * @param clientSecret The client secret.
		 * @return This builder instance for chaining.
		 */
		public Builder clientSecret(String clientSecret) {
			_clientSecret = clientSecret;

			return this;
		}

		/**
		 * Sets the scope of the access request.
		 *
		 * @param scope A space-delimited string of scopes.
		 * @return This builder instance for chaining.
		 */
		public Builder scope(String scope) {
			_scope = scope;

			return this;
		}

		/**
		 * Sets the URL of the authorization server's token endpoint.
		 *
		 * @param tokenEndpoint The token endpoint URL.
		 * @return This builder instance for chaining.
		 */
		public Builder tokenEndpoint(String tokenEndpoint) {
			_tokenEndpoint = tokenEndpoint;

			return this;
		}

		private String _audience;
		private String _clientId;
		private String _clientSecret;
		private String _scope;
		private String _tokenEndpoint;

	}

	private DefaultClientCredentialsSettings(Builder builder) {
		_clientId = builder._clientId;
		_clientSecret = builder._clientSecret;
		_tokenEndpoint = builder._tokenEndpoint;
		_scope = builder._scope;
		_audience = builder._audience;
	}

	private final String _audience;
	private final String _clientId;
	private final String _clientSecret;
	private final String _scope;
	private final String _tokenEndpoint;

}