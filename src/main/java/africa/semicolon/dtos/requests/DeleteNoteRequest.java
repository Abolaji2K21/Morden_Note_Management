package africa.semicolon.dtos.requests;

import lombok.Data;

@Data
public class DeleteNoteRequest {
    public String title;
    public String username;

}
