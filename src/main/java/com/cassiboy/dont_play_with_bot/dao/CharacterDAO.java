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
        jdbcTemplate.update("INSERT INTO Characters(name, class, server) VALUES(?,?,?)",name, characterClass, server);
    }
}
