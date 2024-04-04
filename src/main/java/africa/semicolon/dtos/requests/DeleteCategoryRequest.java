package africa.semicolon.dtos.requests;


import lombok.Data;

@Data
public class DeleteCategoryRequest {
    private String categoryId;
    private String username;
}