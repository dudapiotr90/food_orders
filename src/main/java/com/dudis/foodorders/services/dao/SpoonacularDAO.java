package com.dudis.foodorders.services.dao;

import com.dudis.foodorders.api.dtos.SpoonacularVideoDataDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SpoonacularDAO {

    String youtubeLink = "https://www.youtube.com/watch?v=";

    String getRandomTrivia();

    String getRandomJoke();

    List<SpoonacularVideoDataDTO> searchForVideoRecipe(String query, String mealType, String cuisine, String diet, BigDecimal videoLength, int resultsToSkip);

}
