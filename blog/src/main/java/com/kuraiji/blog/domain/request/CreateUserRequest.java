package com.kuraiji.blog.domain.request;

import com.kuraiji.blog.common.constants.ValidationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {
    @NotBlank(message = "Handle can't be blank")
    @Length(max = ValidationConstants.MAX_HANDLE_LENGTH,
            message = "Handle can't be longer than 15")
    private String handle;

    @NotBlank(message = "Email can't be blank")
    @Length(max = ValidationConstants.MAX_EMAIL_LENGTH,
            message = "Email can't be longer than 254")
    @Email
    private String email;

    @NotBlank(message = "Password")
    @Length(max = ValidationConstants.MAX_PASSWORD_LENGTH,
            min = ValidationConstants.MIN_PASSWORD_LENGTH,
            message = "Password can't be shorter than 8 chars longer than 30 chars")
    @Pattern(regexp = ValidationConstants.PASSWORD_REGEX,
                message = "Password requires one uppercase, one lowercase, one number, and one special character/")
    private String password;
}
