package com.mo7s.academySystem.user.author;

import com.mo7s.academySystem.user.UserDTO;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AuthorDto extends UserDTO {

    @Pattern(regexp = "^author_[a-zA-Z0-9]+$" , message = "Username must start with 'author_' followed by your username")
    private String userName;


    public AuthorDto(Author author){
        this.setId(author.getId());
        this.setFirstName(author.getFirstName());
        this.setLastName(author.getLastName());
        this.setEmail(author.getEmail());
        this.setUserName(author.getUserName());
        this.setRole(author.getRole());
        this.setPassword(author.getPassword());;
        this.setCreatedAt(author.getCreatedAt());
    }

    public static Author toEntity(AuthorDto dto){
        return Author.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .userName(dto.getUserName())
                .role(dto.getRole())
                .password(dto.getPassword())
                .build();
    }

}
