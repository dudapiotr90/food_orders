package com.dudis.foodorders.api.controllers;


import com.dudis.foodorders.api.mappers.FoodMapper;
import com.dudis.foodorders.integration.support.ControllersSupport;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ImageController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class ImageControllerWebMvcTest extends ControllersSupport {

    private MockMvc mockMvc;

    @MockBean
    private FoodMapper foodMapper;

    @Test
    void showImageWorksCorrectly() throws Exception {
        // Given
        Integer someOwnerId = 4;
        Integer someRestaurantId = 8;
        String someImagePath = "some/image/path/to/show/";
        Mockito.when(foodMapper.mapFoodImage(someImagePath)).thenReturn("some/coded/path");

        // When, Then
        mockMvc.perform(MockMvcRequestBuilders.get(ImageController.SHOW_IMAGE, someOwnerId, someRestaurantId)
                .param("foodImagePath", someImagePath))
            .andExpect(status().isOk())
            .andExpect(view().name("image"))
            .andExpect(model().attributeExists("foodImagePath", "restaurantId", "ownerId"));
    }
}