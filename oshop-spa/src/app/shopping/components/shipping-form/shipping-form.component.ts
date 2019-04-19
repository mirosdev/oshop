import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserDataService} from '../../../shared/services/data/user-data.service';
import {CartModel} from '../../../shared/models/cart.model';

@Component({
  selector: 'app-shipping-form',
  templateUrl: './shipping-form.component.html',
  styleUrls: ['./shipping-form.component.css']
})
export class ShippingFormComponent implements OnInit {

  shipping = {
    name: '',
    addressLine1: '',
    addressLine2: '',
    city: ''
  };
  @Input() cart: CartModel;

  constructor(private router: Router,
              private userDataService: UserDataService) { }

  ngOnInit() {
  }

  placeOrder() {
    const order = {
      name: this.shipping.name,
      addressLine1: this.shipping.addressLine1,
      addressLine2: this.shipping.addressLine2,
      city: this.shipping.city,
      items: this.cart.shoppingCartItems.map(item => {
        return {
          title: item.product.title,
          imageUrl: item.product.imageUrl,
          price: item.product.price,
          quantity: item.quantity,
          totalPrice: item.product.price * item.quantity
        };
      }),
      overallPrice: this.cart.totalPrice
    };

    const cartId = localStorage.getItem('cartId');
    this.userDataService.storeOrder(order)
      .subscribe(
        response => {
          this.userDataService.removeShoppingCart(cartId)
            .subscribe(
              () => {
                localStorage.removeItem('cartId');
                this.userDataService.cartChanged.next(true);
                this.router.navigate(['order-success', response.productOrderId]);
              });
        });
  }

}
