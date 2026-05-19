package com.mo7s.academySystem.section;


import com.mo7s.academySystem.baseEntity.BaseEntity;
import com.mo7s.academySystem.course.Course;
import com.mo7s.academySystem.lecture.Lecture;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Section extends BaseEntity {

    private String name;
    private Integer sectionOrder;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "section")
    private List<Lecture> lectures;

}
