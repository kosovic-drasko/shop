import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PaymentCartComponent } from './list/payment-cart.component';
import { PaymentCartDetailComponent } from './detail/payment-cart-detail.component';
import { PaymentCartUpdateComponent } from './update/payment-cart-update.component';
import { PaymentCartDeleteDialogComponent } from './delete/payment-cart-delete-dialog.component';
import { PaymentCartRoutingModule } from './route/payment-cart-routing.module';

@NgModule({
  imports: [SharedModule, PaymentCartRoutingModule],
  declarations: [PaymentCartComponent, PaymentCartDetailComponent, PaymentCartUpdateComponent, PaymentCartDeleteDialogComponent],
})
export class PaymentCartModule {}
