import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IShipmentCart } from '../shipment-cart.model';
import { ShipmentCartService } from '../service/shipment-cart.service';

@Injectable({ providedIn: 'root' })
export class ShipmentCartRoutingResolveService implements Resolve<IShipmentCart | null> {
  constructor(protected service: ShipmentCartService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IShipmentCart | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((shipmentCart: HttpResponse<IShipmentCart>) => {
          if (shipmentCart.body) {
            return of(shipmentCart.body);
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
