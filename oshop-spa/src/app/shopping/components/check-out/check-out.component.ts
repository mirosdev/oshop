import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserDataService} from '../../../shared/services/data/user-data.service';
import {CartModel} from '../../../shared/models/cart.model';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-check-out',
  templateUrl: './check-out.component.html',
  styleUrls: ['./check-out.component.css']
})
export class CheckOutComponent implements OnInit, OnDestroy {


  cart: CartModel;
  shoppingCartItemCount: number;
  cartSubscription: Subscription;

  constructor(private userDataService: UserDataService) { }

  ngOnInit() {
    const cartId = localStorage.getItem('cartId');
    this.userDataService.fetchShoppingCartById(+cartId)
      .subscribe(
        response => {
          this.cart = response;
          this.userDataService.fetchCartItemsIndicator(cartId)
            .subscribe(
              indicator => {
                this.shoppingCartItemCount = indicator;
              });
        });
    this.cartSubscription = this.userDataService.cartChanged
      .subscribe(
        () => {
          this.userDataService.fetchShoppingCartById(+cartId)
            .subscribe(
              response => {
                this.cart = response;
                this.userDataService.fetchCartItemsIndicator(cartId)
                  .subscribe(
                    indicator => {
                      this.shoppingCartItemCount = indicator;
                    });
              });
        });
  }

  ngOnDestroy(): void {
    if (this.cartSubscription) {
      this.cartSubscription.unsubscribe();
    }
  }

}
