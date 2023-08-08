package com.dudis.foodorders.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpoonacularVideoDataDTO {

    private String title;
    private Integer videoLength;
    private Integer views;
    private String dishPreview;
    private String youtubeVideoLink;
}
