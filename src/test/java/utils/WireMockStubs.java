package utils;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockStubs {

    private static final String AUTH_ENDPOINT = "/auth";
    private static final String ACTION_ENDPOINT = "/doAction";

    public static void mockAuthMethodSuccess(WireMockServer wireMockServer) {
        wireMockServer.stubFor(
                post(urlEqualTo(AUTH_ENDPOINT))
                        .willReturn(aResponse().withStatus(200).withBody("{}"))
        );
    }

    public static void mockAuthMethodFailed(WireMockServer wireMockServer) {
        wireMockServer.stubFor(
                post(urlEqualTo(AUTH_ENDPOINT))
                        .willReturn(aResponse().withStatus(400).withBody("{}"))
        );
    }

    public static void mockActionMethodSuccess(WireMockServer wireMockServer) {
        wireMockServer.stubFor(
                post(urlEqualTo(ACTION_ENDPOINT))
                        .willReturn(aResponse().withStatus(200).withBody("{}"))
        );
    }

    public static void mockActionMethodFailed(WireMockServer wireMockServer) {
        wireMockServer.stubFor(
                post(urlEqualTo(ACTION_ENDPOINT))
                        .willReturn(aResponse().withStatus(400).withBody("{}"))
        );
    }
}
