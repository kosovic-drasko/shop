import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProductInOrderMain, NewProductInOrderMain } from '../product-in-order-main.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProductInOrderMain for edit and NewProductInOrderMainFormGroupInput for create.
 */
type ProductInOrderMainFormGroupInput = IProductInOrderMain | PartialWithRequiredKeyOf<NewProductInOrderMain>;

type ProductInOrderMainFormDefaults = Pick<NewProductInOrderMain, 'id'>;

type ProductInOrderMainFormGroupContent = {
  id: FormControl<IProductInOrderMain['id'] | NewProductInOrderMain['id']>;
  category: FormControl<IProductInOrderMain['category']>;
  name: FormControl<IProductInOrderMain['name']>;
  quantity: FormControl<IProductInOrderMain['quantity']>;
  priceNet: FormControl<IProductInOrderMain['priceNet']>;
  vat: FormControl<IProductInOrderMain['vat']>;
  priceGross: FormControl<IProductInOrderMain['priceGross']>;
  totalPriceNet: FormControl<IProductInOrderMain['totalPriceNet']>;
  totalPriceGross: FormControl<IProductInOrderMain['totalPriceGross']>;
  stock: FormControl<IProductInOrderMain['stock']>;
  description: FormControl<IProductInOrderMain['description']>;
  image: FormControl<IProductInOrderMain['image']>;
  imageContentType: FormControl<IProductInOrderMain['imageContentType']>;
  productId: FormControl<IProductInOrderMain['productId']>;
  orderMain: FormControl<IProductInOrderMain['orderMain']>;
};

export type ProductInOrderMainFormGroup = FormGroup<ProductInOrderMainFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductInOrderMainFormService {
  createProductInOrderMainFormGroup(productInOrderMain: ProductInOrderMainFormGroupInput = { id: null }): ProductInOrderMainFormGroup {
    const productInOrderMainRawValue = {
      ...this.getFormDefaults(),
      ...productInOrderMain,
    };
    return new FormGroup<ProductInOrderMainFormGroupContent>({
      id: new FormControl(
        { value: productInOrderMainRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      category: new FormControl(productInOrderMainRawValue.category),
      name: new FormControl(productInOrderMainRawValue.name),
      quantity: new FormControl(productInOrderMainRawValue.quantity),
      priceNet: new FormControl(productInOrderMainRawValue.priceNet),
      vat: new FormControl(productInOrderMainRawValue.vat),
      priceGross: new FormControl(productInOrderMainRawValue.priceGross),
      totalPriceNet: new FormControl(productInOrderMainRawValue.totalPriceNet),
      totalPriceGross: new FormControl(productInOrderMainRawValue.totalPriceGross),
      stock: new FormControl(productInOrderMainRawValue.stock),
      description: new FormControl(productInOrderMainRawValue.description),
      image: new FormControl(productInOrderMainRawValue.image),
      imageContentType: new FormControl(productInOrderMainRawValue.imageContentType),
      productId: new FormControl(productInOrderMainRawValue.productId),
      orderMain: new FormControl(productInOrderMainRawValue.orderMain),
    });
  }

  getProductInOrderMain(form: ProductInOrderMainFormGroup): IProductInOrderMain | NewProductInOrderMain {
    return form.getRawValue() as IProductInOrderMain | NewProductInOrderMain;
  }

  resetForm(form: ProductInOrderMainFormGroup, productInOrderMain: ProductInOrderMainFormGroupInput): void {
    const productInOrderMainRawValue = { ...this.getFormDefaults(), ...productInOrderMain };
    form.reset(
      {
        ...productInOrderMainRawValue,
        id: { value: productInOrderMainRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProductInOrderMainFormDefaults {
    return {
      id: null,
    };
  }
}
