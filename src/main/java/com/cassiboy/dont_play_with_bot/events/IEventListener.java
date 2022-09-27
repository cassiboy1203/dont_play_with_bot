package com.cassiboy.dont_play_with_bot.events;

import discord4j.core.event.domain.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Mono;

public interface IEventListener <T extends Event> {

    Logger LOGGER = LogManager.getLogger(IEventListener.class);

    Class<T> getEventType();
    Mono<Void> execute(T event);

    default Mono<Void> handleError(Throwable error) {
        LOGGER.error("Unable to process " + getEventType().getSimpleName(), error);
        return Mono.empty();
    }
}
