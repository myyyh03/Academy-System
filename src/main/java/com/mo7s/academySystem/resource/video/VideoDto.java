package com.mo7s.academySystem.resource.video;

import com.mo7s.academySystem.resource.ResourceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto extends ResourceDto {

    private Integer length;

    public VideoDto(Video video) {
        super(video);
        this.setLength(video.getLength());
    }

    public static Video toEntity(VideoDto videoDto){
        return Video.builder()
                .id(videoDto.getId())
                .name(videoDto.getName())
                .url(videoDto.getUrl())
                .size(videoDto.getSize())
                .length(videoDto.getLength())
                .build();
    }
}
