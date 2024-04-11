package africa.semicolon.dtos.responds;

import lombok.Data;

@Data
public class EditNoteResponse {
    private String noteId;
    private String dateUpdated;
    private String title;
    private String content;
    private String category;


}
