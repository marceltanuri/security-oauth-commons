package io.github.marceltanuri.security.commons.oauth.token.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import io.github.marceltanuri.security.commons.oauth.token.configuration.TokenCacheConfiguration; // Still needed for ConfigurationPID reference

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * An in-memory implementation of {@link TokenCacheService} that uses a
 * {@link LinkedHashMap} to provide a simple, size-limited,
 * least-recently-used (LRU) cache for access tokens. The cache size is
 * configurable through OSGi Configuration Admin.
 *
 * @author Marcel Tanuri
 */
@Component(
    configurationPid = "io.github.marceltanuri.security.commons.oauth.token.configuration.TokenCacheConfiguration",
    immediate = true, 
    service = TokenCacheService.class
)
public class InMemoryTokenCacheService implements TokenCacheService {

    /**
     * Retrieves a token from the in-memory cache. It returns the token only if
     * it exists and is still valid according to its expiration time.
     *
     * @param key The unique key for the token.
     * @return The {@link TokenResponse} if a valid token is found, otherwise {@code null}.
     */
    public TokenResponse get(String key) {
        TokenResponse token = _cache.get(key);

        if ((token != null) && token.isValid()) {
            return token;
        }

        _cache.remove(key);
        return null;
    }

    /**
     * Stores a token in the in-memory cache.
     *
     * @param key The unique key for the token.
     * @param token The {@link TokenResponse} to be stored.
     */
    public void put(String key, TokenResponse token) {
        _cache.put(key, token);
    }

    /**
     * Activates or modifies the component, configuring the cache size based on
     * the "max-entries" property from the OSGi configuration.
     *
     * @param properties The component properties containing the configuration.
     */
   	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_tokenCacheConfiguration = ConfigurableUtil.createConfigurable(
			TokenCacheConfiguration.class, properties);

		int maxEntries = _tokenCacheConfiguration.maxEntries();

		_cache = Collections.synchronizedMap(
			new LinkedHashMap<String, TokenResponse>(maxEntries, 0.75F, true) {

				@Override
				protected boolean removeEldestEntry(
					Map.Entry<String, TokenResponse> eldest) {

					if (size() > maxEntries) {
						return true;
					}

					return false;
				}

			});
	}

    private Map<String, TokenResponse> _cache;
    private volatile TokenCacheConfiguration _tokenCacheConfiguration;
}