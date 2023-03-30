import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../shipment-cart.test-samples';

import { ShipmentCartFormService } from './shipment-cart-form.service';

describe('ShipmentCart Form Service', () => {
  let service: ShipmentCartFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShipmentCartFormService);
  });

  describe('Service methods', () => {
    describe('createShipmentCartFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createShipmentCartFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            street: expect.any(Object),
            postalCode: expect.any(Object),
            city: expect.any(Object),
            country: expect.any(Object),
            phoneToTheReceiver: expect.any(Object),
            firm: expect.any(Object),
            taxNumber: expect.any(Object),
            cart: expect.any(Object),
          })
        );
      });

      it('passing IShipmentCart should create a new form with FormGroup', () => {
        const formGroup = service.createShipmentCartFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            firstName: expect.any(Object),
            lastName: expect.any(Object),
            street: expect.any(Object),
            postalCode: expect.any(Object),
            city: expect.any(Object),
            country: expect.any(Object),
            phoneToTheReceiver: expect.any(Object),
            firm: expect.any(Object),
            taxNumber: expect.any(Object),
            cart: expect.any(Object),
          })
        );
      });
    });

    describe('getShipmentCart', () => {
      it('should return NewShipmentCart for default ShipmentCart initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createShipmentCartFormGroup(sampleWithNewData);

        const shipmentCart = service.getShipmentCart(formGroup) as any;

        expect(shipmentCart).toMatchObject(sampleWithNewData);
      });

      it('should return NewShipmentCart for empty ShipmentCart initial value', () => {
        const formGroup = service.createShipmentCartFormGroup();

        const shipmentCart = service.getShipmentCart(formGroup) as any;

        expect(shipmentCart).toMatchObject({});
      });

      it('should return IShipmentCart', () => {
        const formGroup = service.createShipmentCartFormGroup(sampleWithRequiredData);

        const shipmentCart = service.getShipmentCart(formGroup) as any;

        expect(shipmentCart).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IShipmentCart should not enable id FormControl', () => {
        const formGroup = service.createShipmentCartFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewShipmentCart should disable id FormControl', () => {
        const formGroup = service.createShipmentCartFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
