package com.luis.demo;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableCaching
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class DemoApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        val context = run(DemoApplication.class, args);
        val env = context.getEnvironment();

        val port = env.getProperty("server.port");
        var contextPath = env.getProperty("server.servlet.context-path");
        if (contextPath == null || contextPath.isEmpty()) {
            contextPath = "/";
        }

        val profiles = env.getProperty("spring.profiles.active");

        LOGGER.info(
                "Running http://localhost:{}{}/swagger-ui/index.html, profiles: {}",
                port,
                contextPath,
                profiles
        );
    }

}
