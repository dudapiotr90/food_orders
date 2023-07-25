package com.dudis.foodorders.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class FoodImage {
    Integer foodImageId;
    String filePath;
}
