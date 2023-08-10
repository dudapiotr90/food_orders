package com.dudis.foodorders.infrastructure.spoonacular;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Diet {
    GLUTEN_FREE("gluten free"),
    KETOGENIC("ketogenic"),
    VEGETARIAN("vegetarian"),
    LACTO_VEGETARIAN("lacto-vegetarian"),
    OVO_VEGETARIAN("ovo-vegetarian"),
    VEGAN("vegan"),
    PESCETARIAN("pescetarian"),
    PALEO("paleo"),
    PRIMAL("primal");
    private final String diet;
}
