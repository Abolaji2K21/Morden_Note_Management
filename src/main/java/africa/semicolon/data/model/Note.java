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
    private String id;
    private String title;
    private String content;
    private LocalDateTime dateTimeCreated;
    private String userId;
    private LocalDateTime dateTimeUpdated;
    @DBRef
    private Category category;
    @Override
    public String toString() {
        return "Note {" +
                "id=" + id + ", title=" + title + ", content=" + content + ", dateTimeCreated=" + dateTimeCreated + ", userId=" + userId + ", dateTimeUpdated=" + dateTimeUpdated + ", category=" + category + "      " +
                "    }";
    }

}