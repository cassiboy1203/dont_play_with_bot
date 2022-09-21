package com.cassiboy.dont_play_with_bot.commands;

import com.cassiboy.dont_play_with_bot.dao.ICharacterDAO;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetCharacterCommand implements ISlashCommand{

    private ICharacterDAO characterDAO;

    @Autowired
    public void setCharacterDAO(ICharacterDAO characterDAO) {
        this.characterDAO = characterDAO;
    }

    @Override
    public String getName() {
        return "getcharacter";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        var name = getStringValue(event, "character", null);

        if (characterDAO.checkIfCharacterIsInList(name)){
            return event.reply(name + " is in the do not play with list.")
                    .withEphemeral(true);
        }
        return event.reply(name + " is not in the do not play with list.")
                .withEphemeral(true);
    }
}
