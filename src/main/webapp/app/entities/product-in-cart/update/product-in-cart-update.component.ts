import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProductInCartFormService, ProductInCartFormGroup } from './product-in-cart-form.service';
import { IProductInCart } from '../product-in-cart.model';
import { ProductInCartService } from '../service/product-in-cart.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICart } from 'app/entities/cart/cart.model';
import { CartService } from 'app/entities/cart/service/cart.service';

@Component({
  selector: 'jhi-product-in-cart-update',
  templateUrl: './product-in-cart-update.component.html',
})
export class ProductInCartUpdateComponent implements OnInit {
  isSaving = false;
  productInCart: IProductInCart | null = null;

  cartsSharedCollection: ICart[] = [];

  editForm: ProductInCartFormGroup = this.productInCartFormService.createProductInCartFormGroup();

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected productInCartService: ProductInCartService,
    protected productInCartFormService: ProductInCartFormService,
    protected cartService: CartService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareCart = (o1: ICart | null, o2: ICart | null): boolean => this.cartService.compareCart(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productInCart }) => {
      this.productInCart = productInCart;
      if (productInCart) {
        this.updateForm(productInCart);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('shopApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productInCart = this.productInCartFormService.getProductInCart(this.editForm);
    if (productInCart.id !== null) {
      this.subscribeToSaveResponse(this.productInCartService.update(productInCart));
    } else {
      this.subscribeToSaveResponse(this.productInCartService.create(productInCart));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductInCart>>): void {
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

  protected updateForm(productInCart: IProductInCart): void {
    this.productInCart = productInCart;
    this.productInCartFormService.resetForm(this.editForm, productInCart);

    this.cartsSharedCollection = this.cartService.addCartToCollectionIfMissing<ICart>(this.cartsSharedCollection, productInCart.cart);
  }

  protected loadRelationshipsOptions(): void {
    this.cartService
      .query()
      .pipe(map((res: HttpResponse<ICart[]>) => res.body ?? []))
      .pipe(map((carts: ICart[]) => this.cartService.addCartToCollectionIfMissing<ICart>(carts, this.productInCart?.cart)))
      .subscribe((carts: ICart[]) => (this.cartsSharedCollection = carts));
  }
}
