package utils;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Assert;
import pojo.*;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertNotNull;

public class UserManagerAPI {
    private static final String BASE_URI = "https://stellarburgers.education-services.ru";
    private static final String REGISTER = "/api/auth/register";
    private static final String LOGIN = "/api/auth/login";
    private static final String USER = "/api/auth/user";

    private Response lastResponse;
    private String lastEmail;
    private String lastPassword;
    private String lastAccessToken;

    public UserManagerAPI() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    // Методы запросов к API

    @Step("Создание пользователя")
    public UserRegisterResponse createTestUser() {
        this.lastEmail = RandomGenerator.generateEmail();
        this.lastPassword = RandomGenerator.generateString();
        String name = RandomGenerator.generateString();

        UserRegisterRequest request = new UserRegisterRequest(this.lastEmail, this.lastPassword, name);

        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .body(request)
                .when()
                .post(REGISTER);
        response.then().statusCode(SC_OK);

        UserRegisterResponse resp = response.body().as(UserRegisterResponse.class);
        this.lastAccessToken = resp.getAccessToken();
        return resp;
    }

    @Step("Авторизуемся по заданным учётным данным")
    public void loginUser(String email, String password) {
        LoginUserRequest loginUserRequest = new LoginUserRequest(email, password);
        this.lastResponse = RestAssured.given()
                .header("Content-type", "application/json")
                .body(loginUserRequest)
                .when()
                .post(LOGIN);
        if (this.lastResponse.statusCode() == SC_OK) {
            saveLastAccessToken();
        }
    }

    @Step("Удаляем пользователя по учётным данным")
    public void deleteTestUser(String email, String password) {
        loginUser(email, password);
        if (this.lastResponse.statusCode() != SC_OK) {
            return;
        }
        deleteTestUserBySavedToken();
        checkResponseSC(SC_ACCEPTED);
    }

    @Step("Удаляем созданного тестового пользователя. По возможности, используем ранее сохранённый токену, если он есть")
    public void deleteTestUserBySavedTokenIfCan(String email, String password) {
        if (this.lastAccessToken != null) {
            // Если для тестов использовали вход по API, у нас уже может быть сохранённый токен и можно использовать его
            deleteTestUserBySavedToken();
            return;
        }
        // Если сохранённого токена нет, удаляем тестового пользователя по его учётным данным
        deleteTestUser(email, password);
    }

    @Step("Удаляем созданного тестового пользователя. Используем сохранённый токен")
    public void deleteTestUserBySavedToken() {
        deleteTestUser(this.lastAccessToken);
    }

    @Step("Удаляем пользователя")
    public void deleteTestUser(String token) {
        Assert.assertNotNull(token);
        this.lastResponse = RestAssured.given()
                .header("Authorization", token)
                .when()
                .delete(USER);
    }

    // Вспомогательные методы

    @Step("Проверяем код ответа")
    public void checkResponseSC(int sc) {
        assertNotNull(this.lastResponse);
        this.lastResponse.then().statusCode(sc);
    }

    @Step("Сохраняем последний AccessToken")
    public void saveLastAccessToken() {
        assertNotNull(this.lastResponse);
        this.lastAccessToken = this.lastResponse.body().as(LoginUserResponse.class).getAccessToken();
    }

    @Step("Получаем последний использованный для регистрации Email")
    public String getLastEmail() {
        return this.lastEmail;
    }

    @Step("Получаем последний использованный для регистрации пароль")
    public String getLastPassword() {
        return this.lastPassword;
    }
}
