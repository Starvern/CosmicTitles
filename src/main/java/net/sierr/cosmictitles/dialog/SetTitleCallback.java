package net.sierr.cosmictitles.dialog;

import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.data.dialog.action.DialogActionCallback;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.jetbrains.annotations.NotNull;
import net.sierr.cosmictitles.CosmicTitles;
import net.sierr.cosmictitles.message.MessageKey;
import net.sierr.cosmictitles.player.PlayerTitleHolder;
import net.sierr.cosmictitles.titles.Title;
import net.sierr.cosmictitles.titles.TitleType;

import java.util.Optional;
import java.util.UUID;

public class SetTitleCallback implements DialogActionCallback
{
    private final CosmicTitles api;
    private final Title title;
    private final TitleType type;

    public SetTitleCallback(CosmicTitles api, Title title, TitleType type)
    {
        this.api = api;
        this.title = title;
        this.type = type;
    }

    @Override
    public void accept(@NotNull DialogResponseView response, @NotNull Audience audience)
    {
        Optional<UUID> playerUUID = audience.get(Identity.UUID);

        if (playerUUID.isEmpty())
            return;

        PlayerTitleHolder holder = api.getPlayerTitleHolderManager().getHolder(playerUUID.get());
        holder.setTitle(type, title);

        api.getMessageManager().sendMessageFormatted(
                audience,
                MessageKey.TITLE_SET,
                Placeholder.parsed("title", title.getDisplay()),
                Placeholder.parsed("type", type.toString())
        );
    }
}
