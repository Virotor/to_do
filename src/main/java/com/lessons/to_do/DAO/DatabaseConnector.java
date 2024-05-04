package com.lessons.to_do.DAO;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;


@Component
@RequiredArgsConstructor
public class DatabaseConnector {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public NamedParameterJdbcTemplate getConnect(){
        return namedParameterJdbcTemplate;
    }


}
