package africa.semicolon.dtos.responds;

import lombok.Data;

@Data
public class CreateCategoryResponse {
    private String id;
    private String description;
    private String username;
}
