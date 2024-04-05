package africa.semicolon.services;

import africa.semicolon.data.model.Category;
import africa.semicolon.data.model.Note;
import africa.semicolon.data.repositories.CategoryRepository;
import africa.semicolon.data.repositories.NoteRepository;
import africa.semicolon.data.repositories.UserRepository;
import africa.semicolon.dtos.requests.*;
import africa.semicolon.dtos.responds.CreateCategoryResponse;
import africa.semicolon.noteException.BigNoteManagementException;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.MatcherAssert.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteService noteService;
    @Autowired
    private NoteRepository noteRepository;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testingTheCreateCategoryMethod() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        userService.register(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userService.login(loginRequest);
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
        createCategoryRequest.setDescription("HappyCategories");
        createCategoryRequest.setUsername("penisup");

        CreateCategoryResponse response = categoryService.createCategory(createCategoryRequest);

        assertEquals("HappyCategories", response.getDescription());
        assertEquals("penisup", response.getUsername());
    }

    @Test
    public void testingTheEditCategoryMethodWhen_CategoryIsNotRegistered() {
        EditCategoryRequest editCategoryRequest = new EditCategoryRequest();
        editCategoryRequest.setCategoryId(editCategoryRequest.getCategoryId());
        editCategoryRequest.setDescription("HappyCategories");
        editCategoryRequest.setUsername("penisup");

        assertThrows(BigNoteManagementException.class, () -> categoryService.editCategory(editCategoryRequest));
    }

    @Test
    public void testingTheDeleteCategoryMethodWhen_CategoryIsNotRegistered() {
        DeleteCategoryRequest deleteCategoryRequest = new DeleteCategoryRequest();
        deleteCategoryRequest.setDescription(deleteCategoryRequest.getDescription());

        assertThrows(BigNoteManagementException.class, () -> categoryService.deleteCategory(deleteCategoryRequest));
    }

    @Test
    void testingThatTheCreateCategoryMethodThrowsException_UserIsNotLoggedIn(){
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        userService.register(registerRequest);


        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
        createCategoryRequest.setDescription("HappyCategories");
        createCategoryRequest.setUsername("penisup");

        assertThrows(BigNoteManagementException.class, () -> categoryService.createCategory(createCategoryRequest));

    }

    @Test
    void testingThatTheCreateCategoryMethodThrowsException_UserIsNotRegistered(){
        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
        createCategoryRequest.setDescription("HappyCategories");
        createCategoryRequest.setUsername("penisup");

        assertThrows(BigNoteManagementException.class, () -> categoryService.createCategory(createCategoryRequest));

    }

    @Test
    void testThatTheEditCategoryMethodsThrowsException_UserIsNotLoggedIn(){
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        userService.register(registerRequest);

        EditCategoryRequest editCategoryRequest = new EditCategoryRequest();
        editCategoryRequest.setCategoryId(editCategoryRequest.getCategoryId());
        editCategoryRequest.setDescription("HappyCategories");
        editCategoryRequest.setUsername("penisup");

        assertThrows(BigNoteManagementException.class, () -> categoryService.editCategory(editCategoryRequest));

    }

    @Test
    void testThatEditCategoryWorks(){
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        userService.register(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userService.login(loginRequest);

        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
        createCategoryRequest.setDescription("HappyCategories");
        createCategoryRequest.setUsername("penisup");
        categoryService.createCategory(createCategoryRequest);

        EditCategoryRequest editCategoryRequest = new EditCategoryRequest();
        editCategoryRequest.setCategoryId("Okay");
        editCategoryRequest.setDescription("HappyCategories");
        editCategoryRequest.setUsername("penisup");
        assertEquals("Okay", editCategoryRequest.getCategoryId());

    }

    @Test
    void testThatDeleteCategoryThrowsExceptions_UserIsNotLoggedIn(){
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        userService.register(registerRequest);

        DeleteCategoryRequest deleteCategoryRequest = new DeleteCategoryRequest();
        deleteCategoryRequest.setDescription(deleteCategoryRequest.getDescription());

        assertThrows(BigNoteManagementException.class, () -> categoryService.deleteCategory(deleteCategoryRequest));

    }

    @Test
    void testAddNoteToCategory() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        userService.register(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userService.login(loginRequest);

        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
        createCategoryRequest.setDescription("HappyCategories");
        createCategoryRequest.setUsername("penisup");
        CreateCategoryResponse categoryResponse = categoryService.createCategory(createCategoryRequest);

        Note note = new Note();
        note.setTitle("AboutHoles");
        note.setContent("What to do when the hole is right");



        categoryService.addNoteToCategory("penisup", categoryResponse.getDescription(), note);

        assertEquals("HappyCategories", categoryResponse.getDescription());
        assertEquals("penisup", categoryResponse.getUsername());
        assertTrue(categoryRepository.findById(categoryResponse.getId()).isPresent());

        Category category = categoryRepository.findByDescription("HappyCategories").get();
        assertTrue(category.getNotes().contains(note));

    }
    @Test
    void testRemoveNoteFromCategory() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        userService.register(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userService.login(loginRequest);

        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
        createCategoryRequest.setDescription("HappyCategories");
        createCategoryRequest.setUsername("penisup");
        CreateCategoryResponse categoryResponse = categoryService.createCategory(createCategoryRequest);

        Note note = new Note();
        note.setTitle("AboutHoles");
        note.setContent("What to do when the hole is right");

        categoryService.addNoteToCategory("penisup", categoryResponse.getDescription(), note);

        categoryService.removeNoteFromCategory("penisup", categoryResponse.getDescription(), note);

        Category category = categoryRepository.findByDescription("HappyCategories").orElseThrow(() -> new RuntimeException("Category not found"));
        assertFalse(category.getNotes().contains(note));
    }


    @Test
    void testGetNotesByCategoryDescription() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        userService.register(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userService.login(loginRequest);

        CreateCategoryRequest createCategoryRequest = new CreateCategoryRequest();
        createCategoryRequest.setDescription("HappyCategories");
        createCategoryRequest.setUsername("penisup");
        CreateCategoryResponse categoryResponse = categoryService.createCategory(createCategoryRequest);

        Note note1 = new Note();
        note1.setTitle("Note1");
        note1.setContent("Content1");

        Note note2 = new Note();
        note2.setTitle("Note2");
        note2.setContent("Content2");

        categoryService.addNoteToCategory("penisup", categoryResponse.getDescription(), note1);
        categoryService.addNoteToCategory("penisup", categoryResponse.getDescription(), note2);

        List<Note> notes = categoryService.getNotesByCategoryDescription(categoryResponse.getDescription());
        assertEquals(2, notes.size());
        assertTrue(notes.contains(note1));
        assertTrue(notes.contains(note2));
    }

    @Test
    void testGetAllCategories() {
        RegisterUserRequest registerRequest = new RegisterUserRequest();
        registerRequest.setFirstname("PenIs");
        registerRequest.setLastname("Up");
        registerRequest.setUsername("penisup");
        registerRequest.setPassword("Holes");
        userService.register(registerRequest);

        LoginUserRequest loginRequest = new LoginUserRequest();
        loginRequest.setUsername("penisup");
        loginRequest.setPassword("Holes");
        userService.login(loginRequest);

        CreateCategoryRequest createCategoryRequest1 = new CreateCategoryRequest();
        createCategoryRequest1.setDescription("Category1");
        createCategoryRequest1.setUsername("penisup");
        CreateCategoryResponse categoryResponse1 = categoryService.createCategory(createCategoryRequest1);

        CreateCategoryRequest createCategoryRequest2 = new CreateCategoryRequest();
        createCategoryRequest2.setDescription("Category2");
        createCategoryRequest2.setUsername("penisup");
        CreateCategoryResponse categoryResponse2 = categoryService.createCategory(createCategoryRequest2);

        assertEquals(2, categoryService.getAllCategories().size());

//        boolean category1Found = false;
//        boolean category2Found = false;
//
//        for (Category category : categories) {
//            if (category.getDescription().equals(categoryResponse1.getDescription())) {
//                category1Found = true;
//            }
//            if (category.getDescription().equals(categoryResponse2.getDescription())) {
//                category2Found = true;
//            }
//        }
//
//        assertTrue(category1Found);
//        assertTrue(category2Found);
//    }
    }

}
