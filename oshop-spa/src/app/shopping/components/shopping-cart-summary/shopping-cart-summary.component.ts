import {Component, Input, OnInit} from '@angular/core';
import {CartModel} from '../../../shared/models/cart.model';

@Component({
  selector: 'app-shopping-cart-summary',
  templateUrl: './shopping-cart-summary.component.html',
  styleUrls: ['./shopping-cart-summary.component.css']
})
export class ShoppingCartSummaryComponent implements OnInit {

  @Input() cart: CartModel;
  @Input() shoppingCartItemCount: number;

  constructor() { }

  ngOnInit() {
  }

}
