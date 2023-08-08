package com.dudis.foodorders.infrastructure.spoonacular;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Cuisine {
    AFRICAN("African"),
    ASIAN("Asian"),
    AMERICAN("American"),
    BRITISH("British"),
    CAJUN("Cajun"),
    CARIBBEAN("Caribbean"),
    CHINESE("Chinese"),
    EASTERN_EUROPEAN("Eastern European"),
    EUROPEAN("European"),
    FRENCH("French"),
    GERMAN("German"),
    GREEK("Greek"),
    INDIAN("Indian"),
    IRISH("Irish"),
    ITALIAN("Italian"),
    JAPANESE("Japanese"),
    JEWISH("Jewish"),
    KOREAN("Korean"),
    LATIN_AMERICAN("Latin American"),
    MEDITERRANEAN("Mediterranean"),
    MEXICAN("Mexican"),
    MIDDLE_EASTERN("Middle Eastern"),
    NORDIC("Nordic"),
    SOUTHERN("Southern"),
    SPANISH("Spanish"),
    THAI("Thai"),
    VIETNAMESE("Vietnamese");

    private final String cuisine;
}
