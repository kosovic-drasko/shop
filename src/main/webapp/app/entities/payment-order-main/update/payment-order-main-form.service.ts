import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPaymentOrderMain, NewPaymentOrderMain } from '../payment-order-main.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPaymentOrderMain for edit and NewPaymentOrderMainFormGroupInput for create.
 */
type PaymentOrderMainFormGroupInput = IPaymentOrderMain | PartialWithRequiredKeyOf<NewPaymentOrderMain>;

type PaymentOrderMainFormDefaults = Pick<NewPaymentOrderMain, 'id'>;

type PaymentOrderMainFormGroupContent = {
  id: FormControl<IPaymentOrderMain['id'] | NewPaymentOrderMain['id']>;
  name: FormControl<IPaymentOrderMain['name']>;
  priceNet: FormControl<IPaymentOrderMain['priceNet']>;
  vat: FormControl<IPaymentOrderMain['vat']>;
  priceGross: FormControl<IPaymentOrderMain['priceGross']>;
  orderMain: FormControl<IPaymentOrderMain['orderMain']>;
};

export type PaymentOrderMainFormGroup = FormGroup<PaymentOrderMainFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PaymentOrderMainFormService {
  createPaymentOrderMainFormGroup(paymentOrderMain: PaymentOrderMainFormGroupInput = { id: null }): PaymentOrderMainFormGroup {
    const paymentOrderMainRawValue = {
      ...this.getFormDefaults(),
      ...paymentOrderMain,
    };
    return new FormGroup<PaymentOrderMainFormGroupContent>({
      id: new FormControl(
        { value: paymentOrderMainRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(paymentOrderMainRawValue.name, {
        validators: [Validators.required],
      }),
      priceNet: new FormControl(paymentOrderMainRawValue.priceNet, {
        validators: [Validators.required],
      }),
      vat: new FormControl(paymentOrderMainRawValue.vat, {
        validators: [Validators.required],
      }),
      priceGross: new FormControl(paymentOrderMainRawValue.priceGross, {
        validators: [Validators.required],
      }),
      orderMain: new FormControl(paymentOrderMainRawValue.orderMain),
    });
  }

  getPaymentOrderMain(form: PaymentOrderMainFormGroup): IPaymentOrderMain | NewPaymentOrderMain {
    return form.getRawValue() as IPaymentOrderMain | NewPaymentOrderMain;
  }

  resetForm(form: PaymentOrderMainFormGroup, paymentOrderMain: PaymentOrderMainFormGroupInput): void {
    const paymentOrderMainRawValue = { ...this.getFormDefaults(), ...paymentOrderMain };
    form.reset(
      {
        ...paymentOrderMainRawValue,
        id: { value: paymentOrderMainRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PaymentOrderMainFormDefaults {
    return {
      id: null,
    };
  }
}
