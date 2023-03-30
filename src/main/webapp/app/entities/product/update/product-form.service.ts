import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProduct, NewProduct } from '../product.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProduct for edit and NewProductFormGroupInput for create.
 */
type ProductFormGroupInput = IProduct | PartialWithRequiredKeyOf<NewProduct>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProduct | NewProduct> = Omit<T, 'createTime' | 'updateTime'> & {
  createTime?: string | null;
  updateTime?: string | null;
};

type ProductFormRawValue = FormValueOf<IProduct>;

type NewProductFormRawValue = FormValueOf<NewProduct>;

type ProductFormDefaults = Pick<NewProduct, 'id' | 'createTime' | 'updateTime'>;

type ProductFormGroupContent = {
  id: FormControl<ProductFormRawValue['id'] | NewProduct['id']>;
  productCategoryEnum: FormControl<ProductFormRawValue['productCategoryEnum']>;
  name: FormControl<ProductFormRawValue['name']>;
  quantity: FormControl<ProductFormRawValue['quantity']>;
  priceNet: FormControl<ProductFormRawValue['priceNet']>;
  vat: FormControl<ProductFormRawValue['vat']>;
  priceGross: FormControl<ProductFormRawValue['priceGross']>;
  stock: FormControl<ProductFormRawValue['stock']>;
  description: FormControl<ProductFormRawValue['description']>;
  createTime: FormControl<ProductFormRawValue['createTime']>;
  updateTime: FormControl<ProductFormRawValue['updateTime']>;
  image: FormControl<ProductFormRawValue['image']>;
  imageContentType: FormControl<ProductFormRawValue['imageContentType']>;
};

export type ProductFormGroup = FormGroup<ProductFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProductFormService {
  createProductFormGroup(product: ProductFormGroupInput = { id: null }): ProductFormGroup {
    const productRawValue = this.convertProductToProductRawValue({
      ...this.getFormDefaults(),
      ...product,
    });
    return new FormGroup<ProductFormGroupContent>({
      id: new FormControl(
        { value: productRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      productCategoryEnum: new FormControl(productRawValue.productCategoryEnum, {
        validators: [Validators.required],
      }),
      name: new FormControl(productRawValue.name, {
        validators: [Validators.required],
      }),
      quantity: new FormControl(productRawValue.quantity),
      priceNet: new FormControl(productRawValue.priceNet, {
        validators: [Validators.required, Validators.min(0), Validators.max(1000000)],
      }),
      vat: new FormControl(productRawValue.vat, {
        validators: [Validators.required, Validators.min(5), Validators.max(100)],
      }),
      priceGross: new FormControl(productRawValue.priceGross),
      stock: new FormControl(productRawValue.stock, {
        validators: [Validators.required, Validators.min(0), Validators.max(1000000)],
      }),
      description: new FormControl(productRawValue.description, {
        validators: [Validators.required],
      }),
      createTime: new FormControl(productRawValue.createTime),
      updateTime: new FormControl(productRawValue.updateTime),
      image: new FormControl(productRawValue.image, {
        validators: [Validators.required],
      }),
      imageContentType: new FormControl(productRawValue.imageContentType),
    });
  }

  getProduct(form: ProductFormGroup): IProduct | NewProduct {
    return this.convertProductRawValueToProduct(form.getRawValue() as ProductFormRawValue | NewProductFormRawValue);
  }

  resetForm(form: ProductFormGroup, product: ProductFormGroupInput): void {
    const productRawValue = this.convertProductToProductRawValue({ ...this.getFormDefaults(), ...product });
    form.reset(
      {
        ...productRawValue,
        id: { value: productRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProductFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createTime: currentTime,
      updateTime: currentTime,
    };
  }

  private convertProductRawValueToProduct(rawProduct: ProductFormRawValue | NewProductFormRawValue): IProduct | NewProduct {
    return {
      ...rawProduct,
      createTime: dayjs(rawProduct.createTime, DATE_TIME_FORMAT),
      updateTime: dayjs(rawProduct.updateTime, DATE_TIME_FORMAT),
    };
  }

  private convertProductToProductRawValue(
    product: IProduct | (Partial<NewProduct> & ProductFormDefaults)
  ): ProductFormRawValue | PartialWithRequiredKeyOf<NewProductFormRawValue> {
    return {
      ...product,
      createTime: product.createTime ? product.createTime.format(DATE_TIME_FORMAT) : undefined,
      updateTime: product.updateTime ? product.updateTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
