import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentOrderMain } from '../payment-order-main.model';
import { PaymentOrderMainService } from '../service/payment-order-main.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './payment-order-main-delete-dialog.component.html',
})
export class PaymentOrderMainDeleteDialogComponent {
  paymentOrderMain?: IPaymentOrderMain;

  constructor(protected paymentOrderMainService: PaymentOrderMainService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentOrderMainService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
