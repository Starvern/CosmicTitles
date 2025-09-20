package net.sierr.cosmictitles.titles;

import org.bukkit.configuration.ConfigurationSection;
import net.sierr.cosmictitles.CosmicTitles;
import net.sierr.cosmictitles.Manager;
import net.sierr.cosmictitles.rarities.Rarity;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;

public final class TitleManager extends Manager
{
    private final List<Title> prefixes;
    private final List<Title> suffixes;

    public TitleManager(CosmicTitles api, File file)
    {
        super(api, file);
        this.prefixes = new ArrayList<>();
        this.suffixes = new ArrayList<>();
    }

    /**
     * Loads all titles from config. Note: Titles with type ANY will appear in both lists.
     * @since 0.1.0
     */
    public void loadTitles()
    {
        Set<String> names = this.config.getKeys(false);

        for (String name : names)
        {
            ConfigurationSection section = this.config.getConfigurationSection(name);
            if (section == null) continue;

            Map<String, Object> values = section.getValues(false);

            Title title = deserializeTitle(name, values);
            if (title == null) continue;

            if (title.getType().equals(TitleType.PREFIX) || title.getType().equals(TitleType.ANY))
                this.prefixes.add(title);

            if (title.getType().equals(TitleType.SUFFIX) || title.getType().equals(TitleType.ANY))
                this.suffixes.add(title);
        }
    }

    /**
     * @return All (prefix / any) titles registered in the API.
     * @since 0.1.0
     */
    public List<Title> getPrefixes()
    {
        return prefixes;
    }

    /**
     * @return All titles (prefixes & suffixes) registered in the API.
     * @since 0.1.0
     */
    public List<Title> getAllTitles()
    {
        List<Title> titles = new ArrayList<>(this.prefixes);

        for (Title suffix : this.suffixes)
        {
            if (!titles.contains(suffix)) titles.add(suffix);
        }

        return titles;
    }

    /**
     * @param name The name of the prefix.
     * @return The prefix with the given name.
     * @since 0.1.0
     */
    public Title getPrefix(String name)
    {
        for (Title prefix : this.prefixes)
        {
            if (prefix.getName().equalsIgnoreCase(name)) return prefix;
        }
        return null;
    }

    /**
     * @param name The name of the suffix.
     * @return The suffix with the given name.
     * @since 0.1.0
     */
    public Title getSuffix(String name)
    {
        for (Title suffix : this.suffixes)
        {
            if (suffix.getName().equalsIgnoreCase(name)) return suffix;
        }
        return null;
    }

    /**
     * @return All (suffix / any) titles registered in the API.
     * @since 0.1.0
     */
    public List<Title> getSuffixes()
    {
        return suffixes;
    }

    /**
     * @param name The name of the title.
     * @param values The values / properties of this title.
     * @return The Title with these values.
     * @since 0.1.0
     */
    @Nullable
    public Title deserializeTitle(String name, Map<String, Object> values)
    {
        String display = values.get("display").toString();
        String permission = values.get("permission").toString();
        String rarityName = values.get("rarity").toString();
        String rawTitleType = values.get("type").toString();

        if (display == null || permission == null || rarityName == null || rawTitleType == null)
        {
            this.api.getLogger().warn("Title '{}' has invalid fields.", name);
            return null;
        }

        Optional<TitleType> optionalType = Arrays.stream(TitleType.values())
                .filter(t -> t.toString().equalsIgnoreCase(rawTitleType))
                .findFirst();

        if (optionalType.isEmpty())
        {
            this.api.getLogger().warn("Title '{}' has an invalid type: '{}'", name, rawTitleType);
            return null;
        }

        Rarity rarity = this.api.getRarityManager().getRarity(rarityName);

        if (rarity == null)
        {
            this.api.getLogger().warn("Title '{}' has an invalid rarity: '{}'", name, rarityName);
            return null;
        }

        return new Title(name, display, permission, rarity, optionalType.get());
    }
}
