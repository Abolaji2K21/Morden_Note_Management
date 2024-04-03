package africa.semicolon.dtos.requests;

import lombok.Data;

@Data
public class EditNoteRequest {
    private String title;
    private String content;
    private String username;
}
