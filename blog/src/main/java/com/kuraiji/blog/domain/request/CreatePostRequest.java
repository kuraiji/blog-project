package com.kuraiji.blog.domain.request;

import com.kuraiji.blog.common.constants.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePostRequest {

    @NotBlank(message = "Title can't be blank")
    @Length(max = ValidationConstants.MAX_TITLE_LENGTH,
            message = "Title can't be longer than 60 characters")
    private String title;

    @NotBlank(message = "Body can't be blank")
    @Length(max = ValidationConstants.MAX_BODY_LENGTH,
            message = "Body can't be longer than 280 characters")
    private String body;
}
