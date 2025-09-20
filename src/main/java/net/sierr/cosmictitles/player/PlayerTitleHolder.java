package net.sierr.cosmictitles.player;

import org.bukkit.entity.Player;
import net.sierr.cosmictitles.titles.Title;
import net.sierr.cosmictitles.titles.TitleType;

import javax.annotation.Nullable;
import java.util.UUID;

public class PlayerTitleHolder
{
    private final UUID uuid;
    private Title prefix;
    private Title suffix;

    public PlayerTitleHolder(Player player, Title prefix, Title suffix)
    {
        this(player.getUniqueId(), prefix, suffix);
    }

    public PlayerTitleHolder(UUID uuid, Title prefix, Title suffix)
    {
        this.uuid = uuid;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    /**
     * @return The Player's UUID who holds these titles.
     * @since 0.1.0
     */
    public UUID getUUID()
    {
        return this.uuid;
    }

    /**
     * @return The prefix of the player.
     * @since 0.1.0
     */
    @Nullable
    public Title getPrefix()
    {
        return this.prefix;
    }

    /**
     * @param prefix The prefix to set on the player.
     * @since 0.1.0
     */
    public void setPrefix(Title prefix)
    {
        this.prefix = prefix;
    }

    /**
     * @return The suffix of the player.
     * @since 0.1.0
     */
    @Nullable
    public Title getSuffix()
    {
        return this.suffix;
    }

    /**
     * @param suffix The suffix to set on the player.
     * @since 0.1.0
     */
    public void setSuffix(Title suffix)
    {
        this.suffix = suffix;
    }

    /**
     * @param type The type to set (prefix / suffix)
     * @param title The title to set.
     * @since 0.1.0
     */
    public void setTitle(TitleType type, Title title)
    {
        switch (type) {
            case PREFIX -> this.setPrefix(title);
            case SUFFIX -> this.setSuffix(title);
        }
    }
}
