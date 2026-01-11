package ru.codes.nord.response;

import ru.codes.nord.enums.Result;

public class EndpointResponse {
    private Result result;
    private String message;


    public Result getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"result\": \"" + result  + "\",\n"+
                "\"message:  \"" + message + "\"\n" +
                "}";
    }
}
