package com.mo7s.academySystem.user.student;

import com.mo7s.academySystem.user.UserDTO;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto extends UserDTO {


    @NotNull
    @Pattern(regexp = "^student_[a-zA-Z0-9]+$" , message = "Username must start with 'student_' followed by your username")
    private String userName;


    private String address;

    @Positive
    private Integer level;

    public StudentDto(Student student) {
        this.setId(student.getId());
        this.setAddress(student.getAddress());
        this.setLevel(student.getLevel());
        this.setFirstName(student.getFirstName());
        this.setLastName(student.getLastName());
        this.setUserName(student.getUserName());
        this.setEmail(student.getEmail());
        this.setRole(student.getRole());
        this.setPassword(student.getPassword());
        this.setCreatedAt(student.getCreatedAt());
    }

    public static Student toEntity(StudentDto dto) {
        return Student.builder()
                .id(dto.getId())
                .address(dto.getAddress())
                .level(dto.getLevel())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .userName(dto.getUserName())
                .email(dto.getEmail())
                .role(dto.getRole())
                .password(dto.getPassword())
                .build();
    }
}
