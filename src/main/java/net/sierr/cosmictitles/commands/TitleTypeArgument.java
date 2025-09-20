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
import net.sierr.cosmictitles.titles.TitleType;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class TitleTypeArgument implements CustomArgumentType.Converted<TitleType, String>
{
    private static final DynamicCommandExceptionType ERROR_BAD_ARGUMENT = new DynamicCommandExceptionType(type ->
        MessageComponentSerializer.message().serialize(Component.text(type + " is an invalid type."))
    );

    @Override
    public @NotNull TitleType convert(@NotNull String nativeType) throws CommandSyntaxException
    {
        if (nativeType.toLowerCase(Locale.ROOT).equals("prefix") || nativeType.toLowerCase(Locale.ROOT).equals("suffix"))
        {
            return TitleType.parse(nativeType);
        }

        throw ERROR_BAD_ARGUMENT.create(nativeType);
    }

    @Override
    public <S> @NotNull CompletableFuture<Suggestions> listSuggestions(
            @NotNull CommandContext<S> context,
            @NotNull SuggestionsBuilder builder
    ) {
        List<String> possible = List.of("prefix", "suffix");

        for (String type : possible)
        {
            if (type.startsWith(builder.getRemainingLowerCase()))
                builder.suggest(type);
        }

        return builder.buildFuture();
    }

    @Override
    public @NotNull ArgumentType<String> getNativeType()
    {
        return StringArgumentType.word();
    }
}
