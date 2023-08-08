package com.dudis.foodorders.infrastructure.spoonacular;

import com.dudis.foodorders.api.dtos.SpoonacularVideoDataDTO;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.infrastructure.spoonacular.api.MiscApi;
import com.dudis.foodorders.infrastructure.spoonacular.model.GetARandomFoodJoke200Response;
import com.dudis.foodorders.infrastructure.spoonacular.model.GetRandomFoodTrivia200Response;
import com.dudis.foodorders.infrastructure.spoonacular.model.SearchFoodVideos200Response;
import com.dudis.foodorders.services.dao.SpoonacularDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SpoonacularClientImpl implements SpoonacularDAO {

    private static final String VIDEO_EXCEPTION_MESSAGE = "No video found with given parameters[%s,%s,%s,%s,%s]";

    private final MiscApi miscApi;

    @Override
    public String getRandomTrivia() {
        GetRandomFoodTrivia200Response randomTriviaResponse = Optional.ofNullable(miscApi.getRandomFoodTrivia().block())
            .orElseThrow(() -> new NotFoundException("No food trivia available at the moment! Try again later"));

        return randomTriviaResponse.getText();
    }

    @Override
    public String getRandomJoke() {
        GetARandomFoodJoke200Response randomFoodJoke = Optional.ofNullable(miscApi.getARandomFoodJoke().block())
            .orElseThrow(() -> new NotFoundException("No food joke available at the moment! Try again later"));
        return randomFoodJoke.getText();
    }

    @Override
    public List<SpoonacularVideoDataDTO> searchForVideoRecipe(String query, String mealType, String cuisine, String diet, BigDecimal videoLength,
                                                              int resultsToSkip) {

        SearchFoodVideos200Response searchFoodVideos200ResponseMono = Optional.ofNullable(
                miscApi.searchFoodVideos(query, mealType, cuisine, diet, null, null, null, videoLength, resultsToSkip, 10).block())
            .orElseThrow(() -> new NotFoundException(
                String.format(VIDEO_EXCEPTION_MESSAGE, query, mealType, cuisine, diet, videoLength)
            ));
        return searchFoodVideos200ResponseMono.getVideos().stream()
            .map(video-> SpoonacularVideoDataDTO.builder()
                .title(video.getTitle())
                .videoLength(video.getLength())
                .views(video.getViews())
                .dishPreview(video.getThumbnail())
                .youtubeVideoLink(youtubeLink +video.getYouTubeId())
                .build())
            .toList();

    }
}
