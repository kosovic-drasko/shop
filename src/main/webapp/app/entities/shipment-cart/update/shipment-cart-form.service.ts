import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IShipmentCart, NewShipmentCart } from '../shipment-cart.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IShipmentCart for edit and NewShipmentCartFormGroupInput for create.
 */
type ShipmentCartFormGroupInput = IShipmentCart | PartialWithRequiredKeyOf<NewShipmentCart>;

type ShipmentCartFormDefaults = Pick<NewShipmentCart, 'id'>;

type ShipmentCartFormGroupContent = {
  id: FormControl<IShipmentCart['id'] | NewShipmentCart['id']>;
  firstName: FormControl<IShipmentCart['firstName']>;
  lastName: FormControl<IShipmentCart['lastName']>;
  street: FormControl<IShipmentCart['street']>;
  postalCode: FormControl<IShipmentCart['postalCode']>;
  city: FormControl<IShipmentCart['city']>;
  country: FormControl<IShipmentCart['country']>;
  phoneToTheReceiver: FormControl<IShipmentCart['phoneToTheReceiver']>;
  firm: FormControl<IShipmentCart['firm']>;
  taxNumber: FormControl<IShipmentCart['taxNumber']>;
  cart: FormControl<IShipmentCart['cart']>;
};

export type ShipmentCartFormGroup = FormGroup<ShipmentCartFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ShipmentCartFormService {
  createShipmentCartFormGroup(shipmentCart: ShipmentCartFormGroupInput = { id: null }): ShipmentCartFormGroup {
    const shipmentCartRawValue = {
      ...this.getFormDefaults(),
      ...shipmentCart,
    };
    return new FormGroup<ShipmentCartFormGroupContent>({
      id: new FormControl(
        { value: shipmentCartRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      firstName: new FormControl(shipmentCartRawValue.firstName, {
        validators: [Validators.required, Validators.maxLength(500)],
      }),
      lastName: new FormControl(shipmentCartRawValue.lastName, {
        validators: [Validators.required, Validators.maxLength(500)],
      }),
      street: new FormControl(shipmentCartRawValue.street, {
        validators: [Validators.required, Validators.maxLength(500)],
      }),
      postalCode: new FormControl(shipmentCartRawValue.postalCode, {
        validators: [Validators.required, Validators.maxLength(20)],
      }),
      city: new FormControl(shipmentCartRawValue.city, {
        validators: [Validators.required, Validators.maxLength(500)],
      }),
      country: new FormControl(shipmentCartRawValue.country, {
        validators: [Validators.required, Validators.maxLength(500)],
      }),
      phoneToTheReceiver: new FormControl(shipmentCartRawValue.phoneToTheReceiver, {
        validators: [Validators.required, Validators.maxLength(30)],
      }),
      firm: new FormControl(shipmentCartRawValue.firm, {
        validators: [Validators.maxLength(10000)],
      }),
      taxNumber: new FormControl(shipmentCartRawValue.taxNumber, {
        validators: [Validators.maxLength(50)],
      }),
      cart: new FormControl(shipmentCartRawValue.cart),
    });
  }

  getShipmentCart(form: ShipmentCartFormGroup): IShipmentCart | NewShipmentCart {
    return form.getRawValue() as IShipmentCart | NewShipmentCart;
  }

  resetForm(form: ShipmentCartFormGroup, shipmentCart: ShipmentCartFormGroupInput): void {
    const shipmentCartRawValue = { ...this.getFormDefaults(), ...shipmentCart };
    form.reset(
      {
        ...shipmentCartRawValue,
        id: { value: shipmentCartRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ShipmentCartFormDefaults {
    return {
      id: null,
    };
  }
}
