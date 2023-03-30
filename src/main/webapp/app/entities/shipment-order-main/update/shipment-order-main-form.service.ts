import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IShipmentOrderMain, NewShipmentOrderMain } from '../shipment-order-main.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IShipmentOrderMain for edit and NewShipmentOrderMainFormGroupInput for create.
 */
type ShipmentOrderMainFormGroupInput = IShipmentOrderMain | PartialWithRequiredKeyOf<NewShipmentOrderMain>;

type ShipmentOrderMainFormDefaults = Pick<NewShipmentOrderMain, 'id'>;

type ShipmentOrderMainFormGroupContent = {
  id: FormControl<IShipmentOrderMain['id'] | NewShipmentOrderMain['id']>;
  firstName: FormControl<IShipmentOrderMain['firstName']>;
  lastName: FormControl<IShipmentOrderMain['lastName']>;
  street: FormControl<IShipmentOrderMain['street']>;
  postalCode: FormControl<IShipmentOrderMain['postalCode']>;
  city: FormControl<IShipmentOrderMain['city']>;
  country: FormControl<IShipmentOrderMain['country']>;
  phoneToTheReceiver: FormControl<IShipmentOrderMain['phoneToTheReceiver']>;
  firm: FormControl<IShipmentOrderMain['firm']>;
  taxNumber: FormControl<IShipmentOrderMain['taxNumber']>;
  orderMain: FormControl<IShipmentOrderMain['orderMain']>;
};

export type ShipmentOrderMainFormGroup = FormGroup<ShipmentOrderMainFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ShipmentOrderMainFormService {
  createShipmentOrderMainFormGroup(shipmentOrderMain: ShipmentOrderMainFormGroupInput = { id: null }): ShipmentOrderMainFormGroup {
    const shipmentOrderMainRawValue = {
      ...this.getFormDefaults(),
      ...shipmentOrderMain,
    };
    return new FormGroup<ShipmentOrderMainFormGroupContent>({
      id: new FormControl(
        { value: shipmentOrderMainRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      firstName: new FormControl(shipmentOrderMainRawValue.firstName, {
        validators: [Validators.required, Validators.maxLength(500)],
      }),
      lastName: new FormControl(shipmentOrderMainRawValue.lastName, {
        validators: [Validators.required, Validators.maxLength(500)],
      }),
      street: new FormControl(shipmentOrderMainRawValue.street, {
        validators: [Validators.required, Validators.maxLength(500)],
      }),
      postalCode: new FormControl(shipmentOrderMainRawValue.postalCode, {
        validators: [Validators.required, Validators.maxLength(20)],
      }),
      city: new FormControl(shipmentOrderMainRawValue.city, {
        validators: [Validators.required, Validators.maxLength(500)],
      }),
      country: new FormControl(shipmentOrderMainRawValue.country, {
        validators: [Validators.required, Validators.maxLength(500)],
      }),
      phoneToTheReceiver: new FormControl(shipmentOrderMainRawValue.phoneToTheReceiver, {
        validators: [Validators.required, Validators.maxLength(30)],
      }),
      firm: new FormControl(shipmentOrderMainRawValue.firm, {
        validators: [Validators.maxLength(10000)],
      }),
      taxNumber: new FormControl(shipmentOrderMainRawValue.taxNumber, {
        validators: [Validators.maxLength(50)],
      }),
      orderMain: new FormControl(shipmentOrderMainRawValue.orderMain),
    });
  }

  getShipmentOrderMain(form: ShipmentOrderMainFormGroup): IShipmentOrderMain | NewShipmentOrderMain {
    return form.getRawValue() as IShipmentOrderMain | NewShipmentOrderMain;
  }

  resetForm(form: ShipmentOrderMainFormGroup, shipmentOrderMain: ShipmentOrderMainFormGroupInput): void {
    const shipmentOrderMainRawValue = { ...this.getFormDefaults(), ...shipmentOrderMain };
    form.reset(
      {
        ...shipmentOrderMainRawValue,
        id: { value: shipmentOrderMainRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ShipmentOrderMainFormDefaults {
    return {
      id: null,
    };
  }
}
