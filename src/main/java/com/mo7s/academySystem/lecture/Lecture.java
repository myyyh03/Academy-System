package com.mo7s.academySystem.lecture;


import com.mo7s.academySystem.baseEntity.BaseEntity;
import com.mo7s.academySystem.resource.Resource;
import com.mo7s.academySystem.section.Section;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Lecture extends BaseEntity {


    private String name;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;
}
