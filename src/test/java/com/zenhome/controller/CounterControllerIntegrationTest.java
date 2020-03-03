package com.zenhome.controller;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import com.zenhome.Application;
import com.zenhome.domain.ConsumptionDTO;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@ActiveProfiles(profiles = "integration-test")
@Sql(scripts = "classpath:insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CounterControllerIntegrationTest {

    private static final String BASE_URL = "http://localhost";

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = port;
    }

    @Test
    public void create_valid_consumption() {
        ConsumptionDTO consumptionDTO = ConsumptionDTO.builder()
                .amount(new BigDecimal("1230.00"))
                .counterId(1)
                .build();

        given()
                .log().all()
                .body(consumptionDTO)
                .header("content-type", "application/json")
                .when()
                .post(BASE_URL + "/counter_callback")
                .prettyPeek()
                .then()
                .statusCode(201)
                .body("counter_id", equalTo(1));

    }


    @Test
    public void return_counter_by_id_when_exist() {
        when()
                .get(BASE_URL + "/counters/1")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("id", equalTo("1"))
                .body("village.id", equalTo("1"))
                .body("village.name", equalTo("Villarriba"));
    }

    @Test
    public void return_404_counter_by_id_when_not_exist() {
        when()
                .get(BASE_URL + "/counters/100")
                .prettyPeek()
                .then()
                .statusCode(404);
    }

    @Test
    public void return_400_counter_by_id_when_invalid_id() {
        when()
                .get(BASE_URL + "/counters/abcd")
                .prettyPeek()
                .then()
                .statusCode(400);
    }

    @Test
    public void last_24h_consumption_invalid_duration() {
        when()
                .get(BASE_URL + "/consumption_reports?duration=24d")
                .prettyPeek()
                .then()
                .statusCode(400);
    }

    @Test
    public void last_24h_consumption_invalid_format() {
        when()
                .get(BASE_URL + "/consumption_reports?duration=abcd")
                .prettyPeek()
                .then()
                .statusCode(400);
    }


}
