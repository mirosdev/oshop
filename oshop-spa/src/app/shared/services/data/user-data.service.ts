import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';
import {ProductModel} from '../../models/product.model';
import {httpOptions} from '../auth/auth.service';
import {CartModel} from '../../models/cart.model';

const CATEGORIES_URL = 'http://localhost:8080/api/admin/oshop/categories';
const ALL_PRODUCTS_URL = 'http://localhost:8080/api/user/oshop/products';
const CREATE_CART = 'http://localhost:8080/api/user/oshop/cart/save';
const ADD_TO_CART = 'http://localhost:8080/api/user/oshop/cart/save/';
const FETCH_CART = 'http://localhost:8080/api/user/oshop/cart/';
const REMOVE_FROM_CART = 'http://localhost:8080/api/user/oshop/cart/remove/';
const CART_INDICATOR = 'http://localhost:8080/api/user/oshop/cart/indicator/';
const REMOVE_CART = 'http://localhost:8080/api/user/oshop/cart/delete/';
const STORE_ORDER = 'http://localhost:8080/api/users/order/save';
const FETCH_ALL_ORDERS = 'http://localhost:8080/api/admin/oshop/orders';
const FETCH_ALL_ORDERS_FOR_USER = 'http://localhost:8080/api/users/orders';
const FETCH_ORDER_BY_ID = 'http://localhost:8080/api/users/order/';

@Injectable({
  providedIn: 'root'
})
export class UserDataService {

  cartChanged = new Subject<boolean>();

  constructor(private httpClient: HttpClient) {}

  fetchCategories(): Observable<any[]> {
    return this.httpClient.get<any[]>(CATEGORIES_URL, httpOptions);
  }

  fetchAllProducts(): Observable<ProductModel[]> {
    return this.httpClient.get<ProductModel[]>(ALL_PRODUCTS_URL, httpOptions);
  }

  createShoppingCart(product: ProductModel) {
    return this.httpClient.post<CartModel>(CREATE_CART, product, httpOptions);
  }

  addToShoppingCart(product: ProductModel, cartId: string) {
    return this.httpClient.post<CartModel>(ADD_TO_CART + cartId, product, httpOptions);
  }

  fetchShoppingCartById(cartId: number) {
    return this.httpClient.get<CartModel>(FETCH_CART + cartId, httpOptions);
  }

  removeItemFromShoppingCart(product: ProductModel, cartId: string) {
    return this.httpClient.post(REMOVE_FROM_CART + cartId, product, httpOptions);
  }

  fetchCartItemsIndicator(cartId: string) {
    return this.httpClient.get<number>(CART_INDICATOR + cartId, httpOptions);
  }

  removeShoppingCart(cartId: string) {
    return this.httpClient.delete(REMOVE_CART + cartId, httpOptions);
  }

  storeOrder(order) {
    return this.httpClient.post<any>(STORE_ORDER, order);
  }

  fetchAllOrders() {
    return this.httpClient.get<any>(FETCH_ALL_ORDERS);
  }

  fetchAllOrdersForLoggedUser() {
    return this.httpClient.get<any>(FETCH_ALL_ORDERS_FOR_USER);
  }

  fetchOrderById(orderId: number) {
    return this.httpClient.get<any>(FETCH_ORDER_BY_ID + orderId);
  }
}
