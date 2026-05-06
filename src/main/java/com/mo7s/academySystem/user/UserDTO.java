package com.mo7s.academySystem.user;

import com.mo7s.academySystem.baseEntity.BaseEntityDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends BaseEntityDto {
    @Size(min = 3 , max = 15)
    @Pattern(regexp = "^[a-zA-z]+(\\s[a-zA-Z]+)*$", message = "First name must contain only letters")
    private String firstName;

    @Size(min = 3 , max = 15)
    @Pattern(regexp = "^[a-zA-z]+(\\s[a-zA-Z]+)*$", message = "Last name must contain only letters")
    private String lastName;

    @Size(min = 6 , max = 100)
    private String password;

    @Email
    @NotNull
    private String email;

    private Role role;

    public UserDTO(User user) {
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setEmail(user.getEmail());
        this.setRole(user.getRole());
        this.setPassword(user.getPassword());;
    }

    // User is not an Entity, it shouldn't have toEntity()
//    public static User toEntity(UserDTO dto){
//        return User.builder()
//                .firstName(dto.getFirstName())
//                .lastName(dto.getLastName())
//                .email(dto.getEmail())
//                .role(dto.getRole())
//                .password(dto.getPassword())
//                .build();
//    }
}
