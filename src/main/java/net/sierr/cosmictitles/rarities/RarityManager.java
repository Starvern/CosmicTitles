package net.sierr.cosmictitles.rarities;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import net.sierr.cosmictitles.CosmicTitles;
import net.sierr.cosmictitles.Manager;

import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class RarityManager extends Manager
{
    private final List<Rarity> rarities;

    public RarityManager(CosmicTitles api, File file)
    {
        super(api, file);
        this.rarities = new ArrayList<>();
    }

    /**
     * Loads all rarities from config.
     * @since 0.1.0
     */
    public void loadRarities()
    {
        Set<String> rarityNames = this.config.getKeys(false);

        for (String name : rarityNames)
        {
            ConfigurationSection section = this.config.getConfigurationSection(name);
            if (section == null) continue;

            Map<String, Object> values = section.getValues(false);

            Rarity rarity = deserializeRarity(name, values);
            if (rarity == null) continue;

            rarities.add(rarity);
        }
    }

    /**
     * @param name The name of the rarity.
     * @return The rarity with the given name.
     * @since 0.1.0
     */
    public @Nullable Rarity getRarity(String name)
    {
        for (Rarity rarity : rarities)
        {
            if (rarity.getName().equalsIgnoreCase(name))
                return rarity;
        }
        return null;
    }

    /**
     * @return The config containing the rarities (rarity.yml)
     * @since 0.1.0
     */
    public FileConfiguration getConfig()
    {
        return this.config;
    }

    /**
     * @return All rarities currently registered with this API.
     * @since 0.1.0
     */
    public List<Rarity> getRarities()
    {
        return this.rarities;
    }

    /**
     * @param name The name of the rarity.
     * @param values The values / properties of this rarity.
     * @return The Rarity with these values.
     * @since 0.1.0
     */
    public @Nullable Rarity deserializeRarity(String name, Map<String, Object> values)
    {
        String display = values.get("display").toString();
        String permission = values.get("permission").toString();

        if (display == null || permission == null)
        {
            this.api.getLogger().warn("Rarity '{}' has invalid fields.", name);
            return null;
        }

        return new Rarity(name, display, permission);
    }
}
