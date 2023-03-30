import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductInOrderMainComponent } from '../list/product-in-order-main.component';
import { ProductInOrderMainDetailComponent } from '../detail/product-in-order-main-detail.component';
import { ProductInOrderMainUpdateComponent } from '../update/product-in-order-main-update.component';
import { ProductInOrderMainRoutingResolveService } from './product-in-order-main-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const productInOrderMainRoute: Routes = [
  {
    path: '',
    component: ProductInOrderMainComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductInOrderMainDetailComponent,
    resolve: {
      productInOrderMain: ProductInOrderMainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductInOrderMainUpdateComponent,
    resolve: {
      productInOrderMain: ProductInOrderMainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductInOrderMainUpdateComponent,
    resolve: {
      productInOrderMain: ProductInOrderMainRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productInOrderMainRoute)],
  exports: [RouterModule],
})
export class ProductInOrderMainRoutingModule {}
