package com.codingshuttle.app.zomatoApp.dto;

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
public class LoginDto {

    @NotBlank(message = "Email field cannot be blank")
    @Email(message = "Please provide a valid email address")
    private String email;


    @NotBlank(message = "Password is mandatory")
    private String password;
}
