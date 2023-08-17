package com.dudis.foodorders.api.controllers;

import com.dudis.foodorders.api.dtos.*;
import com.dudis.foodorders.integration.configuration.ControllersSupport;
import com.dudis.foodorders.services.BillService;
import com.dudis.foodorders.services.OwnerService;
import com.dudis.foodorders.services.RestaurantService;
import com.dudis.foodorders.utils.BillUtils;
import com.dudis.foodorders.utils.OrderUtils;
import com.dudis.foodorders.utils.OwnerUtils;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.UUID;

import static com.dudis.foodorders.api.controllers.RestaurantController.*;
import static com.dudis.foodorders.utils.DeliveryAddressUtils.someAddressesDTO;
import static com.dudis.foodorders.utils.DeliveryAddressUtils.someDeliveryAddressDTO1;
import static com.dudis.foodorders.utils.FoodUtils.someFoodDTO1;
import static com.dudis.foodorders.utils.FoodUtils.someFoodsListDTO;
import static com.dudis.foodorders.utils.OrderUtils.someOrdersDTO;
import static com.dudis.foodorders.utils.RestaurantUtils.someRestaurantDTO1;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RestaurantController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class RestaurantControllerWebMvcTest extends ControllersSupport {
    public static final int RESTAURANT_ID = 4;
    public static final int OWNER_ID = 1;
    public static final String SOME_PATH_FOR_TEST = "some/path/for/test";

    private MockMvc mockMvc;
    @MockBean
    private RestaurantService restaurantService;
    @MockBean
    private BillService billService;
    @MockBean
    private OwnerService ownerService;


    @Test
    void manageRestaurantWorksCorrectly() throws Exception {
        // Given
        String sortBy = "foodId";
        String sortHow = "asc";
        String deliverySortBy = "deliveryAddressId";
        String deliverySortHow = "asc";
        Page<DeliveryAddressDTO> deliveries = new PageImpl<>(someAddressesDTO());
        Page<FoodDTO> foods = new PageImpl<>(someFoodsListDTO());
        when(restaurantService.findProcessingRestaurant(RESTAURANT_ID))
            .thenReturn(someRestaurantDTO1());
        when(restaurantService.getPaginatedMenu(1, 4, sortBy, sortHow, RESTAURANT_ID))
            .thenReturn(foods);
        when(restaurantService.getPaginatedDeliveryAddresses(1, 4, deliverySortBy, deliverySortHow, RESTAURANT_ID))
            .thenReturn(deliveries);
        when(restaurantService.findOrdersByInProgress(RESTAURANT_ID, true))
            .thenReturn(someOrdersDTO());
        when(restaurantService.findPayedOrdersAndNotRealized(anyInt()))
            .thenReturn(someOrdersDTO());

        // When
        mockMvc.perform(get(OWNER + MANAGE, OWNER_ID, RESTAURANT_ID))
            .andExpect(status().isOk())
            .andExpect(view().name("local_manager"))
            .andExpect(model().attributeExists(
                "ownerId",
                "restaurant",
                "foods",
                "deliveries",
                "orders",
                "toRealized",
                "deliveryAddress",
                "menuPage",
                "deliveriesPage",
                "orderToBill"
            ));
    }

    @Test
    void modifyMenuWorksCorrectly() throws Exception {
        // Given, When, Then
        mockMvc.perform(get(OWNER + MODIFY_MENU, OWNER_ID, RESTAURANT_ID))
            .andExpect(status().isOk())
            .andExpect(view().name("menu_manager"))
            .andExpect(model().attributeExists(
                "restaurantId",
                "ownerId",
                "foodTypes",
                "food"
            ));
    }

    @Test
    void addMenuPositionWorksCorrectly() throws Exception {
        // Given
        FoodDTO food = someFoodDTO1();
        MockMultipartFile someFile = getMockMultipartFile();
        LinkedMultiValueMap<String, String> params = prepareParamsForModifyMenu(food);
        when(restaurantService.addFoodToMenu(food, RESTAURANT_ID, someFile)).thenReturn(SOME_PATH_FOR_TEST);

        // When, Then
        mockMvc.perform(multipart(OWNER + ADD_MENU_POSITION, OWNER_ID, RESTAURANT_ID)
                .file("image", someFile.getBytes())
                .params(params))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlTemplate(OWNER + MODIFY_MENU, OWNER_ID, RESTAURANT_ID));
    }

    @Test
    void updateMenuPositionWorksCorrectly() throws Exception {
        // Given
        MockMultipartFile someFile = getMockMultipartFile();
        FoodDTO food = someFoodDTO1();
        LinkedMultiValueMap<String, String> params = prepareParamsForModifyMenu(food);
        when(restaurantService.updateMenuPosition(food, RESTAURANT_ID, someFile)).thenReturn(SOME_PATH_FOR_TEST);

        // When, Then
        mockMvc.perform(multipart(OWNER + ADD_MENU_POSITION, OWNER_ID, RESTAURANT_ID)
                .file("image", someFile.getBytes())
                .params(params))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlTemplate(OWNER + MODIFY_MENU, OWNER_ID, RESTAURANT_ID));
    }

    @Test
    void removeMenuPositionWorksCorrectly() throws Exception {
        // Given
        Integer foodId = 5;
        when(restaurantService.deleteFoodFromMenu(foodId)).thenReturn("Already removed","Removed Successfully");

        // When, Then
        mockMvc.perform(delete(OWNER + DELETE_MENU_POSITION, OWNER_ID, RESTAURANT_ID,foodId))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlTemplate(OWNER + MANAGE, OWNER_ID, RESTAURANT_ID));
    }

    @Test
    void deleteAddressWorksCorrectly() throws Exception {
        // Given
        Integer addressId = 5;
        doNothing().when(restaurantService).deleteAddressFromRestaurant(addressId);

        // When, Then
        mockMvc.perform(delete(OWNER + DELETE_ADDRESS, OWNER_ID, RESTAURANT_ID,addressId))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlTemplate(OWNER + MANAGE, OWNER_ID, RESTAURANT_ID));
    }

    @Test
    void addAddressWorksCorrectly() throws Exception {
        // Given
        DeliveryAddressDTO someAddress = someDeliveryAddressDTO1();
        doNothing().when(restaurantService).addDeliveryAddress(someAddress,RESTAURANT_ID);
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        someAddress.asMap().forEach(params::add);
        // When, Then
        mockMvc.perform(post(OWNER + ADD_ADDRESS, OWNER_ID, RESTAURANT_ID)
                .params(params))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlTemplate(OWNER + MANAGE, OWNER_ID, RESTAURANT_ID));
    }

    @Test
    void issueReceiptWorksCorrectly() throws Exception {
        // Given
        OrderDTO orderDTO = OrderUtils.someOrderDTO3();
        BillDTO someBill = BillUtils.someBillDTO2();
        OwnerDTO someOwner = OwnerUtils.someOwnerDTO1();
        when(ownerService.findOwnerById(OWNER_ID)).thenReturn(someOwner);
        when(billService.issueReceipt(orderDTO, someOwner)).thenReturn(someBill);

        // When, Then
        mockMvc.perform(post(OWNER + ISSUE_RECEIPT, OWNER_ID, RESTAURANT_ID)
                .flashAttr("orderToBill",orderDTO))
            .andExpect(status().isOk())
            .andExpect(view().name("bill_issued"))
            .andExpect(model().attributeExists("owner", "bill", "restaurantId"));
    }

    @Test
    void realizeOrderWorksCorrectly() throws Exception {
        // Given
        String orderNumber = UUID.randomUUID().toString();
        doNothing().when(restaurantService).realizeOrder(orderNumber,RESTAURANT_ID);

        // When, Then
        mockMvc.perform(put(OWNER + REALIZE_ORDER, OWNER_ID, RESTAURANT_ID)
                .param("orderNumber",orderNumber))
            .andExpect(status().isMovedPermanently())
            .andExpect(redirectedUrlTemplate(OWNER + MANAGE, OWNER_ID, RESTAURANT_ID));
    }

    @NotNull
    private MockMultipartFile getMockMultipartFile() {
        byte[] byteArray = new byte[]{1, 2, 3, 4, 5};
        return new MockMultipartFile("someFileName", byteArray);
    }

    @NotNull
    private LinkedMultiValueMap<String, String> prepareParamsForModifyMenu(FoodDTO food) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        food.asMap().forEach(params::add);
        return params;
    }
}