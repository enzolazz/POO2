package exceptions;

public class KeyAlreadyStored extends RuntimeException {
    public KeyAlreadyStored(String message) {
        super(message);
    }
}
