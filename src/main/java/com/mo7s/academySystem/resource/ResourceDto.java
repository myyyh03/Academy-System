package com.mo7s.academySystem.resource;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mo7s.academySystem.baseEntity.BaseEntityDto;
import com.mo7s.academySystem.resource.file.File;
import com.mo7s.academySystem.resource.file.FileDto;
import com.mo7s.academySystem.resource.text.Text;
import com.mo7s.academySystem.resource.text.TextDto;
import com.mo7s.academySystem.resource.video.Video;
import com.mo7s.academySystem.resource.video.VideoDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
       // include = JsonTypeInfo.As.PROPERTY,
        property = "resource_type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextDto.class, name = "T"),
        @JsonSubTypes.Type(value = VideoDto.class, name = "V"),
        @JsonSubTypes.Type(value = FileDto.class, name = "F")
}
)
public class ResourceDto extends BaseEntityDto {

    @Pattern(regexp = "^[a-zA-z]+(\\s[a-zA-Z]+)*$" , message = "Name must contain only letters")
    private String name;

    @Max(500)//lower than 500 mb
    private Integer size;

    @Pattern(regexp = "^https://[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}(/.*)?$" , message = "Invalid URL")
    private String url;


    public ResourceDto(Resource resource){
        this.setId(resource.getId());
        this.setName(resource.getName());
        this.setSize(resource.getSize());
        this.setUrl(resource.getUrl());
        this.setCreatedAt(resource.getCreatedAt());
    }

    /**
     * Ensures we return the correct DTO subtype (TextDto/VideoDto/FileDto) when serializing.
     */
    public static ResourceDto fromEntity(Resource resource) {
        if (resource == null) {
            return null;
        }
        if (resource instanceof Text text) {
            return new TextDto(text);
        }
        if (resource instanceof Video video) {
            return new VideoDto(video);
        }
        if (resource instanceof File file) {
            return new FileDto(file);
        }
        return new ResourceDto(resource);
    }

    /**
     * Creates the correct Resource entity subtype based on the DTO subtype.
     */
    public static Resource toEntity(ResourceDto resourceDto){
        if (resourceDto == null) {
            return null;
        }
        if (resourceDto instanceof TextDto textDto) {
            return TextDto.toEntity(textDto);
        }
        if (resourceDto instanceof VideoDto videoDto) {
            return VideoDto.toEntity(videoDto);
        }
        if (resourceDto instanceof FileDto fileDto) {
            return FileDto.toEntity(fileDto);
        }
        return Resource.builder()
                .id(resourceDto.getId())
                .name(resourceDto.name)
                .url(resourceDto.url)
                .size(resourceDto.size)
                .build();
    }
}
