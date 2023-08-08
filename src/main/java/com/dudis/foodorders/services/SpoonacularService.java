package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.SearchParametersDTO;
import com.dudis.foodorders.api.dtos.SpoonacularVideoDataDTO;
import com.dudis.foodorders.services.dao.SpoonacularDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class SpoonacularService {

    private final SpoonacularDAO spoonacularDAO;

    public String getRandomFoodTrivia() {
        return spoonacularDAO.getRandomTrivia();
    }

    public String getRandomJoke() {
        return spoonacularDAO.getRandomJoke();
    }

    public SpoonacularVideoDataDTO searchForRandomVideoRecipe(SearchParametersDTO parameters) {
        String query = parameters.getQuery();
        String diet = parameters.getDiet();
        String[] cuisineTable = parameters.getCuisine();
        String mealType = parameters.getMealType();
        BigDecimal videoLength = parameters.getMaxVideoLengthInSeconds();
        int resultsToSkip = new Random().nextInt(1, 5);

        String cuisine = Arrays.stream(cuisineTable)
            .reduce((prev, next) -> prev + "," + next)
            .orElse("");

        List<SpoonacularVideoDataDTO> spoonacularVideoDataDTOs = spoonacularDAO.searchForVideoRecipe(query, mealType, cuisine, diet, videoLength,
            resultsToSkip);

        return spoonacularVideoDataDTOs.size() > 0 ?
            spoonacularVideoDataDTOs.get(new Random().nextInt(spoonacularVideoDataDTOs.size())) :
            SpoonacularVideoDataDTO.builder()
                .title("No results found!").build();

    }
}
