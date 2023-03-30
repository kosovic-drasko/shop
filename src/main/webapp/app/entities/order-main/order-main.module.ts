import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { OrderMainComponent } from './list/order-main.component';
import { OrderMainDetailComponent } from './detail/order-main-detail.component';
import { OrderMainUpdateComponent } from './update/order-main-update.component';
import { OrderMainDeleteDialogComponent } from './delete/order-main-delete-dialog.component';
import { OrderMainRoutingModule } from './route/order-main-routing.module';

@NgModule({
  imports: [SharedModule, OrderMainRoutingModule],
  declarations: [OrderMainComponent, OrderMainDetailComponent, OrderMainUpdateComponent, OrderMainDeleteDialogComponent],
})
export class OrderMainModule {}
