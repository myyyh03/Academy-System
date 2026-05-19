package com.mo7s.academySystem.lecture;

import com.mo7s.academySystem.baseEntity.BaseEntityDto;
import com.mo7s.academySystem.resource.ResourceDto;
import com.mo7s.academySystem.section.SectionDto;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureDto extends BaseEntityDto {

    @Pattern(regexp = "^[a-zA-z]+(\\s[a-zA-Z]+)*$", message = "Lecture name must contain only letters")
    private String name;

    private ResourceDto resource;

    private SectionDto section;

    public LectureDto(Lecture lecture){
        this.setId(lecture.getId());
        this.setName(lecture.getName());
        this.setResource(new ResourceDto(lecture.getResource()));
        this.setSection(new SectionDto(lecture.getSection()));
        this.setCreatedAt(lecture.getCreatedAt());
    }

    public static Lecture toEntity(LectureDto lectureDto){
        return Lecture.builder()
                .id(lectureDto.getId())
                .name(lectureDto.name)
                .resource(ResourceDto.toEntity(lectureDto.getResource()))
                .section(SectionDto.toEntity(lectureDto.getSection()))
                .build();
    }

}
