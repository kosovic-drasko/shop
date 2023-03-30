import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PaymentCartComponent } from '../list/payment-cart.component';
import { PaymentCartDetailComponent } from '../detail/payment-cart-detail.component';
import { PaymentCartUpdateComponent } from '../update/payment-cart-update.component';
import { PaymentCartRoutingResolveService } from './payment-cart-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const paymentCartRoute: Routes = [
  {
    path: '',
    component: PaymentCartComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentCartDetailComponent,
    resolve: {
      paymentCart: PaymentCartRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentCartUpdateComponent,
    resolve: {
      paymentCart: PaymentCartRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentCartUpdateComponent,
    resolve: {
      paymentCart: PaymentCartRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(paymentCartRoute)],
  exports: [RouterModule],
})
export class PaymentCartRoutingModule {}
