package net.sierr.cosmictitles.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import net.sierr.cosmictitles.CosmicTitles;
import net.sierr.cosmictitles.dialog.TitleDialog;
import net.sierr.cosmictitles.message.MessageKey;
import net.sierr.cosmictitles.titles.Title;
import net.sierr.cosmictitles.titles.TitleType;

public class TitleCommand
{
    private static final String PERMISSION = "cosmictitles.command";
    private static final String RELOAD_PERMISSION = "cosmictitles.command.reload";
    private static final String SET_PERMISSION = "cosmictitles.command.set";
    private static final String UNSET_PERMISSION = "cosmictitles.command.unset";

    public static LiteralArgumentBuilder<CommandSourceStack> createCommand(CosmicTitles api)
    {
        return Commands.literal("cosmictitles")
                .requires(src -> src.getSender().hasPermission(PERMISSION))
                .then(Commands.argument("type", new TitleTypeArgument())
                        .executes(ctx -> openDialog(ctx, api)))
                .then(Commands.literal("reload")
                        .executes(ctx -> reload(ctx, api))
                        .requires(src -> src.getSender().hasPermission(RELOAD_PERMISSION))
                )
                .then(Commands.literal("unset")
                        .requires(src -> src.getSender().hasPermission(UNSET_PERMISSION))
                        .then(Commands.argument("type", new TitleTypeArgument())
                                .executes(ctx -> unsetTitle(ctx, api))
                        )
                )
                .then(Commands.literal("set")
                        .requires(src -> src.getSender().hasPermission(SET_PERMISSION))
                        .then(Commands.argument("type", new TitleTypeArgument())
                                .then(Commands.argument("title", new TitleArgument(api))
                                        .executes(ctx -> setTitle(ctx, api))
                                )
                        )
                );
    }

    private static int openDialog(CommandContext<CommandSourceStack> ctx, CosmicTitles api)
    {
        TitleType type = ctx.getArgument("type", TitleType.class);
        Entity executor = ctx.getSource().getExecutor();

        if (executor instanceof Player player)
            player.showDialog(TitleDialog.getDialog(player, api, type));

        return Command.SINGLE_SUCCESS;
    }

    private static int reload(CommandContext<CommandSourceStack> ctx, CosmicTitles api)
    {
        api.getPlayerTitleHolderManager().saveHolders();
        api.reload();
        api.getMessageManager().sendMessage(ctx.getSource().getSender(), MessageKey.PLUGIN_RELOAD);
        return Command.SINGLE_SUCCESS;
    }

    private static int unsetTitle(CommandContext<CommandSourceStack> ctx, CosmicTitles api)
    {
        TitleType type = ctx.getArgument("type", TitleType.class);
        Entity executor = ctx.getSource().getExecutor();

        if (!(executor instanceof Player player))
            return Command.SINGLE_SUCCESS;

        api.getPlayerTitleHolderManager().setTitle(player, null, type);

        TagResolver.Single typePl = Placeholder.parsed("type", type.toString());
        api.getMessageManager().sendMessageFormatted(ctx.getSource().getSender(), MessageKey.TITLE_UNSET, typePl);

        return Command.SINGLE_SUCCESS;
    }

    private static int setTitle(CommandContext<CommandSourceStack> ctx, CosmicTitles api)
    {
        Entity executor = ctx.getSource().getExecutor();

        if (!(executor instanceof Player player))
            return Command.SINGLE_SUCCESS;

        TitleType type = ctx.getArgument("type", TitleType.class);
        Title title = ctx.getArgument("title", Title.class);

        api.getPlayerTitleHolderManager().setTitle(player, title, type);

        TagResolver.Single typePl = Placeholder.parsed("type", type.toString());
        TagResolver.Single titlePl = Placeholder.parsed("title", title.getDisplay());

        api.getMessageManager().sendMessageFormatted(ctx.getSource().getSender(), MessageKey.TITLE_SET, typePl, titlePl);

        return Command.SINGLE_SUCCESS;
    }

}
