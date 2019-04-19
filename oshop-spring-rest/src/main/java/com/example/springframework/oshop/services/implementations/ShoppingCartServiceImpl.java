package com.example.springframework.oshop.services.implementations;

import com.example.springframework.oshop.domain.Product;
import com.example.springframework.oshop.domain.ShoppingCart;
import com.example.springframework.oshop.domain.ShoppingCartItem;
import com.example.springframework.oshop.repositories.ShoppingCartItemRepository;
import com.example.springframework.oshop.repositories.ShoppingCartRepository;
import com.example.springframework.oshop.services.interfaces.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   ShoppingCartItemRepository shoppingCartItemRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
    }

    @Override
    public ShoppingCart createShoppingCart(Product product) {

        ShoppingCart shoppingCart = new ShoppingCart();
        List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();

        shoppingCartItem.setProduct(product);
        shoppingCartItem.setQuantity(1L);

        shoppingCartItems.add(shoppingCartItem);

        shoppingCart.setShoppingCartItems(shoppingCartItems);

        shoppingCartItemRepository.save(shoppingCartItem);

        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart addToShoppingCart(Product product, Long cartId) {

        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(cartId);
        if(shoppingCartOptional.isPresent()){
            ShoppingCart shoppingCart = shoppingCartOptional.get();
            List<ShoppingCartItem> shoppingCartItems = shoppingCart.getShoppingCartItems();
            boolean itemExists = false;

            for (ShoppingCartItem item : shoppingCartItems){
                if(item.getProduct().getId().equals(product.getId())){
                    item.setQuantity(item.getQuantity() + 1);
                    itemExists = true;
                }
            }

            if(!itemExists){
                ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
                shoppingCartItem.setProduct(product);
                shoppingCartItem.setQuantity(1L);

                shoppingCartItemRepository.save(shoppingCartItem);
                shoppingCartItems.add(shoppingCartItem);
            }

            return shoppingCartRepository.save(shoppingCart);
        } else {
            return null;
        }
    }

    @Override
    public ShoppingCart findShoppingCartById(Long cartId) {
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(cartId);
        if(shoppingCartOptional.isPresent()){
            ShoppingCart shoppingCart = shoppingCartOptional.get();
            float totalPrice = 0F;

            for(ShoppingCartItem item : shoppingCart.getShoppingCartItems()){
                totalPrice = totalPrice + item.getProduct().getPrice() * item.getQuantity();
            }

            shoppingCart.setTotalPrice(totalPrice);

            return shoppingCart;
        } else {
            return null;
        }
    }

    @Override
    public Boolean removeItemFromCart(Product product, Long cartId) {

        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(cartId);
        if(shoppingCartOptional.isPresent()){

            ShoppingCart shoppingCart = shoppingCartOptional.get();
            List<ShoppingCartItem> shoppingCartItems = shoppingCart.getShoppingCartItems();

            ShoppingCartItem itemToDelete = new ShoppingCartItem();
            itemToDelete.setItemId(0L);

            for(ShoppingCartItem item : shoppingCartItems){

                if(item.getProduct().getId().equals(product.getId())){

                    if(item.getQuantity() > 1){
                        item.setQuantity(item.getQuantity() - 1);
                        shoppingCartItemRepository.save(item);

                    } else if(item.getQuantity().equals(1L)) {
                        itemToDelete = item;
                    }
                }
            }

            shoppingCart.setShoppingCartItems(shoppingCartItems);
            shoppingCartRepository.save(shoppingCart);

            if(!itemToDelete.getItemId().equals(0L)){
                shoppingCartItems.remove(itemToDelete);
                shoppingCartItemRepository.delete(itemToDelete);
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public Integer getShoppingCartIndicator(Long cartId) {
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(cartId);

        if(shoppingCartOptional.isPresent()){
            ShoppingCart shoppingCart = shoppingCartOptional.get();
            int counter = 0;

            for (ShoppingCartItem item : shoppingCart.getShoppingCartItems()){
                counter++;
                if(item.getQuantity() > 1){
                    counter = counter + item.getQuantity().intValue() - 1;
                }
            }

            return counter;
        } else {
            return 0;
        }
    }

    @Override
    public Boolean deleteShoppingCart(Long cartId) {

        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(cartId);
        if(shoppingCartOptional.isPresent()){
            ShoppingCart shoppingCart = shoppingCartOptional.get();

            shoppingCartRepository.delete(shoppingCart);

            return true;
        } else {
            return false;
        }
    }


}
