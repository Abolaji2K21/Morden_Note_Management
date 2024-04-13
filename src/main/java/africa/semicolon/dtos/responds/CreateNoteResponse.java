package africa.semicolon.dtos.responds;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CreateNoteResponse {
    private String NoteId;
    private String title;
    private String content;
    private LocalDateTime dateCreated = LocalDateTime.now();
    private String Category;
}
