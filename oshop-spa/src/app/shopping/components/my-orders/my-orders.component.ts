import { Component, OnInit } from '@angular/core';
import {UserDataService} from '../../../shared/services/data/user-data.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.css']
})
export class MyOrdersComponent implements OnInit {

  orders: any[];

  constructor(private userDataService: UserDataService,
              private router: Router) { }

  ngOnInit() {
    this.userDataService.fetchAllOrdersForLoggedUser()
      .subscribe(
        response => {
          this.orders = response;
        });
  }

  navigateToOrder(orderId: number) {
    this.router.navigate(['my/order/' + orderId]);
  }

}
