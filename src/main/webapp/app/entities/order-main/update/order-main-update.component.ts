import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { OrderMainFormService, OrderMainFormGroup } from './order-main-form.service';
import { IOrderMain } from '../order-main.model';
import { OrderMainService } from '../service/order-main.service';
import { OrderMainStatusEnum } from 'app/entities/enumerations/order-main-status-enum.model';

@Component({
  selector: 'jhi-order-main-update',
  templateUrl: './order-main-update.component.html',
})
export class OrderMainUpdateComponent implements OnInit {
  isSaving = false;
  orderMain: IOrderMain | null = null;
  orderMainStatusEnumValues = Object.keys(OrderMainStatusEnum);

  editForm: OrderMainFormGroup = this.orderMainFormService.createOrderMainFormGroup();

  constructor(
    protected orderMainService: OrderMainService,
    protected orderMainFormService: OrderMainFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ orderMain }) => {
      this.orderMain = orderMain;
      if (orderMain) {
        this.updateForm(orderMain);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const orderMain = this.orderMainFormService.getOrderMain(this.editForm);
    if (orderMain.id !== null) {
      this.subscribeToSaveResponse(this.orderMainService.update(orderMain));
    } else {
      this.subscribeToSaveResponse(this.orderMainService.create(orderMain));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrderMain>>): void {
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

  protected updateForm(orderMain: IOrderMain): void {
    this.orderMain = orderMain;
    this.orderMainFormService.resetForm(this.editForm, orderMain);
  }
}
