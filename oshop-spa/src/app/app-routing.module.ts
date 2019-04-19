import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './core/components/login/login.component';
import {LoginGuard} from './shared/services/auth/login.guard';
import {ProductsComponent} from './shopping/components/products/products.component';

const routes: Routes = [
  {path: '', component: ProductsComponent},
  {path: 'login', component: LoginComponent, canActivate: [LoginGuard]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
