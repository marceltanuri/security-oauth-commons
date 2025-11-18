package io.github.marceltanuri.security.commons.oauth.token.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * Defines the OSGi Metatype configuration for the token cache. This interface
 * allows administrators to configure the cache properties, such as its maximum size,
 * through the OSGi configuration admin service.
 *
 * @author Marcel Tanuri
 */
@Meta.OCD(
    id = "io.github.marceltanuri.security.commons.oauth.token.configuration.TokenCacheConfiguration"
)
public interface TokenCacheConfiguration {

    /**
     * The maximum number of access tokens to store in the cache. When the cache
     * is full, the least recently used entries will be evicted.
     *
     * @return The maximum number of entries to keep in the cache. Defaults to 100.
     */
    @Meta.AD(
        deflt = "100",
        description = "The maximum number of entries to keep in the cache.",
        name = "max-entries", required = false
    )
    public int maxEntries();

}