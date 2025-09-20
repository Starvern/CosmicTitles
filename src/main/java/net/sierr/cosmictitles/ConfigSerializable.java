package net.sierr.cosmictitles;

import java.util.Map;

public interface ConfigSerializable<T>
{
    /**
     * @return The Map containing all values. Empty Map if not implemented.
     * @since 0.1.0
     */
    Map<String, Object> serialize();

    /**
     * @return The object to be serialized.
     * @since 0.1.0
     */
    T getHolder();
}
