package com.lessons.to_do.DAO;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DatabaseConnector {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public NamedParameterJdbcTemplate getConnect(){
        return namedParameterJdbcTemplate;
    }


}
