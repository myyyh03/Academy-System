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
public class AuthenticationResponse {
    private String userName;
    private Role role;
    private Integer userId;
    private String token;
}
