package com.luis.demo.repository.mapper;

import com.luis.demo.model.entities.Cep;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CepDbMapper implements RowMapper<Cep> {
    @Override
    public Cep mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Cep(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getInt(7),
                rs.getShort(8)
        );
    }
}
