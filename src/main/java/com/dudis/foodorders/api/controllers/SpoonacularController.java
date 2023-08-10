package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.SearchSpoonacularParametersDTO;
import com.dudis.foodorders.api.dtos.SpoonacularVideoDataDTO;
import com.dudis.foodorders.infrastructure.spoonacular.Cuisine;
import com.dudis.foodorders.infrastructure.spoonacular.Diet;
import com.dudis.foodorders.infrastructure.spoonacular.Intolerance;
import com.dudis.foodorders.infrastructure.spoonacular.MealType;
import com.dudis.foodorders.services.SpoonacularService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
public class SpoonacularController {

    public static final String EXTRAS = "/extras";
    public static final String TRIVIA = "/extras/trivia";
    public static final String JOKE = "/extras/joke";
    public static final String VIDEO_RECIPE = "/extras/videoRecipe";

    private final SpoonacularService spoonacularService;


    @GetMapping(EXTRAS)
    public String showAdditionalPortalFeatures(ModelMap modelMap) {
        prepareAttributes(modelMap);
        return "extras";
    }

    @GetMapping(TRIVIA)
    public String showTrivia(ModelMap modelMap) {
        String trivia = spoonacularService.getRandomFoodTrivia();
        modelMap.addAttribute("trivia", trivia);
        prepareAttributes(modelMap);
        return "extras";
    }

    @GetMapping(JOKE)
    public String showJoke(ModelMap modelMap) {
        String joke = spoonacularService.getRandomJoke();
        modelMap.addAttribute("joke", joke);
        prepareAttributes(modelMap);
        return "extras";
    }

    @GetMapping(VIDEO_RECIPE)
    public String searchRandomVideoRecipe(
        @ModelAttribute("searchParameters") SearchSpoonacularParametersDTO parameters,
        ModelMap modelMap
    ){
        SpoonacularVideoDataDTO videoData = spoonacularService.searchForRandomVideoRecipe(parameters);
        modelMap.addAttribute("videoData", videoData);
        prepareAttributes(modelMap);
        return "extras";
    }


    private void prepareAttributes(ModelMap modelMap) {
        List<Cuisine> cuisines = Arrays.asList(Cuisine.values());
        List<Diet> diets = Arrays.asList(Diet.values());
        List<Intolerance> intolerances = Arrays.asList(Intolerance.values());
        List<MealType> mealTypes = Arrays.asList(MealType.values());
        modelMap.addAttribute("cuisines",cuisines);
        modelMap.addAttribute("diets",diets);
        modelMap.addAttribute("intolerances",intolerances);
        modelMap.addAttribute("mealTypes",mealTypes);
        modelMap.addAttribute("searchParameters", SearchSpoonacularParametersDTO.builder().build());
    }
}
