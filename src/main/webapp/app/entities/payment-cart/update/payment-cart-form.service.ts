import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPaymentCart, NewPaymentCart } from '../payment-cart.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPaymentCart for edit and NewPaymentCartFormGroupInput for create.
 */
type PaymentCartFormGroupInput = IPaymentCart | PartialWithRequiredKeyOf<NewPaymentCart>;

type PaymentCartFormDefaults = Pick<NewPaymentCart, 'id'>;

type PaymentCartFormGroupContent = {
  id: FormControl<IPaymentCart['id'] | NewPaymentCart['id']>;
  name: FormControl<IPaymentCart['name']>;
  priceNet: FormControl<IPaymentCart['priceNet']>;
  vat: FormControl<IPaymentCart['vat']>;
  priceGross: FormControl<IPaymentCart['priceGross']>;
  paymentStatusEnum: FormControl<IPaymentCart['paymentStatusEnum']>;
  cart: FormControl<IPaymentCart['cart']>;
};

export type PaymentCartFormGroup = FormGroup<PaymentCartFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PaymentCartFormService {
  createPaymentCartFormGroup(paymentCart: PaymentCartFormGroupInput = { id: null }): PaymentCartFormGroup {
    const paymentCartRawValue = {
      ...this.getFormDefaults(),
      ...paymentCart,
    };
    return new FormGroup<PaymentCartFormGroupContent>({
      id: new FormControl(
        { value: paymentCartRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(paymentCartRawValue.name, {
        validators: [Validators.required],
      }),
      priceNet: new FormControl(paymentCartRawValue.priceNet, {
        validators: [Validators.required],
      }),
      vat: new FormControl(paymentCartRawValue.vat, {
        validators: [Validators.required],
      }),
      priceGross: new FormControl(paymentCartRawValue.priceGross, {
        validators: [Validators.required],
      }),
      paymentStatusEnum: new FormControl(paymentCartRawValue.paymentStatusEnum),
      cart: new FormControl(paymentCartRawValue.cart),
    });
  }

  getPaymentCart(form: PaymentCartFormGroup): IPaymentCart | NewPaymentCart {
    return form.getRawValue() as IPaymentCart | NewPaymentCart;
  }

  resetForm(form: PaymentCartFormGroup, paymentCart: PaymentCartFormGroupInput): void {
    const paymentCartRawValue = { ...this.getFormDefaults(), ...paymentCart };
    form.reset(
      {
        ...paymentCartRawValue,
        id: { value: paymentCartRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PaymentCartFormDefaults {
    return {
      id: null,
    };
  }
}
