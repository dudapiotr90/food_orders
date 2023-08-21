package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.SearchSpoonacularParametersDTO;
import com.dudis.foodorders.services.SpoonacularService;
import com.dudis.foodorders.utils.SpoonacularUtils;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.regex.Pattern;

import static com.dudis.foodorders.api.controllers.SpoonacularController.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SpoonacularController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class SpoonacularControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private SpoonacularService spoonacularService;

    @Test
    void showAdditionalPortalFeaturesWorksCorrectly() throws Exception {
        // Given, When, Then
        MvcResult result = mockMvc.perform(get(EXTRAS))
            .andExpect(status().isOk())
            .andExpect(view().name("extras"))
            .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Pattern pattern = Pattern.compile("Check out our awesome additional features!");
        Assertions.assertTrue(pattern.matcher(responseContent).find());
    }

    @Test
    void showTriviaWorksCorrectly() throws Exception {
        // Given
        String trivia = "random Trivia";
        when(spoonacularService.getRandomFoodTrivia()).thenReturn(trivia);

        // When, Then
        mockMvc.perform(get(TRIVIA))
            .andExpect(status().isOk())
            .andExpect(view().name("extras"))
            .andExpect(model().attributeExists(
                "trivia",
                "cuisines",
                "diets",
                "intolerances",
                "mealTypes",
                "searchParameters"
            ))
            .andExpect(model().attributeDoesNotExist(
                  "videoData",
                "joke"
            ));
    }

    @Test
    void showJokeWorksCorrectly() throws Exception {
        // Given
        String joke = "random Joke";
        when(spoonacularService.getRandomJoke()).thenReturn(joke);

        // When, Then
        mockMvc.perform(get(JOKE))
            .andExpect(status().isOk())
            .andExpect(view().name("extras"))
            .andExpect(model().attributeExists(
                "joke",
                "cuisines",
                "diets",
                "intolerances",
                "mealTypes",
                "searchParameters"
            ))
            .andExpect(model().attributeDoesNotExist(
                "videoData",
                "trivia"
            ));
    }

    @Test
    void searchRandomVideoRecipeWorksCorrectly() throws Exception {
        // Given
        SearchSpoonacularParametersDTO parameters = SpoonacularUtils.someParameters();
        when(spoonacularService.searchForRandomVideoRecipe(parameters))
            .thenReturn(SpoonacularUtils.someVideoData1());

        // When, Then
        mockMvc.perform(get(VIDEO_RECIPE)
            .flashAttr("searchParameters",parameters))
            .andExpect(status().isOk())
            .andExpect(view().name("extras"))
            .andExpect(model().attributeExists(
                "videoData",
                "cuisines",
                "diets",
                "intolerances",
                "mealTypes",
                "searchParameters"
            ))
            .andExpect(model().attributeDoesNotExist(
                "joke",
                "trivia"
            ));
    }
}