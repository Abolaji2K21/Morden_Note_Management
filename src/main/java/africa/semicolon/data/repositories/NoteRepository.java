package africa.semicolon.data.repositories;

import africa.semicolon.data.model.Note;
import africa.semicolon.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    Boolean existsByTitle (String note);
    Note findByUsername(String username);

}
