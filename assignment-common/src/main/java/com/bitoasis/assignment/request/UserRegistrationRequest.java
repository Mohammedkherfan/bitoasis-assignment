package com.bitoasis.assignment.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NotNull(message = "Invalid request")
public class UserRegistrationRequest {

    @NotBlank(message = "Invalid name")
    private String name;

    @NotBlank(message = "Invalid email")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Invalid email")
    private String email;

    @NotNull(message = "Invalid password")
    @Size(min = 8, max = 12, message = "Password should be from 8 to 12 digits")
    private String password;
}
