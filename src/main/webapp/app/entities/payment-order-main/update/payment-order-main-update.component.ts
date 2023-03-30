import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PaymentOrderMainFormService, PaymentOrderMainFormGroup } from './payment-order-main-form.service';
import { IPaymentOrderMain } from '../payment-order-main.model';
import { PaymentOrderMainService } from '../service/payment-order-main.service';
import { IOrderMain } from 'app/entities/order-main/order-main.model';
import { OrderMainService } from 'app/entities/order-main/service/order-main.service';

@Component({
  selector: 'jhi-payment-order-main-update',
  templateUrl: './payment-order-main-update.component.html',
})
export class PaymentOrderMainUpdateComponent implements OnInit {
  isSaving = false;
  paymentOrderMain: IPaymentOrderMain | null = null;

  orderMainsSharedCollection: IOrderMain[] = [];

  editForm: PaymentOrderMainFormGroup = this.paymentOrderMainFormService.createPaymentOrderMainFormGroup();

  constructor(
    protected paymentOrderMainService: PaymentOrderMainService,
    protected paymentOrderMainFormService: PaymentOrderMainFormService,
    protected orderMainService: OrderMainService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareOrderMain = (o1: IOrderMain | null, o2: IOrderMain | null): boolean => this.orderMainService.compareOrderMain(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentOrderMain }) => {
      this.paymentOrderMain = paymentOrderMain;
      if (paymentOrderMain) {
        this.updateForm(paymentOrderMain);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentOrderMain = this.paymentOrderMainFormService.getPaymentOrderMain(this.editForm);
    if (paymentOrderMain.id !== null) {
      this.subscribeToSaveResponse(this.paymentOrderMainService.update(paymentOrderMain));
    } else {
      this.subscribeToSaveResponse(this.paymentOrderMainService.create(paymentOrderMain));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentOrderMain>>): void {
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

  protected updateForm(paymentOrderMain: IPaymentOrderMain): void {
    this.paymentOrderMain = paymentOrderMain;
    this.paymentOrderMainFormService.resetForm(this.editForm, paymentOrderMain);

    this.orderMainsSharedCollection = this.orderMainService.addOrderMainToCollectionIfMissing<IOrderMain>(
      this.orderMainsSharedCollection,
      paymentOrderMain.orderMain
    );
  }

  protected loadRelationshipsOptions(): void {
    this.orderMainService
      .query()
      .pipe(map((res: HttpResponse<IOrderMain[]>) => res.body ?? []))
      .pipe(
        map((orderMains: IOrderMain[]) =>
          this.orderMainService.addOrderMainToCollectionIfMissing<IOrderMain>(orderMains, this.paymentOrderMain?.orderMain)
        )
      )
      .subscribe((orderMains: IOrderMain[]) => (this.orderMainsSharedCollection = orderMains));
  }
}
