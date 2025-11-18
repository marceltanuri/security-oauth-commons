package io.github.marceltanuri.security.commons.oauth.token.internal;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Marcel Tanuri
 *
 * Represents the response from a token endpoint.
 * This class is used to deserialize the JSON response from the token endpoint.
 */
public class TokenResponse {

	/**
	 * Constructs a new TokenResponse and sets the creation time to the current time.
	 */
	public TokenResponse() {
		_createdAt = System.currentTimeMillis();
	}

	/**
	 * @return The access token.
	 */
	public String getAccessToken() {
		return _accessToken;
	}

	/**
	 * @return The lifetime in seconds of the access token.
	 */
	public long getExpiresIn() {
		return _expiresIn;
	}

	/**
	 * @return The type of the token.
	 */
	public String getTokenType() {
		return _tokenType;
	}

	/**
	 * Checks if the token is still valid, considering a 60-second buffer.
	 * @return {@code true} if the token is valid, {@code false} otherwise.
	 */
	public boolean isValid() {
		long expiryTime = _createdAt + (_expiresIn * 1000) - 60000;

		if (expiryTime > System.currentTimeMillis()) {
			return true;
		}

		return false;
	}

	/**
	 * Sets the access token.
	 * @param accessToken The access token.
	 */
	public void setAccessToken(String accessToken) {
		_accessToken = accessToken;
	}

	/**
	 * Sets the lifetime in seconds of the access token.
	 * @param expiresIn The lifetime in seconds.
	 */
	public void setExpiresIn(long expiresIn) {
		_expiresIn = expiresIn;
	}

	/**
	 * Sets the type of the token.
	 * @param tokenType The token type.
	 */
	public void setTokenType(String tokenType) {
		_tokenType = tokenType;
	}

	@JsonProperty("access_token")
	private String _accessToken;

	private final long _createdAt;

	@JsonProperty("expires_in")
	private long _expiresIn;

	@JsonProperty("token_type")
	private String _tokenType;

}