package com.cassiboy.dont_play_with_bot.dao.RowMappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StringRowMapper implements RowMapper<String> {
    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString(1);
    }
}
