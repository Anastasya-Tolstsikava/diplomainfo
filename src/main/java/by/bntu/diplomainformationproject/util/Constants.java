package by.bntu.diplomainformationproject.util;

public class Constants {
    public static final String ENTRY_IS_NOT_FOUND_MSG = "User with id = %d not found";
    public static final String USER_WITH_EMAIL_NOT_FOUND_MSG = "User with email = %s not found";
    public static final String FILE_IS_NOT_FOUND_MSG = "File is not found";


    public static final String INCORRECT_PASSWORD_MSG = "Incorrect login or password";
    public static final String IS_NOT_CONFIRMED_EMAIL = "You can't use this method. Confirm your email";


    //dto validation
    public static final String NOT_NULL_OR_EMPTY_MSG = "can't be null or empty";

    public static final String REGEX_FOR_NAME = "[a-zA-ZА-Яа-я]*";
    public static final String NAME_PATTERN_MSG = "must consist of characters: A-Z, a-z, А-Я, а-я";
    public static final int NAME_MIN_SIZE = 2;
    public static final int NAME_MAX_SIZE = 20;
    public static final String NAME_SIZE_MSG = "must be 2 - 20 characters length";

    public static final String REGEX_FOR_EMAIL = "^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    public static final String EMAIL_PATTERN_MSG = "must match the email pattern";
    public static final String UNIQUE_EMAIL_MSG = "such mail is already registered";

    public static final int PASSWORD_MIN_SIZE = 4;
    public static final int PASSWORD_MAX_SIZE = 30;
    public static final String PASSWORD_SIZE_MSG = "must be 4 - 30 characters length";

    public static final String REGEX_FOR_PASSWORD = "[A-Za-z0-9_]*";
    public static final String PASSWORD_PATTERN_MSG = "must consist of characters A-Z, a-z, 0-9 and '_'";

    //security
    public static final String AUTHORIZATION = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    //email
    public static final String EMAIL_SUBJECT = "Diploma info";

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}
