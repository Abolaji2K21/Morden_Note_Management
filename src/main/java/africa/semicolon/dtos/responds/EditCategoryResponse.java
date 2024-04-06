package africa.semicolon.dtos.responds;

import lombok.Data;

@Data
public class EditCategoryResponse {
    private String categoryId;
    private String description;
    private String username;
}
