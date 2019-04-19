import {Component, OnDestroy, OnInit} from '@angular/core';
import {AdminDataService} from '../../services/admin-data.service';
import {Observable, Subscription} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {ProductModel} from '../../../shared/models/product.model';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css']
})
export class ProductFormComponent implements OnInit, OnDestroy {

  categories$: Observable<any>;
  product: ProductModel = new class implements ProductModel {
    category: string;
    id?: number;
    imageUrl: string;
    price: number;
    title: string;
  };
  fetchProductSubscription: Subscription;

  constructor(private adminDataService: AdminDataService,
              private router: Router,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.categories$ = this.adminDataService.fetchCategories();

    const id = this.activatedRoute.snapshot.paramMap.get('id');
    if (id !== null) {
      this.fetchProductSubscription = this.adminDataService.fetchProductById(+id)
        .subscribe(
          response => {
            this.product = response;
          }, () => {
          }
        );
    }
  }

  save(product) {
    if (this.product.id) {
      this.adminDataService.saveProduct(this.product)
        .subscribe(
          () => {
          }, () => {
          });
    } else {
      this.adminDataService.saveProduct(product)
        .subscribe(
          () => {
            // console.log(result);
          }, () => {
            // console.log(error);
          });
    }
    this.router.navigate(['/admin/products']);
  }

  delete() {
    if (!confirm('Are you sure you want to delete this product?')) {
      return;
    }
    this.adminDataService.deleteProductById(this.product.id)
      .subscribe(
        () => {
        }, () => {
        });
    this.router.navigate(['/admin/products']);
  }

  ngOnDestroy(): void {
    if (this.fetchProductSubscription) {
      this.fetchProductSubscription.unsubscribe();
    }
  }
}
