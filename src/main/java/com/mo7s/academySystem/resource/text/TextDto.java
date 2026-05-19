package com.mo7s.academySystem.resource.text;

import com.mo7s.academySystem.resource.ResourceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextDto extends ResourceDto {

    private String content;

     public TextDto(Text text) {
         super(text);
         this.setContent(text.getContent());
    }

    public static Text toEntity(TextDto textDto){
         return Text.builder()
                  .id(textDto.getId())
                 .name(textDto.getName())
                 .url(textDto.getUrl())
                 .size(textDto.getSize())
                 .content(textDto.getContent())
                 .build();
    }
}
