import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProductInCart, NewProductInCart } from '../product-in-cart.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProductInCart for edit and NewProductInCartFormGroupInput for create.
 */
type ProductInCartFormGroupInput = IProductInCart | PartialWithRequiredKeyOf<NewProductInCart>;

type ProductInCartFormDefaults = Pick<NewProductInCart, 'id'>;

type ProductInCartFormGroupContent = {
  id: FormControl<IProductInCart['id'] | NewProductInCart['id']>;
  category: FormControl<IProductInCart['category']>;
  name: FormControl<IProductInCart['name']>;
  quantity: FormControl<IProductInCart['quantity']>;
  priceNet: FormControl<IProductInCart['priceNet']>;
  vat: FormControl<IProductInCart['vat']>;
  priceGross: FormControl<IProductInCart['priceGross']>;
  totalPriceNet: FormControl<IProductInCart['totalPriceNet']>;
  totalPriceGross: FormControl<IProductInCart['totalPriceGross']>;
  stock: FormControl<IProductInCart['stock']>;
  description: FormControl<IProductInCart['description']>;
  image: FormControl<IProductInCart['image']>;
  imageContentType: FormControl<IProductInCart['imageContentType']>;
  productId: FormControl<IProductInCart['productId']>;
  cart: FormControl<IProductInCart['cart']>;
};

export type ProductInCartFormGroup = FormGroup<ProductInCartFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductInCartFormService {
  createProductInCartFormGroup(productInCart: ProductInCartFormGroupInput = { id: null }): ProductInCartFormGroup {
    const productInCartRawValue = {
      ...this.getFormDefaults(),
      ...productInCart,
    };
    return new FormGroup<ProductInCartFormGroupContent>({
      id: new FormControl(
        { value: productInCartRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      category: new FormControl(productInCartRawValue.category),
      name: new FormControl(productInCartRawValue.name),
      quantity: new FormControl(productInCartRawValue.quantity),
      priceNet: new FormControl(productInCartRawValue.priceNet),
      vat: new FormControl(productInCartRawValue.vat),
      priceGross: new FormControl(productInCartRawValue.priceGross),
      totalPriceNet: new FormControl(productInCartRawValue.totalPriceNet),
      totalPriceGross: new FormControl(productInCartRawValue.totalPriceGross),
      stock: new FormControl(productInCartRawValue.stock),
      description: new FormControl(productInCartRawValue.description),
      image: new FormControl(productInCartRawValue.image),
      imageContentType: new FormControl(productInCartRawValue.imageContentType),
      productId: new FormControl(productInCartRawValue.productId),
      cart: new FormControl(productInCartRawValue.cart),
    });
  }

  getProductInCart(form: ProductInCartFormGroup): IProductInCart | NewProductInCart {
    return form.getRawValue() as IProductInCart | NewProductInCart;
  }

  resetForm(form: ProductInCartFormGroup, productInCart: ProductInCartFormGroupInput): void {
    const productInCartRawValue = { ...this.getFormDefaults(), ...productInCart };
    form.reset(
      {
        ...productInCartRawValue,
        id: { value: productInCartRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProductInCartFormDefaults {
    return {
      id: null,
    };
  }
}
