package com.cassiboy.dont_play_with_bot.commands;

import com.cassiboy.dont_play_with_bot.dao.ICharacterDAO;
import com.cassiboy.dont_play_with_bot.dto.PlayerCharacter;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

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
        return event.deferReply()
                .withEphemeral(true)
                .then(sendMessage(event))
                .then();
    }

    public Mono<Message> sendMessage(ChatInputInteractionEvent event){
        var charClass = getStringValue(event, "class", null);
        var server = getStringValue(event, "server", null);
        var stronghold = getStringValue(event, "stronghold", null);

        var characters = characterDAO.getCharactersInList(charClass, server, stronghold);

        var embeds = new ArrayList<EmbedCreateSpec>();

        for (var character: characters){
            var reasons = characterDAO.getReasons(character.getName());
            var sb = new StringBuilder();
            sb.append("**Reasons** \n");
            for (var reason: reasons){
                sb.append("- ");
                sb.append(reason);
                sb.append("\n");
            }

            var embedBuilder = EmbedCreateSpec.builder()
                    .title(character.getName())
                    .description(sb.toString())
                    .addField("class", character.getCharClass() == null ? "none": character.getCharClass(), true)
                    .addField("server", character.getServer() == null ? "none": character.getServer(), true)
                    .addField("Number of reports", String.valueOf(character.getNumberOfReports()), true)
                    .addField("stronghold", character.getStronghold(), true);

            embeds.add(embedBuilder.build());

            if (embeds.size() == 10){
                event.createFollowup()
                        .withEmbeds(embeds)
                        .withEphemeral(true)
                        .subscribe();

                embeds = new ArrayList<>();
            }
        }

        if (!embeds.isEmpty()){
            event.createFollowup()
                    .withEmbeds(embeds)
                    .withEphemeral(true)
                    .subscribe();
        }

        if (characters.isEmpty()){
            return event.createFollowup("There are no characters with the selected filters registered.")
                    .withEphemeral(true);
        }

        return event.createFollowup("All characters returned.")
                .withEphemeral(true);
    }
}
