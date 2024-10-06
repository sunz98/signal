package com.signal.domain.post.dto.request;

import com.signal.domain.post.model.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {

    @NotBlank
    @Size(min = 5, max = 20)
    private String title;

    @NotBlank
    @Size(min = 10)
    private String contents;

    @NotBlank
    private Category category;
}
