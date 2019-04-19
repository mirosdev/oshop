import {Component, Input, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {UserDataService} from '../../../../shared/services/data/user-data.service';

@Component({
  selector: 'app-product-filter',
  templateUrl: './product-filter.component.html',
  styleUrls: ['./product-filter.component.css']
})
export class ProductFilterComponent implements OnInit {

  categories$: Observable<any>;
  @Input() category;

  constructor(private userDataService: UserDataService) { }

  ngOnInit() {
    this.categories$ = this.userDataService.fetchCategories();
  }

}
