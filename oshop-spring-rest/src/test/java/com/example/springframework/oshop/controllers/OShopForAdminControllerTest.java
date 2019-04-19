package com.example.springframework.oshop.controllers;

import com.example.springframework.oshop.domain.Category;
import com.example.springframework.oshop.domain.Product;
import com.example.springframework.oshop.domain.ProductOrder;
import com.example.springframework.oshop.services.interfaces.CategoryService;
import com.example.springframework.oshop.services.interfaces.MapValidationErrorService;
import com.example.springframework.oshop.services.interfaces.ProductOrderService;
import com.example.springframework.oshop.services.interfaces.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = OShopForAdminController.class, secure = false)
class OShopForAdminControllerTest {

    @MockBean
    CategoryService categoryService;
    @MockBean
    ProductService productService;
    @MockBean
    MapValidationErrorService mapValidationErrorService;
    @MockBean
    ProductOrderService productOrderService;

    @Autowired
    MockMvc mockMvc;

    List<Category> categories = new ArrayList<>();
    Category category = new Category();
    Product product = new Product();
    List<Product> products = new ArrayList<>();
    ProductOrder productOrder = new ProductOrder();
    List<ProductOrder> productOrders = new ArrayList<>();

    Collection<GrantedAuthority> authorities = new ArrayList<>();
    UsernamePasswordAuthenticationToken authentication;
    SecurityContext securityContext = mock(SecurityContext.class);

    @BeforeEach
    void setUp() {
        category.setId(1L);
        category.setName("categoryName");

        categories.add(category);

        product.setId(1L);
        product.setTitle("productTitle");

        products.add(product);

        productOrder.setName("nameExample");
        productOrders.add(productOrder);

        //SecurityContextHolder Mocking
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authentication = new UsernamePasswordAuthenticationToken("customUsername", "customPassword", authorities);

        SecurityContextHolder.setContext(securityContext);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //End
    }

    @AfterEach
    void tearDown() {
        reset(categoryService);
        reset(productService);
        reset(mapValidationErrorService);
        reset(productOrderService);
    }

    @Test
    void getCategories() throws Exception {
        given(categoryService.findAll()).willReturn(categories);

        mockMvc.perform(get("/api/admin/oshop/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void saveProduct() throws Exception {
        given(mapValidationErrorService.mapValidationService(any())).willReturn(null);
        given(productService.save(any())).willReturn(product);
        given(SecurityContextHolder.getContext().getAuthentication()).willReturn(authentication);

        mockMvc.perform(post("/api/admin/oshop/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"title\":\"productTitle\",\"price\":1.0,\"category\":\"categoryExample\",\"imageUrl\":\"urlExample\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.title", is(product.getTitle())));
    }

    @Test
    void findAllProducts() throws Exception {
        given(SecurityContextHolder.getContext().getAuthentication()).willReturn(authentication);
        given(productService.findAll()).willReturn(products);

        mockMvc.perform(get("/api/admin/oshop/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void findProductById() throws Exception {
        given(SecurityContextHolder.getContext().getAuthentication()).willReturn(authentication);
        given(productService.findById(anyLong())).willReturn(product);

        mockMvc.perform(get("/api/admin/oshop/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void deleteById() throws Exception {
        given(SecurityContextHolder.getContext().getAuthentication()).willReturn(authentication);
        given(productService.deleteById(anyLong())).willReturn(true);

        mockMvc.perform(delete("/api/admin/oshop/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    void findAllProductOrders() throws Exception {
        given(SecurityContextHolder.getContext().getAuthentication()).willReturn(authentication);
        given(productOrderService.findAll()).willReturn(productOrders);

        mockMvc.perform(get("/api/admin/oshop/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(productOrder.getName())));
    }
}