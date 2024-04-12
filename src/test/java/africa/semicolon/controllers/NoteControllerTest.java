package africa.semicolon.controllers;

import africa.semicolon.data.repositories.NoteRepository;
import africa.semicolon.data.repositories.UserRepository;
import africa.semicolon.dtos.requests.*;
import africa.semicolon.dtos.responds.ApiResponse;
import africa.semicolon.dtos.responds.CreateNoteResponse;
import africa.semicolon.dtos.responds.RegisterUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class NoteControllerTest {
    @Autowired
    private NoteController noteController;
    @Autowired
    private UserController userController;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        noteRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testCreateNote_isSuccessful_isTrue() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        ResponseEntity<?> registrationResponseEntity = userController.registerUser(registerRequest);

        RegisterUserResponse registrationResponse = (RegisterUserResponse) ((ApiResponse) registrationResponseEntity.getBody()).getData();
        String userId = registrationResponse.getId();


        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userController.login(loginRequest);

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setUsername("penisup");
        createNoteRequest.setTitle("AboutHoles");
        createNoteRequest.setContent("What to do when the hole is right");
        createNoteRequest.setUserId(userId);
        createNoteRequest.setCategory("DarkestHourOfLove");
        ResponseEntity<?> responseEntity = noteController.createNote(createNoteRequest);
        assertIsSuccessful(responseEntity, true);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));


    }

    @Test
    void testEditNote_isSuccessful_isTrue() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        ResponseEntity<?> registrationResponseEntity = userController.registerUser(registerRequest);
        RegisterUserResponse registrationResponse = (RegisterUserResponse) ((ApiResponse) registrationResponseEntity.getBody()).getData();
        String userId = registrationResponse.getId();

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userController.login(loginRequest);

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setUsername("penisup");
        createNoteRequest.setTitle("AboutHoles");
        createNoteRequest.setContent("What to do when the hole is right");
        createNoteRequest.setUserId(userId);
        createNoteRequest.setCategory("DarkestHourOfLove");
        ResponseEntity<?> createNoteResponseEntity = noteController.createNote(createNoteRequest);
        CreateNoteResponse createNoteResponse = (CreateNoteResponse) ((ApiResponse) createNoteResponseEntity.getBody()).getData();
        String noteId = createNoteResponse.getNoteId();

        EditNoteRequest editNoteRequest = new EditNoteRequest();
        editNoteRequest.setUsername("penisup");
        editNoteRequest.setTitle("AboutHoles");
        editNoteRequest.setContent("New content");
        editNoteRequest.setUserId(userId);
        editNoteRequest.setNoteId(noteId);
        ResponseEntity<?> editNoteResponseEntity = noteController.editNote(editNoteRequest);

        assertIsSuccessful(editNoteResponseEntity, true);
    }

    @Test
    void testDeleteNote_isSuccessful_isTrue() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        ResponseEntity<?> registrationResponseEntity = userController.registerUser(registerRequest);
        RegisterUserResponse registrationResponse = (RegisterUserResponse) ((ApiResponse) registrationResponseEntity.getBody()).getData();
        String userId = registrationResponse.getId();

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userController.login(loginRequest);

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setUsername("penisup");
        createNoteRequest.setTitle("AboutHoles");
        createNoteRequest.setContent("What to do when the hole is right");
        createNoteRequest.setUserId(userId);
        createNoteRequest.setCategory("DarkestHourOfLove");
        ResponseEntity<?> createNoteResponseEntity = noteController.createNote(createNoteRequest);
        CreateNoteResponse createNoteResponse = (CreateNoteResponse) ((ApiResponse) createNoteResponseEntity.getBody()).getData();
        String noteId = createNoteResponse.getNoteId();

        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setUsername("penisup");
        deleteNoteRequest.setNoteId(noteId);
        deleteNoteRequest.setUserId(userId);
        ResponseEntity<?> deleteNoteResponseEntity = noteController.deleteNote(deleteNoteRequest);

        assertIsSuccessful(deleteNoteResponseEntity, true);
    }


    @Test
    void testEditNote_isSuccessful_isFalse() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        ResponseEntity<?> registrationResponseEntity = userController.registerUser(registerRequest);
        RegisterUserResponse registrationResponse = (RegisterUserResponse) ((ApiResponse) registrationResponseEntity.getBody()).getData();
        String userId = registrationResponse.getId();

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userController.login(loginRequest);

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setUsername("penisup");
        createNoteRequest.setTitle("AboutHoles");
        createNoteRequest.setContent("What to do when the hole is right");
        createNoteRequest.setUserId(userId);
        createNoteRequest.setCategory("DarkestHourOfLove");
        ResponseEntity<?> createNoteResponseEntity = noteController.createNote(createNoteRequest);

        EditNoteRequest editNoteRequest = new EditNoteRequest();
        editNoteRequest.setUsername("penisup");
        editNoteRequest.setTitle("NonExistentNoteTitle");
        editNoteRequest.setContent("New content");
        editNoteRequest.setUserId(userId);
        ResponseEntity<?> editNoteResponseEntity = noteController.editNote(editNoteRequest);

        assertIsSuccessful(editNoteResponseEntity, false);
    }

    @Test
    void testDeleteNote_isSuccessful_isFalse() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        ResponseEntity<?> registrationResponseEntity = userController.registerUser(registerRequest);
        RegisterUserResponse registrationResponse = (RegisterUserResponse) ((ApiResponse) registrationResponseEntity.getBody()).getData();
        String userId = registrationResponse.getId();

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userController.login(loginRequest);

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setUsername("penisup");
        createNoteRequest.setTitle("AboutHoles");
        createNoteRequest.setContent("What to do when the hole is right");
        createNoteRequest.setUserId(userId);
        createNoteRequest.setCategory("DarkestHourOfLove");
        ResponseEntity<?> createNoteResponseEntity = noteController.createNote(createNoteRequest);
        CreateNoteResponse createNoteResponse = (CreateNoteResponse) ((ApiResponse) createNoteResponseEntity.getBody()).getData();
        String noteId = createNoteResponse.getNoteId();

        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setUsername("penisup");
        deleteNoteRequest.setNoteId("NonExistentNoteId");
        deleteNoteRequest.setUserId(userId);
        ResponseEntity<?> deleteNoteResponseEntity = noteController.deleteNote(deleteNoteRequest);

        assertIsSuccessful(deleteNoteResponseEntity, false);
    }

    @Test
    void testGetAllNotesByUserId_isSuccessful() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        ResponseEntity<?> registrationResponseEntity = userController.registerUser(registerRequest);
        RegisterUserResponse registrationResponse = (RegisterUserResponse) ((ApiResponse) registrationResponseEntity.getBody()).getData();
        String userId = registrationResponse.getId();

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userController.login(loginRequest);

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setUsername("penisup");
        createNoteRequest.setTitle("AboutHoles");
        createNoteRequest.setContent("What to do when the hole is right");
        createNoteRequest.setUserId(userId);
        createNoteRequest.setCategory("DarkestHourOfLove");
        ResponseEntity<?> createNoteResponseEntity = noteController.createNote(createNoteRequest);

        ResponseEntity<?> getAllNotesResponseEntity = noteController.getAllNotesByUserId(userId);

        assertIsSuccessful(getAllNotesResponseEntity, true);
    }

    @Test
    void testGetAllNotesByCategory_isSuccessful() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        ResponseEntity<?> registrationResponseEntity = userController.registerUser(registerRequest);
        RegisterUserResponse registrationResponse = (RegisterUserResponse) ((ApiResponse) registrationResponseEntity.getBody()).getData();
        String userId = registrationResponse.getId();

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userController.login(loginRequest);

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setUsername("penisup");
        createNoteRequest.setTitle("AboutHoles");
        createNoteRequest.setContent("What to do when the hole is right");
        createNoteRequest.setUserId(userId);
        createNoteRequest.setCategory("DarkestHourOfLove");
        ResponseEntity<?> createNoteResponseEntity = noteController.createNote(createNoteRequest);

        ResponseEntity<?> getAllNotesResponseEntity = noteController.getAllNotesByCategory(userId, "DarkestHourOfLove");

        assertIsSuccessful(getAllNotesResponseEntity, true);
    }


    @Test
    void testCreateNote_isSuccessful_isFalse() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("penIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        ResponseEntity<?> registrationResponseEntity = userController.registerUser(registerRequest);
        RegisterUserResponse registrationResponse = (RegisterUserResponse) ((ApiResponse) registrationResponseEntity.getBody()).getData();
        String userId = registrationResponse.getId();

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userController.login(loginRequest);

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setUsername("penisup");
        createNoteRequest.setUserId("InvalidPenis");
        createNoteRequest.setCategory("DarkestHourOfLove");
        createNoteRequest.setTitle("madass");
        createNoteRequest.setContent("TiredMotherfucker");

        ResponseEntity<?> responseEntity = noteController.createNote(createNoteRequest);

        assertIsSuccessful(responseEntity, false);
    }

    @Test
    void testGetAllNotesByCategory_isSuccessful_isFalse() {
        ResponseEntity<?> responseEntity = noteController.getAllNotesByCategory("invalidUserId", "DarkestHourOfLove");
        assertIsSuccessful(responseEntity, false);
    }

    @Test
    void testGetAllNotesByUserId_isSuccessful_is_false() {
        ResponseEntity<?> responseEntity = noteController.getAllNotesByUserId("invalidUserId");
        assertIsSuccessful(responseEntity, false);
    }


    private void assertIsSuccessful(ResponseEntity<?> response, boolean expected) {
        if (response.hasBody() && response.getBody() instanceof ApiResponse apiResponse) {
            boolean success = apiResponse.isSuccessful();
            assertThat(success, is(expected));
        }
    }



    }