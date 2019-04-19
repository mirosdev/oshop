import {RouterModule, Routes} from '@angular/router';
import {ProductsComponent} from './components/products/products.component';
import {ShoppingCartComponent} from './components/shopping-cart/shopping-cart.component';
import {CheckOutComponent} from './components/check-out/check-out.component';
import {AuthGuard} from '../shared/services/auth/auth.guard';
import {OrderSuccessComponent} from './components/order-success/order-success.component';
import {MyOrdersComponent} from './components/my-orders/my-orders.component';
import {OrderDetailsComponent} from './components/order-details/order-details.component';
import {NgModule} from '@angular/core';

const routes: Routes = [
  {path: 'products', component: ProductsComponent},
  {path: 'shopping-cart', component: ShoppingCartComponent},
  {path: 'check-out', component: CheckOutComponent, canActivate: [AuthGuard]},
  {path: 'order-success/:id', component: OrderSuccessComponent, canActivate: [AuthGuard]},
  {path: 'my/orders', component: MyOrdersComponent, canActivate: [AuthGuard]},
  {path: 'my/order/:id', component: OrderDetailsComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class ShoppingRoutingModule {
}
