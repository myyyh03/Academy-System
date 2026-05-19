package com.mo7s.academySystem.user;


import com.mo7s.academySystem.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NullMarked
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public class User extends BaseEntity implements UserDetails {

    private String firstName;

    private String lastName;

    private String userName;

    @Column(unique = true , nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

//    @Data already has getPassword()
//    @Override
//    public String getPassword() {
//        return password;
//    }

    // If i don't create this, the getUsername() method from UserDetails will be implemented by Lombok's @Data and it will return null because the field is userName not username
    public String getUserName(){
        return this.userName;
    }

    public String getUsername(){
        return this.userName;
    }
}
