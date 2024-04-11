package africa.semicolon.dtos.requests;

import lombok.Data;

@Data
public class DeleteNoteRequest {
    private String title;
    private String category;
    private String NoteId;
    private String userId;
    private String username;



}
