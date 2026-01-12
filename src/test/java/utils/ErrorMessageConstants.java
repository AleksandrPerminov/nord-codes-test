package utils;

public class ErrorMessageConstants {
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String INVALID_TOKEN_ERROR = "token: должно соответствовать \"^[0-9A-F]{32}$\"";
    public static final String EXISTING_TOKEN_ERROR = "Token '%s' already exists";
    public static final String TOKEN_NOT_FOUND_ERROR = "Token '%s' not found";
    public static final String INVALID_ACTION_ERROR = "action: invalid action '%s'. Allowed: LOGIN, LOGOUT, ACTION";
    public static final String MISSING_INVALID_KEY_ERROR = "Missing or invalid API Key";
    public static final String NULL_TOKEN_ERROR = "token: не должно равняться null";
}
