package africa.semicolon.dtos.requests;

import lombok.Data;

@Data
public class EditCategoryRequest {
    private String description;
    private String username;
    private String categoryId;
}
