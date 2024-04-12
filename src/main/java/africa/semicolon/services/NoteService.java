
package africa.semicolon.services;


import africa.semicolon.data.model.Note;
import africa.semicolon.data.model.User;
import africa.semicolon.dtos.requests.CreateNoteRequest;
import africa.semicolon.dtos.requests.DeleteNoteRequest;
import africa.semicolon.dtos.requests.EditNoteRequest;
import africa.semicolon.dtos.responds.CreateNoteResponse;
import africa.semicolon.dtos.responds.DeleteNoteResponse;
import africa.semicolon.dtos.responds.EditNoteResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface NoteService {
    CreateNoteResponse createNoteForUser(CreateNoteRequest createNoteRequest);

    EditNoteResponse editNoteForUser(EditNoteRequest editNoteRequest);

    DeleteNoteResponse deleteNoteForUser(DeleteNoteRequest deleteNoteRequest);

    List<Note> getAllNoteByCategory(String userId, String category);

    Optional<Note> getAllNotesByUserId(String userId);

    User findUserBy(String username);


}
