import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TOKEN} from './auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const token: string = localStorage.getItem(TOKEN);

    if (req.headers.get('No-Auth') === 'True') {

      return next.handle(req.clone());

    } else if (token) {

      req = req.clone({
        setHeaders: { Authorization : 'Bearer ' + token}
      });
    }
    return next.handle(req.clone());

  }

}
