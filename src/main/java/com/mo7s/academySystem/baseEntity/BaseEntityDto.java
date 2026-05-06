package com.mo7s.academySystem.baseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntityDto {
    private Integer id;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    private String createdBy;
    private String lastModifiedBy;

    public BaseEntityDto(BaseEntity entity){
        this.id = entity.getId();
        this.createdAt = entity.getCreatedAt();
        this.lastModifiedAt = entity.getLastModifiedAt();
        this.createdBy = entity.getCreatedBy();
        this.lastModifiedBy = entity.getLastModifiedBy();
    }

    public static BaseEntity toEntity(BaseEntityDto baseEntityDto){
        return BaseEntity.builder()
                .id(baseEntityDto.getId())
                .createdAt(baseEntityDto.getCreatedAt())
                .lastModifiedAt(baseEntityDto.getLastModifiedAt())
                .createdBy(baseEntityDto.getCreatedBy())
                .lastModifiedBy(baseEntityDto.getLastModifiedBy())
                .build();
    }
}
