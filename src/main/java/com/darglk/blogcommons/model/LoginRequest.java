package com.darglk.blogcommons.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class LoginRequest {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
