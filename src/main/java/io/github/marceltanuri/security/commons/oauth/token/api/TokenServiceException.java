package io.github.marceltanuri.security.commons.oauth.token.api;

/**
 * Unchecked exception thrown when an error occurs within the
 * {@link TokenService}, such as a failure to fetch or parse an OAuth 2.0 token.
 * 
 * @author Marcel Tanuri
 */
public class TokenServiceException extends RuntimeException {

	/**
	 * Constructs a new {@code TokenServiceException} with the specified detail
	 * message.
	 *
	 * @param message The detail message.
	 */
	public TokenServiceException(String message) {
		super(message);
	}

	/**
	 * Constructs a new {@code TokenServiceException} with the specified detail
	 * message and cause.
	 *
	 * @param message The detail message.
	 * @param cause   The cause of the exception.
	 */
	public TokenServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
