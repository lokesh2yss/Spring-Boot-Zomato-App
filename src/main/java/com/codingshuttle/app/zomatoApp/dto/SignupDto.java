package com.codingshuttle.app.zomatoApp.dto;

import com.codingshuttle.app.zomatoApp.annotations.validations.ValidPhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {

    @NotBlank(message = "Name field cannot be left blank")
    @Size(min = 2, max = 30)
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be in a valid format")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    @ValidPhoneNumber
    private String phone;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character")
    private String password;
}
