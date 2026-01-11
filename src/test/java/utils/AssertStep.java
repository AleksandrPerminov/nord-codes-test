package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import ru.codes.nord.enums.Result;


public class AssertStep {

    private final String ERROR_MESSAGE =
            "Результат не совпадает:\n" +
                    "Ожидаемый: %s\n" +
                    "Фактический: %s";

    public void verifyResultEquals(Result expected, Result actual) {
        Allure.step("Проверка статуса ответа", () -> {
            if (!expected.equals(actual)) {
                String message = String.format(
                        ERROR_MESSAGE,
                        expected.name(),
                        actual.name()
                );
                throw new AssertionError(message);
            }
        });
    }

    public void verifyMessageEquals(String expected, String actual) {
        Allure.step("Проверка текста сообщения", () -> {
            String message = String.format(
                    ERROR_MESSAGE,
                    expected,
                    actual
            );
            if (expected == null) {
                if (actual != null) {
                    throw new AssertionError(message);
                }
            } else {
                if (!expected.equals(actual)) {
                    throw new AssertionError(message);
                }
            }
        });
    }
}
