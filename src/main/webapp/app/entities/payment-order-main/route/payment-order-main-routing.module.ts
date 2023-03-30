import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PaymentOrderMainComponent } from '../list/payment-order-main.component';
import { PaymentOrderMainDetailComponent } from '../detail/payment-order-main-detail.component';
import { PaymentOrderMainUpdateComponent } from '../update/payment-order-main-update.component';
import { PaymentOrderMainRoutingResolveService } from './payment-order-main-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const paymentOrderMainRoute: Routes = [
  {
    path: '',
    component: PaymentOrderMainComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentOrderMainDetailComponent,
    resolve: {
      paymentOrderMain: PaymentOrderMainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentOrderMainUpdateComponent,
    resolve: {
      paymentOrderMain: PaymentOrderMainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentOrderMainUpdateComponent,
    resolve: {
      paymentOrderMain: PaymentOrderMainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(paymentOrderMainRoute)],
  exports: [RouterModule],
})
export class PaymentOrderMainRoutingModule {}
