package com.mo7s.academySystem.resource;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mo7s.academySystem.baseEntity.BaseEntity;
import com.mo7s.academySystem.lecture.Lecture;
import com.mo7s.academySystem.resource.file.File;
import com.mo7s.academySystem.resource.text.Text;
import com.mo7s.academySystem.resource.video.Video;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "resource_type")
@Entity
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "resource_type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Text.class, name = "T"),
        @JsonSubTypes.Type(value = Video.class, name = "V"),
        @JsonSubTypes.Type(value = File.class, name = "F")
}
)
public class Resource extends BaseEntity {

    private String name;

    private Integer size;

    private String url;

    @OneToOne(mappedBy = "resource")
    private Lecture lecture;
}
