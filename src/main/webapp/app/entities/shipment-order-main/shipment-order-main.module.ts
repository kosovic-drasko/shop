import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ShipmentOrderMainComponent } from './list/shipment-order-main.component';
import { ShipmentOrderMainDetailComponent } from './detail/shipment-order-main-detail.component';
import { ShipmentOrderMainUpdateComponent } from './update/shipment-order-main-update.component';
import { ShipmentOrderMainDeleteDialogComponent } from './delete/shipment-order-main-delete-dialog.component';
import { ShipmentOrderMainRoutingModule } from './route/shipment-order-main-routing.module';

@NgModule({
  imports: [SharedModule, ShipmentOrderMainRoutingModule],
  declarations: [
    ShipmentOrderMainComponent,
    ShipmentOrderMainDetailComponent,
    ShipmentOrderMainUpdateComponent,
    ShipmentOrderMainDeleteDialogComponent,
  ],
})
export class ShipmentOrderMainModule {}
