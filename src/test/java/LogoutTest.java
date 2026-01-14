import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import ru.codes.nord.enums.Action;
import ru.codes.nord.enums.Result;

import static utils.ErrorMessageConstants.*;
import static utils.TokenUtil.generateUniqueToken;
import static utils.WireMockStubs.*;

@DisplayName("Проверка /endpoint с акшеном LOGOUT")
@Story("/endpoint LOGOUT Action")
@Feature("/endpoint API")
public class LogoutTest extends BaseTest {
    @AfterEach
    void rest() {
        wireMockServer.resetAll();
    }

    @Test
    @Tag("Positive")
    @DisplayName("Успешное выполнение логаута")
    @Description("Проверка выполнения логаута после активации токена")
    @Severity(SeverityLevel.CRITICAL)
    void successLogoutTest() {
        // given
        mockAuthMethodSuccess(wireMockServer);
        mockActionMethodSuccess(wireMockServer);
        var token = generateUniqueToken();

        // when
        endpointClient.sendRequest(Action.LOGIN, token);
        endpointClient.sendRequest(Action.ACTION, token);
        var resultLogout = endpointClient.sendRequest(Action.LOGOUT, token);

        // then
        assertStep.verifyResultEquals(Result.OK, resultLogout.getResult());
        assertStep.verifyMessageEquals(null, resultLogout.getMessage());

        //when
        var resultAction = endpointClient.sendRequest(Action.ACTION, token);

        // then
        assertStep.verifyResultEquals(Result.ERROR, resultAction.getResult());
        assertStep.verifyMessageEquals(String.format(TOKEN_NOT_FOUND_ERROR, token), resultAction.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Неуспешное выполнение логаута без предварительного логина")
    @Description("Проверка выполнения логаута без активации токена")
    @Severity(SeverityLevel.MINOR)
    void failedLogoutTest() {
        // given
        mockAuthMethodSuccess(wireMockServer);
        var token = generateUniqueToken();

        // when
        var result = endpointClient.sendRequest(Action.LOGOUT, token);

        // then
        assertStep.verifyResultEquals(Result.ERROR, result.getResult());
        assertStep.verifyMessageEquals(String.format(TOKEN_NOT_FOUND_ERROR, token), result.getMessage());
    }

    @Test
    @Tag("Positive")
    @DisplayName("Успешное выполнение экшена после релогина")
    @Description("Проверка выполнения экшена с использованием активного токена после логаута")
    @Severity(SeverityLevel.NORMAL)
    void successReloginTest() {
        // given
        mockAuthMethodSuccess(wireMockServer);
        mockActionMethodSuccess(wireMockServer);
        var token = generateUniqueToken();

        // when
        endpointClient.sendRequest(Action.LOGIN, token);
        endpointClient.sendRequest(Action.LOGOUT, token);
        endpointClient.sendRequest(Action.LOGIN, token);
        var result = endpointClient.sendRequest(Action.ACTION, token);

        // then
        assertStep.verifyResultEquals(Result.OK, result.getResult());
        assertStep.verifyMessageEquals(null, result.getMessage());
    }
}