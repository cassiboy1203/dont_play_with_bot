package com.cassiboy.dont_play_with_bot.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import reactor.core.publisher.Mono;

public interface ISlashCommand {

    default String getStringValue(ChatInputInteractionEvent event, String name, String other){
        return event.getOption(name)
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .orElse(other);
    }

    String getName();
    Mono<Void> handle(ChatInputInteractionEvent event);
}
