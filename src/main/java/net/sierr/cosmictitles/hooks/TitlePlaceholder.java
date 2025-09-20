package net.sierr.cosmictitles.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.sierr.cosmictitles.CosmicTitles;
import net.sierr.cosmictitles.player.PlayerTitleHolder;
import net.sierr.cosmictitles.titles.Title;

import java.util.UUID;

public class TitlePlaceholder extends PlaceholderExpansion
{
    private final CosmicTitles api;

    public TitlePlaceholder(CosmicTitles api)
    {
        this.api = api;
    }

    @Override
    public @NotNull String getIdentifier()
    {
        return "cosmictitles";
    }

    @Override
    public @NotNull String getAuthor()
    {
        return "Starvern";
    }

    @Override
    public @NotNull String getVersion()
    {
        return "0.1.0";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params)
    {
        UUID playerUUID = player.getUniqueId();

        PlayerTitleHolder holder = this.api.getPlayerTitleHolderManager().getHolder(playerUUID);
        if (holder == null) return "";

        Title prefix = holder.getPrefix();
        Title suffix = holder.getSuffix();

        if (prefix != null)
        {
            if (params.equalsIgnoreCase("prefix"))
                return PlaceholderAPI.setPlaceholders(player, prefix.getDisplay());
            if (params.equalsIgnoreCase("prefix_name"))
                return PlaceholderAPI.setPlaceholders(player, prefix.getName());
            if (params.equalsIgnoreCase("prefix_rarity"))
                return PlaceholderAPI.setPlaceholders(player, prefix.getRarity().getDisplay());
            if (params.equalsIgnoreCase("prefix_rarity_name"))
                return PlaceholderAPI.setPlaceholders(player, prefix.getRarity().getName());
        }
        if (suffix != null)
        {
            if (params.equalsIgnoreCase("suffix"))
                return PlaceholderAPI.setPlaceholders(player, suffix.getDisplay());
            if (params.equalsIgnoreCase("suffix_name"))
                return PlaceholderAPI.setPlaceholders(player, suffix.getName());
            if (params.equalsIgnoreCase("suffix_rarity"))
                return PlaceholderAPI.setPlaceholders(player, suffix.getRarity().getDisplay());
            if (params.equalsIgnoreCase("suffix_rarity_name"))
                return PlaceholderAPI.setPlaceholders(player, suffix.getRarity().getName());
        }

        return "";
    }
}
