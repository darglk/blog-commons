package com.darglk.blogcommons.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

@Data
public class LoginRequest {
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;
    @NotBlank
    @Size(min = 4, max = 100)
    private String password;
}
