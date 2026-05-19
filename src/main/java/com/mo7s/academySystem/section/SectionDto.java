package com.mo7s.academySystem.section;

import com.mo7s.academySystem.baseEntity.BaseEntityDto;
import com.mo7s.academySystem.course.CourseDto;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionDto extends BaseEntityDto {

    @Pattern(regexp = "^[a-zA-z]+(\\s[a-zA-Z]+)*$", message = "Section name must contain only letters")
    private String name;

    @Positive
    private Integer sectionOrder;

    private CourseDto course;

    public SectionDto(Section section){
        this.setId(section.getId());
        this.setName(section.getName());
        this.setSectionOrder(section.getSectionOrder());
        this.setCourse(new CourseDto(section.getCourse()));
        this.setCreatedAt(section.getCreatedAt());
    }

    public static Section toEntity(SectionDto sectionDto){
        return Section.builder()
                .id(sectionDto.getId())
                .name(sectionDto.name)
                .sectionOrder(sectionDto.sectionOrder)
                .course(CourseDto.toEntity(sectionDto.course))
                .build();
    }
}
