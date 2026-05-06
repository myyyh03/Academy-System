package com.mo7s.academySystem.user.auth;


import com.mo7s.academySystem.config.JwtService;
import com.mo7s.academySystem.user.User;
import com.mo7s.academySystem.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    UserService userService;
    AuthenticationManager authenticationManager;
    BCryptPasswordEncoder passwordEncoder;
    JwtService jwtService;

    public ResponseEntity<?> register(RegisterRequest request){
        try {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
            User user = userService.register(request);
            String jwtToken = jwtService.generateToken(user);
            return ResponseEntity.ok()
                    .body(AuthenticationResponse.builder()
                            .userName(user.getUserName())
                            .role(user.getRole())
                            .userId(user.getId())
                            .token(jwtToken)
                    .build());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    public ResponseEntity<?> authenticate(AuthenticationRequest request){
        try {
            User user = userService.getUserByUserName(request.getUserName());
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUserName(), request.getPassword()
                        )
                );
                String token = jwtService.generateToken(user);
                return ResponseEntity.ok()
                        .body(AuthenticationResponse.builder()
                                .userName(user.getUserName())
                                .role(user.getRole())
                                .userId(user.getId())
                                .token(token)
                                .build());
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
