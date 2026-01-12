package ru.codes.nord.client;

import io.qameta.allure.Allure;
import io.restassured.specification.RequestSpecification;
import ru.codes.nord.enums.Action;
import ru.codes.nord.response.EndpointResponse;

import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static ru.codes.nord.utils.MaskUtil.maskToken;

public class EndpointClient {
    private static final Logger log = Logger.getLogger(EndpointClient.class.getName());

    private final String ENDPOINT = "/endpoint";
    private final String X_API_HEADER_NAME = "X-Api-Key";
    private final String X_API_HEADER_VALUE = "qazWSXedc";
    private final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private final String TOKEN_PARAM_NAME = "token";
    private final String TOKEN_PARAM_ACTION = "action";


    public EndpointResponse sendRequest(Action action, String token) {
        Allure.step(String.format("Вызов метода /endpoint, action = %s, token = %s", action.name(), maskToken(token)));

        log.info(String.format("Sent request - /endpoint, action = %s, token = %s", action.name(), maskToken(token)));
        var spec = given()
                .header(X_API_HEADER_NAME, X_API_HEADER_VALUE)
                .contentType(CONTENT_TYPE)
                .formParam(TOKEN_PARAM_NAME, token)
                .formParam(TOKEN_PARAM_ACTION, action.name());

        return sendSpec(spec);
    }

    public EndpointResponse sendRequestWithoutHeader(Action action, String token) {
        Allure.step(String.format("Вызов метода /endpoint без X-Api-Key, action = %s, token = %s", action.name(), maskToken(token)));

        log.info(String.format("Sent request - /endpoint, action = %s, token = %s", action.name(), maskToken(token)));
        var spec = given()
                .contentType(CONTENT_TYPE)
                .formParam(TOKEN_PARAM_NAME, token)
                .formParam(TOKEN_PARAM_ACTION, action.name());

        return sendSpec(spec);
    }

    public EndpointResponse sendRequestWithInvalidHeader(Action action, String token, String apiValue) {
        Allure.step(String.format("Вызов метода /endpoint с невалидным X-Api-Key, action = %s, token = %s", action.name(), maskToken(token)));

        log.info(String.format("Sent request - /endpoint, action = %s, token = %s", action.name(), maskToken(token)));
        var spec = given()
                .header(X_API_HEADER_NAME, apiValue)
                .contentType(CONTENT_TYPE)
                .formParam(TOKEN_PARAM_NAME, token)
                .formParam(TOKEN_PARAM_ACTION, action.name());

        return sendSpec(spec);
    }

    public EndpointResponse sendRequestWithoutToken(Action action) {
        Allure.step(String.format("Вызов метода /endpoint с пустым token, action = %s", action.name()));

        log.info(String.format("Sent request - /endpoint, action = %s", action.name()));
        var spec = given()
                .header(X_API_HEADER_NAME, X_API_HEADER_VALUE)
                .contentType(CONTENT_TYPE)
                .formParam(TOKEN_PARAM_ACTION, action.name());

        return sendSpec(spec);
    }

    public EndpointResponse sendRequestWithoutAction(String token) {
        Allure.step(String.format("Вызов метода /endpoint без параметра action, token = %s", maskToken(token)));

        log.info(String.format("Sent request - /endpoint, token = %s", maskToken(token)));
        var spec = given()
                .header(X_API_HEADER_NAME, X_API_HEADER_VALUE)
                .contentType(CONTENT_TYPE)
                .formParam(TOKEN_PARAM_NAME, token);

        return sendSpec(spec);
    }

    private EndpointResponse sendSpec(RequestSpecification req) {

        var result = req
                .when()
                .post(ENDPOINT)
                .as(EndpointResponse.class);

        log.info(result.toString());
        Allure.step("Получен ответ: " + result);
        return result;
    }
}