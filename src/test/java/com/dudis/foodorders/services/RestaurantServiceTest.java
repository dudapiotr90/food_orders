package com.dudis.foodorders.services;

import com.dudis.foodorders.api.dtos.*;
import com.dudis.foodorders.api.mappers.*;
import com.dudis.foodorders.domain.*;
import com.dudis.foodorders.domain.exception.NotFoundException;
import com.dudis.foodorders.domain.exception.SearchingException;
import com.dudis.foodorders.services.dao.RestaurantDAO;
import com.dudis.foodorders.utils.OwnerUtils;
import jakarta.persistence.EntityNotFoundException;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static com.dudis.foodorders.utils.DeliveryAddressUtils.*;
import static com.dudis.foodorders.utils.FoodUtils.*;
import static com.dudis.foodorders.utils.MenuUtils.someMenu1;
import static com.dudis.foodorders.utils.MenuUtils.someMenuDTO;
import static com.dudis.foodorders.utils.OrderUtils.*;
import static com.dudis.foodorders.utils.RestaurantUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, OutputCaptureExtension.class})
class RestaurantServiceTest {

    TestLogger logger = TestLoggerFactory.getTestLogger(RestaurantService.class);
    @InjectMocks
    private RestaurantService restaurantService;
    @Mock
    private RestaurantDAO restaurantDAO;
    @Mock
    private DeliveryAddressService deliveryAddressService;
    @Mock
    private BillService billService;
    @Mock
    private OrderService orderService;
    @Mock
    private FoodService foodService;
    @Mock
    private StorageService storageService;
    @Mock
    private PageableService pageableService;
    @Mock
    private RestaurantMapper restaurantMapper;
    @Mock
    private MenuMapper menuMapper;
    @Mock
    private FoodMapper foodMapper;
    @Mock
    private DeliveryAddressMapper deliveryAddressMapper;
    @Mock
    private OrderMapper orderMapper;

    public static Stream<Arguments> deleteFoodFromMenuWorksCorrectly() {
        return Stream.of(
            Arguments.of("Already removed", null, 123),
            Arguments.of("Removed Successfully", "some/image/path/to/delete", 765)
        );
    }

    @AfterEach
    public void clearLoggers() {
        TestLoggerFactory.clear();
    }

    @Test
    void findOwnerLocalsWorksCorrectly() {
        // Given
        List<Restaurant> expected = someRestaurants();
        when(restaurantDAO.findRestaurantsWhereOwnerId(anyInt())).thenReturn(expected);

        // When
        List<Restaurant> result = restaurantService.findOwnerLocals(4);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void addLocalWorksCorrectly() {
        // Given
        Restaurant someRestaurant = someRestaurant1();
        doNothing().when(restaurantDAO).addLocal(any(Restaurant.class));

        // When
        restaurantService.addLocal(someRestaurant);

        // Then
        verify(restaurantDAO, times(1)).addLocal(any(Restaurant.class));
    }

    @Test
    void findProcessingRestaurantWorksCorrectly() {
        // Given
        Restaurant someRestaurant = someRestaurant1();
        RestaurantDTO expected = someRestaurantDTO2();
        Integer someId = 87;
        when(restaurantDAO.findProcessingRestaurant(anyInt())).thenReturn(Optional.of(someRestaurant));
        when(restaurantDAO.findProcessingRestaurant(someId)).thenReturn(Optional.empty());
        String expectedMessage = "Couldn't find restaurant with id: [%s]".formatted(someId);
        when(restaurantMapper.mapToDTO(any(Restaurant.class))).thenReturn(expected);

        // When
        RestaurantDTO result = restaurantService.findProcessingRestaurant(6);
        Throwable exception = assertThrows(NotFoundException.class,
            () -> restaurantService.findProcessingRestaurant(someId));

        // Then
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expected, result);
    }

    @Test
    void getCurrentMenuWorksCorrectly() {
        // Given
        Menu someMenu = someMenu1();
        Integer someId = 65;
        when(restaurantDAO.getMenu(anyInt())).thenReturn(Optional.of(someMenu));
        when(restaurantDAO.getMenu(someId)).thenReturn(Optional.empty());
        MenuDTO expected1 = someMenuDTO();
        MenuDTO expected2 = someMenuDTO().withMenuName(null).withFoods(Set.of());
        when(menuMapper.mapToDTO(any(Menu.class))).thenReturn(expected1);

        // When
        MenuDTO result1 = restaurantService.getCurrentMenu(2341);
        MenuDTO result2 = restaurantService.getCurrentMenu(someId);

        // Then
        assertEquals(expected1, result1);
        assertEquals(expected2, result2);
    }

    @Test
    void findAllOwnerPendingOrdersWorksCorrectly() {
        // Given
        List<RestaurantDTO> someRestaurantsDTO = someRestaurantsDTO();
        List<Restaurant> someRestaurants = someRestaurants();
        when(restaurantMapper.mapFromDTO(any(RestaurantDTO.class))).thenReturn(someRestaurant1(), someRestaurant2(), someRestaurant3());
        when(orderService.countPendingOrdersForRestaurant(any(Restaurant.class))).thenReturn(5, 9, 2);
        List<Integer> expected = List.of(5, 9, 2);

        // When
        List<Integer> result = restaurantService.findAllOwnerPendingOrders(3, someRestaurantsDTO);

        // Then
        assertEquals(expected, result);
        verify(restaurantMapper, times(someRestaurants.size())).mapFromDTO(any(RestaurantDTO.class));
        verify(orderService, times(someRestaurants.size())).countPendingOrdersForRestaurant(any(Restaurant.class));

    }

    @Test
    void getDeliveryAddressesWorksCorrectly() {
        // Given
        DeliveryAddressesDTO expected = someDeliveryAddresses();
        when(deliveryAddressService.getRestaurantDeliveryAddresses(anyInt())).thenReturn(someAddresses());
        when(deliveryAddressMapper.mapToDTO(any(DeliveryAddress.class)))
            .thenReturn(someDeliveryAddressDTO1(), someDeliveryAddressDTO2(), someDeliveryAddressDTO3());

        // When
        DeliveryAddressesDTO result = restaurantService.getDeliveryAddresses(6);

        // Then
        assertEquals(expected, result);
        verify(deliveryAddressService, times(1)).getRestaurantDeliveryAddresses(anyInt());
        verify(deliveryAddressMapper, times(someAddresses().size())).mapToDTO(any(DeliveryAddress.class));
    }

    @Test
    void findOrdersByInProgressWorksCorrectly() {
        // Given
        OrdersDTO expected = someOrdersDTO();
        when(orderService.findOrdersByInProgress(anyInt(), anyBoolean())).thenReturn(someOrders());
        when(orderMapper.mapToDTO(any(Order.class))).thenReturn(someOrderDTO1(), someOrderDTO2());

        // When
        OrdersDTO result = restaurantService.findOrdersByInProgress(432, true);

        // Then
        assertEquals(expected, result);
        verify(orderService, times(1)).findOrdersByInProgress(anyInt(), anyBoolean());
        verify(orderMapper, times(someOrders().size())).mapToDTO(any(Order.class));
    }

    @Test
    void findPayedOrdersAndNotRealizedWorksCorrectly() {
        // Given
        OrdersDTO expected = someOrdersDTO();
        when(billService.findOrdersNotInProgressAndPayedAndNotRealized(anyInt(), anyBoolean(), anyBoolean(), anyBoolean()))
            .thenReturn(someOrders());
        when(orderMapper.mapToDTO(any(Order.class))).thenReturn(someOrderDTO1(), someOrderDTO2());

        // When
        OrdersDTO result = restaurantService.findPayedOrdersAndNotRealized(43);

        // Then
        assertEquals(expected, result);
        verify(billService, times(1))
            .findOrdersNotInProgressAndPayedAndNotRealized(anyInt(), anyBoolean(), anyBoolean(), anyBoolean());
        verify(orderMapper, times(someOrders().size())).mapToDTO(any(Order.class));
    }

    @Test
    void addFoodToMenuThrowsCorrectly() {
        // Given
        FoodDTO someFoodDTO = someFoodDTO2();
        Integer someId = 654;
        MultipartFile someFile = new MockMultipartFile("someFileName", new byte[0]);
        when(restaurantDAO.getMenu(someId)).thenReturn(Optional.empty());
//        when(restaurantDAO.getMenu(anyInt())).thenReturn(Optional.of(someMenu1()));
        String expectedMessage1 = "Menu doesn't exist";

        // When
        Throwable exception = assertThrows(NotFoundException.class,
            () -> restaurantService.addFoodToMenu(someFoodDTO, someId, someFile));

        assertEquals(expectedMessage1, exception.getMessage());
    }

    @Test
    void addFoodToMenuThrowsCheckedExceptionCorrectly() throws IOException {
        // Given
        FoodDTO someFoodDTO = someFoodDTO2();
        Integer someId = 654;
        MultipartFile someFile = new MockMultipartFile("someFileName", new byte[0]);
        when(foodMapper.mapFromDTO(any(FoodDTO.class))).thenReturn(someFood1());
        when(restaurantDAO.getMenu(anyInt())).thenReturn(Optional.of(someMenu1()));
        String expectedMessage1 = "Failed to upload an image: [%s]".formatted(someFile.getOriginalFilename());
        BDDMockito.given(storageService.uploadImageToServer(someFile, someId)).willAnswer(invocation -> {
            throw new IOException("Failed to upload an image");
        });
        Throwable exception = assertThrows(FileUploadException.class,
            () -> restaurantService.addFoodToMenu(someFoodDTO, someId, someFile));

        assertEquals(expectedMessage1, exception.getMessage());
    }

    @Test
    void addFoodToMenuWorksCorrectly() throws IOException {
        FoodDTO someFoodDTO = someFoodDTO2();
        Menu someMenu = someMenu1();
        Integer someId = 654;
        String expected1 = "No image";
        String expected2 = "some/Food/Image/Path";
        byte[] byteArray = new byte[]{1, 2, 3, 4, 5};
        MultipartFile someFile1 = new MockMultipartFile("someFileName", new byte[0]);
        MultipartFile someFile2 = new MockMultipartFile("someFileName", byteArray);
        when(foodMapper.mapFromDTO(any(FoodDTO.class))).thenReturn(someFood1());
        when(restaurantDAO.getMenu(anyInt())).thenReturn(Optional.of(someMenu));
        when(storageService.uploadImageToServer(any(MultipartFile.class), anyInt()))
            .thenReturn("");
        when(storageService.uploadImageToServer(someFile2, someId))
            .thenReturn(expected2);
        doNothing().when(foodService).addFoodToMenu(someFood1(), someMenu, null);
        doNothing().when(foodService).addFoodToMenu(someFood1(), someMenu, expected2);

        // When
        String result1 = restaurantService.addFoodToMenu(someFoodDTO, someId, someFile1);
        String result2 = restaurantService.addFoodToMenu(someFoodDTO, someId, someFile2);

        // Then
        assertEquals(expected1, result1);
        assertEquals(expected2, result2);

    }

    @Test
    void updateMenuPositionWorksCorrectly() throws IOException {
        // Given
        Integer someId = 123;
        FoodDTO someFoodDTO = someFoodDTO2();
        String expected1 = "No image";
        String expected2 = "some/Food/Image/Path";
        byte[] byteArray = new byte[]{1, 2, 3, 4, 5};
        MultipartFile someFile1 = new MockMultipartFile("someFileName", new byte[0]);
        MultipartFile someFile2 = new MockMultipartFile("someFileName", byteArray);
        when(foodMapper.mapFromDTO(any(FoodDTO.class))).thenReturn(someFood1());
        when(storageService.uploadImageToServer(any(MultipartFile.class), anyInt()))
            .thenReturn("");
        when(storageService.uploadImageToServer(someFile2, someId))
            .thenReturn(expected2);
        when(foodService.updateMenuPosition(someFood1(), null)).thenReturn(null);
        when(foodService.updateMenuPosition(someFood1(), expected2)).thenReturn("some/path/to/delete");
        doNothing().when(storageService).removeImageFromServer(anyString());

        // When
        String result1 = restaurantService.updateMenuPosition(someFoodDTO, someId, someFile1);
        String result2 = restaurantService.updateMenuPosition(someFoodDTO, someId, someFile2);

        // Then
        assertEquals(expected1, result1);
        assertEquals(expected2, result2);
    }

    @Test
    void updateMenuPositionThrowsCheckedExceptionCorrectly() throws IOException {
        // Given
        FoodDTO someFoodDTO = someFoodDTO2();
        Integer someId = 654;
        MultipartFile someFile = new MockMultipartFile("someFileName", new byte[0]);
        when(foodMapper.mapFromDTO(any(FoodDTO.class))).thenReturn(someFood1());
        String expectedMessage1 = "Failed to upload an image: [%s]".formatted(someFile.getOriginalFilename());
        BDDMockito.given(storageService.uploadImageToServer(someFile, someId)).willAnswer(invocation -> {
            throw new IOException("Failed to upload an image");
        });
        Throwable exception = assertThrows(FileUploadException.class,
            () -> restaurantService.updateMenuPosition(someFoodDTO, someId, someFile));

        assertEquals(expectedMessage1, exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource
    void deleteFoodFromMenuWorksCorrectly(String expected, String imagePathToDelete, Integer foodId) throws FileUploadException {
        // Given
        when(foodService.deleteFood(anyInt())).thenReturn(imagePathToDelete);
        if (Objects.nonNull(imagePathToDelete)) {
            doNothing().when(storageService).removeImageFromServer(anyString());
        }
        // When
        String result = restaurantService.deleteFoodFromMenu(foodId);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void getPaginatedMenuWorksCorrectly() {
        // Given
        Integer throwingId = 54;
        Pageable pageable = PageRequest.of(1, 5);
        Page<FoodDTO> expected = new PageImpl<>(someFoodsListDTO());
        String expectedMessage = "Restaurant doesn't have a menu";
        when(pageableService.preparePageable(anyInt(), anyInt(), anyString(), anyString())).thenReturn(pageable);
        when(restaurantDAO.getMenu(anyInt())).thenReturn(Optional.of(someMenu1()));
        when(restaurantDAO.getMenu(throwingId)).thenReturn(Optional.empty());
        when(foodService.getPaginatedFoods(anyInt(), any(Pageable.class))).thenReturn(expected);

        // When
        Page<FoodDTO> result = restaurantService.getPaginatedMenu(3, 67, "menuId", "desc", 213);
        Throwable exception = assertThrows(EntityNotFoundException.class,
            () -> restaurantService.getPaginatedMenu(12, 65, "menuId", "desc", throwingId));

        // Then
        assertEquals(expected.getSize(), result.getSize());
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getPaginatedDeliveryAddressesWorksCorrectly() {
        // Given
        Page<DeliveryAddressDTO> expected = new PageImpl<>(someAddressesDTO());
        Pageable pageable = PageRequest.of(1, 5);
        when(pageableService.preparePageable(anyInt(), anyInt(), anyString(), anyString())).thenReturn(pageable);
        when(deliveryAddressService.getPaginatedRestaurantDeliveryAddresses(anyInt(), any(Pageable.class)))
            .thenReturn(expected);

        // When
        Page<DeliveryAddressDTO> result = restaurantService.getPaginatedDeliveryAddresses(3, 7, "City", "asc", 6);

        // Then
        assertEquals(expected, result);
    }

    @Test
    void addDeliveryAddressWorksCorrectly() {
        // Given
        DeliveryAddress someDeliveryAddress = someDeliveryAddress1();
        Restaurant someRestaurant = someRestaurant1();
        when(deliveryAddressMapper.mapFromDTO(any(DeliveryAddressDTO.class))).thenReturn(someDeliveryAddress);
        when(restaurantMapper.mapFromDTO(any(RestaurantDTO.class))).thenReturn(someRestaurant);
        doNothing().when(deliveryAddressService).addDeliveryAddressToRestaurant(any(DeliveryAddress.class), any(Restaurant.class));
        when(restaurantDAO.findProcessingRestaurant(anyInt())).thenReturn(Optional.of(someRestaurant));
        when(restaurantMapper.mapToDTO(any(Restaurant.class))).thenReturn(someRestaurantDTO2());

        // When
        restaurantService.addDeliveryAddress(someDeliveryAddressDTO2(), 88);

        // Then
        verify(deliveryAddressService, times(1))
            .addDeliveryAddressToRestaurant(any(DeliveryAddress.class), any(Restaurant.class));
        verify(restaurantMapper, times(1))
            .mapFromDTO(any(RestaurantDTO.class));
        verify(deliveryAddressMapper, times(1))
            .mapFromDTO(any(DeliveryAddressDTO.class));


    }

    @Test
    void deleteAddressFromRestaurantWorksCorrectly() {
        // Given
        Integer someId = 432;
        doNothing().when(deliveryAddressService).deleteById(anyInt());

        // When
        restaurantService.deleteAddressFromRestaurant(someId);

        // Then
        verify(deliveryAddressService, times(1)).deleteById(anyInt());

    }

    @Test
    void findRestaurantByMenuWorksCorrectly() {
        // Given
        Restaurant expected = someRestaurant1();
        when(restaurantDAO.findRestaurantByMenu(any(Menu.class))).thenReturn(expected);

        // When
        Restaurant result = restaurantService.findRestaurantByMenu(someMenu1());

        // Then
        assertEquals(expected, result);

    }

    public static Stream<Arguments> findOwnerByRestaurantWorksCorrectly() {
        return Stream.of(
            Arguments.of(OwnerUtils.someOwner1(),someRestaurant1()),
            Arguments.of(null,someRestaurant2())
        );
    }

    @ParameterizedTest
    @MethodSource
    void findOwnerByRestaurantWorksCorrectly(Owner owner, Restaurant restaurant) {
        // Given
        Optional<Owner> expected = Optional.ofNullable(owner);
        when(restaurantDAO.findOwnerByRestaurant(any(Restaurant.class))).thenReturn(expected);

        // When
        Optional<Owner> result = restaurantService.findOwnerByRestaurant(restaurant);

        // Then
        assertEquals(expected,result);

    }

    @Test
    void realizeOrderWorksCorrectly() {
        // Given
        String expectedMessage = "Can't realize order for non existing restaurant";
        String someOrderNumber = UUID.randomUUID().toString();
        Integer someThrowingId = 65;
        Restaurant someRestaurant = someRestaurant1();
        when(restaurantDAO.findProcessingRestaurant(anyInt())).thenReturn(Optional.of(someRestaurant));
        when(restaurantDAO.findProcessingRestaurant(someThrowingId)).thenReturn(Optional.empty());
        doNothing().when(orderService).realizeOrder(anyString(),any(Restaurant.class));

        // When
        restaurantService.realizeOrder(someOrderNumber,45345);
        Throwable exception = assertThrows(NotFoundException.class,
            () -> restaurantService.realizeOrder(someOrderNumber,someThrowingId));

        // Then
        assertEquals(expectedMessage, exception.getMessage());
        verify(restaurantDAO, times(2)).findProcessingRestaurant(anyInt());
        verify(orderService, times(1)).realizeOrder(anyString(),any(Restaurant.class));
    }

    @Test
    void findAllRestaurantsWorksCorrectly() {
        // Given
        Page<RestaurantForCustomerDTO> expected = new PageImpl<>(someRestaurantsForCustomerDTO());
        Page<Restaurant> pagedRestaurants = new PageImpl<>(someRestaurants());
        Pageable pageable = PageRequest.of(4, 1);
        when(pageableService.preparePageable(anyInt(), anyInt(), anyString(), anyString()))
            .thenReturn(pageable);
        when(restaurantDAO.findAllRestaurants(any(Pageable.class))).thenReturn(pagedRestaurants);
        when(restaurantMapper.mapToDTOForCustomer(any(Restaurant.class)))
            .thenReturn(someRestaurantForCustomerDTO1(), someRestaurantForCustomerDTO2(), someRestaurantForCustomerDTO3());

        // When
        Page<RestaurantForCustomerDTO> result = restaurantService.findAllRestaurants(43, 5, "Id", "desc");

        // Then
        assertEquals(expected,result);

    }

    @Test
    void findAllRestaurantsByParametersWithCityOnlyWorksCorrectly() {
        // Given
        Page<RestaurantForCustomerDTO> expected = new PageImpl<>(someRestaurantsForCustomerDTO());
        DeliveryAddressDTO someAddress = someDeliveryAddressWithCityOnlyDTO();
        Pageable pageable = PageRequest.of(1, 4);
        Page<Restaurant> pagedRestaurants = new PageImpl<>(someRestaurants());
        when(pageableService.preparePageable(anyInt(), anyInt(), anyString(), anyString()))
            .thenReturn(pageable);
        when(restaurantDAO.findAllRestaurantsByCity(anyString(), any(Pageable.class)))
            .thenReturn(pagedRestaurants);
        when(restaurantMapper.mapToDTOForCustomer(any(Restaurant.class)))
            .thenReturn(someRestaurantForCustomerDTO1(), someRestaurantForCustomerDTO2(), someRestaurantForCustomerDTO3());

        // When
        Page<RestaurantForCustomerDTO> result = restaurantService.findAllRestaurantsByParameters(someAddress, 54, 12, "City", "asc");

        // Then
        assertEquals(expected,result);
    }

    @Test
    void findAllRestaurantsByParametersWithCityPostalCodeStreetWorksCorrectly() {
        // Given
        Page<RestaurantForCustomerDTO> expected = new PageImpl<>(someRestaurantsForCustomerDTO());
        DeliveryAddressDTO someAddress = someDeliveryAddressDTO3();
        Pageable pageable = PageRequest.of(1, 4);
        Page<Restaurant> pagedRestaurants = new PageImpl<>(someRestaurants());
        when(pageableService.preparePageable(anyInt(), anyInt(), anyString(), anyString()))
            .thenReturn(pageable);
        when(restaurantDAO.findAllRestaurantsByFullAddress(anyString(),anyString(),anyString(), any(Pageable.class)))
            .thenReturn(pagedRestaurants);
        when(restaurantMapper.mapToDTOForCustomer(any(Restaurant.class)))
            .thenReturn(someRestaurantForCustomerDTO1(), someRestaurantForCustomerDTO2(), someRestaurantForCustomerDTO3());

        // When
        Page<RestaurantForCustomerDTO> result = restaurantService.findAllRestaurantsByParameters(someAddress, 54, 12, "City", "asc");

        // Then
        assertEquals(expected,result);

    }

    @ParameterizedTest
    @MethodSource
    void findAllRestaurantsByParametersThrowsCorrectly(
        String expectedMessage,
        DeliveryAddressDTO someAddress,
        Integer pageNumber,
        Integer pageSize,
        String sortBy,
        String sortHow
    ) {
        // Given, When

        Throwable exception = assertThrows(SearchingException.class,
            ()->restaurantService.findAllRestaurantsByParameters(someAddress, pageNumber,pageSize, sortBy, sortHow));

        // Then
        assertEquals(expectedMessage,exception.getMessage());


    }

    @Test
    void addFoodToMenuLogsCorrectly(CapturedOutput output) throws IOException {
        // Given
        logger.setEnabledLevels(Level.ERROR);
        FoodDTO someFoodDTO = someFoodDTO2();
        Integer someId = 654;
        MultipartFile someFile = new MockMultipartFile("someFileName", new byte[0]);
        String expectedLog = "Failed to upload an image";
        when(foodMapper.mapFromDTO(any(FoodDTO.class))).thenReturn(someFood1());
        when(restaurantDAO.getMenu(anyInt())).thenReturn(Optional.of(someMenu1()));
        BDDMockito.given(storageService.uploadImageToServer(someFile, someId)).willAnswer(invocation -> {
            throw new IOException("Failed to upload an image");
        });

        // When
        assertThrows(FileUploadException.class,
            () -> restaurantService.addFoodToMenu(someFoodDTO, someId, someFile));

        // Then
        Assertions.assertTrue(output.getOut().contains(expectedLog));
    }

    public static Stream<Arguments> findAllRestaurantsByParametersThrowsCorrectly() {
        return Stream.of(
            Arguments.of(
                """
                City is missing.
                You can search by city or with every detail.
                You can also search without any input
                """, someDeliveryAddressWithPostalCodeOnlyDTO(),12,54,"street","asc"
            ),
            Arguments.of(
                """
                City is missing.
                You can search by city or with every detail.
                You can also search without any input
                """,someDeliveryAddressWithoutCityOnlyDTO(),92,14,"city","asc"
            ),
            Arguments.of(
                """
                City is missing.
                You can search by city or with every detail.
                You can also search without any input
                """, someDeliveryAddressWithStreetOnlyDTO(),1,4,"postalCode","desc"
            )
        );
    }
}