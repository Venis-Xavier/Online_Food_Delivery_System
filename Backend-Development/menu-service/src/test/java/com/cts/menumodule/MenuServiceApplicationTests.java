package com.cts.menumodule;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.cts.menumodule.service.MenuService;
import com.cts.menumodule.dao.MenuDAO;
import com.cts.menumodule.dao.RestaurantDAO;
import com.cts.menumodule.exceptions.MenuNotFoundException;
import com.cts.menumodule.exceptions.RestaurantNotFoundException;
import com.cts.menumodule.model.Menu;
import com.cts.menumodule.model.Restaurant;

class MenuServiceApplicationTests {

    @Mock
    private MenuDAO menuDAO;

    @Mock
    private RestaurantDAO restaurantDAO;

    @InjectMocks
    private MenuService menuService;

    private UUID restaurantId;
    private UUID itemId1;
    private UUID itemId2;
    private Restaurant restaurant;
    private Menu menu1;
    private Menu menu2;
    private MultipartFile mockImage;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        restaurantId = UUID.randomUUID();
        itemId1 = UUID.randomUUID();
        itemId2 = UUID.randomUUID();
        restaurant = new Restaurant();
        restaurant.setRestaurantId(restaurantId);
        restaurant.setRestaurantName("Test Restaurant");
        menu1 = new Menu("Item 1", "Description 1", 10.0, true, restaurant, true, new byte[0]);
        menu1.setItemId(itemId1);
        menu2 = new Menu("Item 2", "Description 2", 15.0, false, restaurant, false, new byte[0]);
        menu2.setItemId(itemId2);
        mockImage = new MockMultipartFile("itemImage", "image.jpg", "image/jpeg", "some image".getBytes());
    }

    @Test
    void getAllMenu_shouldReturnMenuListForGivenRestaurantId() {
        List<Menu> menuList = Arrays.asList(menu1, menu2);
        when(menuDAO.findAllByRestaurantRestaurantId(restaurantId)).thenReturn(menuList);

        List<Menu> result = menuService.getAllMenu(restaurantId);

        assertEquals(2, result.size());
        assertEquals("Item 1", result.get(0).getItemName());
        assertEquals("Item 2", result.get(1).getItemName());
        verify(menuDAO, times(1)).findAllByRestaurantRestaurantId(restaurantId);
    }

    @Test
    void getMenuOfRestaurant_shouldReturnRestaurantIfExists() throws RestaurantNotFoundException {
        when(restaurantDAO.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        Restaurant result = menuService.getMenuOfRestaurant(restaurantId);

        assertEquals("Test Restaurant", result.getRestaurantName());
        verify(restaurantDAO, times(1)).findById(restaurantId);
    }

    @Test
    void getMenuOfRestaurant_shouldThrowRestaurantNotFoundExceptionIfNotExists() {
        when(restaurantDAO.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> menuService.getMenuOfRestaurant(restaurantId));
        verify(restaurantDAO, times(1)).findById(restaurantId);
    }

    @Test
    void getItems_shouldReturnListOfMenuItemsForGivenIds() {
        Set<UUID> itemIds = Set.of(itemId1, itemId2);
        when(menuDAO.findById(itemId1)).thenReturn(Optional.of(menu1));
        when(menuDAO.findById(itemId2)).thenReturn(Optional.of(menu2));

        List<Menu> result = menuService.getItems(itemIds);

        assertEquals(2, result.size());
        // Ensure the order of items is as expected if it matters, otherwise you might need to sort
        assertTrue(result.contains(menu1));
        assertTrue(result.contains(menu2));
        if (result.get(0).getItemId().equals(itemId1)) {
            assertEquals("Item 1", result.get(0).getItemName());
            assertEquals("Item 2", result.get(1).getItemName());
        } else {
            assertEquals("Item 2", result.get(0).getItemName());
            assertEquals("Item 1", result.get(1).getItemName());
        }
        verify(menuDAO, times(1)).findById(itemId1);
        verify(menuDAO, times(1)).findById(itemId2);
    }

    @Test
    void createMenu_shouldSaveNewMenuWithRestaurant() throws IOException, RestaurantNotFoundException {
        when(restaurantDAO.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        Menu newMenu = new Menu("New Item", "New Description", 20.0, true, restaurant, false, mockImage.getBytes());
        when(menuDAO.save(any(Menu.class))).thenReturn(newMenu);

        Menu result = menuService.createMenu("New Item", mockImage, false, "New Description", 20.0, true, restaurantId);

        assertEquals("New Item", result.getItemName());
        assertEquals(restaurant, result.getRestaurant());
        verify(restaurantDAO, times(1)).findById(restaurantId);
        verify(menuDAO, times(1)).save(any(Menu.class));
    }

    @Test
    void createMenu_shouldThrowRestaurantNotFoundExceptionIfRestaurantDoesNotExist() throws IOException {
        when(restaurantDAO.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () ->
                menuService.createMenu("New Item", mockImage, false, "New Description", 20.0, true, restaurantId));
        verify(restaurantDAO, times(1)).findById(restaurantId);
        verify(menuDAO, never()).save(any(Menu.class));
    }

    @Test
    void updateMenu_shouldUpdateExistingMenu() throws MenuNotFoundException, IOException {
        when(menuDAO.findById(itemId1)).thenReturn(Optional.of(menu1));
        Menu updatedMenu = new Menu("Updated Item", "Updated Description", 25.0, false, restaurant, true, mockImage.getBytes());
        updatedMenu.setItemId(itemId1);
        when(menuDAO.save(any(Menu.class))).thenReturn(updatedMenu);

        Menu result = menuService.updateMenu("Updated Item", "Updated Description", 25.0, false, itemId1, true, mockImage);

        assertEquals("Updated Item", result.getItemName());
        assertEquals("Updated Description", result.getItemDesc());
        assertEquals(25.0, result.getPrice());
        assertFalse(result.isAvailable());
        assertTrue(result.isVeg());
        assertArrayEquals(mockImage.getBytes(), result.getItemImg());
        verify(menuDAO, times(1)).findById(itemId1);
        verify(menuDAO, times(1)).save(any(Menu.class));
    }

    @Test
    void updateMenu_shouldUpdateExistingMenuWithoutNewImage() throws MenuNotFoundException, IOException {
        when(menuDAO.findById(itemId1)).thenReturn(Optional.of(menu1));
        Menu updatedMenu = new Menu("Updated Item", "Updated Description", 25.0, false, restaurant, true, new byte[0]);
        updatedMenu.setItemId(itemId1);
        when(menuDAO.save(any(Menu.class))).thenReturn(updatedMenu);
        MultipartFile nullImage = null;

        Menu result = menuService.updateMenu("Updated Item", "Updated Description", 25.0, false, itemId1, true, nullImage);

        assertEquals("Updated Item", result.getItemName());
        assertEquals("Updated Description", result.getItemDesc());
        assertEquals(25.0, result.getPrice());
        assertFalse(result.isAvailable());
        assertTrue(result.isVeg());
        assertArrayEquals(new byte[0], result.getItemImg());
        verify(menuDAO, times(1)).findById(itemId1);
        verify(menuDAO, times(1)).save(any(Menu.class));
    }

    @Test
    void updateMenu_shouldThrowMenuNotFoundExceptionIfItemDoesNotExist() {
        when(menuDAO.findById(itemId1)).thenReturn(Optional.empty());

        assertThrows(MenuNotFoundException.class, () ->
                menuService.updateMenu("Updated Item", "Updated Description", 25.0, false, itemId1, true, mockImage));
        verify(menuDAO, times(1)).findById(itemId1);
        verify(menuDAO, never()).save(any(Menu.class));
    }

    @Test
    void toggleAvailability_shouldToggleIsAvailableStatus() throws MenuNotFoundException {
        when(menuDAO.findById(itemId1)).thenReturn(Optional.of(menu1));
        when(menuDAO.save(menu1)).thenReturn(menu1);

        Menu result = menuService.toggleAvailability(itemId1);

        assertFalse(result.isAvailable());
        verify(menuDAO, times(1)).findById(itemId1);
        verify(menuDAO, times(1)).save(menu1);

        when(menuDAO.findById(itemId1)).thenReturn(Optional.of(result));
        when(menuDAO.save(result)).thenReturn(result);
        Menu resultAgain = menuService.toggleAvailability(itemId1);
        assertTrue(resultAgain.isAvailable());
        verify(menuDAO, times(2)).findById(itemId1);
        verify(menuDAO, times(2)).save(result);
    }

    @Test
    void toggleAvailability_shouldThrowMenuNotFoundExceptionIfItemDoesNotExist() {
        when(menuDAO.findById(itemId1)).thenReturn(Optional.empty());

        assertThrows(MenuNotFoundException.class, () -> menuService.toggleAvailability(itemId1));
        verify(menuDAO, times(1)).findById(itemId1);
        verify(menuDAO, never()).save(any(Menu.class));
    }

    @Test
    void deleteItem_shouldDeleteMenuItemById() {
        doNothing().when(menuDAO).deleteById(itemId1);

        menuService.deleteItem(itemId1);

        verify(menuDAO, times(1)).deleteById(itemId1);
    }

    @Test
    void priceOfItem_shouldReturnPriceOfExistingItem() throws MenuNotFoundException {
        when(menuDAO.findById(itemId1)).thenReturn(Optional.of(menu1));

        double price = menuService.priceOfItem(itemId1);

        assertEquals(10.0, price);
        verify(menuDAO, times(1)).findById(itemId1);
    }

    @Test
    void priceOfItem_shouldThrowMenuNotFoundExceptionIfItemDoesNotExist() {
        when(menuDAO.findById(itemId1)).thenReturn(Optional.empty());

        assertThrows(MenuNotFoundException.class, () -> menuService.priceOfItem(itemId1));
        verify(menuDAO, times(1)).findById(itemId1);
    }

    
}