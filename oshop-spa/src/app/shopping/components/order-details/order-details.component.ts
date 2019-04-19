import {Component, OnInit} from '@angular/core';
import {UserDataService} from '../../../shared/services/data/user-data.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css']
})
export class OrderDetailsComponent implements OnInit {

  order: any;

  constructor(private userDataService: UserDataService,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    const orderId = this.activatedRoute.snapshot.params.id;
    this.userDataService.fetchOrderById(orderId)
      .subscribe(
        response => {
          this.order = response;
        }, () => {
          console.log('navigate to "unavailable" or something');
        });
  }

}
