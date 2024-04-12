package africa.semicolon.data.repositories;

import africa.semicolon.data.model.Note;
import africa.semicolon.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    Boolean existsByTitleAndUserId(String title, String userId);
    Optional<Note> findNoteByNoteIdAndUserId(String noteId, String userId);
    List<Note> findAllByUserIdAndCategory(String userId, String category);
    Optional<Note> findByUserId(String userId);


}
