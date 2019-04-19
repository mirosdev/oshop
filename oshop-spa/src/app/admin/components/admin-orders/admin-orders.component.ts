import { Component, OnInit } from '@angular/core';
import {UserDataService} from '../../../shared/services/data/user-data.service';

@Component({
  selector: 'app-admin-orders',
  templateUrl: './admin-orders.component.html',
  styleUrls: ['./admin-orders.component.css']
})
export class AdminOrdersComponent implements OnInit {

  orders: any[];

  constructor(private userDataService: UserDataService) { }

  ngOnInit() {
    this.userDataService.fetchAllOrders()
      .subscribe(
        response => {
          this.orders = response;
        });
  }

}
