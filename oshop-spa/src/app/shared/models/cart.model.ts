import {ProductModel} from './product.model';

export interface CartModel {
  cartId: number;
  createdAt: Date;
  totalPrice: number;
  shoppingCartItems: ShoppingCartItems[];
}

export interface ShoppingCartItems {
  itemId: number;
  product: ProductModel;
  quantity: number;
}
