package ru.practikum.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;

import static io.restassured.RestAssured.given;

public class UserClient {
    private static final String BASE = "https://stellarburgers.nomoreparties.site";

    public static User randomUser() {
        String s = RandomStringUtils.randomAlphanumeric(10);
        return new User("auto_" + s + "@yandex.ru", "Pwd" + s, "User" + s);
    }

    public static ValidatableResponse register(User u) {
        return given().filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(u)
                .post(BASE + "/api/auth/register")
                .then();
    }

    public static String loginAndGetToken(User u) {
        return given().filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body("{\"email\":\"" + u.email + "\",\"password\":\"" + u.password + "\"}")
                .post(BASE + "/api/auth/login")
                .then().statusCode(200)
                .extract().path("accessToken");
    }

    public static void delete(String accessToken) {
        if (accessToken == null) return;
        given().filter(new AllureRestAssured())
                .header("Authorization", accessToken)
                .delete(BASE + "/api/auth/user")
                .then().statusCode(202);
    }
}