import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductInCart } from '../product-in-cart.model';
import { ProductInCartService } from '../service/product-in-cart.service';

@Injectable({ providedIn: 'root' })
export class ProductInCartRoutingResolveService implements Resolve<IProductInCart | null> {
  constructor(protected service: ProductInCartService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductInCart | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((productInCart: HttpResponse<IProductInCart>) => {
          if (productInCart.body) {
            return of(productInCart.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
