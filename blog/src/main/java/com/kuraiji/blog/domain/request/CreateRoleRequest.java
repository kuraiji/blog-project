package com.kuraiji.blog.domain.request;

import com.kuraiji.blog.common.constants.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRoleRequest {
    @NotBlank(message = "Name can't be blank")
    @Length(max = ValidationConstants.MAX_GENERIC_STRING_LENGTH, message = "Name can't be longer than 255")
    private String name;
}
