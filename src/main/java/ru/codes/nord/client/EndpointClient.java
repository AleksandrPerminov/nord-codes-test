package ru.codes.nord.client;

import io.qameta.allure.Allure;
import io.restassured.http.ContentType;
import io.qameta.allure.restassured.AllureRestAssured;
import ru.codes.nord.enums.Action;
import ru.codes.nord.response.EndpointResponse;

import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public class EndpointClient {
    private static final Logger log = Logger.getLogger(EndpointClient.class.getName());

    private final String ENDPOINT = "/endpoint";
    private final String X_API_HEADER_NAME = "X-Api-Key";
    private final String X_API_HEADER_VALUE = "qazWSXedc";
    private final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private final String TOKEN_PARAM_NAME = "token";
    private final String TOKEN_PARAM_ACTION = "action";


    public EndpointResponse sendRequest(Action action, String token) {
        Allure.step(String.format("Вызов метода метода /endpoint, action = %s, token = %s", action.name(), token));

        var result = given()
                .contentType(ContentType.JSON)
                .header(X_API_HEADER_NAME, X_API_HEADER_VALUE)
                .contentType(CONTENT_TYPE)
                .formParam(TOKEN_PARAM_NAME, token)
                .formParam(TOKEN_PARAM_ACTION, action.name())
//                .filter(new AllureRestAssured())
                .when()
                .post(ENDPOINT)
                .as(EndpointResponse.class);

        log.info(result.toString());
        Allure.step("Получен ответ: " + result);
        return result;
    }
}