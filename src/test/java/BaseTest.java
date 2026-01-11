import com.github.tomakehurst.wiremock.WireMockServer;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.codes.nord.client.EndpointClient;
import utils.AssertStep;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@ExtendWith(AllureJunit5.class)
public abstract class BaseTest {
    protected static WireMockServer wireMockServer;
    protected static EndpointClient endpointClient;
    protected AssertStep assertStep = new AssertStep();

    @BeforeAll
    static void init() {
        RestAssured.baseURI = "http://localhost:8080";

        wireMockServer = new WireMockServer(options().port(8888));
        wireMockServer.start();

        endpointClient = new EndpointClient();

    }

    @AfterAll
    static void shutDown () {
        wireMockServer.stop();
    }
}
