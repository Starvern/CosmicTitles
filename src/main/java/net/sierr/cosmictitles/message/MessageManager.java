package net.sierr.cosmictitles.message;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.sierr.cosmictitles.CosmicTitles;
import net.sierr.cosmictitles.Manager;

import java.io.File;

public final class MessageManager extends Manager
{
    public MessageManager(CosmicTitles api, File file)
    {
        super(api, file);
    }

    /**
     * @return The prefix for any message. Returns blank String if disabled or missing.
     * @since 0.1.0
     */
    public String getPrefix()
    {
        if (!config.getBoolean("prefix.enabled")) return "";
        return config.getString("prefix.value", "");
    }

    /**
     * @param key The key of the message in the message config.
     * @return The message value. Blank if missing.
     * @since 0.1.0
     */
    public String getMessage(MessageKey key)
    {
        String message = this.config.getString(key.toString(), "");
        return getPrefix() + message;
    }

    /**
     * @param message The unformatted message.
     * @return The message formatted as an Adventure component.
     */
    public Component format(String message, TagResolver... resolvers)
    {
        return MiniMessage.miniMessage().deserialize(message, resolvers);
    }

    /**
     * @param audience The {@link Audience} to send to.
     * @param key The {@link MessageKey} of the message to send.
     * @param resolvers The placeholders to parse.
     * @since 0.1.0
     */
    public void sendMessageFormatted(Audience audience, MessageKey key, TagResolver... resolvers)
    {
        String message = this.api.getMessageManager().getMessage(key);
        audience.sendMessage(this.api.getMessageManager().format(message, resolvers));
    }

    /**
     * @param audience The {@link Audience} to send to.
     * @param keys The {@link MessageKey}(s) of the message(s) to send.
     * @since 0.1.0
     */
    public void sendMessage(Audience audience, MessageKey ... keys)
    {
        for (MessageKey key : keys)
        {
            String message = this.api.getMessageManager().getMessage(key);
            audience.sendMessage(this.api.getMessageManager().format(message));
        }
    }
}
