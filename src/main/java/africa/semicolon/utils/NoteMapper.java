package africa.semicolon.utils;

import africa.semicolon.data.model.Note;
import africa.semicolon.data.model.User;
import africa.semicolon.data.repositories.NoteRepository;
import africa.semicolon.dtos.requests.CreateNoteRequest;
import africa.semicolon.dtos.requests.DeleteNoteRequest;
import africa.semicolon.dtos.requests.EditNoteRequest;
import africa.semicolon.dtos.responds.CreateNoteResponse;
import africa.semicolon.dtos.responds.DeleteNoteResponse;
import africa.semicolon.dtos.responds.EditNoteResponse;
import africa.semicolon.noteException.BigNoteManagementException;
import africa.semicolon.noteException.NoteNotFoundExceptionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class NoteMapper {
    public static Note mapNoteForCreate(CreateNoteRequest createNoteRequest, User user) {
        Note newNote = new Note();
        newNote.setTitle(createNoteRequest.getTitle());
        newNote.setContent(createNoteRequest.getContent());
        newNote.setUserId(user.getId());
        return newNote;
    }

    public static CreateNoteResponse mapCreateNoteResponse(Note savedNote) {
        CreateNoteResponse response = new CreateNoteResponse();
        response.setNoteId(savedNote.getNoteId());
        response.setTitle(savedNote.getTitle());
        response.setContent(savedNote.getContent());
        return response;
    }

    public static EditNoteResponse mapEditNoteResponse(Note updatedNote) {
        EditNoteResponse response = new EditNoteResponse();
        response.setNoteId(updatedNote.getNoteId());
        response.setTitle(updatedNote.getTitle());
        response.setContent(updatedNote.getContent());
        return response;
    }

    public static DeleteNoteResponse mapDeleteNoteResponse(Note deletedNote) {
        DeleteNoteResponse response = new DeleteNoteResponse();
        response.setNoteId(deletedNote.getNoteId());
        response.setDeleted(true);
        return response;
    }

    public static void updateNoteWithEditRequest(EditNoteRequest editNoteRequest, Note existingNote) {
        existingNote.setTitle(editNoteRequest.getTitle());
        existingNote.setContent(editNoteRequest.getContent());
    }
}
