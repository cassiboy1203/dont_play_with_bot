package com.cassiboy.dont_play_with_bot.configuration;

import com.cassiboy.dont_play_with_bot.events.IEventListener;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import discord4j.rest.RestClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BotConfiguration {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Value("${discord.bot.token}")
    private String botToken;

    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient (List<IEventListener<T>> eventListeners){
        var client = DiscordClientBuilder.create(botToken)
                .build()
                .gateway()
                .setInitialPresence(ignore -> ClientPresence.online(ClientActivity.listening("to /commands")))
                .login()
                .block();

        for(var listener : eventListeners) {
            client.on(listener.getEventType())
                    .flatMap(listener::execute)
                    .onErrorResume(listener::handleError)
                    .subscribe();
        }

        return client;
    }

    @Bean
    public RestClient discordRestClient(GatewayDiscordClient client) {
        return client.getRestClient();
    }
}
