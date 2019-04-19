package com.example.springframework.oshop.controllers;

import com.example.springframework.oshop.domain.ProductOrder;
import com.example.springframework.oshop.domain.User;
import com.example.springframework.oshop.security.JwtTokenProvider;
import com.example.springframework.oshop.services.interfaces.MapValidationErrorService;
import com.example.springframework.oshop.services.interfaces.ProductOrderService;
import com.example.springframework.oshop.services.interfaces.UserService;
import com.example.springframework.oshop.validator.UserValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = UserController.class, secure = false)
class UserControllerTest {

    @MockBean
    UserService userService;
    @MockBean
    MapValidationErrorService mapValidationErrorService;
    @MockBean
    UserValidator userValidator;
    @MockBean
    JwtTokenProvider jwtTokenProvider;
    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    ProductOrderService productOrderService;

    @Autowired
    MockMvc mockMvc;

    Collection<GrantedAuthority> authorities = new ArrayList<>();
    Authentication authentication = mock(Authentication.class);

    User user = new User();
    ProductOrder productOrder = new ProductOrder();
    Principal principal = () -> "strExample";
    List<ProductOrder> productOrders = new ArrayList<>();

    @BeforeEach
    void setUp() {
        productOrder.setProductOrderId(1L);
        productOrder.setName("nameExample");
    }

    @AfterEach
    void tearDown() {
        reset(userService);
        reset(mapValidationErrorService);
        reset(userValidator);
        reset(jwtTokenProvider);
        reset(authenticationManager);
        reset(productOrderService);
    }

    @Test
    void authenticateUser() throws Exception {
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authentication = new UsernamePasswordAuthenticationToken("customUsername", "customPassword", authorities);

        given(mapValidationErrorService.mapValidationService(any())).willReturn(null);
        given(authenticationManager.authenticate(any())).willReturn(authentication);
        given(jwtTokenProvider.generateToken(any())).willReturn("example");

        mockMvc.perform(post("/api/users/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\":\"example@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    void registerUser() throws Exception {
        user.setId(1L);
        user.setEmail("example@example.com");
        given(mapValidationErrorService.mapValidationService(any())).willReturn(null);
        given(userService.saveUser(any())).willReturn(user);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\":\"example@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void saveOrder() throws Exception {
        given(mapValidationErrorService.mapValidationService(any())).willReturn(null);
        given(productOrderService.saveOrder(any(), anyString())).willReturn(productOrder);

        mockMvc.perform(post("/api/users/order/save")
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\":\"nameExample\",\"addressLine1\":\"addressLnExample\", \"city\":\"cityExample\", \"overallPrice\":10}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.productOrderId", is(1)));
    }

    @Test
    void findAllOrdersOfLoggedUser() throws Exception {
        productOrders.add(productOrder);
        given(productOrderService.findAllByLoggedUser(anyString())).willReturn(productOrders);

        mockMvc.perform(get("/api/users/orders")
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].productOrderId", is(1)));
    }

    @Test
    void findOrderById() throws Exception {
        given(productOrderService.findOrderById(anyString(), anyLong())).willReturn(productOrder);

        mockMvc.perform(get("/api/users/order/{orderId}", "1")
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.productOrderId", is(1)));
    }
}