package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.FoodDTO;
import com.dudis.foodorders.api.mappers.MenuMapper;
import com.dudis.foodorders.infrastructure.security.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class ImageController {

    public static final String SHOW_IMAGE = "/owner/{id}/manage/{restaurantId}/showImage";
    private final SecurityUtils securityUtils;
    private final MenuMapper menuMapper;

    @GetMapping(SHOW_IMAGE)
    public String showImage(
        @PathVariable(value = "id") Integer ownerId,
        @PathVariable(value = "restaurantId") Integer restaurantId,
        @RequestParam("foodImagePath") String foodImagePath,
        Model model,
        HttpServletRequest request
    ) throws IOException {
        securityUtils.checkAccess(ownerId, request);
        String foodImageAsBase64 = menuMapper.mapFoodImage(foodImagePath);
        model.addAttribute("foodImagePath", foodImageAsBase64);
        model.addAttribute("restaurantId", restaurantId);
        model.addAttribute("ownerId", ownerId);
        return "image.html";
    }
}
