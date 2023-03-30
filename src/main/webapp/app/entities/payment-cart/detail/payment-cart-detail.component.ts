import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPaymentCart } from '../payment-cart.model';

@Component({
  selector: 'jhi-payment-cart-detail',
  templateUrl: './payment-cart-detail.component.html',
})
export class PaymentCartDetailComponent implements OnInit {
  paymentCart: IPaymentCart | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentCart }) => {
      this.paymentCart = paymentCart;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
