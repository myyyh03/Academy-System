package com.mo7s.academySystem.course;

import com.mo7s.academySystem.user.author.Author;
import com.mo7s.academySystem.baseEntity.BaseEntity;
import com.mo7s.academySystem.section.Section;
import com.mo7s.academySystem.user.student.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseEntity {

    @NotNull
    private String title;


    private String description;

    @ManyToMany
    private List<Author> authors;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    @OneToMany(mappedBy = "course")
    private List<Section> sections;
}
