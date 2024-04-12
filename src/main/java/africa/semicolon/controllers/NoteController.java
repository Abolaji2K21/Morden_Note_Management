package africa.semicolon.controllers;

import africa.semicolon.data.model.Note;
import africa.semicolon.data.model.User;
import africa.semicolon.dtos.requests.CreateNoteRequest;
import africa.semicolon.dtos.requests.DeleteNoteRequest;
import africa.semicolon.dtos.requests.EditNoteRequest;
import africa.semicolon.dtos.responds.ApiResponse;
import africa.semicolon.dtos.responds.CreateNoteResponse;
import africa.semicolon.dtos.responds.DeleteNoteResponse;
import africa.semicolon.dtos.responds.EditNoteResponse;
import africa.semicolon.noteException.BigNoteManagementException;
import africa.semicolon.services.NoteService;
import africa.semicolon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/Modern_Note")
public class NoteController {
    @Autowired
    private NoteService noteService;
    @Autowired
    private UserService userService;


    @PostMapping("/create_note")
    public ResponseEntity<?> writeNote(@RequestBody CreateNoteRequest createNoteRequest) {
        try {
            CreateNoteResponse result = noteService.createNoteForUser(createNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (BigNoteManagementException message) {
            return new ResponseEntity<>(new ApiResponse(false, message.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/edit_note")
    public ResponseEntity<?> editNote(@RequestBody EditNoteRequest editNoteRequest) {
        try {
            EditNoteResponse result = noteService.editNoteForUser(editNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (BigNoteManagementException message) {
            return new ResponseEntity<>(new ApiResponse(false, message.getMessage()), BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete_note")
    public ResponseEntity<?> deleteNote(@RequestBody DeleteNoteRequest deleteNoteRequest) {
        try {
            DeleteNoteResponse result = noteService.deleteNoteForUser(deleteNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (BigNoteManagementException message) {
            return new ResponseEntity<>(new ApiResponse(false, message.getMessage()), BAD_REQUEST);
        }
    }
    @GetMapping("/getAllByUserId/{userId}")
    public ResponseEntity<?> getAllNotesByUserId(@PathVariable String userId) {
        try {
            Optional<Note> result = noteService.getAllNotesByUserId(userId);
            return result.isPresent() ?
                    new ResponseEntity<>(new ApiResponse(true, result.get()), CREATED) :
                    new ResponseEntity<>(new ApiResponse(false, "No Note found"), NOT_FOUND);
        } catch (BigNoteManagementException message) {
            return new ResponseEntity<>(new ApiResponse(false, message.getMessage()), BAD_REQUEST);
        }

    }

        @GetMapping("/users/{username}")
    public ResponseEntity<?> findUserByUsername(@RequestBody String userId) {
        try {
            User user = userService.findUserBy(userId);
            return new ResponseEntity<>(new ApiResponse(true, user), CREATED);
        } catch (BigNoteManagementException message) {
            return new ResponseEntity<>(new ApiResponse(false, message.getMessage()), BAD_REQUEST);
        }
    }


}
