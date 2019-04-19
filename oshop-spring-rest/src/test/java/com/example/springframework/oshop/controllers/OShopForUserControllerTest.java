package com.example.springframework.oshop.controllers;

import com.example.springframework.oshop.domain.Product;
import com.example.springframework.oshop.domain.ShoppingCart;
import com.example.springframework.oshop.services.interfaces.MapValidationErrorService;
import com.example.springframework.oshop.services.interfaces.ProductService;
import com.example.springframework.oshop.services.interfaces.ShoppingCartService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = OShopForUserController.class, secure = false)
class OShopForUserControllerTest {

    @MockBean
    ShoppingCartService shoppingCartService;
    @MockBean
    ProductService productService;
    @MockBean
    MapValidationErrorService mapValidationErrorService;

    @Autowired
    MockMvc mockMvc;

    List<Product> products = new ArrayList<>();
    Product product = new Product();
    ShoppingCart shoppingCart = new ShoppingCart();

    @BeforeEach
    void setUp() {
        product.setId(1L);
        product.setTitle("titleExample");
        products.add(product);

        shoppingCart.setCartId(1L);
        shoppingCart.setTotalPrice(10F);
    }

    @AfterEach
    void tearDown() {
        reset(shoppingCartService);
        reset(productService);
        reset(mapValidationErrorService);
    }

    @Test
    void findAllProducts() throws Exception {
        given(productService.findAll()).willReturn(products);

        mockMvc.perform(get("/api/user/oshop/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    void createShoppingCart() throws Exception {
        given(mapValidationErrorService.mapValidationService(any())).willReturn(null);
        given(shoppingCartService.createShoppingCart(any())).willReturn(shoppingCart);

        mockMvc.perform(post("/api/user/oshop/cart/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"title\":\"titleExample\", \"price\":10, \"category\":\"categoryExample\", \"imageUrl\":\"urlExample\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.cartId", is(1)))
                .andExpect(jsonPath("$.totalPrice", is(10.0)));
    }

    @Test
    void addToShoppingCart() throws Exception {
        given(mapValidationErrorService.mapValidationService(any())).willReturn(null);
        given(shoppingCartService.addToShoppingCart(any(), anyLong())).willReturn(shoppingCart);

        mockMvc.perform(post("/api/user/oshop/cart/save/{cartId}", "1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"title\":\"titleExample\", \"price\":10, \"category\":\"categoryExample\", \"imageUrl\":\"urlExample\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.cartId", is(1)))
                .andExpect(jsonPath("$.totalPrice", is(10.0)));
    }

    @Test
    void findCartById() throws Exception {
        given(shoppingCartService.findShoppingCartById(anyLong())).willReturn(shoppingCart);

        mockMvc.perform(get("/api/user/oshop/cart/{cartId}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.cartId", is(1)))
                .andExpect(jsonPath("$.totalPrice", is(10.0)));
    }

    @Test
    void removeItemFromCart() throws Exception {
        given(mapValidationErrorService.mapValidationService(any())).willReturn(null);
        given(shoppingCartService.removeItemFromCart(any(), anyLong())).willReturn(true);

        mockMvc.perform(post("/api/user/oshop/cart/remove/{cartId}", "1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"title\":\"titleExample\", \"price\":10, \"category\":\"categoryExample\", \"imageUrl\":\"urlExample\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", is(true)));

    }

    @Test
    void getShoppingCartNumberIndicator() throws Exception {
        given(shoppingCartService.getShoppingCartIndicator(anyLong())).willReturn(1);

        mockMvc.perform(get("/api/user/oshop/cart/indicator/{cartId}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", is(1)));
    }

    @Test
    void deleteShoppingCart() throws Exception {
        given(shoppingCartService.deleteShoppingCart(anyLong())).willReturn(true);

        mockMvc.perform(delete("/api/user/oshop/cart/delete/{cartId}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", is(true)));
    }
}