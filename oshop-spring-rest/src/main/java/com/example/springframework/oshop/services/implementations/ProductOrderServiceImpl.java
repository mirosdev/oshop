package com.example.springframework.oshop.services.implementations;

import com.example.springframework.oshop.domain.ProductOrder;
import com.example.springframework.oshop.domain.User;
import com.example.springframework.oshop.repositories.ItemRepository;
import com.example.springframework.oshop.repositories.ProductOrderRepository;
import com.example.springframework.oshop.repositories.UserRepository;
import com.example.springframework.oshop.services.interfaces.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    private final ProductOrderRepository productOrderRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository,
                                   ItemRepository itemRepository,
                                   UserRepository userRepository) {
        this.productOrderRepository = productOrderRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProductOrder saveOrder(ProductOrder productOrder, String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){

            itemRepository.saveAll(productOrder.getItems());

            productOrder.setUser(optionalUser.get());
            return productOrderRepository.save(productOrder);
        } else {
            return null;
        }
    }

    @Override
    public List<ProductOrder> findAll() {
        return productOrderRepository.findAll();
    }

    @Override
    public List<ProductOrder> findAllByLoggedUser(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            Optional<List<ProductOrder>> optionalProductOrders =
                    productOrderRepository.findAllByUser(optionalUser.get());
            return optionalProductOrders.orElse(null);
        } else {
            return null;
        }
    }

    @Override
    public ProductOrder findOrderById(String email, Long orderId) {

        Optional<ProductOrder> productOrderOptional = productOrderRepository.findById(orderId);
        if(productOrderOptional.isPresent()){
            ProductOrder productOrder = productOrderOptional.get();
            if(productOrder.getUser().getEmail().equals(email)){
                return productOrder;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


}
