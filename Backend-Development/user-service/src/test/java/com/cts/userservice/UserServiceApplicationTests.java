package com.cts.userservice;

import com.cts.userservice.dao.UserDAO;
import com.cts.userservice.dto.AuthResponse;
import com.cts.userservice.dto.UserDetails;
import com.cts.userservice.enums.Roles;
import com.cts.userservice.exception.UserNotFoundException;
import com.cts.userservice.feign.DeliveryInterface;
import com.cts.userservice.feign.MenuInterface;
import com.cts.userservice.model.User;
import com.cts.userservice.model.UserPrincipal;
import com.cts.userservice.service.JWTService;
import com.cts.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceApplicationTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserDAO userDAO;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MenuInterface menuInterface;

    @Mock
    private DeliveryInterface deliveryInterface;

    private User testUser;
    private UUID testUserId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUserId = UUID.randomUUID();
        testUser = new User();
        testUser.setUserId(testUserId);
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPhone(1234567890L);
        testUser.setAddress("Test Address");
        testUser.setRole(Roles.CUSTOMER);
        testUser.setPassword("encodedPassword");
    }

    @Test
    void getAllUser_shouldReturnListOfUsers() {
        List<User> userList = Collections.singletonList(testUser);
        when(userDAO.findAll()).thenReturn(userList);
        List<User> result = userService.getAllUser();
        assertEquals(1, result.size());
        assertEquals(testUser, result.get(0));
    }

    @Test
    void verify_shouldReturnToken_whenCredentialsAreValidAndRoleMatches() {
        String email = "test@example.com";
        String password = "password";
        Roles role = Roles.CUSTOMER;
        Authentication authentication = mock(Authentication.class);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(userDAO.findByEmail(email)).thenReturn(testUser);
        when(jwtService.getToken(testUserId, role, email)).thenReturn("testToken");

        String token = userService.verify(email, password, role);
        assertEquals("testToken", token);
    }

    @Test
    void verify_shouldThrowUserNotFoundException_whenAuthenticationFails() {
        String email = "test@example.com";
        String password = "password";
        Roles role = Roles.CUSTOMER;
        Authentication authentication = mock(Authentication.class);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.verify(email, password, role));
    }

    @Test
    void verify_shouldThrowUserNotFoundException_whenRoleDoesNotMatch() {
        String email = "test@example.com";
        String password = "password";
        Roles role = Roles.MANAGER;
        Authentication authentication = mock(Authentication.class);
        User differentRoleUser = new User();
        differentRoleUser.setRole(Roles.CUSTOMER);

        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(userDAO.findByEmail(email)).thenReturn(differentRoleUser);

        assertThrows(UserNotFoundException.class, () -> userService.verify(email, password, role));
    }

    @Test
    void update_shouldUpdateUser_whenUserExists() {
        UUID userId = UUID.randomUUID();
        String newName = "Updated User";
        Long newPhone = 9876543210L;
        String newAddress = "Updated Address";
        String newPassword = "newPassword";
        User existingUser = new User();
        existingUser.setUserId(userId);
        existingUser.setName("Old User");
        existingUser.setPassword("oldEncodedPassword");
        existingUser.setPhone(1112223330L);
        existingUser.setAddress("Old Address");

        when(userDAO.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(newPassword)).thenReturn("newEncodedPassword");
        when(userDAO.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = userService.update(userId, newName, newPhone, newAddress, newPassword);

        assertEquals(newName, updatedUser.getName());
        assertEquals("newEncodedPassword", updatedUser.getPassword());
        assertEquals(newPhone, updatedUser.getPhone());
        assertEquals(newAddress, updatedUser.getAddress());
        verify(userDAO, times(1)).save(existingUser);
    }

    @Test
    void update_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();
        when(userDAO.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.update(userId, "Name", 123L, "Address", "Password"));
    }


    @Test
    void register_shouldRegisterNewUserAndAddAgent_whenRoleIsAgent() {
        String name = "New Agent";
        String email = "agent@example.com";
        Long phone = 1119992220L;
        String address = "Agent Address";
        String password = "agentPassword";
        Roles role = Roles.AGENT;
        User newUser = new User(name, email, phone, address, role, "encodedAgentPassword");

        when(passwordEncoder.encode(password)).thenReturn("encodedAgentPassword");
        when(userDAO.save(any(User.class))).thenReturn(newUser);
        doNothing().when(deliveryInterface).addAgent(any(UUID.class));

        User registeredUser = userService.register(name, email, phone, address, password, role);

        assertEquals(name, registeredUser.getName());
        assertEquals(email, registeredUser.getEmail());
        assertEquals(role, registeredUser.getRole());
        verify(userDAO, times(1)).save(any(User.class));
        // Ensure the argument passed to addAgent is the user's ID
        verify(deliveryInterface, times(1)).addAgent(registeredUser.getUserId());
        verify(menuInterface, never()).createNewRestaurant(any());
    }

    @Test
    void register_shouldRegisterNewUser_whenRoleIsCustomer() {
        String name = "New Customer";
        String email = "customer@example.com";
        Long phone = 3334445550L;
        String address = "Customer Address";
        String password = "customerPassword";
        Roles role = Roles.CUSTOMER;
        User newUser = new User( name, email, phone, address, role, "encodedCustomerPassword");

        when(passwordEncoder.encode(password)).thenReturn("encodedCustomerPassword");
        when(userDAO.save(any(User.class))).thenReturn(newUser);

        User registeredUser = userService.register(name, email, phone, address, password, role);

        assertEquals(name, registeredUser.getName());
        assertEquals(email, registeredUser.getEmail());
        assertEquals(role, registeredUser.getRole());
        verify(userDAO, times(1)).save(any(User.class));
        verify(menuInterface, never()).createNewRestaurant(any());
        verify(deliveryInterface, never()).addAgent(any());
    }

    
    @Test
    void validate_shouldReturnFalse_whenTokenIsInvalid() {
        String token = "invalidToken";
        String email = "test@example.com";
        UserPrincipal userPrincipal = new UserPrincipal(testUser);

        when(jwtService.extractEmail(token)).thenReturn(email);
        when(userDAO.findByEmail(email)).thenReturn(testUser);
        when(jwtService.validateToken(token, userPrincipal)).thenReturn(false);

        boolean isValid = userService.validate(token);
        assertFalse(isValid);
    }


    @Test
    void validateAndGetData_shouldReturnAuthResponseWithInvalidFalse_whenTokenIsInvalid() {
        String token = "invalidToken";
        String email = "test@example.com";
        UserPrincipal userPrincipal = new UserPrincipal(testUser);

        when(jwtService.extractEmail(token)).thenReturn(email);
        when(jwtService.validateToken(token, userPrincipal)).thenReturn(false);
        when(userDAO.findByEmail(email)).thenReturn(testUser);

        AuthResponse response = userService.validateAndGetData(token);

        assertFalse(response.isValid());
        assertEquals(email, response.getEmail());
        assertNull(response.getUserId());
        assertNull(response.getRole());
    }

    @Test
    void loadUserByUsername_shouldReturnUserPrincipal_whenUserExists() {
        String email = "test@example.com";
        when(userDAO.findByEmail(email)).thenReturn(testUser);
        org.springframework.security.core.userdetails.UserDetails userDetails = userService.loadUserByUsername(email);
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_shouldThrowUsernameNotFoundException_whenUserDoesNotExist() {
        String email = "nonexistent@example.com";
        when(userDAO.findByEmail(email)).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(email));
    }

    @Test
    void fetchData_shouldReturnListOfUserDetails_forGivenUserIds() {
        Set<UUID> userIds = new HashSet<>(Arrays.asList(testUserId));
        when(userDAO.findById(testUserId)).thenReturn(Optional.of(testUser));

        List<UserDetails> detailsList = userService.fetchData(userIds);

        assertEquals(1, detailsList.size());
        assertEquals(testUserId, detailsList.get(0).getUserId());
        assertEquals(testUser.getName(), detailsList.get(0).getName());
        //assertEquals(testUser.getPhone().toString(), detailsList.get(0).getPhone());
        assertEquals(testUser.getAddress(), detailsList.get(0).getAddress());
    }

    @Test
    void fetchData_shouldThrowUserNotFoundException_whenUserDoesNotExistForGivenId() {
        Set<UUID> userIds = new HashSet<>(Arrays.asList(UUID.randomUUID()));
        when(userDAO.findById(any())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.fetchData(userIds));
    }
}