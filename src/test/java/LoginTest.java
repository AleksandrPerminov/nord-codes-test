import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import ru.codes.nord.enums.Action;
import ru.codes.nord.enums.Result;

import static utils.ErrorMessageConstants.*;
import static utils.TokenUtil.generateUniqueToken;
import static utils.WireMockStubs.mockAuthMethodFailed;
import static utils.WireMockStubs.mockAuthMethodSuccess;

public class LoginTest extends BaseTest {
    @AfterEach
    void rest() {
        wireMockServer.resetAll();
    }

    @Test
    @Tag("Positive")
    @DisplayName("Успешный вход в систему")
    @Description("Проверка успешного входа в систему с использованием корректного токена")
    @Severity(SeverityLevel.CRITICAL)
    @Story("/endpoint LOGIN Action")
    @Feature("/endpoint API")
    void successLoginTest() {
        // given
        mockAuthMethodSuccess(BaseTest.wireMockServer);
        var token = generateUniqueToken();

        // when
        var result = endpointClient.sendRequest(Action.LOGIN, token);

        // then
        assertStep.verifyResultEquals(Result.OK, result.getResult());
        assertStep.verifyMessageEquals(null, result.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Ошибка входа в систему при повторном использовании активного токена")
    @Description("Проверка получения ошибки при повторном входе в систему с использованием корректного активного токена")
    @Severity(SeverityLevel.NORMAL)
    @Story("/endpoint LOGIN Action")
    @Feature("/endpoint API")
    void existingTokenFailLoginTest() {
        // given
        mockAuthMethodSuccess(BaseTest.wireMockServer);
        var token = generateUniqueToken();

        // when
        endpointClient.sendRequest(Action.LOGIN, token);
        var result = endpointClient.sendRequest(Action.LOGIN, token);

        // then
        assertStep.verifyResultEquals(Result.ERROR, result.getResult());
        assertStep.verifyMessageEquals(String.format(EXISTING_TOKEN_ERROR, token), result.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Ошибка входа в систему, полученная от внешней системы")
    @Description("Проверка поведения системы при получении ошибки из внешней системы")
    @Severity(SeverityLevel.NORMAL)
    @Story("/endpoint LOGIN Action")
    @Feature("/endpoint API")
    void externalFailLoginTest() {
        // given
        mockAuthMethodFailed(BaseTest.wireMockServer);
        var token = generateUniqueToken();

        // when
        var result = endpointClient.sendRequest(Action.LOGIN, token);

        // then
        assertStep.verifyResultEquals(Result.ERROR, result.getResult());
        assertStep.verifyMessageEquals(INTERNAL_SERVER_ERROR, result.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Ошибка входа в систему при использовании невалидного токена")
    @Description("Проверка получения ошибки при входе в систему с использованием невалидного токена")
    @Severity(SeverityLevel.CRITICAL)
    @Story("/endpoint LOGIN Action")
    @Feature("/endpoint API")
    void invalidTokenFailLoginTest() {
        // given
        var invalidToken = "A1B2C3D4E5F6G7H8I9J0K";

        // when
        var result = endpointClient.sendRequest(Action.LOGIN, invalidToken);

        // then
        assertStep.verifyResultEquals(Result.ERROR, result.getResult());
        assertStep.verifyMessageEquals(INVALID_TOKEN_ERROR, result.getMessage());
    }
}