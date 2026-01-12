import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import ru.codes.nord.enums.Action;
import ru.codes.nord.enums.Result;

import static utils.ErrorMessageConstants.*;
import static utils.TokenUtil.generateUniqueToken;
import static utils.WireMockStubs.*;

public class ActionTest extends BaseTest {
    @AfterEach
    void rest() {
        wireMockServer.resetAll();
    }

    @Test
    @Tag("Positive")
    @DisplayName("Успешное выполнение экшена после логина")
    @Description("Проверка выполнения экшена с использованием активного токена")
    @Severity(SeverityLevel.CRITICAL)
    @Story("/endpoint ACTION Action")
    @Feature("/endpoint API")
    void successActionTest() {
        // given
        mockAuthMethodSuccess(wireMockServer);
        mockActionMethodSuccess(wireMockServer);
        var token = generateUniqueToken();

        // when
        endpointClient.sendRequest(Action.LOGIN, token);
        var result = endpointClient.sendRequest(Action.ACTION, token);

        // then
        assertStep.verifyResultEquals(Result.OK, result.getResult());
        assertStep.verifyMessageEquals(null, result.getMessage());
    }

    @Test
    @Tag("Positive")
    @DisplayName("Успешное выполнение серии экшенов после логина")
    @Description("Проверка выполнения серии экшенов с использованием активного токена")
    @Severity(SeverityLevel.NORMAL)
    @Story("/endpoint ACTION Action")
    @Feature("/endpoint API")
    void successSeriesOfActionsTest() {
        // given
        mockAuthMethodSuccess(wireMockServer);
        mockActionMethodSuccess(wireMockServer);
        var token = generateUniqueToken();

        // when
        endpointClient.sendRequest(Action.LOGIN, token);
        endpointClient.sendRequest(Action.ACTION, token);
        var result = endpointClient.sendRequest(Action.ACTION, token);

        // then
        assertStep.verifyResultEquals(Result.OK, result.getResult());
        assertStep.verifyMessageEquals(null, result.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Неуспешное выполнение экшена без логина")
    @Description("Проверка выполнения экшена с использованием неактивного токена")
    @Severity(SeverityLevel.CRITICAL)
    @Story("/endpoint ACTION Action")
    @Feature("/endpoint API")
    void inactiveTokenFailActionTest() {
        // given
        mockActionMethodSuccess(wireMockServer);
        var token = generateUniqueToken();

        // when
        var result = endpointClient.sendRequest(Action.ACTION, token);

        // then
        assertStep.verifyResultEquals(Result.ERROR, result.getResult());
        assertStep.verifyMessageEquals(String.format(TOKEN_NOT_FOUND_ERROR, token), result.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Ошибка выполнения экшена, полученная от внешней системы")
    @Description("Проверка поведения системы при получении ошибки из внешней системы")
    @Severity(SeverityLevel.NORMAL)
    @Story("/endpoint ACTION Action")
    @Feature("/endpoint API")
    void externalFailActionTest() {
        // given
        mockAuthMethodSuccess(wireMockServer);
        mockActionMethodFailed(wireMockServer);
        var token = generateUniqueToken();

        // when
        endpointClient.sendRequest(Action.LOGIN, token);
        var result = endpointClient.sendRequest(Action.ACTION, token);

        // then
        assertStep.verifyResultEquals(Result.ERROR, result.getResult());
        assertStep.verifyMessageEquals(INTERNAL_SERVER_ERROR, result.getMessage());
    }
}