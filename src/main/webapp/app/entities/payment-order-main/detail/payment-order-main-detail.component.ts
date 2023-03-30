import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentOrderMain } from '../payment-order-main.model';

@Component({
  selector: 'jhi-payment-order-main-detail',
  templateUrl: './payment-order-main-detail.component.html',
})
export class PaymentOrderMainDetailComponent implements OnInit {
  paymentOrderMain: IPaymentOrderMain | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentOrderMain }) => {
      this.paymentOrderMain = paymentOrderMain;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
