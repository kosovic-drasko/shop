import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPaymentOrderMain } from '../payment-order-main.model';
import { PaymentOrderMainService } from '../service/payment-order-main.service';

@Injectable({ providedIn: 'root' })
export class PaymentOrderMainRoutingResolveService implements Resolve<IPaymentOrderMain | null> {
  constructor(protected service: PaymentOrderMainService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentOrderMain | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((paymentOrderMain: HttpResponse<IPaymentOrderMain>) => {
          if (paymentOrderMain.body) {
            return of(paymentOrderMain.body);
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
