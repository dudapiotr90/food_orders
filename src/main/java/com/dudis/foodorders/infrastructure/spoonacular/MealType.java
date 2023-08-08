package com.dudis.foodorders.infrastructure.spoonacular;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MealType {

    MAIN_COURSE("main course"),
    SIDE_DISH("side dish"),
    DESSERT("dessert"),
    APPETIZER("appetizer"),
    SALAD("salad"),
    BREAD("bread"),
    BREAKFAST("breakfast"),
    SOUP("soup"),
    BEVERAGE("beverage"),
    SAUCE("sauce"),
    MARINADE("marinade"),
    DRINK("drink"),
    FINGERFOOD("fingerfood"),
    SNACK("snack");

    private final String mealType;
}