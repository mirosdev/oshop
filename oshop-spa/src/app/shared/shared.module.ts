import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProductCardComponent} from './components/product-card/product-card.component';
import {AuthGuard} from './services/auth/auth.guard';
import {LoginGuard} from './services/auth/login.guard';
import {AuthService} from './services/auth/auth.service';
import {UserDataService} from './services/data/user-data.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CustomFormsModule} from 'ng2-validation';
import {DataTableModule} from 'angular5-data-table';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    ProductCardComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    CustomFormsModule,
    DataTableModule,
    ReactiveFormsModule,
    NgbModule
  ],
  exports: [
    CommonModule,
    ProductCardComponent,
    FormsModule,
    ReactiveFormsModule,
    CustomFormsModule,
    DataTableModule,
    NgbModule
  ],
  providers: [
    AuthGuard,
    LoginGuard,
    AuthService,
    UserDataService
  ]
})
export class SharedModule { }
