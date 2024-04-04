package africa.semicolon.services;

import africa.semicolon.data.model.Note;
import africa.semicolon.data.model.User;
import africa.semicolon.data.repositories.NoteRepository;
import africa.semicolon.data.repositories.UserRepository;
import africa.semicolon.dtos.requests.CreateNoteRequest;
import africa.semicolon.dtos.requests.DeleteNoteRequest;
import africa.semicolon.dtos.requests.EditNoteRequest;
import africa.semicolon.dtos.responds.CreateNoteResponse;
import africa.semicolon.dtos.responds.DeleteNoteResponse;
import africa.semicolon.dtos.responds.EditNoteResponse;
import africa.semicolon.noteException.BigNoteManagementException;
import africa.semicolon.noteException.NoteNotFoundExceptionException;
import africa.semicolon.noteException.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static africa.semicolon.utils.Mapper.*;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Override
    public CreateNoteResponse writeNote(CreateNoteRequest createNoteRequest) {
        checkStatus(createNoteRequest.getUsername());

        Note newNote = map(createNoteRequest);
            validate(createNoteRequest);
            Note savedNote = noteRepository.save(newNote);
            return map(savedNote);
    }

    @Override
    public EditNoteResponse editNote(EditNoteRequest editNoteRequest) {
        checkStatus(editNoteRequest.getUsername());

        Note existingNote = findNoteBy(editNoteRequest.getTitle());
        User user = findUserBy(editNoteRequest.getUsername());
        if (!existingNote.getUserId().equals(user.getId())) {
            throw new UserNotFoundException("You are not authorized to edit this note");
        }

        mapEditNoteRequest(editNoteRequest, existingNote);
        Note updatedNote = noteRepository.save(existingNote);

        return mapEditNoteResponse(updatedNote);
    }

    private void checkStatus(String editNoteRequest) {
        String username = editNoteRequest;
        if (!userService.isUserRegistered(username)) {
            throw new BigNoteManagementException("User with username " + username + " is not registered");
        }

        if (!userService.isUserLoggedIn(username)) {
            throw new BigNoteManagementException("User with username " + username + " is not logged in");
        }
    }


    @Override
    public Note findNoteBy(String title) {
        Note note = noteRepository.findBy(title);
        if (note == null) {
            throw new NoteNotFoundExceptionException("Note not found");
        }
        return note;
    }

    @Override
    public User findUserBy(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("Username not found");
        }
        return user;
    }

    @Override
    public DeleteNoteResponse deleteNote(DeleteNoteRequest deleteNoteRequest) {
        checkStatus(deleteNoteRequest.getUsername());
        Note existingNote = findNoteBy(deleteNoteRequest.getTitle());
        User user = findUserBy(deleteNoteRequest.getUsername());
        if (!existingNote.getUserId().equals(user.getId())) {
            throw new UserNotFoundException("You are not authorized to delete this note");
        }

        noteRepository.delete(existingNote);

        return new DeleteNoteResponse();

    }


    private void validate(CreateNoteRequest createNoteRequest) {
        String username = createNoteRequest.getUsername();
        String noteTitle = createNoteRequest.getTitle();
        boolean noteExistsForUser = noteRepository.existsBy(username, noteTitle);
        if (noteExistsForUser) {
            throw new BigNoteManagementException("Note already exists1");
        }
    }

}
