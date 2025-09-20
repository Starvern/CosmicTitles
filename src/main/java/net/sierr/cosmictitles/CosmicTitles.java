package net.sierr.cosmictitles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sierr.cosmictitles.message.MessageManager;
import net.sierr.cosmictitles.player.PlayerTitleHolderManager;
import net.sierr.cosmictitles.rarities.RarityManager;
import net.sierr.cosmictitles.titles.TitleManager;

import java.io.File;

public class CosmicTitles
{
    private final CosmicTitlesPlugin plugin;
    private RarityManager rarityManager;
    private TitleManager titleManager;
    private PlayerTitleHolderManager playerTitleHolderManager;
    private MessageManager messageManager;
    private final Logger logger;

    public CosmicTitles(CosmicTitlesPlugin plugin)
    {
        this.plugin = plugin;
        this.logger = LoggerFactory.getLogger("CosmicTitlesAPI");
        this.reload();
    }

    /**
     * Loads all configurations used by the API. All unsaved data will be removed. All holders are pre-saved.
     * @since 0.1.0
     */
    public void reload()
    {
        if (this.getPlayerTitleHolderManager() != null)
            this.getPlayerTitleHolderManager().saveHolders();

        File rarityFile = new File(this.plugin.getDataFolder(), "rarity.yml");
        File titleFile = new File(this.plugin.getDataFolder(), "title.yml");
        File dataFile = new File(this.plugin.getDataFolder(), "data.yml");
        File localeFile = new File(this.plugin.getDataFolder(), "locale.yml");

        this.rarityManager = new RarityManager(this, rarityFile);
        this.rarityManager.register();
        this.rarityManager.loadRarities();

        this.titleManager = new TitleManager(this, titleFile);
        this.titleManager.register();
        this.titleManager.loadTitles();

        this.playerTitleHolderManager = new PlayerTitleHolderManager(this, dataFile);
        this.playerTitleHolderManager.register();
        this.playerTitleHolderManager.loadHolders();

        this.messageManager = new MessageManager(this, localeFile);
        this.messageManager.register();
    }

    /**
     * @return The API logger.
     * @since 0.1.0
     */
    public Logger getLogger()
    {
        return this.logger;
    }

    /**
     * @return The message manager.
     * @since 0.1.0
     */
    public MessageManager getMessageManager()
    {
        return this.messageManager;
    }

    /**
     * @return The rarity manager.
     * @since 0.1.0
     */
    public RarityManager getRarityManager()
    {
        return this.rarityManager;
    }

    /**
     * @return The title manager.
     * @since 0.1.0
     */
    public TitleManager getTitleManager()
    {
        return this.titleManager;
    }

    /**
     * @return The player title holder manager.
     * @since 0.1.0
     */
    public PlayerTitleHolderManager getPlayerTitleHolderManager()
    {
        return this.playerTitleHolderManager;
    }

    /**
     * @return The plugin running this API.
     * @since 0.1.0
     */
    public CosmicTitlesPlugin getPlugin()
    {
        return this.plugin;
    }
}
