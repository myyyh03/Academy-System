package com.mo7s.academySystem.user.author;

import com.mo7s.academySystem.course.Course;
import com.mo7s.academySystem.user.Role;
import com.mo7s.academySystem.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Data //Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.
@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder // for Builder Design Pattern
public class Author extends User {

    @ManyToMany(mappedBy = "authors")
    private List<Course> courses;


    protected void onCreate() {
        setRole(Role.AUTHOR);
    }

}
