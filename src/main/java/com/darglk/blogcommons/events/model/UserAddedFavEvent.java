package com.darglk.blogcommons.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddedFavEvent {
    private String userId;
    private String username;
    private String favUserId;
}
