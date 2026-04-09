package com.kuraiji.blog.domain.request;

import com.kuraiji.blog.common.annotations.EnumNamePattern;
import com.kuraiji.blog.common.constants.ValidationConstants;
import com.kuraiji.blog.domain.entity.PermissionScope;
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
public class CreatePermissionRequest {

    @NotNull(message = "Role Id can't be null")
    private Short roleId;

    @NotBlank(message = "Name can't be blank")
    @Length(max = ValidationConstants.MAX_GENERIC_STRING_LENGTH,
            message = "Name can't be longer than 255 characters")
    private String name;

    @NotBlank
    @EnumNamePattern(regexp = "ALL|SELF|NONE")
    private PermissionScope scope;
}
