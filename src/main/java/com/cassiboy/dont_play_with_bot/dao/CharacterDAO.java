package com.cassiboy.dont_play_with_bot.dao;

import com.cassiboy.dont_play_with_bot.dto.PlayerCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CharacterDAO implements ICharacterDAO{

    private JdbcTemplate jdbcTemplate;
    private RowMapper<PlayerCharacter> characterRowMapper;
    private RowMapper<String> stringRowMapper;

    @Autowired
    public void setStringRowMapper(RowMapper<String> stringRowMapper) {
        this.stringRowMapper = stringRowMapper;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setCharacterRowMapper(RowMapper<PlayerCharacter> characterRowMapper) {
        this.characterRowMapper = characterRowMapper;
    }

    @Override
    public void register(String name, String characterClass, String server, String stronghold, String reason) {
        jdbcTemplate.update("EXECUTE SP_ADD_CHARACTER @name=?, @class=?, @server=?, @stronghold=?, @reason=?",name, characterClass, server, stronghold,reason);
    }

    @Override
    public boolean checkIfCharacterIsInList(String name) {
        return jdbcTemplate.queryForObject("DECLARE @isInList BIT " +
                "EXECUTE SP_CHECK_CHARACTER @name = ?, @isInList = @isInList OUTPUT " +
                "SELECT @isInList", Boolean.class, name);
    }

    @Override
    public List<PlayerCharacter> getCharactersInList(String charClass, String server, String stronghold) {
        return jdbcTemplate.query("EXECUTE SP_GET_USERS_IN_LIST @class = ?, @server = ?, @stronghold=?", characterRowMapper , charClass, server, stronghold);
    }

    @Override
    public List<String> getReasons(String character) {
        return jdbcTemplate.query("EXECUTE SP_GET_REASONS @character = ?", stringRowMapper, character);
    }
}
