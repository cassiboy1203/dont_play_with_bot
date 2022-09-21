package com.cassiboy.dont_play_with_bot.events;

import com.cassiboy.dont_play_with_bot.commands.ISlashCommand;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

@Service
public class SlashCommandListener implements IEventListener<ChatInputInteractionEvent>{
    @Override
    public Class<ChatInputInteractionEvent> getEventType() {
        return ChatInputInteractionEvent.class;
    }

    private final Collection<ISlashCommand> commands;

    public SlashCommandListener(List<ISlashCommand> slashCommands) {
        commands = slashCommands;
    }

    @Override
    public Mono<Void> execute(ChatInputInteractionEvent event) {
        //Convert our list to a flux that we can iterate through
        return Flux.fromIterable(commands)
                //Filter out all commands that don't match the name this event is for
                .filter(command -> command.getName().equals(event.getCommandName()))
                //Get the first (and only) item in the flux that matches our filter
                .next()
                //Have our command class handle all logic related to its specific command.
                .flatMap(command -> command.handle(event));
    }
}
