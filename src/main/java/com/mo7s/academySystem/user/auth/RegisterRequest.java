package com.mo7s.academySystem.user.auth;

import com.mo7s.academySystem.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private Role role;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
