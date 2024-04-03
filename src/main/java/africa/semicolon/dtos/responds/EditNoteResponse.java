package africa.semicolon.dtos.responds;

import lombok.Data;

@Data
public class EditNoteResponse {
    private String newTitle;
    private String newContent;
}
