import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthData} from '../../models/auth-data.model';
import {ActivatedRoute, Router} from '@angular/router';
import {map} from 'rxjs/operators';
import {Subject} from 'rxjs';
import {LoginResponseModel} from '../../models/login.response.model';

export const httpOptions = {
  headers: new HttpHeaders({'No-Auth': 'True', 'Content-Type': 'application/json' })
};

export const TOKEN = 'token';
export const IS_ADMIN = 'admin';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isAuth = new Subject<boolean>();
  isAdminChange = new Subject<boolean>();

  private loginUrl = 'http://localhost:8080/api/users/login';
  private signupUrl = 'http://localhost:8080/api/users/register';

  constructor(private httpClient: HttpClient,
              private router: Router,
              private activatedRoute: ActivatedRoute) { }

  attemptAuth(authData: AuthData) {
    const returnUrl = this.activatedRoute.snapshot.queryParamMap.get('returnUrl') || '/';
    localStorage.setItem('returnUrl', returnUrl);

    return this.httpClient.post<LoginResponseModel>(this.loginUrl, authData, httpOptions).pipe(
      map(
        data => {
          localStorage.setItem(TOKEN, data.token.substring(7));
          data.authorities
            .forEach(value => {
              if (value.authority === 'ROLE_ADMIN') {
                localStorage.setItem(IS_ADMIN, 'true');
                this.isAdminChange.next(true);
              }
            });
          return data;
        }
      )
    );
  }

  // signUp(signUpData: SignUpData) {
  //   return this.httpClient.post(this.signupUrl, signUpData, httpOptions);
  // }

  logout() {
    localStorage.removeItem(TOKEN);
    localStorage.removeItem(IS_ADMIN);
    this.isAuth.next(false);
    this.isAdminChange.next(false);
    this.router.navigate(['/login']);
  }

  isAuthenticated() {
    const token = localStorage.getItem(TOKEN);
    return token ? token : null;
  }

  isAdmin() {
    const isAdmin = localStorage.getItem(IS_ADMIN);
    return isAdmin ? isAdmin : null;
  }
}
