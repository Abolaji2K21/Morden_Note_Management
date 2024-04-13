package africa.semicolon.utils;

import africa.semicolon.data.model.Note;
import africa.semicolon.data.model.User;
import africa.semicolon.dtos.requests.CreateNoteRequest;
import africa.semicolon.dtos.requests.EditNoteRequest;
import africa.semicolon.dtos.responds.CreateNoteResponse;
import africa.semicolon.dtos.responds.DeleteNoteResponse;
import africa.semicolon.dtos.responds.EditNoteResponse;
import lombok.extern.slf4j.Slf4j;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
public class NoteMapper {
    public static Note mapNoteForCreate(CreateNoteRequest createNoteRequest, User user) {
        Note newNote = new Note();
        newNote.setTitle(createNoteRequest.getTitle());
        newNote.setContent(createNoteRequest.getContent());
        newNote.setUserId(user.getId());
        newNote.setCategory(createNoteRequest.getCategory());
        newNote.setUsername(createNoteRequest.getUsername());
        newNote.setDateTimeCreated(LocalDateTime.now());
        return newNote;
    }

    public static CreateNoteResponse mapCreateNoteResponse(Note savedNote) {
        CreateNoteResponse response = new CreateNoteResponse();
        response.setNoteId(savedNote.getNoteId());
        response.setTitle(savedNote.getTitle());
        response.setContent(savedNote.getContent());
        response.setDateCreated(savedNote.getDateTimeCreated());
        response.setCategory(savedNote.getCategory());
        return response;
    }

    public static EditNoteResponse mapEditNoteResponse(Note updatedNote) {
        EditNoteResponse response = new EditNoteResponse();
        response.setNoteId(updatedNote.getNoteId());
        response.setTitle(updatedNote.getTitle());
        response.setContent(updatedNote.getContent());
        response.setCategory(updatedNote.getCategory());
        response.setDateUpdated(LocalDateTime.now());
        return response;
    }

    public static DeleteNoteResponse mapDeleteNoteResponse(Note deletedNote) {
        DeleteNoteResponse response = new DeleteNoteResponse();
        response.setNoteId(deletedNote.getNoteId());
        response.setDeleted(true);
        response.setUsername(deletedNote.getUsername());
        return response;
    }

    public static void updateNoteWithEditRequest(EditNoteRequest editNoteRequest, Note existingNote) {
        existingNote.setTitle(editNoteRequest.getTitle());
        existingNote.setContent(editNoteRequest.getContent());
    }
}
