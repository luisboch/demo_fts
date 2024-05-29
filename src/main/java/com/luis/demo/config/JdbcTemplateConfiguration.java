package com.luis.demo.config;

import com.luis.demo.config.MultipleDataSourceProperties.DataSourceProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@Import(MultipleDataSourceProperties.class)
public class JdbcTemplateConfiguration {

    MultipleDataSourceProperties datasource;

    public JdbcTemplateConfiguration(MultipleDataSourceProperties datasource) {
        this.datasource = datasource;
    }

    @Bean
    @Qualifier("main")
    public NamedParameterJdbcTemplate getMainDataSource() {
        return new NamedParameterJdbcTemplate(new JdbcTemplate(createDataSource(datasource.getMain())));
    }

    @Bean
    @Qualifier("replica")
    public NamedParameterJdbcTemplate getReplicaDataSource() {
        return new NamedParameterJdbcTemplate(new JdbcTemplate(createDataSource(datasource.getReplica())));
    }

    private DataSource createDataSource(DataSourceProperties source) {
        return DataSourceBuilder.create()
                .url(source.getUrl())
                .username(source.getUsername())
                .driverClassName(source.getDriverClassName())
                .password(source.getPassword())
                .build();
    }
}
