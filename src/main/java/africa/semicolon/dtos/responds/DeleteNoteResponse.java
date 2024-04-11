package africa.semicolon.dtos.responds;

import africa.semicolon.data.model.Note;
import lombok.Data;

@Data
public class  DeleteNoteResponse {
    private String noteId;
    private boolean deleted;
    private String username;

}
