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

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setCharacterRowMapper(RowMapper<PlayerCharacter> characterRowMapper) {
        this.characterRowMapper = characterRowMapper;
    }

    @Override
    public void register(String name, String characterClass, String server) {
        jdbcTemplate.update("EXECUTE SP_ADD_CHARACTER @name=?, @class=?, @server=?",name, characterClass, server);
    }

    @Override
    public boolean checkIfCharacterIsInList(String name) {
        return jdbcTemplate.queryForObject("DECLARE @isInList BIT " +
                "EXECUTE SP_CHECK_CHARACTER @name = ?, @isInList = @isInList OUTPUT " +
                "SELECT @isInList", Boolean.class, name);
    }

    @Override
    public List<PlayerCharacter> getCharactersInList(String charClass, String server) {
        return jdbcTemplate.query("EXECUTE SP_GET_USERS_IN_LIST @class = ?, @server = ?", characterRowMapper , charClass, server);
    }
}
