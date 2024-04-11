package africa.semicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("Notes")
public class Note {
    @Id
    private String noteId;
    private String userId;
    private String username;


    private String title;
    private String content;
    private String category;

    private LocalDateTime dateTimeCreated;
    private LocalDateTime dateTimeUpdated;

}