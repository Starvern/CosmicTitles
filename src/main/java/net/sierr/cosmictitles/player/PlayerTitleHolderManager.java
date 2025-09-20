package net.sierr.cosmictitles.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import net.sierr.cosmictitles.CosmicTitles;
import net.sierr.cosmictitles.Manager;
import net.sierr.cosmictitles.titles.Title;
import net.sierr.cosmictitles.titles.TitleType;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public final class PlayerTitleHolderManager extends Manager
{
    private final List<PlayerTitleHolder> holders;

    public PlayerTitleHolderManager(CosmicTitles api, File file)
    {
        super(api, file);
        this.holders = new ArrayList<>();
    }

    /**
     * Loads starting player titles from database.
     * @since 0.1.0
     */
    public void loadHolders()
    {
        Set<String> playerUUIDs = this.config.getKeys(false);

        for (String rawPlayerUUID : playerUUIDs)
        {
            UUID playerUUID = UUID.fromString(rawPlayerUUID);

            if (!Bukkit.getOfflinePlayer(playerUUID).hasPlayedBefore())
            {
                this.api.getLogger().warn("Player (uuid={}) has never played before. Clean up config?", playerUUID);
                continue;
            }

            String prefixName = this.config.getString(rawPlayerUUID + ".prefix");
            String suffixName = this.config.getString(rawPlayerUUID + ".suffix");

            Title prefix = this.api.getTitleManager().getPrefix(prefixName);
            Title suffix = this.api.getTitleManager().getSuffix(suffixName);

            PlayerTitleHolder holder = new PlayerTitleHolder(playerUUID, prefix, suffix);
            this.holders.add(holder);
        }
    }

    /**
     * Saves all holders to the config.
     * @since 0.1.0
     */
    public void saveHolders()
    {
        for (PlayerTitleHolder holder : this.holders)
        {
            UUID playerUUID = holder.getUUID();
            Title prefix = holder.getPrefix();
            Title suffix = holder.getSuffix();

            if (prefix != null)
                this.config.set(playerUUID + ".prefix", prefix.getName());
            if (suffix != null)
                this.config.set(playerUUID + ".suffix", suffix.getName());
        }

        try
        {
            this.config.save(this.file);
        }
        catch(IOException exception)
        {
            this.api.getLogger().warn("Failed to save player title data to data.yml " +
                    "(you may experience player's equipped title being reset).");
        }
    }

    /**
     * Ensures a player has a holder. (Creates new if not existent).
     * @param player The player to get titles for.
     * @return The player's titles.
     * @since 0.1.0
     */
    public PlayerTitleHolder initializeHolder(Player player)
    {
        return initializeHolder(player.getUniqueId());
    }

    /**
     * Ensures a player has a holder. (Creates new if not existent).
     * @param uuid The player's UUID to get titles for.
     * @return The player's titles.
     * @since 0.1.0
     */
    public PlayerTitleHolder initializeHolder(UUID uuid)
    {
        PlayerTitleHolder existentHolder = this.getHolder(uuid);
        if (existentHolder != null) return existentHolder;

        PlayerTitleHolder holder = new PlayerTitleHolder(uuid, null, null);
        this.holders.add(holder);
        return holder;
    }

    /**
     * @return The API.
     * @since 0.1.0
     */
    public CosmicTitles getApi()
    {
        return api;
    }

    /**
     * @return All players & their titles on the server.
     * @since 0.1.0
     */
    public List<PlayerTitleHolder> getHolders()
    {
        return holders;
    }

    /**
     * @param player The player's title to get. Null if player not found.
     * @return The player's PlayerTitleHolder
     * @since 0.1.0
     */
    @Nullable
    public PlayerTitleHolder getHolder(Player player)
    {
        return this.getHolder(player.getUniqueId());
    }

    /**
     * @param playerUUID The player's (UUID) title to get. Null if player not found.
     * @return The player's PlayerTitleHolder
     * @since 0.1.0
     */
    @Nullable
    public PlayerTitleHolder getHolder(UUID playerUUID)
    {
        for (PlayerTitleHolder holder : this.holders)
        {
            if (holder.getUUID().equals(playerUUID)) return holder;
        }
        return null;
    }

    /**
     * @param player The player to set for.
     * @param title The title to set with the player.
     * @param type The type / slot to set it on.
     * @return false if the title's type doesn't match the type specified.
     * @since 0.1.0
     */
    public boolean setTitle(Player player, Title title, TitleType type)
    {
        if (type.equals(TitleType.PREFIX))
            return setPrefix(player, title);
        if (type.equals(TitleType.SUFFIX))
            return setSuffix(player, title);

        return false;
    }

    /**
     * @param player The player to set for.
     * @param title The title to set with the player.
     * @return false if the title's type is not PREFIX or ANY.
     * @since 0.1.0
     */
    public boolean setPrefix(Player player, Title title)
    {
        PlayerTitleHolder holder = this.getHolder(player);
        if (holder == null) return false;

        if (title != null && !title.getType().equals(TitleType.PREFIX) && !title.getType().equals(TitleType.ANY))
        {
            this.api.getLogger().warn("Failed to set {} prefix to player {} (Title type is not prefix or any).",
                    title.getName(), player.getName());
            return false;
        }

        holder.setPrefix(title);
        return true;
    }

    /**
     * @param player The player to set for.
     * @param title The title to set with the player.
     * @return false if the title's type is not SUFFIX or ANY.
     * @since 0.1.0
     */
    public boolean setSuffix(Player player, Title title)
    {
        PlayerTitleHolder holder = this.getHolder(player);
        if (holder == null) return false;

        if (title != null && !title.getType().equals(TitleType.SUFFIX) && !title.getType().equals(TitleType.ANY))
        {
            this.api.getLogger().info("Failed to set {} suffix to player {} (Title type is not suffix or any).",
                    title.getName(), player.getName());
            return false;
        }

        holder.setSuffix(title);
        return true;
    }
}
