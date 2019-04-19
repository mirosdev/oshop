package com.example.springframework.oshop.controllers;

import com.example.springframework.oshop.domain.ProductOrder;
import com.example.springframework.oshop.domain.User;
import com.example.springframework.oshop.payload.JWTLoginSuccessResponse;
import com.example.springframework.oshop.payload.LoginRequest;
import com.example.springframework.oshop.security.JwtTokenProvider;
import com.example.springframework.oshop.services.interfaces.MapValidationErrorService;
import com.example.springframework.oshop.services.interfaces.ProductOrderService;
import com.example.springframework.oshop.services.interfaces.UserService;
import com.example.springframework.oshop.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;

import static com.example.springframework.oshop.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final MapValidationErrorService mapValidationErrorService;
    private final UserValidator userValidator;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ProductOrderService productOrderService;

    @Autowired
    public UserController(UserService userService,
                          MapValidationErrorService mapValidationErrorService,
                          UserValidator userValidator,
                          JwtTokenProvider jwtTokenProvider,
                          AuthenticationManager authenticationManager,
                          ProductOrderService productOrderService) {
        this.userService = userService;
        this.mapValidationErrorService = mapValidationErrorService;
        this.userValidator = userValidator;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.productOrderService = productOrderService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap != null){
            return errorMap;
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        return new ResponseEntity<>(new JWTLoginSuccessResponse(true,
                authentication.getAuthorities(), jwt), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {

        userValidator.validate(user, result);

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap != null){
            return errorMap;
        }

        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);

    }

    @PostMapping("/order/save")
    public ResponseEntity<?> saveOrder(@Valid @RequestBody ProductOrder productOrder,
                                       BindingResult result,
                                       Principal principal) {

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap != null){
            return errorMap;
        }

        return new ResponseEntity<>(productOrderService.saveOrder(productOrder, principal.getName()), HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public ResponseEntity<?> findAllOrdersOfLoggedUser(Principal principal){
        return new ResponseEntity<>(productOrderService.findAllByLoggedUser(principal.getName()),
                HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> findOrderById(@PathVariable Long orderId, Principal principal){
        return new ResponseEntity<>(productOrderService.findOrderById(principal.getName(), orderId),
                HttpStatus.OK);
    }
}
