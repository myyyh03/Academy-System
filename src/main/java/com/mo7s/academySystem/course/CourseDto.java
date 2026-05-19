package com.mo7s.academySystem.course;

import com.mo7s.academySystem.user.author.AuthorDto;
import com.mo7s.academySystem.baseEntity.BaseEntityDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto extends BaseEntityDto {

    @Size(min = 4 , max = 100)
    @NotNull
    private String title;

    @Size(max = 1000)
    private String description;

    @NotEmpty
    private List<AuthorDto> authors;

    public CourseDto(Course course){
        this.setId(course.getId());
        this.setTitle(course.getTitle());
        this.setDescription(course.getDescription());
        this.setAuthors(course.getAuthors().stream().map(AuthorDto::new).toList());
        this.setCreatedAt(course.getCreatedAt());
    }

    public static Course toEntity(CourseDto dto){
        return Course.builder()
                .id(dto.getId())
                .title(dto.title)
                .description(dto.description)
                .authors(dto.authors.stream().map(AuthorDto::toEntity).toList())
                .build();
    }
}
