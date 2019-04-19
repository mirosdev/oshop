package com.example.springframework.oshop.controllers;

import com.example.springframework.oshop.domain.Product;
import com.example.springframework.oshop.services.interfaces.MapValidationErrorService;
import com.example.springframework.oshop.services.interfaces.ProductService;
import com.example.springframework.oshop.services.interfaces.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user/oshop")
public class OShopForUserController {

    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;
    private final MapValidationErrorService mapValidationErrorService;

    @Autowired
    public OShopForUserController(ShoppingCartService shoppingCartService,
                                  ProductService productService,
                                  MapValidationErrorService mapValidationErrorService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @GetMapping("/products")
    public ResponseEntity<?> findAllProducts(){
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/cart/save")
    public ResponseEntity<?> createShoppingCart(@Valid @RequestBody Product product,
                                              BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap != null){return errorMap;}

        return new ResponseEntity<>(shoppingCartService.createShoppingCart(product), HttpStatus.OK);
    }

    @PostMapping("/cart/save/{cartId}")
    public ResponseEntity<?> addToShoppingCart(@Valid @RequestBody Product product,
                                               BindingResult result,
                                               @PathVariable Long cartId){

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap != null){return errorMap;}

        return new ResponseEntity<>(shoppingCartService.addToShoppingCart(product, cartId), HttpStatus.OK);
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<?> findCartById(@PathVariable Long cartId){
        return new ResponseEntity<>(shoppingCartService.findShoppingCartById(cartId), HttpStatus.OK);
    }

    @PostMapping("/cart/remove/{cartId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Long cartId,
                                                @Valid @RequestBody Product product,
                                                BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap != null){return errorMap;}

        return new ResponseEntity<>(shoppingCartService.removeItemFromCart(product, cartId), HttpStatus.OK);
    }

    @GetMapping("/cart/indicator/{cartId}")
    public ResponseEntity<?> getShoppingCartNumberIndicator(@PathVariable Long cartId){
        return new ResponseEntity<>(shoppingCartService.getShoppingCartIndicator(cartId), HttpStatus.OK);
    }

    @DeleteMapping("/cart/delete/{cartId}")
    public ResponseEntity<?> deleteShoppingCart(@PathVariable Long cartId) {
        return new ResponseEntity<>(shoppingCartService.deleteShoppingCart(cartId), HttpStatus.OK);
    }
}
