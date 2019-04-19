import {Component, Input, OnInit} from '@angular/core';
import {ProductModel} from '../../models/product.model';
import {UserDataService} from '../../services/data/user-data.service';
import {CartModel} from '../../models/cart.model';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css']
})
export class ProductCardComponent implements OnInit {

  @Input() product: ProductModel;
  @Input() showActions: true;
  @Input() shoppingCart: CartModel;
  cart: CartModel;

  constructor(private userDataService: UserDataService) { }

  ngOnInit() {
  }

  addToCart() {
    const cartId = localStorage.getItem('cartId');
    if (!cartId) {
      this.userDataService.createShoppingCart(this.product)
        .subscribe(
          response => {
            this.cart = response;
            localStorage.setItem('cartId', this.cart.cartId.toString());

            this.userDataService.fetchShoppingCartById(this.cart.cartId)
              .subscribe(
                newResponse => {
                  this.shoppingCart = newResponse;
                  this.userDataService.cartChanged.next(true);
                });
          });
    } else {
      this.userDataService.addToShoppingCart(this.product, cartId)
        .subscribe(
          () => {
            this.userDataService.fetchShoppingCartById(+cartId)
              .subscribe(
                newResponse => {
                  this.shoppingCart = newResponse;
                  this.userDataService.cartChanged.next(true);
                });
          });
    }
  }

  removeFromCart() {
    const cartId = localStorage.getItem('cartId');
    this.userDataService.removeItemFromShoppingCart(this.product, cartId)
      .subscribe(
        () => {
          this.userDataService.fetchShoppingCartById(+cartId)
            .subscribe(
              newResponse => {
                this.shoppingCart = newResponse;
                if (this.shoppingCart.shoppingCartItems.length === 0) {
                  localStorage.removeItem('cartId');

                  this.userDataService.removeShoppingCart(cartId)
                    .subscribe(
                      () => {
                        this.userDataService.cartChanged.next(true);
                      });
                }
                this.userDataService.cartChanged.next(true);
              });
        });

  }

  getQuantity() {
    if (!this.shoppingCart) {
      return 0;
    }

    let quantity = 0;
    this.shoppingCart.shoppingCartItems
      .forEach(
        item => {
          if (item.product.id === this.product.id) {
            quantity = item.quantity;
          }
        });
    return quantity;
  }

}
