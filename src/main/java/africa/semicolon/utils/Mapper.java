package africa.semicolon.utils;

import africa.semicolon.data.model.Note;
import africa.semicolon.data.model.User;
import africa.semicolon.dtos.requests.CreateNoteRequest;
import africa.semicolon.dtos.requests.EditNoteRequest;
import africa.semicolon.dtos.requests.RegisterUserRequest;
import africa.semicolon.dtos.responds.CreateNoteResponse;
import africa.semicolon.dtos.responds.EditNoteResponse;
import africa.semicolon.dtos.responds.RegisterUserResponse;

import java.time.format.DateTimeFormatter;

public class Mapper {
    public static User map(RegisterUserRequest registerUserRequest) {
        User user = new User();
        user.setFirstName(registerUserRequest.getFirstname());
        user.setLastName(registerUserRequest.getLastname());
        user.setPassword(registerUserRequest.getPassword());
        user.setUsername(registerUserRequest.getUsername());
        return user;
    }

    public static RegisterUserResponse map(User user){
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setUsername(user.getUsername());
        registerUserResponse.setId(user.getId());
        registerUserResponse.setDateRegistered(DateTimeFormatter.ofPattern("dd-MM-yyyy, hh:mm:ss").format(user.getDateCreated()));
        return registerUserResponse;
    }

    public static CreateNoteResponse map(Note savedNote) {
        CreateNoteResponse response = new CreateNoteResponse();
        response.setContent(savedNote.getContent());
        response.setNoteId(savedNote.getId());
        return response;
    }

    public static Note map(CreateNoteRequest createNoteRequest) {
        Note newNote = new Note();
        newNote.setTitle(createNoteRequest.getTitle());
        newNote.setContent(createNoteRequest.getContent());
        return newNote;
    }

    public static void mapEditNoteRequest(EditNoteRequest editNoteRequest, Note existingNote) {
        existingNote.setTitle(editNoteRequest.getTitle());
        existingNote.setContent(editNoteRequest.getContent());
    }

    public static EditNoteResponse mapEditNoteResponse(Note updatedNote) {
        EditNoteResponse response = new EditNoteResponse();
        response.setNewTitle(updatedNote.getTitle());
        response.setNewContent(updatedNote.getContent());
        return response;
    }
}
