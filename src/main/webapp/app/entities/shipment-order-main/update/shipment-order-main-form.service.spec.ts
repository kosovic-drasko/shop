import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../shipment-order-main.test-samples';

import { ShipmentOrderMainFormService } from './shipment-order-main-form.service';

describe('ShipmentOrderMain Form Service', () => {
  let service: ShipmentOrderMainFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ShipmentOrderMainFormService);
  });

  describe('Service methods', () => {
    describe('createShipmentOrderMainFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createShipmentOrderMainFormGroup();

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
            orderMain: expect.any(Object),
          })
        );
      });

      it('passing IShipmentOrderMain should create a new form with FormGroup', () => {
        const formGroup = service.createShipmentOrderMainFormGroup(sampleWithRequiredData);

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
            orderMain: expect.any(Object),
          })
        );
      });
    });

    describe('getShipmentOrderMain', () => {
      it('should return NewShipmentOrderMain for default ShipmentOrderMain initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createShipmentOrderMainFormGroup(sampleWithNewData);

        const shipmentOrderMain = service.getShipmentOrderMain(formGroup) as any;

        expect(shipmentOrderMain).toMatchObject(sampleWithNewData);
      });

      it('should return NewShipmentOrderMain for empty ShipmentOrderMain initial value', () => {
        const formGroup = service.createShipmentOrderMainFormGroup();

        const shipmentOrderMain = service.getShipmentOrderMain(formGroup) as any;

        expect(shipmentOrderMain).toMatchObject({});
      });

      it('should return IShipmentOrderMain', () => {
        const formGroup = service.createShipmentOrderMainFormGroup(sampleWithRequiredData);

        const shipmentOrderMain = service.getShipmentOrderMain(formGroup) as any;

        expect(shipmentOrderMain).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IShipmentOrderMain should not enable id FormControl', () => {
        const formGroup = service.createShipmentOrderMainFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewShipmentOrderMain should disable id FormControl', () => {
        const formGroup = service.createShipmentOrderMainFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
