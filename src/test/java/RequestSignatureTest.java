import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import ru.codes.nord.enums.Action;
import ru.codes.nord.enums.Result;

import static utils.ErrorMessageConstants.*;
import static utils.TokenUtil.generateUniqueToken;

public class RequestSignatureTest extends BaseTest {

    @Test
    @Tag("Negative")
    @DisplayName("Параметр action уходит с невалидным значением")
    @Description("Проверка поведения системы при невалидном значении параметра action")
    @Severity(SeverityLevel.NORMAL)
    @Story("/endpoint Request Signatures Validation")
    @Feature("/endpoint API")
    void invalidActionFail() {
        // given
        var token = generateUniqueToken();

        // when
        var result = endpointClient.sendRequest(Action.TEST, token);

        // then
        assertStep.verifyResultEquals(Result.ERROR, result.getResult());
        assertStep.verifyMessageEquals(String.format(INVALID_ACTION_ERROR, Action.TEST), result.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Хедер X-Api-Key отсутствует в запросе")
    @Description("Проверка поведения системы при отсутствии обязательного хедера X-Api-Key")
    @Severity(SeverityLevel.CRITICAL)
    @Story("/endpoint Request Signatures Validation")
    @Feature("/endpoint API")
    void missingHeaderFail() {
        // given
        var token = generateUniqueToken();

        // when
        var result = endpointClient.sendRequestWithoutHeader(Action.LOGIN, token);

        // then
        assertStep.verifyResultEquals(Result.ERROR, result.getResult());
        assertStep.verifyMessageEquals(MISSING_INVALID_KEY_ERROR, result.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Невалидный X-Api-Key хедер в запросе")
    @Description("Проверка поведения системы при невалидном значении обязательного хедера X-Api-Key")
    @Severity(SeverityLevel.CRITICAL)
    @Story("/endpoint Request Signatures Validation")
    @Feature("/endpoint API")
    void invalidHeaderFail() {
        // given
        var token = generateUniqueToken();
        var xApiKey = "invalIDKey";

        // when
        var result = endpointClient.sendRequestWithInvalidHeader(Action.LOGIN, token, xApiKey);

        // then
        assertStep.verifyResultEquals(Result.ERROR, result.getResult());
        assertStep.verifyMessageEquals(MISSING_INVALID_KEY_ERROR, result.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Параметр token отсутствует в запросе")
    @Description("Проверка поведения системы при отсутствии обязательного параметра token")
    @Severity(SeverityLevel.CRITICAL)
    @Story("/endpoint Request Signatures Validation")
    @Feature("/endpoint API")
    void missingTokenFail() {
        // when
        var result = endpointClient.sendRequestWithoutToken(Action.LOGIN);

        // then
        assertStep.verifyResultEquals(Result.ERROR, result.getResult());
        assertStep.verifyMessageEquals(NULL_TOKEN_ERROR, result.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Параметр action отсутствует в запросе")
    @Description("Проверка поведения системы при отсутствии обязательного параметра action")
    @Severity(SeverityLevel.CRITICAL)
    @Story("/endpoint Request Signatures Validation")
    @Feature("/endpoint API")
    void missingActionFail() {
        // given
        var token = generateUniqueToken();

        // when
        var result = endpointClient.sendRequestWithoutAction(token);

        // then
        assertStep.verifyResultEquals(Result.ERROR, result.getResult());
        assertStep.verifyMessageEquals(String.format(INVALID_ACTION_ERROR, null), result.getMessage());
    }
}