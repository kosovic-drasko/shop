import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IOrderMain, NewOrderMain } from '../order-main.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrderMain for edit and NewOrderMainFormGroupInput for create.
 */
type OrderMainFormGroupInput = IOrderMain | PartialWithRequiredKeyOf<NewOrderMain>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IOrderMain | NewOrderMain> = Omit<T, 'createTime' | 'updateTime'> & {
  createTime?: string | null;
  updateTime?: string | null;
};

type OrderMainFormRawValue = FormValueOf<IOrderMain>;

type NewOrderMainFormRawValue = FormValueOf<NewOrderMain>;

type OrderMainFormDefaults = Pick<NewOrderMain, 'id' | 'createTime' | 'updateTime'>;

type OrderMainFormGroupContent = {
  id: FormControl<OrderMainFormRawValue['id'] | NewOrderMain['id']>;
  buyerLogin: FormControl<OrderMainFormRawValue['buyerLogin']>;
  buyerFirstName: FormControl<OrderMainFormRawValue['buyerFirstName']>;
  buyerLastName: FormControl<OrderMainFormRawValue['buyerLastName']>;
  buyerEmail: FormControl<OrderMainFormRawValue['buyerEmail']>;
  buyerPhone: FormControl<OrderMainFormRawValue['buyerPhone']>;
  amountOfCartNet: FormControl<OrderMainFormRawValue['amountOfCartNet']>;
  amountOfCartGross: FormControl<OrderMainFormRawValue['amountOfCartGross']>;
  amountOfShipmentNet: FormControl<OrderMainFormRawValue['amountOfShipmentNet']>;
  amountOfShipmentGross: FormControl<OrderMainFormRawValue['amountOfShipmentGross']>;
  amountOfOrderNet: FormControl<OrderMainFormRawValue['amountOfOrderNet']>;
  amountOfOrderGross: FormControl<OrderMainFormRawValue['amountOfOrderGross']>;
  orderMainStatus: FormControl<OrderMainFormRawValue['orderMainStatus']>;
  createTime: FormControl<OrderMainFormRawValue['createTime']>;
  updateTime: FormControl<OrderMainFormRawValue['updateTime']>;
};

export type OrderMainFormGroup = FormGroup<OrderMainFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrderMainFormService {
  createOrderMainFormGroup(orderMain: OrderMainFormGroupInput = { id: null }): OrderMainFormGroup {
    const orderMainRawValue = this.convertOrderMainToOrderMainRawValue({
      ...this.getFormDefaults(),
      ...orderMain,
    });
    return new FormGroup<OrderMainFormGroupContent>({
      id: new FormControl(
        { value: orderMainRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      buyerLogin: new FormControl(orderMainRawValue.buyerLogin),
      buyerFirstName: new FormControl(orderMainRawValue.buyerFirstName),
      buyerLastName: new FormControl(orderMainRawValue.buyerLastName),
      buyerEmail: new FormControl(orderMainRawValue.buyerEmail),
      buyerPhone: new FormControl(orderMainRawValue.buyerPhone),
      amountOfCartNet: new FormControl(orderMainRawValue.amountOfCartNet),
      amountOfCartGross: new FormControl(orderMainRawValue.amountOfCartGross),
      amountOfShipmentNet: new FormControl(orderMainRawValue.amountOfShipmentNet),
      amountOfShipmentGross: new FormControl(orderMainRawValue.amountOfShipmentGross),
      amountOfOrderNet: new FormControl(orderMainRawValue.amountOfOrderNet),
      amountOfOrderGross: new FormControl(orderMainRawValue.amountOfOrderGross),
      orderMainStatus: new FormControl(orderMainRawValue.orderMainStatus),
      createTime: new FormControl(orderMainRawValue.createTime),
      updateTime: new FormControl(orderMainRawValue.updateTime),
    });
  }

  getOrderMain(form: OrderMainFormGroup): IOrderMain | NewOrderMain {
    return this.convertOrderMainRawValueToOrderMain(form.getRawValue() as OrderMainFormRawValue | NewOrderMainFormRawValue);
  }

  resetForm(form: OrderMainFormGroup, orderMain: OrderMainFormGroupInput): void {
    const orderMainRawValue = this.convertOrderMainToOrderMainRawValue({ ...this.getFormDefaults(), ...orderMain });
    form.reset(
      {
        ...orderMainRawValue,
        id: { value: orderMainRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): OrderMainFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createTime: currentTime,
      updateTime: currentTime,
    };
  }

  private convertOrderMainRawValueToOrderMain(rawOrderMain: OrderMainFormRawValue | NewOrderMainFormRawValue): IOrderMain | NewOrderMain {
    return {
      ...rawOrderMain,
      createTime: dayjs(rawOrderMain.createTime, DATE_TIME_FORMAT),
      updateTime: dayjs(rawOrderMain.updateTime, DATE_TIME_FORMAT),
    };
  }

  private convertOrderMainToOrderMainRawValue(
    orderMain: IOrderMain | (Partial<NewOrderMain> & OrderMainFormDefaults)
  ): OrderMainFormRawValue | PartialWithRequiredKeyOf<NewOrderMainFormRawValue> {
    return {
      ...orderMain,
      createTime: orderMain.createTime ? orderMain.createTime.format(DATE_TIME_FORMAT) : undefined,
      updateTime: orderMain.updateTime ? orderMain.updateTime.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
