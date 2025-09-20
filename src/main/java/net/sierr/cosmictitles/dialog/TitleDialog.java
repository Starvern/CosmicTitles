package net.sierr.cosmictitles.dialog;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import org.bukkit.entity.Player;
import net.sierr.cosmictitles.CosmicTitles;
import net.sierr.cosmictitles.titles.Title;
import net.sierr.cosmictitles.titles.TitleType;

import java.util.ArrayList;
import java.util.List;

public class TitleDialog
{
    public static Dialog getDialog(Player player, CosmicTitles api, TitleType type)
    {
        List<Title> titles = api.getTitleManager().getPrefixes();

        if (type.equals(TitleType.SUFFIX))
            titles = api.getTitleManager().getSuffixes();

        List<ActionButton> buttonList = new ArrayList<>();

        DialogAction lockedCallback = DialogAction.customClick(
                new LockedTitleCallback(),
                ClickCallback.Options.builder().build()
        );

        for (Title title : titles)
        {
            DialogAction titleSetAction = DialogAction.customClick(
                    new SetTitleCallback(api, title, type),
                    ClickCallback.Options.builder().build()
            );

            Component tooltip = api.getMessageManager().format(
                    player.hasPermission(title.getPermission())
                            ? title.getRarity().getDisplay()
                            : title.getRarity().getDisplay() + " <red>LOCKED"
                    );

            buttonList.add(ActionButton.create(
                    api.getMessageManager().format(title.getDisplay()),
                    tooltip,
                    75,
                    player.hasPermission(title.getPermission()) ? titleSetAction : lockedCallback
            ));
        }

        return Dialog.create(factory -> factory.empty()
                .base(DialogBase.builder(Component.text("Select Title"))
                        .build()
                )
                .type(
                        DialogType.multiAction(buttonList)
                                .columns(3)
                                .exitAction(ActionButton.create(Component.text("Close"), null, 100, null))
                                .build()
                )
        );
    }
}
