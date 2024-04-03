package africa.semicolon.noteException;

public class UserNotFoundException extends BigNoteManagementException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
