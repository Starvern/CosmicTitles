package net.sierr.cosmictitles.dialog;

import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.data.dialog.action.DialogActionCallback;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class LockedTitleCallback implements DialogActionCallback
{
    @Override
    public void accept(@NotNull DialogResponseView response, Audience audience)
    {
        audience.sendMessage(Component.text("You cannot equip this title."));
    }
}
