package africa.semicolon.utils;
import africa.semicolon.data.model.Note;
import africa.semicolon.data.model.User;
import africa.semicolon.dtos.requests.CreateNoteRequest;
import africa.semicolon.dtos.requests.EditNoteRequest;
import africa.semicolon.dtos.requests.RegisterUserRequest;
import africa.semicolon.dtos.responds.*;

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

    public static UpdateUserResponse mapUpdateUserResponse(User user) {
        UpdateUserResponse response = new UpdateUserResponse();
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setFirstname(user.getFirstName());
        response.setLastname(user.getLastName());
        response.setDateUpdated(user.getDateUpdated().toString());
        response.setLoggedIn(user.isLoggedIn());
        return response;
    }
//    public static CreateCategoryResponse mapToCreateCategoryResponse(Category category) {
//        CreateCategoryResponse response = new CreateCategoryResponse();
//        response.setCategoryId(category.getId());
//        response.setDescription(category.getDescription());
//        response.setUsername(category.getUsername());
//        return response;
//    }
//
//    public static EditCategoryResponse mapToEditCategoryResponse(Category category) {
//        EditCategoryResponse response = new EditCategoryResponse();
//        response.setCategoryId(category.getId());
//        response.setDescription(category.getDescription());
//        response.setUsername(category.getUsername());
//        return response;
//    }
}
