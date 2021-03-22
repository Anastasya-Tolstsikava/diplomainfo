package by.bntu.diplomainformationproject.user.service.exception;

public class IsNotConfirmedEmail extends RuntimeException {
    public IsNotConfirmedEmail(String message) {
        super(message);
    }

    public IsNotConfirmedEmail(String message, Throwable cause) {
        super(message, cause);
    }
}
