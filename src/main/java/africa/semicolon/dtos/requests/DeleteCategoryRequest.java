package africa.semicolon.dtos.requests;


import lombok.Data;

@Data
public class DeleteCategoryRequest {
    private String description ;
    private String username;

}