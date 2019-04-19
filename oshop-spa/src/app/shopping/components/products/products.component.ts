import {Component, OnInit} from '@angular/core';
import {UserDataService} from '../../../shared/services/data/user-data.service';
import {ProductModel} from '../../../shared/models/product.model';
import {ActivatedRoute} from '@angular/router';
import 'rxjs/add/operator/switchMap';
import {CartModel} from '../../../shared/models/cart.model';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  products: ProductModel[] = [];
  filteredProducts: ProductModel[] = [];
  category: string;
  cart: CartModel;

  constructor(private userDataService: UserDataService,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    if (localStorage.getItem('cartId')) {
      const cartId = localStorage.getItem('cartId');
      this.userDataService.fetchShoppingCartById(+cartId)
        .subscribe(
          response => {
            this.cart = response;
            console.log(response);
            if (response == null) {
              // If H2 Database resets then clear cartId from localStorage.
              localStorage.removeItem('cartId');
            }
          });
    } else {
      this.cart = null;
    }

    this.userDataService.fetchAllProducts()
      .switchMap(
        response => {
          this.products = response;
          return this.activatedRoute.queryParamMap;
        })
      .subscribe(param => {
        this.category = param.get('category');
        this.filteredProducts = (this.category) ?
          this.products.filter(p => p.category === this.category) :
          this.products;
      });
  }
}
