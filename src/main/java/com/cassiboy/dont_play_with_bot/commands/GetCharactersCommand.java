package com.cassiboy.dont_play_with_bot.commands;

import com.cassiboy.dont_play_with_bot.dao.ICharacterDAO;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.spec.EmbedCreateSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class GetCharactersCommand implements ISlashCommand {

    private ICharacterDAO characterDAO;

    @Autowired
    public void setCharacterDAO(ICharacterDAO characterDAO) {
        this.characterDAO = characterDAO;
    }

    @Override
    public String getName() {
        return "getcharacters";
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        var charClass = getStringValue(event, "class", null);
        var server = getStringValue(event, "server", null);
        var stronghold = getStringValue(event, "stronghold", null);

        var characters = characterDAO.getCharactersInList(charClass, server, stronghold);

        var embeds = new ArrayList<EmbedCreateSpec>();
        for (var character: characters){
            var embedBuilder = EmbedCreateSpec.builder()
                    .title(character.getName())
                    .addField("class", character.getCharClass() == null ? "none": character.getCharClass(), true)
                    .addField("server", character.getServer() == null ? "none": character.getServer(), true)
                    .addField("Number of reports", String.valueOf(character.getNumberOfReports()), true)
                    .addField("stronghold", character.getStronghold(), true);

            embeds.add(embedBuilder.build());
        }

        if (embeds.isEmpty()){
            return event.reply("There are no characters with the selected filters registered.")
                    .withEphemeral(true);
        }

        return event.reply()
                .withEmbeds(embeds)
                .withEphemeral(true);
    }
}
