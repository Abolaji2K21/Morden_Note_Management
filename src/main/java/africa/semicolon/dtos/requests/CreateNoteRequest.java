package africa.semicolon.dtos.requests;

import lombok.Data;

@Data
public class CreateNoteRequest {
    private String userId;
    private String username;

    private String category;
    private String title;
    private String content;
}
