package com.luis.demo.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "datasource")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MultipleDataSourceProperties {

    private DataSourceProperties main;
    private DataSourceProperties replica;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class DataSourceProperties {
        private String driverClassName;
        private String url;
        private String username;
        private String password;
    }
}
