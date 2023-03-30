import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentCart } from '../payment-cart.model';
import { PaymentCartService } from '../service/payment-cart.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './payment-cart-delete-dialog.component.html',
})
export class PaymentCartDeleteDialogComponent {
  paymentCart?: IPaymentCart;

  constructor(protected paymentCartService: PaymentCartService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paymentCartService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
