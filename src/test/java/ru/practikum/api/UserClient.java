package ru.practikum.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.given;
import static ru.practikum.api.Endpoints.*;

public class UserClient {

    public static User randomUser() {
        String s = RandomStringUtils.randomAlphanumeric(10);
        return new User("auto_" + s + "@yandex.ru", "Pwd" + s, "User" + s);
    }

    public static io.restassured.response.ValidatableResponse register(User u) {
        return given().filter(new AllureRestAssured())
                .baseUri(BASE)
                .contentType(ContentType.JSON)
                .body(u)
                .post(REGISTER)
                .then();
    }

    public static Response login(User u) {
        return given().filter(new AllureRestAssured())
                .baseUri(BASE)
                .contentType(ContentType.JSON)
                .body(new LoginRequest(u.email, u.password))
                .post(LOGIN);
    }

    public static String extractAccessToken(Response resp) {
        try {
            return resp.then().extract().path("accessToken");
        } catch (Exception e) {
            return null;
        }
    }

    public static io.restassured.response.ValidatableResponse delete(String accessToken) {
        return given().filter(new AllureRestAssured())
                .baseUri(BASE)
                .header("Authorization", accessToken)
                .delete(USER)
                .then();
    }
}