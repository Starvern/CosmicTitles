package net.sierr.cosmictitles.hooks;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class PlaceholderAPIHook
{
    /**
     * @return If PlaceholderAPI is installed.
     * @since 0.1.0
     */
    public static boolean isEnabled()
    {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    /**
     * This function will also parse nested placeholders (placeholders that return placeholders).
     * @param player The player to parse the placeholders.
     * @param text The text to check for placeholders.
     * @return The parsed text.
     * @since 0.1.0
     */
    public static String parse(Player player, String text)
    {
        if (!isEnabled() || text == null) return text;

        String setString = text;

        while (PlaceholderAPI.containsPlaceholders(setString))
            setString = PlaceholderAPI.setPlaceholders(player, setString);

        return setString;
    }

    /**
     * This function will also parse nested placeholders (placeholders that return placeholders).
     * @param player The player to parse the placeholders.
     * @param list The list of texts to check for placeholders.
     * @return The parsed text.
     * @since 0.1.0
     */
    public static List<String> parse(Player player, List<String> list)
    {
        return list.stream()
                .map(line -> parse(player, line))
                .collect(Collectors.toList());
    }

}
