package africa.semicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document("categories")
public class Category {
    @Id
    private String id;
    @DBRef
    private List<Note> notes;
    private String description;
    private String username;

}
