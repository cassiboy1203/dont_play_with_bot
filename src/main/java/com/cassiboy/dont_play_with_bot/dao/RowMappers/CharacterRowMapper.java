package com.cassiboy.dont_play_with_bot.dao.RowMappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CharacterRowMapper implements RowMapper<Character> {
    @Override
    public Character mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
}
