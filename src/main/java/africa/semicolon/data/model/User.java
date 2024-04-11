package africa.semicolon.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("Users")
@RequiredArgsConstructor
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private LocalDateTime dateCreated = LocalDateTime.now();
    private LocalDateTime dateUpdated = LocalDateTime.now();
    private boolean loggedIn;


    @Override
    public String toString() {
    return "User { " +
            "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username + ", password=" + password +
            "   }";
    }
}
