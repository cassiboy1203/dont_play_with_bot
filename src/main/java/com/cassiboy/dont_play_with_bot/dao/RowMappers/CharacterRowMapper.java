package com.cassiboy.dont_play_with_bot.dao.RowMappers;

import com.cassiboy.dont_play_with_bot.dto.PlayerCharacter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CharacterRowMapper implements RowMapper<PlayerCharacter> {
    @Override
    public PlayerCharacter mapRow(ResultSet rs, int rowNum) throws SQLException {
        var character = new PlayerCharacter();
        character.setName(rs.getString("name"));
        character.setCharClass(rs.getString("class"));
        character.setServer(rs.getString("server"));
        character.setNumberOfReports(rs.getInt("reportCount"));
        return character;
    }
}
