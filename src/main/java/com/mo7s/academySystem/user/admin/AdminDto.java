package com.mo7s.academySystem.user.admin;

import com.mo7s.academySystem.user.UserDTO;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto extends UserDTO {


    @Pattern(regexp = "^admin_[a-zA-Z0-9]+$" , message = "Username must start with 'admin_' followed by your username")
    private String userName;



    public AdminDto(Admin admin) {
        this.setId(admin.getId());
        this.setUserName(admin.getUserName());
        this.setFirstName(admin.getFirstName());
        this.setLastName(admin.getLastName());
        this.setEmail(admin.getEmail());
        this.setRole(admin.getRole());
        this.setPassword(admin.getPassword());
        this.setCreatedAt(admin.getCreatedAt());
    }

    public static Admin toEntity(AdminDto adminDto){
        return Admin.builder()
                .id(adminDto.getId())
                .userName(adminDto.getUserName())
                .firstName(adminDto.getFirstName())
                .lastName(adminDto.getLastName())
                .email(adminDto.getEmail())
                .role(adminDto.getRole())
                .password(adminDto.getPassword())
                .build();
    }

    public String toString() {
        return "AdminDto{" +
                "id=" + getId() +
                ", userName='" + getUserName() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role=" + getRole() +
                '}';
    }

}
