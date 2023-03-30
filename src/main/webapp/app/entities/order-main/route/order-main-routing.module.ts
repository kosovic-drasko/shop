import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrderMainComponent } from '../list/order-main.component';
import { OrderMainDetailComponent } from '../detail/order-main-detail.component';
import { OrderMainUpdateComponent } from '../update/order-main-update.component';
import { OrderMainRoutingResolveService } from './order-main-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const orderMainRoute: Routes = [
  {
    path: '',
    component: OrderMainComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrderMainDetailComponent,
    resolve: {
      orderMain: OrderMainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrderMainUpdateComponent,
    resolve: {
      orderMain: OrderMainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrderMainUpdateComponent,
    resolve: {
      orderMain: OrderMainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(orderMainRoute)],
  exports: [RouterModule],
})
export class OrderMainRoutingModule {}
