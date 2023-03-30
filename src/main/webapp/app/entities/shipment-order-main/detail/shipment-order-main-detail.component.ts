import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShipmentOrderMain } from '../shipment-order-main.model';

@Component({
  selector: 'jhi-shipment-order-main-detail',
  templateUrl: './shipment-order-main-detail.component.html',
})
export class ShipmentOrderMainDetailComponent implements OnInit {
  shipmentOrderMain: IShipmentOrderMain | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shipmentOrderMain }) => {
      this.shipmentOrderMain = shipmentOrderMain;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
