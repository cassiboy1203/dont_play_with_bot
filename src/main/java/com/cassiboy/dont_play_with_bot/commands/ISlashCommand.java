package com.cassiboy.dont_play_with_bot.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import reactor.core.publisher.Mono;

public interface ISlashCommand {
    String getName();
    Mono<Void> handle(ChatInputInteractionEvent event);
}
