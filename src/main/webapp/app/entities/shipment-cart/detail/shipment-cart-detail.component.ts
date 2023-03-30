import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShipmentCart } from '../shipment-cart.model';

@Component({
  selector: 'jhi-shipment-cart-detail',
  templateUrl: './shipment-cart-detail.component.html',
})
export class ShipmentCartDetailComponent implements OnInit {
  shipmentCart: IShipmentCart | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shipmentCart }) => {
      this.shipmentCart = shipmentCart;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
