package com.cassiboy.dont_play_with_bot.commands;

import com.cassiboy.dont_play_with_bot.dao.ICharacterDAO;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RegisterCommand implements ISlashCommand{

    private ICharacterDAO characterDAO;

    @Autowired
    public void setCharacterDAO(ICharacterDAO characterDAO) {
        this.characterDAO = characterDAO;
    }

    @Override
    public String getName() {
        return "register";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        var name = getStringValue(event, "character", null);
        var characterClass = getStringValue(event, "class", null);
        var server = getStringValue(event, "server", null);

        characterDAO.register(name, characterClass, server);

        return event.reply(name + " has been registered")
                .withEphemeral(true);
    }
}
