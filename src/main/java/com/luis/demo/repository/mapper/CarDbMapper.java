package com.luis.demo.repository.mapper;

import com.luis.demo.model.entities.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarDbMapper implements RowMapper<Car> {
    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Car(
                rs.getLong(1),
                rs.getString(2),
                rs.getLong(3)
        );
    }
}
