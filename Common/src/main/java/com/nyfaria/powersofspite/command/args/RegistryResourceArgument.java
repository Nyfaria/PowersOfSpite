package com.nyfaria.powersofspite.command.args;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class RegistryResourceArgument<T> implements ArgumentType<T> {
    protected RegistryResourceArgument() {
    }

    @Override
    public final T parse(StringReader reader) throws CommandSyntaxException {
        int cursor = reader.getCursor();
        ResourceLocation id = ResourceLocation.read(reader);
        return Optional.ofNullable(this.getRegistry().get(id)).orElseThrow(() -> {
            reader.setCursor(cursor);
            return this.getUnknownExceptionType().createWithContext(reader, id.toString());
        });
    }

    @Override
    public final <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return context.getSource() instanceof SharedSuggestionProvider
                ? SharedSuggestionProvider.suggestResource(this.getRegistry().keySet(), builder)
                : Suggestions.empty();
    }

    protected abstract Registry<T> getRegistry();

    protected abstract DynamicCommandExceptionType getUnknownExceptionType();
}
