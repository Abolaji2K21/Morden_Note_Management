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

@Service
public interface NoteService {
    CreateNoteResponse writeNote (CreateNoteRequest createNoteRequest) ;

    EditNoteResponse editNote(EditNoteRequest editNoteRequest);

    Note findNoteBy(String username);

    User findUserBy(String username);

    DeleteNoteResponse deleteNote(DeleteNoteRequest deleteNoteRequest);


}
