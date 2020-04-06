package com.example.photosappusersservice.ui.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestModel {

    @NotNull(message = "First name cannot be empty")
    @Size(min=2, message = "First name must be at least 2 characters long")
    private String firstName;

    @NotNull(message = "Last name cannot be empty")
    @Size(min=2, message = "Last name must be at least 2 characters long")
    private String lastName;

    @NotNull(message="Email cannot be empty")
    @Email(message="Email must be valid")
    private String email;

    @NotNull(message="Password cannot be empty")
    @Size(min=6, max=18, message="Password must be between 6 and 18 characters long")
    private String password;
}
