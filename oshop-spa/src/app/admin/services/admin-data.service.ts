import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {httpOptions} from '../../shared/services/auth/auth.service';
import {ProductModel} from '../../shared/models/product.model';
import {Observable} from 'rxjs';

const CATEGORIES_URL = 'http://localhost:8080/api/admin/oshop/categories';
const SAVE_PRODUCT_URL = 'http://localhost:8080/api/admin/oshop/save';
const ALL_PRODUCTS_URL = 'http://localhost:8080/api/admin/oshop/products';
const PRODUCT_BY_ID_URL = 'http://localhost:8080/api/admin/oshop/';
const DELETE_PRODUCT_BY_ID = 'http://localhost:8080/api/admin/oshop/';

@Injectable({
  providedIn: 'root'
})
export class AdminDataService {

  constructor(private httpClient: HttpClient) { }

  fetchCategories() {
    return this.httpClient.get(CATEGORIES_URL, httpOptions);
  }

  saveProduct(product: ProductModel) {
    return this.httpClient.post<ProductModel>(SAVE_PRODUCT_URL, product);
  }

  fetchAllProducts(): Observable<ProductModel[]> {
    return this.httpClient.get<ProductModel[]>(ALL_PRODUCTS_URL);
  }

  fetchProductById(productId: number): Observable<ProductModel> {
    return this.httpClient.get<ProductModel>(PRODUCT_BY_ID_URL + productId);
  }

  deleteProductById(productId: number) {
    return this.httpClient.delete(DELETE_PRODUCT_BY_ID + productId);
  }
}
