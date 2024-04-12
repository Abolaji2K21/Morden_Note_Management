package africa.semicolon.services;

import africa.semicolon.data.model.Note;
import africa.semicolon.data.repositories.NoteRepository;
import africa.semicolon.data.repositories.UserRepository;
import africa.semicolon.dtos.requests.*;
import africa.semicolon.dtos.responds.CreateNoteResponse;
import africa.semicolon.dtos.responds.LoginUserResponse;
import africa.semicolon.dtos.responds.RegisterUserResponse;
import africa.semicolon.noteException.BigNoteManagementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NoteServiceImplTest {

    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {

        userRepository.deleteAll();
        noteRepository.deleteAll();
    }


    @Test
    public void testingTheWriteNoteMethodWhen_UserIsNotRegistered() {
        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setUsername("PenIsUp");
        createNoteRequest.setTitle("AboutHoles");
        createNoteRequest.setContent("What to do when the hole is right");

        assertThrows(BigNoteManagementException.class, () -> noteService.createNoteForUser(createNoteRequest));
    }

    @Test
    public void testingTheWriteNoteMethodWhen_UserIsRegisteredButNotLoggedIn() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("PenIsUp");
        registerRequest.setPassword("Holes");
        userService.register(registerRequest);

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setUsername("penisup");
        createNoteRequest.setTitle("AboutHoles");
        createNoteRequest.setContent("What to do when the hole is right");
        assertThrows(BigNoteManagementException.class, () -> noteService.createNoteForUser(createNoteRequest));


    }

    @Test
    public void testingTheWriteNoteMethodWhen_UserIsRegisteredAndLoggedIn() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        RegisterUserResponse userResponse = userService.register(registerRequest);
        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        LoginUserResponse loginResponse = userService.login(loginRequest);
        assertThat(loginResponse.getUsername(), is("penisup"));

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setUsername("penisup");
        createNoteRequest.setTitle("AboutHoles");
        createNoteRequest.setContent("What to do when the hole is right");
        createNoteRequest.setUserId(userResponse.getId());
        createNoteRequest.setCategory("DarkestHourOfLove");
        CreateNoteResponse noteResponse = noteService.createNoteForUser(createNoteRequest);

        Optional<Note> savedNote = noteRepository.findNoteByNoteIdAndUserId(noteResponse.getNoteId(), userResponse.getId());
        assertEquals("AboutHoles", savedNote.get().getTitle());
    }

    @Test
    public void testingTheEditNoteMethodWhen_UserIsNotRegistered() {
        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setUsername("penisup");
        createNoteRequest.setTitle("AboutHoles");
        createNoteRequest.setContent("What to do when the hole is right");

        EditNoteRequest editNoteRequest = new EditNoteRequest();
        editNoteRequest.setUsername("penisup");
        editNoteRequest.setTitle("AboutHoles");
        editNoteRequest.setContent("What to do when the hole is right");
        assertThrows(BigNoteManagementException.class, () -> noteService.editNoteForUser(editNoteRequest));

    }

    @Test
    public void testingTheWEditNoteMethodWhen_UserIsRegisteredAndLoggedIn() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("PenIsUp");
        registerRequest.setPassword("Holes");
        userService.register(registerRequest);

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setUsername("penisup");
        createNoteRequest.setTitle("AboutHoles");
        createNoteRequest.setContent("What to do when the hole is right");

        EditNoteRequest editNoteRequest = new EditNoteRequest();
        editNoteRequest.setUsername("penisup");
        editNoteRequest.setTitle("AboutHoles");
        editNoteRequest.setContent("What to do when the hole is right");

        assertThrows(BigNoteManagementException.class, () -> noteService.editNoteForUser(editNoteRequest));

    }

    @Test
    public void testingTheEditNoteMethodWhen_UserIsRegisteredAndLoggedIn() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        RegisterUserResponse userResponse = userService.register(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userService.login(loginRequest);


        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setUsername("penisup");
        createNoteRequest.setTitle("AboutHoles");
        createNoteRequest.setContent("What to do when the hole is right");
        createNoteRequest.setUserId(userResponse.getId());
        createNoteRequest.setCategory("DarkestHourOfLove");
        CreateNoteResponse noteResponse = noteService.createNoteForUser(createNoteRequest);

        String noteId = noteResponse.getNoteId();
        String userId = userResponse.getId();

        EditNoteRequest editNoteRequest = new EditNoteRequest();
        editNoteRequest.setUsername("penisup");
        editNoteRequest.setTitle("AboutHoles");
        editNoteRequest.setContent("Call the police");
        editNoteRequest.setUserId(userId);
        editNoteRequest.setNoteId(noteId);

        noteService.editNoteForUser(editNoteRequest);

        Optional<Note> editedNote = noteRepository.findNoteByNoteIdAndUserId(noteId,userId);
        assertEquals("Call the police", editedNote.get().getContent());

    }

    @Test
    public void testingTheDeleteNoteMethodWhen_UserIsNotRegistered() {
        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setUsername("penisup");
        deleteNoteRequest.setTitle("AboutHoles");

        assertThrows(BigNoteManagementException.class, () -> noteService.deleteNoteForUser(deleteNoteRequest));
    }

    @Test
    public void testingTheDeleteNoteMethodWhen_UserIsRegisteredButNotLoggedIn() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        userService.register(registerRequest);

        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setUsername("penisup");
        deleteNoteRequest.setTitle("AboutHoles");

        assertThrows(BigNoteManagementException.class, () -> noteService.deleteNoteForUser(deleteNoteRequest));
    }

    @Test
    public void testingTheDeleteNoteMethodWhen_UserIsRegisteredAndLoggedIn() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        RegisterUserResponse userResponse = userService.register(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userService.login(loginRequest);

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setUsername("penisup");
        createNoteRequest.setTitle("AboutHoles");
        createNoteRequest.setContent("What to do when the hole is right");
        createNoteRequest.setUserId(userResponse.getId());
        createNoteRequest.setCategory("DarkestHourOfLove");
        CreateNoteResponse noteResponse = noteService.createNoteForUser(createNoteRequest);


        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setUsername("penisup");
        deleteNoteRequest.setTitle("AboutHoles");
        deleteNoteRequest.setNoteId(noteResponse.getNoteId());
        deleteNoteRequest.setUserId(userResponse.getId());
        noteService.deleteNoteForUser(deleteNoteRequest);
        assertFalse(noteRepository.existsByTitleAndUserId(deleteNoteRequest.getTitle(), userResponse.getId()));
    }

    @Test
    void testThatAnotherUserCanNotDeleteAnotherUserNote(){
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        RegisterUserResponse userResponse = userService.register(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userService.login(loginRequest);

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setUsername("penisup");
        createNoteRequest.setTitle("AboutHoles");
        createNoteRequest.setContent("What to do when the hole is right");
        createNoteRequest.setUserId(userResponse.getId());
        createNoteRequest.setCategory("DarkestHourOfLove");
        CreateNoteResponse noteResponse = noteService.createNoteForUser(createNoteRequest);

        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setUsername("penisDown");
        deleteNoteRequest.setTitle("AboutHoles");
        deleteNoteRequest.setUserId("FakePen");
        deleteNoteRequest.setNoteId(noteResponse.getNoteId());
        assertThrows(BigNoteManagementException.class, () -> noteService.deleteNoteForUser(deleteNoteRequest));

    }
}
