package com.luis.demo;

import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.testcontainers.containers.BindMode.READ_ONLY;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BaseIntegrationTests {

    protected ClientAndServer mockServer;


    @Autowired
    protected MockMvc mockMvc;

    @SuppressWarnings("resource")
    protected static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withPassword("password")
            .withUsername("user")
            .withClasspathResourceMapping("00_init.sql", "/docker-entrypoint-initdb.d/00_init.sql", READ_ONLY);

    protected static RedisContainer redis = new RedisContainer(DockerImageName.parse("redis:6.2.6"));


    @BeforeAll
    static void beforeAll() {
        postgres.start();
        redis.start();

        // Apply to system properties
        System.setProperty("spring.data.redis.host", redis.getRedisHost());
        System.setProperty("spring.data.redis.port", "" + redis.getRedisPort());
        System.setProperty("datasource.main.url", postgres.getJdbcUrl());
        System.setProperty("datasource.replica.url", postgres.getJdbcUrl());
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
        redis.stop();
    }

    @BeforeEach
    public void startMockServer() {
        mockServer = startClientAndServer(8551);
    }

    @AfterEach
    public void stopMockServer() {
        mockServer.stop();
    }
}
