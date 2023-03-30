import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ShipmentCartFormService, ShipmentCartFormGroup } from './shipment-cart-form.service';
import { IShipmentCart } from '../shipment-cart.model';
import { ShipmentCartService } from '../service/shipment-cart.service';
import { ICart } from 'app/entities/cart/cart.model';
import { CartService } from 'app/entities/cart/service/cart.service';

@Component({
  selector: 'jhi-shipment-cart-update',
  templateUrl: './shipment-cart-update.component.html',
})
export class ShipmentCartUpdateComponent implements OnInit {
  isSaving = false;
  shipmentCart: IShipmentCart | null = null;

  cartsSharedCollection: ICart[] = [];

  editForm: ShipmentCartFormGroup = this.shipmentCartFormService.createShipmentCartFormGroup();

  constructor(
    protected shipmentCartService: ShipmentCartService,
    protected shipmentCartFormService: ShipmentCartFormService,
    protected cartService: CartService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCart = (o1: ICart | null, o2: ICart | null): boolean => this.cartService.compareCart(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shipmentCart }) => {
      this.shipmentCart = shipmentCart;
      if (shipmentCart) {
        this.updateForm(shipmentCart);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shipmentCart = this.shipmentCartFormService.getShipmentCart(this.editForm);
    if (shipmentCart.id !== null) {
      this.subscribeToSaveResponse(this.shipmentCartService.update(shipmentCart));
    } else {
      this.subscribeToSaveResponse(this.shipmentCartService.create(shipmentCart));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShipmentCart>>): void {
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

  protected updateForm(shipmentCart: IShipmentCart): void {
    this.shipmentCart = shipmentCart;
    this.shipmentCartFormService.resetForm(this.editForm, shipmentCart);

    this.cartsSharedCollection = this.cartService.addCartToCollectionIfMissing<ICart>(this.cartsSharedCollection, shipmentCart.cart);
  }

  protected loadRelationshipsOptions(): void {
    this.cartService
      .query()
      .pipe(map((res: HttpResponse<ICart[]>) => res.body ?? []))
      .pipe(map((carts: ICart[]) => this.cartService.addCartToCollectionIfMissing<ICart>(carts, this.shipmentCart?.cart)))
      .subscribe((carts: ICart[]) => (this.cartsSharedCollection = carts));
  }
}
