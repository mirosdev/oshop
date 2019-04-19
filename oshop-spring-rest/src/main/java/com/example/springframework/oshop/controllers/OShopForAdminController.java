package com.example.springframework.oshop.controllers;

import com.example.springframework.oshop.domain.Product;
import com.example.springframework.oshop.services.interfaces.CategoryService;
import com.example.springframework.oshop.services.interfaces.MapValidationErrorService;
import com.example.springframework.oshop.services.interfaces.ProductOrderService;
import com.example.springframework.oshop.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/oshop")
public class OShopForAdminController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final MapValidationErrorService mapValidationErrorService;
    private final ProductOrderService productOrderService;

    @Autowired
    public OShopForAdminController(CategoryService categoryService,
                                   ProductService productService,
                                   MapValidationErrorService mapValidationErrorService,
                                   ProductOrderService productOrderService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.mapValidationErrorService = mapValidationErrorService;
        this.productOrderService = productOrderService;
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories(){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveProduct(@Valid @RequestBody Product product,
                                         BindingResult result) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))){

            ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
            if(errorMap != null){return errorMap;}

            return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User is not admin", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/products")
    public ResponseEntity<?> findAllProducts(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))){
            return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User is not admin", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findProductById(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))){
            return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User is not admin", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))){
            return new ResponseEntity<>(productService.deleteById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User is not admin", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<?> findAllProductOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))){
            return new ResponseEntity<>(productOrderService.findAll(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User is not admin", HttpStatus.BAD_REQUEST);
        }
    }
}
