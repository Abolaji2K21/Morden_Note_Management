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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

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
            CreateNoteResponse result = noteService.addNote(createNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (BigNoteManagementException message) {
            return new ResponseEntity<>(new ApiResponse(false, message.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/edit_note")
    public ResponseEntity<?> editNote(@RequestBody EditNoteRequest editNoteRequest) {
        try {
            EditNoteResponse result = noteService.editNote(editNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (BigNoteManagementException message) {
            return new ResponseEntity<>(new ApiResponse(false, message.getMessage()), BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete_note")
    public ResponseEntity<?> deleteNote(@RequestBody DeleteNoteRequest deleteNoteRequest) {
        try {
            DeleteNoteResponse result = noteService.deleteNote(deleteNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (BigNoteManagementException message) {
            return new ResponseEntity<>(new ApiResponse(false, message.getMessage()), BAD_REQUEST);
        }
    }
    @GetMapping("/notes/{username}")
    public ResponseEntity<?> findNoteByUsername(@PathVariable String username) {
        try {
            Note note = noteService.findNoteBy(username);
            return new ResponseEntity<>(new ApiResponse(true, note), CREATED);
        } catch (BigNoteManagementException message) {
            return new ResponseEntity<>(new ApiResponse(false, message.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<?> findUserByUsername(@PathVariable String username) {
        try {
            User user = userService.findUserBy(username);
            return new ResponseEntity<>(new ApiResponse(true, user), CREATED);
        } catch (BigNoteManagementException message) {
            return new ResponseEntity<>(new ApiResponse(false, message.getMessage()), BAD_REQUEST);
        }
    }


}
