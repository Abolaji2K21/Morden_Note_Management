package africa.semicolon.noteException;

public class InvalidPassCodeException extends BigNoteManagementException {
    public InvalidPassCodeException(String message) {
        super(message);
    }
}
