import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ShipmentCartComponent } from './list/shipment-cart.component';
import { ShipmentCartDetailComponent } from './detail/shipment-cart-detail.component';
import { ShipmentCartUpdateComponent } from './update/shipment-cart-update.component';
import { ShipmentCartDeleteDialogComponent } from './delete/shipment-cart-delete-dialog.component';
import { ShipmentCartRoutingModule } from './route/shipment-cart-routing.module';

@NgModule({
  imports: [SharedModule, ShipmentCartRoutingModule],
  declarations: [ShipmentCartComponent, ShipmentCartDetailComponent, ShipmentCartUpdateComponent, ShipmentCartDeleteDialogComponent],
})
export class ShipmentCartModule {}
