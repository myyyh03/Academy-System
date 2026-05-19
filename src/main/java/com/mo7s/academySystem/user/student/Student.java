package com.mo7s.academySystem.user.student;


import com.mo7s.academySystem.course.Course;
import com.mo7s.academySystem.user.Role;
import com.mo7s.academySystem.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Student extends User {
    private String address;

    private Integer level;

    @ManyToMany
    private List<Course> courses;

    @Override
    protected void onCreate() {
        setRole(Role.STUDENT);
    }
}
