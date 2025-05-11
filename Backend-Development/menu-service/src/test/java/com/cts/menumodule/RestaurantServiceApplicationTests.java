package com.cts.menumodule;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cts.menumodule.service.RestaurantService;
import com.cts.menumodule.dao.RestaurantDAO;
import com.cts.menumodule.dto.RestaurantData;
import com.cts.menumodule.exceptions.RestaurantNotFoundException;
import com.cts.menumodule.model.Restaurant;

class RestaurantServiceApplicationTests {

    @Mock
    private RestaurantDAO restaurantDAO;

    @InjectMocks
    private RestaurantService restaurantService;

    private UUID restaurantId;
    private UUID managerId;
    private Restaurant restaurant;
    private MultipartFile mockImage;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        restaurantId = UUID.randomUUID();
        managerId = UUID.randomUUID();
        restaurant = new Restaurant();
        restaurant.setRestaurantId(restaurantId);
        restaurant.setRestaurantName("Test Restaurant");
        restaurant.setAddress("Test Address");
        mockImage = new MockMultipartFile("restaurantImg", "image.jpg", "image/jpeg", "some image".getBytes());
    }

    @Test
    void createRestaurant_shouldSaveNewRestaurant() {
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setRestaurantId(managerId);
        newRestaurant.setRestaurantName("New Restaurant");
        newRestaurant.setAddress("New Address");
        when(restaurantDAO.save(any(Restaurant.class))).thenReturn(newRestaurant);

        Restaurant result = restaurantService.createRestaurant(managerId, "New Restaurant", "New Address");

        assertEquals("New Restaurant", result.getRestaurantName());
        assertEquals("New Address", result.getAddress());
        assertEquals(managerId, result.getRestaurantId());
        verify(restaurantDAO, times(1)).save(any(Restaurant.class));
    }

    @Test
    void toggleRestaurantOpen_shouldToggleOpenStatusToTrue() {
        restaurant.setOpen(false);
        when(restaurantDAO.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(restaurantDAO.save(restaurant)).thenReturn(restaurant);

        boolean result = restaurantService.toggleRestaurantOpen(restaurantId);

        assertTrue(result);
        assertTrue(restaurant.isOpen());
        verify(restaurantDAO, times(1)).findById(restaurantId);
        verify(restaurantDAO, times(1)).save(restaurant);
    }

    @Test
    void toggleRestaurantOpen_shouldToggleOpenStatusToFalse() {
        restaurant.setOpen(true);
        when(restaurantDAO.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(restaurantDAO.save(restaurant)).thenReturn(restaurant);

        boolean result = restaurantService.toggleRestaurantOpen(restaurantId);

        assertFalse(result);
        assertFalse(restaurant.isOpen());
        verify(restaurantDAO, times(1)).findById(restaurantId);
        verify(restaurantDAO, times(1)).save(restaurant);
    }

    @Test
    void toggleRestaurantOpen_shouldThrowRestaurantNotFoundExceptionIfNotFound() {
        when(restaurantDAO.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> restaurantService.toggleRestaurantOpen(restaurantId));
        verify(restaurantDAO, times(1)).findById(restaurantId);
        verify(restaurantDAO, never()).save(any(Restaurant.class));
    }

    @Test
    void updateRestaurant_shouldUpdateExistingRestaurant() {
        when(restaurantDAO.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        Restaurant updatedRestaurant = new Restaurant();
        updatedRestaurant.setRestaurantId(restaurantId);
        updatedRestaurant.setRestaurantName("Updated Restaurant");
        updatedRestaurant.setAddress("Updated Address");
        when(restaurantDAO.save(updatedRestaurant)).thenReturn(updatedRestaurant);

    }

    @Test
    void updateRestaurant_shouldThrowRestaurantNotFoundExceptionIfNotFound() {
        when(restaurantDAO.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () ->
                restaurantService.updateRestaurant(restaurantId, "Updated Restaurant", "Updated Address"));
        verify(restaurantDAO, times(1)).findById(restaurantId);
        verify(restaurantDAO, never()).save(any(Restaurant.class));
    }

    @Test
    void getAllRestaurant_shouldReturnListOfOpenRestaurantsAsRestaurantData() {
        Restaurant openRestaurant1 = new Restaurant();
        openRestaurant1.setRestaurantId(UUID.randomUUID());
        openRestaurant1.setRestaurantName("Open Restaurant 1");
        openRestaurant1.setAddress("Address 1");
        openRestaurant1.setOpen(true);
        openRestaurant1.setMenuItems(Collections.emptyList());
        openRestaurant1.setRestaurantImg(new byte[0]);

        Restaurant openRestaurant2 = new Restaurant();
        openRestaurant2.setRestaurantId(UUID.randomUUID());
        openRestaurant2.setRestaurantName("Open Restaurant 2");
        openRestaurant2.setAddress("Address 2");
        openRestaurant2.setOpen(true);
        openRestaurant2.setMenuItems(Collections.emptyList());
        openRestaurant2.setRestaurantImg(new byte[0]);

        List<Restaurant> openRestaurants = Arrays.asList(openRestaurant1, openRestaurant2);
        when(restaurantDAO.findAllOpenRestaurants()).thenReturn(openRestaurants);

        List<RestaurantData> result = restaurantService.getAllRestaurant();

        assertEquals(2, result.size());
        assertEquals(openRestaurant1.getRestaurantId(), result.get(0).getRestaurantId());
        assertEquals(openRestaurant1.getRestaurantName(), result.get(0).getRestaurantName());
        assertEquals(openRestaurant1.getAddress(), result.get(0).getAddress());
        assertTrue(result.get(0).isOpen());
        assertEquals(openRestaurant2.getRestaurantId(), result.get(1).getRestaurantId());
        assertEquals(openRestaurant2.getRestaurantName(), result.get(1).getRestaurantName());
        assertEquals(openRestaurant2.getAddress(), result.get(1).getAddress());
        assertTrue(result.get(1).isOpen());
        verify(restaurantDAO, times(1)).findAllOpenRestaurants();
    }

    @Test
    void getAllRestaurant_shouldReturnEmptyListIfNoOpenRestaurants() {
        when(restaurantDAO.findAllOpenRestaurants()).thenReturn(Collections.emptyList());

        List<RestaurantData> result = restaurantService.getAllRestaurant();

        assertTrue(result.isEmpty());
        verify(restaurantDAO, times(1)).findAllOpenRestaurants();
    }

    @Test
    void editRestaurantImage_shouldUpdateRestaurantImage() throws IOException {
        when(restaurantDAO.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        byte[] imageBytes = mockImage.getBytes();
        restaurant.setRestaurantImg(imageBytes);
        when(restaurantDAO.save(restaurant)).thenReturn(restaurant);

        Restaurant result = restaurantService.editRestaurantImage(mockImage, restaurantId);

        assertArrayEquals(imageBytes, result.getRestaurantImg());
        verify(restaurantDAO, times(1)).findById(restaurantId);
        verify(restaurantDAO, times(1)).save(restaurant);
    }

    @Test
    void editRestaurantImage_shouldThrowRestaurantNotFoundExceptionIfRestaurantDoesNotExist() throws IOException {
        when(restaurantDAO.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> restaurantService.editRestaurantImage(mockImage, restaurantId));
        verify(restaurantDAO, times(1)).findById(restaurantId);
        verify(restaurantDAO, never()).save(any(Restaurant.class));
    }

    @Test
    void createRestaurant_shouldHandleEmptyAddress() {
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setRestaurantId(managerId);
        newRestaurant.setRestaurantName("Restaurant with No Address");
        newRestaurant.setAddress("");
        when(restaurantDAO.save(any(Restaurant.class))).thenReturn(newRestaurant);

        Restaurant result = restaurantService.createRestaurant(managerId, "Restaurant with No Address", "");

        assertEquals("Restaurant with No Address", result.getRestaurantName());
        assertEquals("", result.getAddress());
        verify(restaurantDAO, times(1)).save(any(Restaurant.class));
    }

    @Test
    void editRestaurantImage_shouldHandleEmptyImage() throws IOException {
        when(restaurantDAO.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        MockMultipartFile emptyImage = new MockMultipartFile("restaurantImg", "empty.jpg", "image/jpeg", new byte[0]);
        restaurant.setRestaurantImg(new byte[0]);
        when(restaurantDAO.save(restaurant)).thenReturn(restaurant);

        Restaurant result = restaurantService.editRestaurantImage(emptyImage, restaurantId);

        assertArrayEquals(new byte[0], result.getRestaurantImg());
        verify(restaurantDAO, times(1)).findById(restaurantId);
        verify(restaurantDAO, times(1)).save(restaurant);
    }
}