package net.sierr.cosmictitles.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import net.sierr.cosmictitles.CosmicTitles;

public class PlayerJoinEventListener implements Listener
{
    private final CosmicTitles api;

    public PlayerJoinEventListener(CosmicTitles api)
    {
        this.api = api;
    }

    @EventHandler
    public void onEvent(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        this.api.getPlayerTitleHolderManager().initializeHolder(player);
    }
}
