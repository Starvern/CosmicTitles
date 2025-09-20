package net.sierr.cosmictitles;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Takes in {@link CosmicTitles} and {@link File}, manages ensuring file
 * existence, and loading the config.
 */
public class Manager
{
    protected final CosmicTitles api;
    protected final File file;
    protected FileConfiguration config;

    protected Manager(CosmicTitles api, File file)
    {
        this.api = api;
        this.file = file;
    }

    /**
     * @return The instance of {@link CosmicTitles}.
     * @since 0.1.0
     */
    public CosmicTitles getApi()
    {
        return this.api;
    }

    /**
     * @return The {@link File} to load the config from.
     * @since 0.1.0
     */
    public File getFile()
    {
        return this.file;
    }

    /**
     * @return The {@link FileConfiguration} loaded from the {@link File}.
     * @since 0.1.0
     */
    public FileConfiguration getConfig()
    {
        return this.config;
    }

    /**
     * Register the manager.
     * @since 0.1.0
     */
    public void register()
    {
        try
        {
            if (!file.exists())
                this.api.getPlugin().saveResource(file.getName(), false);
            this.config = YamlConfiguration.loadConfiguration(this.file);
        }
        catch (IllegalArgumentException e)
        {
            this.api.getLogger().warn("Resource file ({}) is missing. Please restart the plugin.", file.getName());
            this.config = new YamlConfiguration();
        }
    }
}
