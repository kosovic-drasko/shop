import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IShipmentOrderMain } from '../shipment-order-main.model';
import { ShipmentOrderMainService } from '../service/shipment-order-main.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './shipment-order-main-delete-dialog.component.html',
})
export class ShipmentOrderMainDeleteDialogComponent {
  shipmentOrderMain?: IShipmentOrderMain;

  constructor(protected shipmentOrderMainService: ShipmentOrderMainService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.shipmentOrderMainService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
