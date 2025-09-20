package net.sierr.cosmictitles;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import net.sierr.cosmictitles.commands.TitleCommand;
import net.sierr.cosmictitles.events.PlayerJoinEventListener;
import net.sierr.cosmictitles.hooks.PlaceholderAPIHook;
import net.sierr.cosmictitles.hooks.TitlePlaceholder;

import java.util.List;

public final class CosmicTitlesPlugin extends JavaPlugin
{
    private CosmicTitles api;

    @Override
    public void onEnable()
    {
        this.api = new CosmicTitles(this);

        this.getServer().getPluginManager().registerEvents(
                new PlayerJoinEventListener(this.api),
                this
        );

        this.getLifecycleManager().registerEventHandler(
                LifecycleEvents.COMMANDS,
                commands ->
                        commands.registrar().register(
                                TitleCommand.createCommand(api).build(),
                                "Access the titles plugin.",
                                List.of("ct", "title")
                        )
        );

        if (PlaceholderAPIHook.isEnabled())
            new TitlePlaceholder(this.api).register();
    }

    @Override
    public void onDisable()
    {
        this.api.getPlayerTitleHolderManager().saveHolders();
    }

    /**
     * @return The API this plugin is running.
     * @since 0.1.0
     */
    public CosmicTitles getApi()
    {
        return this.api;
    }
}
