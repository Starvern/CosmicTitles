package net.sierr.cosmictitles.rarities;

import net.sierr.cosmictitles.ConfigSerializable;

import java.util.HashMap;
import java.util.Map;

public class Rarity implements ConfigSerializable<Rarity>
{
    private final String name;
    private final String display;
    private final String permission;

    public Rarity(String name, String display, String permission)
    {
        this.name = name;
        this.display = display;
        this.permission = permission;
    }

    /**
     * @return The name / id of the rarity. ex: common.
     * @since 0.1.0
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return The display name of the rarity.
     * @since 0.1.0
     */
    public String getDisplay()
    {
        return display;
    }

    /**
     * @return The permission required to own / use any tags with this rarity.
     * @since 0.1.0
     */
    public String getPermission()
    {
        return permission;
    }

    @Override
    public Map<String, Object> serialize()
    {
        Map<String, Object> values = new HashMap<>();
        values.put("display", display);
        values.put("permission", permission);
        return values;
    }

    @Override
    public Rarity getHolder()
    {
        return this;
    }
}
