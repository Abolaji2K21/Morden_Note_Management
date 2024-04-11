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

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public CreateNoteResponse createNoteForUser(CreateNoteRequest createNoteRequest) {
        String userId = createNoteRequest.getUserId();
        User user = findUserBy(userId);
        checkUserStatus(user.getUsername());
        Note newNote = mapNote(createNoteRequest, user);
        validateCreateNoteRequest(createNoteRequest);
        Note savedNote = noteRepository.save(newNote);
        return mapCreateNoteResponse(savedNote);
    }
    
    @Override
    public EditNoteResponse editNoteForUser(EditNoteRequest editNoteRequest) {
        String userId = editNoteRequest.getUserId();
        User user = findUserBy(userId);
        checkUserStatus(user.getUsername());
        Note existingNote = mapNoteForEdit(editNoteRequest, user);
        updateNoteWithEditRequest(editNoteRequest, existingNote);
        return mapEditNoteResponse(existingNote);
    }
    @Override
    public DeleteNoteResponse deleteNoteForUser(DeleteNoteRequest deleteNoteRequest) {
        String userId = deleteNoteRequest.getUserId();
        User user = findUserBy(userId);
        checkUserStatus(user.getUsername());

        String noteId = deleteNoteRequest.getNoteId();
        Optional<Note> existingNoteOptional = noteRepository.findById(noteId);
        if (!existingNoteOptional.isPresent()) {
            throw new NoteNotFoundExceptionException("Note with ID " + noteId + " not found");
        }

        Note existingNote = existingNoteOptional.get();
        if (!existingNote.getUserId().equals(user.getId())) {
            throw new BigNoteManagementException("You are not authorized to delete this note");
        }

        noteRepository.delete(existingNote);

        return new DeleteNoteResponse();
    }

    @Override
    public Optional<Note> getNoteById(String noteId) {
        return noteRepository.findById(noteId);
    }

    @Override
    public Optional<Note> getAllNotesByUserId(String userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        return noteRepository.findByUserId(userId);
    }

    @Override
    public User findUserBy(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found with username: " + username);
        }
        return user;
    }

    private void validateCreateNoteRequest(CreateNoteRequest createNoteRequest) {
        String userId = createNoteRequest.getUserId();
        User user = findUserBy(userId);
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new BigNoteManagementException("Username cannot be null or empty");
        }

        String noteTitle = createNoteRequest.getTitle();
        boolean noteExistsForUser = noteRepository.existsByTitle(noteTitle);
        if (noteExistsForUser) {
            throw new BigNoteManagementException("Note already exists");
        }
    }

    private void checkUserStatus(String username) {
        if (!userService.isUserRegistered(username)) {
            throw new BigNoteManagementException("User with username " + username + " is not registered");
        }

        if (!userService.isUserLoggedIn(username)) {
            throw new BigNoteManagementException("User with username " + username + " is not logged in");
        }
    }

    private static CreateNoteResponse mapCreateNoteResponse(Note savedNote) {
        CreateNoteResponse response = new CreateNoteResponse();
        response.setNoteId(savedNote.getNoteId());
        response.setTitle(savedNote.getTitle());
        response.setContent(savedNote.getContent());
        return response;
    }

    private static Note mapNote(CreateNoteRequest createNoteRequest, User user) {
        Note newNote = new Note();
        newNote.setTitle(createNoteRequest.getTitle());
        newNote.setContent(createNoteRequest.getContent());
        newNote.setUserId(user.getId());
        return newNote;
    }

    private EditNoteResponse mapEditNoteResponse(Note existingNote) {
        Note updatedNote = noteRepository.save(existingNote);
        EditNoteResponse response = new EditNoteResponse();
        response.setNoteId(updatedNote.getNoteId());
        response.setTitle(updatedNote.getTitle());
        response.setContent(updatedNote.getContent());
        return response;
    }

    private static void updateNoteWithEditRequest(EditNoteRequest editNoteRequest, Note existingNote) {
        existingNote.setTitle(editNoteRequest.getTitle());
        existingNote.setContent(editNoteRequest.getContent());
    }

    private Note mapNoteForEdit(EditNoteRequest editNoteRequest, User user) {
        String noteId = editNoteRequest.getNoteId();
        Optional<Note> existingNoteOptional = noteRepository.findById(noteId);
        if (!existingNoteOptional.isPresent()) {
            throw new NoteNotFoundExceptionException("Note with ID " + noteId + " not found");
        }

        Note existingNote = existingNoteOptional.get();
        if (!existingNote.getUserId().equals(user.getId())) {
            throw new BigNoteManagementException("You are not authorized to edit this note");
        }
        return existingNote;
    }



}
