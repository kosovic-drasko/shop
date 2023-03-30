import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ShipmentOrderMainFormService, ShipmentOrderMainFormGroup } from './shipment-order-main-form.service';
import { IShipmentOrderMain } from '../shipment-order-main.model';
import { ShipmentOrderMainService } from '../service/shipment-order-main.service';
import { IOrderMain } from 'app/entities/order-main/order-main.model';
import { OrderMainService } from 'app/entities/order-main/service/order-main.service';

@Component({
  selector: 'jhi-shipment-order-main-update',
  templateUrl: './shipment-order-main-update.component.html',
})
export class ShipmentOrderMainUpdateComponent implements OnInit {
  isSaving = false;
  shipmentOrderMain: IShipmentOrderMain | null = null;

  orderMainsSharedCollection: IOrderMain[] = [];

  editForm: ShipmentOrderMainFormGroup = this.shipmentOrderMainFormService.createShipmentOrderMainFormGroup();

  constructor(
    protected shipmentOrderMainService: ShipmentOrderMainService,
    protected shipmentOrderMainFormService: ShipmentOrderMainFormService,
    protected orderMainService: OrderMainService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareOrderMain = (o1: IOrderMain | null, o2: IOrderMain | null): boolean => this.orderMainService.compareOrderMain(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shipmentOrderMain }) => {
      this.shipmentOrderMain = shipmentOrderMain;
      if (shipmentOrderMain) {
        this.updateForm(shipmentOrderMain);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shipmentOrderMain = this.shipmentOrderMainFormService.getShipmentOrderMain(this.editForm);
    if (shipmentOrderMain.id !== null) {
      this.subscribeToSaveResponse(this.shipmentOrderMainService.update(shipmentOrderMain));
    } else {
      this.subscribeToSaveResponse(this.shipmentOrderMainService.create(shipmentOrderMain));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShipmentOrderMain>>): void {
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

  protected updateForm(shipmentOrderMain: IShipmentOrderMain): void {
    this.shipmentOrderMain = shipmentOrderMain;
    this.shipmentOrderMainFormService.resetForm(this.editForm, shipmentOrderMain);

    this.orderMainsSharedCollection = this.orderMainService.addOrderMainToCollectionIfMissing<IOrderMain>(
      this.orderMainsSharedCollection,
      shipmentOrderMain.orderMain
    );
  }

  protected loadRelationshipsOptions(): void {
    this.orderMainService
      .query()
      .pipe(map((res: HttpResponse<IOrderMain[]>) => res.body ?? []))
      .pipe(
        map((orderMains: IOrderMain[]) =>
          this.orderMainService.addOrderMainToCollectionIfMissing<IOrderMain>(orderMains, this.shipmentOrderMain?.orderMain)
        )
      )
      .subscribe((orderMains: IOrderMain[]) => (this.orderMainsSharedCollection = orderMains));
  }
}
