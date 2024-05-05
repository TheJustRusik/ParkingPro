package we.hack.securityservice.exception;

public class ApplicationNotFoundException extends RuntimeException {

    public ApplicationNotFoundException(String message) {
        super(message);
    }
}
