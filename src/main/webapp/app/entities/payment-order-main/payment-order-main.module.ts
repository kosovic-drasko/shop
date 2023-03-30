import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PaymentOrderMainComponent } from './list/payment-order-main.component';
import { PaymentOrderMainDetailComponent } from './detail/payment-order-main-detail.component';
import { PaymentOrderMainUpdateComponent } from './update/payment-order-main-update.component';
import { PaymentOrderMainDeleteDialogComponent } from './delete/payment-order-main-delete-dialog.component';
import { PaymentOrderMainRoutingModule } from './route/payment-order-main-routing.module';

@NgModule({
  imports: [SharedModule, PaymentOrderMainRoutingModule],
  declarations: [
    PaymentOrderMainComponent,
    PaymentOrderMainDetailComponent,
    PaymentOrderMainUpdateComponent,
    PaymentOrderMainDeleteDialogComponent,
  ],
})
export class PaymentOrderMainModule {}
