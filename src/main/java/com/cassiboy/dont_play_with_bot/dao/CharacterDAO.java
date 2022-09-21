package com.cassiboy.dont_play_with_bot.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CharacterDAO implements ICharacterDAO{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
}
