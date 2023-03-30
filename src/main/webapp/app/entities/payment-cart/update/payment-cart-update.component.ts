import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PaymentCartFormService, PaymentCartFormGroup } from './payment-cart-form.service';
import { IPaymentCart } from '../payment-cart.model';
import { PaymentCartService } from '../service/payment-cart.service';
import { ICart } from 'app/entities/cart/cart.model';
import { CartService } from 'app/entities/cart/service/cart.service';
import { PaymentStatusEnum } from 'app/entities/enumerations/payment-status-enum.model';

@Component({
  selector: 'jhi-payment-cart-update',
  templateUrl: './payment-cart-update.component.html',
})
export class PaymentCartUpdateComponent implements OnInit {
  isSaving = false;
  paymentCart: IPaymentCart | null = null;
  paymentStatusEnumValues = Object.keys(PaymentStatusEnum);

  cartsSharedCollection: ICart[] = [];

  editForm: PaymentCartFormGroup = this.paymentCartFormService.createPaymentCartFormGroup();

  constructor(
    protected paymentCartService: PaymentCartService,
    protected paymentCartFormService: PaymentCartFormService,
    protected cartService: CartService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCart = (o1: ICart | null, o2: ICart | null): boolean => this.cartService.compareCart(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentCart }) => {
      this.paymentCart = paymentCart;
      if (paymentCart) {
        this.updateForm(paymentCart);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentCart = this.paymentCartFormService.getPaymentCart(this.editForm);
    if (paymentCart.id !== null) {
      this.subscribeToSaveResponse(this.paymentCartService.update(paymentCart));
    } else {
      this.subscribeToSaveResponse(this.paymentCartService.create(paymentCart));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentCart>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(paymentCart: IPaymentCart): void {
    this.paymentCart = paymentCart;
    this.paymentCartFormService.resetForm(this.editForm, paymentCart);

    this.cartsSharedCollection = this.cartService.addCartToCollectionIfMissing<ICart>(this.cartsSharedCollection, paymentCart.cart);
  }

  protected loadRelationshipsOptions(): void {
    this.cartService
      .query()
      .pipe(map((res: HttpResponse<ICart[]>) => res.body ?? []))
      .pipe(map((carts: ICart[]) => this.cartService.addCartToCollectionIfMissing<ICart>(carts, this.paymentCart?.cart)))
      .subscribe((carts: ICart[]) => (this.cartsSharedCollection = carts));
  }
}
