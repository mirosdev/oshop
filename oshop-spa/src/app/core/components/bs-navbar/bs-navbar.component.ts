import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../../../shared/services/auth/auth.service';
import {Subscription} from 'rxjs';
import {UserDataService} from '../../../shared/services/data/user-data.service';

@Component({
  selector: 'app-bs-navbar',
  templateUrl: './bs-navbar.component.html',
  styleUrls: ['./bs-navbar.component.css']
})
export class BsNavbarComponent implements OnInit, OnDestroy {

  isAuth = false;
  isAdmin = false;
  authSubscription: Subscription;
  adminSubscription: Subscription;
  cartChangedSubscription: Subscription;
  shoppingCartItemCount: number;

  constructor(private authService: AuthService,
              private userDataService: UserDataService) { }

  ngOnInit() {

    if (localStorage.getItem('cartId')) {
      this.userDataService.fetchCartItemsIndicator(localStorage.getItem('cartId'))
        .subscribe(
          response => {
            this.shoppingCartItemCount = response;
          });
    } else {
      this.shoppingCartItemCount = 0;
    }

    this.cartChangedSubscription = this.userDataService.cartChanged
      .subscribe(
        isChanged => {
          if (isChanged) {
            const cartId = localStorage.getItem('cartId');

            if (cartId) {
              this.userDataService.fetchCartItemsIndicator(cartId)
                .subscribe(
                  response => {
                    this.shoppingCartItemCount = response;
                  });

            } else {
              this.shoppingCartItemCount = 0;
            }
          }
        });

    this.authSubscription = this.authService.isAuth.subscribe(
      isAuth => {
        this.isAuth = isAuth;
      });

    this.adminSubscription = this.authService.isAdminChange
      .subscribe(
        isAdmin => {
          this.isAdmin = isAdmin;
        });

    if (this.authService.isAuthenticated() != null) {
      this.authService.isAuth.next(true);
    } else {
      this.authService.isAuth.next(false);
    }

    if (this.authService.isAdmin() != null) {
      this.authService.isAdminChange.next(true);
    } else {
      this.authService.isAdminChange.next(false);
    }
  }

  logout() {
    this.authService.logout();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
    if (this.adminSubscription) {
      this.adminSubscription.unsubscribe();
    }
    if (this.cartChangedSubscription) {
      this.cartChangedSubscription.unsubscribe();
    }
  }
}
