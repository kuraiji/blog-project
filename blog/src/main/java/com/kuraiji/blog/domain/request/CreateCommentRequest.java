package com.kuraiji.blog.domain.request;

import com.kuraiji.blog.common.annotations.ValidUuid;
import com.kuraiji.blog.common.constants.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCommentRequest {

    @NotNull
    @ValidUuid
    private UUID postId;

    @NotBlank
    @Length(max = ValidationConstants.MAX_CONTENT_LENGTH,
            message = "Body can't be longer than 140 characters")
    private String content;
}
