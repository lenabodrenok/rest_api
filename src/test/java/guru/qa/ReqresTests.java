package guru.qa;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static guru.qa.ReqresEndpoints.*;
import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresTests {

    @BeforeAll
    static void testBase() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    void createTest() {
        String body = "{\"name\": \"morpheus\",\"job\": \"leader\"}";
            given()
                .log().uri()
                .log().body()
                .body(body)
                .contentType(JSON)
                .when()
                .post(listUsersUri)
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void deleteTest() {
        delete(deleteUsersUri)
                .then()
                .statusCode(204);
    }

    @Test
    void registerUnsuccessfulTest() {
        String body = "{\"email\": \"my@email.com\"}";
            given()
                .log().uri()
                .log().body()
                .body(body)
                .contentType(JSON)
                .when()
                .post(loginUsersUri)
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void loginTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", " +
                "\"password\": \"cityslicka\" }";
            given()
                .log().uri()
                .log().body()
                .body(body)
                .contentType(JSON)
                .when()
                .post(loginUsersUri)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void listUsersTest() {
        get(listUsersUri)
                .then()
                .log().body()
                .body("page",is(1))
                .body("per_page", is(6))
                .body("total",is(12))
                .body("total_pages",is(2));
    }
}
