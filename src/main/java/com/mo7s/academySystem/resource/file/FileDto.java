package com.mo7s.academySystem.resource.file;

import com.mo7s.academySystem.resource.ResourceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDto extends ResourceDto{


    private String fileType;


    public FileDto(File file) {
        super(file);
        this.setFileType(file.getType());
    }

    public static File toEntity(FileDto fileDto){
        return File.builder()
                 .id(fileDto.getId())
                .name(fileDto.getName())
                .url(fileDto.getUrl())
                .size(fileDto.getSize())
                .type(fileDto.getFileType())
                .build();
    }

}
