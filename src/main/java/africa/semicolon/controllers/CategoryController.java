package africa.semicolon.controllers;

import africa.semicolon.data.model.Category;
import africa.semicolon.data.model.Note;
import africa.semicolon.dtos.requests.*;
import africa.semicolon.dtos.responds.ApiResponse;
import africa.semicolon.dtos.responds.CreateCategoryResponse;
import africa.semicolon.dtos.responds.DeleteCategoryResponse;
import africa.semicolon.dtos.responds.EditCategoryResponse;
import africa.semicolon.services.CategoryService;
import africa.semicolon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/Modern_Note")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @PostMapping("/create_category")
    public ResponseEntity<?> createCategory(@RequestBody CreateCategoryRequest createCategoryRequest) {
        try {
            CreateCategoryResponse result = categoryService.createCategory(createCategoryRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/edit_category")
    public ResponseEntity<?> editCategory(@RequestBody EditCategoryRequest editCategoryRequest) {
        try {
            EditCategoryResponse result = categoryService.editCategory(editCategoryRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete_category")
    public ResponseEntity<?> deleteCategory(@RequestBody DeleteCategoryRequest deleteCategoryRequest) {
        try {
            DeleteCategoryResponse result = categoryService.deleteCategory(deleteCategoryRequest);
            return new ResponseEntity<>(new ApiResponse(true, result), CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return new ResponseEntity<>(new ApiResponse(true, categories), CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/categories/{description}/notes")
    public ResponseEntity<?> getNotesByCategoryDescription(@PathVariable String description) {
        try {
            List<Note> notes = categoryService.getNotesByCategoryDescription(description);
            return new ResponseEntity<>(new ApiResponse(true, notes), CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/add_note_to_category")
    public ResponseEntity<?> addNoteToCategory(@RequestBody AddNoteToCategoryRequest request) {
        try {
            categoryService.addNoteToCategory(request.getUsername(), request.getDescription(), request.getNote());
            return new ResponseEntity<>(new ApiResponse(true, "Note added to category successfully"), CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/remove_note_from_category")
    public ResponseEntity<?> removeNoteFromCategory(@RequestBody RemoveNoteFromCategoryRequest request) {
        try {
            categoryService.removeNoteFromCategory(request.getUsername(), request.getDescription(), request.getNote());
            return new ResponseEntity<>(new ApiResponse(true, "Note removed from category successfully"), CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), BAD_REQUEST);
        }
    }
}
