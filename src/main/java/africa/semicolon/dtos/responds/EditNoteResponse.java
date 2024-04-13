package africa.semicolon.dtos.responds;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EditNoteResponse {
    private String noteId;
    private LocalDateTime dateUpdated = LocalDateTime.now();
    private String title;
    private String content;
    private String category;


}
