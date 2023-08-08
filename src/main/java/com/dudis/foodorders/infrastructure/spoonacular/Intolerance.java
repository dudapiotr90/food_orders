package com.dudis.foodorders.infrastructure.spoonacular;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Intolerance {
    DAIRY("Dairy"),
    EGG("Egg"),
    GLUTEN("Gluten"),
    GRAIN("Grain"),
    PEANUT("Peanut"),
    SEAFOOD("Seafood"),
    SESAME("Sesame"),
    SHELLFISH("Shellfish"),
    SOY("Soy"),
    SULFITE("Sulfite"),
    TREE_NUT("Tree Nut"),
    WHEAT("Wheat");

    private final String intolerance;

}
