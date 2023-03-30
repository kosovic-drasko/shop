import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ShipmentOrderMainComponent } from '../list/shipment-order-main.component';
import { ShipmentOrderMainDetailComponent } from '../detail/shipment-order-main-detail.component';
import { ShipmentOrderMainUpdateComponent } from '../update/shipment-order-main-update.component';
import { ShipmentOrderMainRoutingResolveService } from './shipment-order-main-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const shipmentOrderMainRoute: Routes = [
  {
    path: '',
    component: ShipmentOrderMainComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ShipmentOrderMainDetailComponent,
    resolve: {
      shipmentOrderMain: ShipmentOrderMainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ShipmentOrderMainUpdateComponent,
    resolve: {
      shipmentOrderMain: ShipmentOrderMainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ShipmentOrderMainUpdateComponent,
    resolve: {
      shipmentOrderMain: ShipmentOrderMainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(shipmentOrderMainRoute)],
  exports: [RouterModule],
})
export class ShipmentOrderMainRoutingModule {}
