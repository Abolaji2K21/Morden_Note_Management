package africa.semicolon.dtos.responds;

import lombok.Data;

@Data
public class CreateNoteResponse {
    private String NoteId;
    private String title;
    private String content;
    private String dateCreated;
    private String Category;
}
