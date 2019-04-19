import { Component, OnInit } from '@angular/core';
import {UserDataService} from '../../../shared/services/data/user-data.service';
import {CartModel, ShoppingCartItems} from '../../../shared/models/cart.model';
import {ProductModel} from '../../../shared/models/product.model';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {
  cart: CartModel;
  cartItems: ShoppingCartItems[];
  shoppingCartItemCount = 0;

  constructor(private userDataService: UserDataService) { }

  ngOnInit() {
    const cartId = localStorage.getItem('cartId');
    if (cartId) {
      this.userDataService.fetchShoppingCartById(+cartId)
        .subscribe(
          response => {
            this.cart = response;
            this.cartItems = this.cart.shoppingCartItems;
            if (response == null) {
              // If H2 Database resets then clear cartId from localStorage.
              localStorage.removeItem('cartId');
            }
          });

      this.userDataService.fetchCartItemsIndicator(cartId)
        .subscribe(
          response => {
            this.shoppingCartItemCount = response;
          });
    }
  }

  addToCart(product: ProductModel) {
    const cartId = localStorage.getItem('cartId');
    if (cartId) {
      this.userDataService.addToShoppingCart(product, cartId)
        .subscribe(
          () => {
            this.userDataService.fetchShoppingCartById(+cartId)
              .subscribe(
                newResponse => {
                  this.cart = newResponse;
                  this.cartItems = this.cart.shoppingCartItems;
                  this.userDataService.cartChanged.next(true);

                  this.userDataService.fetchCartItemsIndicator(cartId)
                    .subscribe(
                      indicator => {
                        this.shoppingCartItemCount = indicator;
                      });
                });
          });
    }
  }

  removeFromCart(product: ProductModel) {
    const cartId = localStorage.getItem('cartId');
    if (cartId) {
      this.userDataService.removeItemFromShoppingCart(product, cartId)
        .subscribe(
          () => {
            this.userDataService.fetchShoppingCartById(+cartId)
              .subscribe(
                newResponse => {
                  this.cart = newResponse;
                  if (this.cart.shoppingCartItems.length === 0) {

                    localStorage.removeItem('cartId');
                    this.cart = null;
                    this.cartItems = null;
                    this.shoppingCartItemCount = 0;

                    this.userDataService.removeShoppingCart(cartId)
                      .subscribe(
                        () => {
                          this.userDataService.cartChanged.next(true);
                        });

                  } else {

                    this.cartItems = this.cart.shoppingCartItems;
                    this.userDataService.cartChanged.next(true);

                    this.userDataService.fetchCartItemsIndicator(cartId)
                      .subscribe(
                        indicator => {
                          this.shoppingCartItemCount = indicator;
                        });
                  }
                });
          });
    }
  }

  clearCart() {
    const cartId = localStorage.getItem('cartId');
    if (cartId) {
      this.userDataService.removeShoppingCart(cartId)
        .subscribe(
          () => {
            localStorage.removeItem('cartId');
            this.cart = null;
            this.cartItems = null;
            this.shoppingCartItemCount = 0;
            this.userDataService.cartChanged.next(true);
          });
    }
  }
}
