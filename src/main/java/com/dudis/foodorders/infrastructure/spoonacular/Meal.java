package com.dudis.foodorders.infrastructure.spoonacular;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("imageType")
public class Meal implements Comparable<Meal> {
     @JsonProperty("id")
     private Integer mealId;
     private String title;
     @JsonProperty("readyInMinutes")
     private Integer preparationTime;
     private BigDecimal servings;
     @JsonProperty("sourceUrl")
     private String recipeUrl;

     @Override
     public int compareTo(Meal o) {
          return this.mealId - o.mealId;
     }
}
