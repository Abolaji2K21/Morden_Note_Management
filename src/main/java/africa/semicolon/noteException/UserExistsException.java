package africa.semicolon.noteException;

public class UserExistsException extends BigNoteManagementException {
    public UserExistsException(String message) {
        super(message);
    }
}
