import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../order-main.test-samples';

import { OrderMainFormService } from './order-main-form.service';

describe('OrderMain Form Service', () => {
  let service: OrderMainFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrderMainFormService);
  });

  describe('Service methods', () => {
    describe('createOrderMainFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrderMainFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            buyerLogin: expect.any(Object),
            buyerFirstName: expect.any(Object),
            buyerLastName: expect.any(Object),
            buyerEmail: expect.any(Object),
            buyerPhone: expect.any(Object),
            amountOfCartNet: expect.any(Object),
            amountOfCartGross: expect.any(Object),
            amountOfShipmentNet: expect.any(Object),
            amountOfShipmentGross: expect.any(Object),
            amountOfOrderNet: expect.any(Object),
            amountOfOrderGross: expect.any(Object),
            orderMainStatus: expect.any(Object),
            createTime: expect.any(Object),
            updateTime: expect.any(Object),
          })
        );
      });

      it('passing IOrderMain should create a new form with FormGroup', () => {
        const formGroup = service.createOrderMainFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            buyerLogin: expect.any(Object),
            buyerFirstName: expect.any(Object),
            buyerLastName: expect.any(Object),
            buyerEmail: expect.any(Object),
            buyerPhone: expect.any(Object),
            amountOfCartNet: expect.any(Object),
            amountOfCartGross: expect.any(Object),
            amountOfShipmentNet: expect.any(Object),
            amountOfShipmentGross: expect.any(Object),
            amountOfOrderNet: expect.any(Object),
            amountOfOrderGross: expect.any(Object),
            orderMainStatus: expect.any(Object),
            createTime: expect.any(Object),
            updateTime: expect.any(Object),
          })
        );
      });
    });

    describe('getOrderMain', () => {
      it('should return NewOrderMain for default OrderMain initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createOrderMainFormGroup(sampleWithNewData);

        const orderMain = service.getOrderMain(formGroup) as any;

        expect(orderMain).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrderMain for empty OrderMain initial value', () => {
        const formGroup = service.createOrderMainFormGroup();

        const orderMain = service.getOrderMain(formGroup) as any;

        expect(orderMain).toMatchObject({});
      });

      it('should return IOrderMain', () => {
        const formGroup = service.createOrderMainFormGroup(sampleWithRequiredData);

        const orderMain = service.getOrderMain(formGroup) as any;

        expect(orderMain).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrderMain should not enable id FormControl', () => {
        const formGroup = service.createOrderMainFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrderMain should disable id FormControl', () => {
        const formGroup = service.createOrderMainFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
