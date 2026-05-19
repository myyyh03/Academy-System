package com.mo7s.academySystem.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {


    private final UserService userService;

    public User loadUserByUsername(String username) {
        return userService.getUserByUserName(username);
    }
}
