package com.cassiboy.dont_play_with_bot;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.presence.ClientPresence;
import discord4j.rest.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DontPlayWithBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(DontPlayWithBotApplication.class, args);
	}
}
