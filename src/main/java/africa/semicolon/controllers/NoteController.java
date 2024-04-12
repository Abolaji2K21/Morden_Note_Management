package africa.semicolon.controllers;

import africa.semicolon.data.model.Note;
import africa.semicolon.dtos.requests.CreateNoteRequest;
import africa.semicolon.dtos.requests.DeleteNoteRequest;
import africa.semicolon.dtos.requests.EditNoteRequest;
import africa.semicolon.dtos.responds.ApiResponse;
import africa.semicolon.dtos.responds.CreateNoteResponse;
import africa.semicolon.dtos.responds.DeleteNoteResponse;
import africa.semicolon.dtos.responds.EditNoteResponse;
import africa.semicolon.noteException.BigNoteManagementException;
import africa.semicolon.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/Modern_Note")
public class NoteController {
    @Autowired
    private NoteService noteService;


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

    @GetMapping("/getAllByCategory/{userId}/{category}")
    public ResponseEntity<?> getAllContactsByCategory(@PathVariable String userId, @PathVariable String category) {

        try {
            List<Note> result = noteService.getAllNoteByCategory(userId, category);
            return !result.isEmpty() ?
                    new ResponseEntity<>(new ApiResponse(true, result),CREATED) :
                    new ResponseEntity<>(new ApiResponse(false, "No Note found for the given category"), NOT_FOUND);
        } catch (BigNoteManagementException message) {
            return new ResponseEntity<>(new ApiResponse(false, message.getMessage()),BAD_REQUEST);
        }
    }


}

