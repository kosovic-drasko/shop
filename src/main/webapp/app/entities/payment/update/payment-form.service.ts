import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPayment, NewPayment } from '../payment.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPayment for edit and NewPaymentFormGroupInput for create.
 */
type PaymentFormGroupInput = IPayment | PartialWithRequiredKeyOf<NewPayment>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPayment | NewPayment> = Omit<T, 'createTime' | 'updateTime'> & {
  createTime?: string | null;
  updateTime?: string | null;
};

type PaymentFormRawValue = FormValueOf<IPayment>;

type NewPaymentFormRawValue = FormValueOf<NewPayment>;

type PaymentFormDefaults = Pick<NewPayment, 'id' | 'createTime' | 'updateTime'>;

type PaymentFormGroupContent = {
  id: FormControl<PaymentFormRawValue['id'] | NewPayment['id']>;
  name: FormControl<PaymentFormRawValue['name']>;
  priceNet: FormControl<PaymentFormRawValue['priceNet']>;
  vat: FormControl<PaymentFormRawValue['vat']>;
  priceGross: FormControl<PaymentFormRawValue['priceGross']>;
  paymentStatusEnum: FormControl<PaymentFormRawValue['paymentStatusEnum']>;
  createTime: FormControl<PaymentFormRawValue['createTime']>;
  updateTime: FormControl<PaymentFormRawValue['updateTime']>;
};

export type PaymentFormGroup = FormGroup<PaymentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PaymentFormService {
  createPaymentFormGroup(payment: PaymentFormGroupInput = { id: null }): PaymentFormGroup {
    const paymentRawValue = this.convertPaymentToPaymentRawValue({
      ...this.getFormDefaults(),
      ...payment,
    });
    return new FormGroup<PaymentFormGroupContent>({
      id: new FormControl(
        { value: paymentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(paymentRawValue.name, {
        validators: [Validators.required, Validators.maxLength(1000)],
      }),
      priceNet: new FormControl(paymentRawValue.priceNet, {
        validators: [Validators.required, Validators.min(0), Validators.max(10000)],
      }),
      vat: new FormControl(paymentRawValue.vat, {
        validators: [Validators.required, Validators.min(0), Validators.max(100)],
      }),
      priceGross: new FormControl(paymentRawValue.priceGross),
      paymentStatusEnum: new FormControl(paymentRawValue.paymentStatusEnum),
      createTime: new FormControl(paymentRawValue.createTime),
      updateTime: new FormControl(paymentRawValue.updateTime),
    });
  }

  getPayment(form: PaymentFormGroup): IPayment | NewPayment {
    return this.convertPaymentRawValueToPayment(form.getRawValue() as PaymentFormRawValue | NewPaymentFormRawValue);
  }

  resetForm(form: PaymentFormGroup, payment: PaymentFormGroupInput): void {
    const paymentRawValue = this.convertPaymentToPaymentRawValue({ ...this.getFormDefaults(), ...payment });
    form.reset(
      {
        ...paymentRawValue,
        id: { value: paymentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PaymentFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createTime: currentTime,
      updateTime: currentTime,
    };
  }

  private convertPaymentRawValueToPayment(rawPayment: PaymentFormRawValue | NewPaymentFormRawValue): IPayment | NewPayment {
    return {
      ...rawPayment,
      createTime: dayjs(rawPayment.createTime, DATE_TIME_FORMAT),
      updateTime: dayjs(rawPayment.updateTime, DATE_TIME_FORMAT),
    };
  }

  private convertPaymentToPaymentRawValue(
    payment: IPayment | (Partial<NewPayment> & PaymentFormDefaults)
  ): PaymentFormRawValue | PartialWithRequiredKeyOf<NewPaymentFormRawValue> {
    return {
      ...payment,
      createTime: payment.createTime ? payment.createTime.format(DATE_TIME_FORMAT) : undefined,
      updateTime: payment.updateTime ? payment.updateTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
