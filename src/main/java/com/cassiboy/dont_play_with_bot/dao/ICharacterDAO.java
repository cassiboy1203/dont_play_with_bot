package com.cassiboy.dont_play_with_bot.dao;

import com.cassiboy.dont_play_with_bot.dto.PlayerCharacter;

import java.util.List;

public interface ICharacterDAO {
    void register(String name, String characterClass, String server, String stronghold, String reason);
    boolean checkIfCharacterIsInList(String name);
    List<PlayerCharacter> getCharactersInList(String charClass, String server, String stronghold);
    List<String> getReasons(String character);
}
