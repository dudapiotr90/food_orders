package com.dudis.foodorders.integration.configuration;


import com.dudis.foodorders.integration.support.AuthenticationTestSupport;
import com.dudis.foodorders.integration.support.TestSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

public abstract class RestAssuredIntegrationTestBase
    extends AbstractIntegrationTest
    implements TestSupport, AuthenticationTestSupport {


    protected static WireMockServer wireMockServer;

    private String jSessionIdValue;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll() {
        wireMockServer = new WireMockServer(
            WireMockConfiguration.wireMockConfig()
                .port(9999)
                .extensions(new ResponseTemplateTransformer(false))
        );
        wireMockServer.start();

    }

    @AfterAll
    static void afterAll() {
        wireMockServer.stop();
    }

//    @BeforeEach
//    void beforeEach() {

//        jSessionIdValue = login("api_user", "test")
//            .and()
//            .cookie("JSESSIONID")
//            .header(HttpHeaders.LOCATION, "http://localhost:%s%s/".formatted(port, basePath))
//            .extract()
//            .cookie("JSESSIONID");
//    }

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void logInto(String login, String password) {
        jSessionIdValue = login(login, password)
            .and()
            .cookie("JSESSIONID")
            .header(HttpHeaders.LOCATION, "http://localhost:%s%s/".formatted(port, basePath))
            .extract()
            .cookie("JSESSIONID");
    }

    @AfterEach
    void afterEach() {
        logout()
            .and()
            .cookie("JSESSIONID", "");
        jSessionIdValue = null;
        wireMockServer.resetAll();
    }

    public RequestSpecification requestSpecification() {
        return restAssuredBase()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
//            .header("x-api-key", RANDOM_API_KEY)
            .cookie("JSESSIONID", jSessionIdValue);
    }

    @Override
    public RequestSpecification requestSpecificationNoAuthentication() {
        return restAssuredBase();
    }

    private RequestSpecification restAssuredBase() {
        return RestAssured
            .given()
            .config(getConfig())
            .basePath(basePath)
            .port(port);
    }

    private RestAssuredConfig getConfig() {
        return RestAssuredConfig
            .config()
            .objectMapperConfig(new ObjectMapperConfig()
                .jackson2ObjectMapperFactory((type, s) -> objectMapper));
    }
}
