package africa.semicolon.services;

import africa.semicolon.data.model.Note;
import africa.semicolon.data.model.User;
import africa.semicolon.data.repositories.NoteRepository;
import africa.semicolon.data.repositories.UserRepository;
import africa.semicolon.dtos.requests.CreateNoteRequest;
import africa.semicolon.dtos.requests.LoginUserRequest;
import africa.semicolon.dtos.requests.RegisterUserRequest;
import africa.semicolon.dtos.responds.LoginUserResponse;
import africa.semicolon.noteException.BigNoteManagementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void setUp(){

        userRepository.deleteAll();
        noteRepository.deleteAll();
    }


    @Test
    public void testingTheWriteNoteMethodWhen_UserIsNotRegistered() {
        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setUsername("PenIsUp");
        createNoteRequest.setTitle("AboutHoles");
        createNoteRequest.setContent("What to do when the hole is right");

        assertThrows(BigNoteManagementException.class, () -> noteService.writeNote(createNoteRequest));
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
        assertThrows(BigNoteManagementException.class, () -> noteService.writeNote(createNoteRequest));


    }
    @Test
    public void testingTheWriteNoteMethodWhen_UserIsRegisteredAndLoggedIn() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        userService.register(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        LoginUserResponse loginResponse = userService.login(loginRequest);
        assertThat(loginResponse.getUsername(), is("penisup"));


        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setUsername("penisup");
        createNoteRequest.setTitle("AboutHoles");
        createNoteRequest.setContent("What to do when the hole is right");
        noteService.writeNote(createNoteRequest);

        Note savedNote = noteRepository.findBy("AboutHoles");
        assertEquals("AboutHoles", savedNote.getTitle());

    }



}

