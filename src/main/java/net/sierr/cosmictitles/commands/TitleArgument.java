package net.sierr.cosmictitles.commands;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.MessageComponentSerializer;
import io.papermc.paper.command.brigadier.argument.CustomArgumentType;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import net.sierr.cosmictitles.CosmicTitles;
import net.sierr.cosmictitles.titles.Title;
import net.sierr.cosmictitles.titles.TitleType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TitleArgument implements CustomArgumentType.Converted<Title, String>
{
    private final CosmicTitles api;

    public TitleArgument(CosmicTitles api)
    {
        this.api = api;
    }

    private static final DynamicCommandExceptionType ERROR_INVALID_TITLE =
            new DynamicCommandExceptionType(title ->
                    MessageComponentSerializer.message()
                            .serialize(Component.text(title + " is not a valid title."))
    );

    @Override
    public @NotNull Title convert(@NotNull String nativeType) throws CommandSyntaxException
    {
        List<Title> titles = this.api.getTitleManager().getAllTitles();

        for (Title title : titles)
        {
            if (title.getName().equals(nativeType))
                return title;
        }

        throw ERROR_INVALID_TITLE.create(nativeType);
    }

    @Override
    public <S> @NotNull CompletableFuture<Suggestions> listSuggestions(
            @NotNull CommandContext<S> context,
            @NotNull SuggestionsBuilder builder
    ) {
        TitleType targetType = context.getArgument("type", TitleType.class);
        List<Title> titles = new ArrayList<>();

        if (targetType.equals(TitleType.PREFIX))
            titles = this.api.getTitleManager().getPrefixes();

        if (targetType.equals(TitleType.SUFFIX))
            titles = this.api.getTitleManager().getSuffixes();

        for (Title title : titles)
        {
            String titleName = title.getName();
            if (titleName.startsWith(builder.getRemainingLowerCase()))
                builder.suggest(titleName);
        }

        return builder.buildFuture();
    }

    @Override
    public @NotNull ArgumentType<String> getNativeType()
    {
        return StringArgumentType.word();
    }
}
