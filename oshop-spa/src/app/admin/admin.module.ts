import { NgModule } from '@angular/core';
import {AdminProductsComponent} from './components/admin-products/admin-products.component';
import {AdminOrdersComponent} from './components/admin-orders/admin-orders.component';
import {ProductFormComponent} from './components/product-form/product-form.component';
import {AdminAuthGuard} from './services/admin-auth.guard';
import {AdminDataService} from './services/admin-data.service';
import {SharedModule} from '../shared/shared.module';
import {AdminRoutingModule} from './admin-routing.module';

@NgModule({
  declarations: [
    AdminProductsComponent,
    AdminOrdersComponent,
    ProductFormComponent
  ],
  imports: [
    SharedModule,
    AdminRoutingModule
  ],
  providers: [
    AdminAuthGuard,
    AdminDataService
  ]
})
export class AdminModule { }
