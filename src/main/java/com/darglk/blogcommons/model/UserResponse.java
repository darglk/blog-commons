package com.darglk.blogcommons.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String email;
    private Boolean enabled;
    private List<AuthorityResponse> authorities;
}
