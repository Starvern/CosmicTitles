package net.sierr.cosmictitles.titles;

import net.sierr.cosmictitles.ConfigSerializable;
import net.sierr.cosmictitles.rarities.Rarity;

import java.util.HashMap;
import java.util.Map;

public class Title implements ConfigSerializable<Title>
{
    private final String name;
    private final String display;
    private final String permission;
    private final Rarity rarity;
    private final TitleType type;

    public Title(String name, String display, String permission, Rarity rarity, TitleType type)
    {
        this.name = name;
        this.display = display;
        this.permission = permission;
        this.rarity = rarity;
        this.type = type;
    }

    /**
     * @return The name / id of this tag. ex: super.
     * @since 0.1.0
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return The displayed value of the prefix.
     * @since 0.1.0
     */
    public String getDisplay()
    {
        return display;
    }

    /**
     * @return The permission required to own / use this prefix.
     * @since 0.1.0
     */
    public String getPermission()
    {
        return permission;
    }

    /**
     * @return Whether this title is a prefix or a suffix.
     * @since 0.1.0
     */
    public TitleType getType()
    {
        return type;
    }

    /**
     * @return The rarity of this tag.
     * @since 0.1.0
     */
    public Rarity getRarity()
    {
        return rarity;
    }

    @Override
    public Map<String, Object> serialize()
    {
        Map<String, Object> values = new HashMap<>();
        values.put("display", display);
        values.put("permission", permission);
        values.put("type", type.toString());
        values.put("rarity", rarity.serialize());
        return values;
    }

    @Override
    public Title getHolder()
    {
        return this;
    }
}
