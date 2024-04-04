package africa.semicolon.services;

import africa.semicolon.data.model.Category;
import africa.semicolon.data.repositories.CategoryRepository;
import africa.semicolon.data.repositories.UserRepository;
import africa.semicolon.dtos.requests.*;
import africa.semicolon.dtos.responds.CreateCategoryResponse;
import africa.semicolon.dtos.responds.DeleteCategoryResponse;
import africa.semicolon.dtos.responds.EditCategoryResponse;
import africa.semicolon.noteException.BigNoteManagementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        deleteCategoryRequest.setCategoryId(deleteCategoryRequest.getCategoryId());

        assertThrows(BigNoteManagementException.class, () -> categoryService.deleteCategory(deleteCategoryRequest));
    }

}
