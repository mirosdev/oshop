import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../../shared/services/auth/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  invalidCredentials = false;

  constructor(private authService: AuthService,
              private router: Router) { }

  ngOnInit() {
    this.loginForm = new FormGroup({
      email: new FormControl('', {validators: [Validators.required, Validators.email]}),
      password: new FormControl('', {validators: [Validators.required, Validators.minLength(6)]})
    });
  }

  onSubmit() {
    this.authService.attemptAuth({
      email: this.loginForm.value.email,
      password: this.loginForm.value.password
    }).subscribe(
      () => {
        this.authService.isAuth.next(true);
        this.router.navigate([localStorage.getItem('returnUrl')]);
        localStorage.removeItem('returnUrl');
        this.invalidCredentials = false;
      }, () => {
        this.invalidCredentials = true;
    });
  }

}
