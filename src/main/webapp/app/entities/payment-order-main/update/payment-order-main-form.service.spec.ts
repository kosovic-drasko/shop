import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../payment-order-main.test-samples';

import { PaymentOrderMainFormService } from './payment-order-main-form.service';

describe('PaymentOrderMain Form Service', () => {
  let service: PaymentOrderMainFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PaymentOrderMainFormService);
  });

  describe('Service methods', () => {
    describe('createPaymentOrderMainFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPaymentOrderMainFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            priceNet: expect.any(Object),
            vat: expect.any(Object),
            priceGross: expect.any(Object),
            orderMain: expect.any(Object),
          })
        );
      });

      it('passing IPaymentOrderMain should create a new form with FormGroup', () => {
        const formGroup = service.createPaymentOrderMainFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            priceNet: expect.any(Object),
            vat: expect.any(Object),
            priceGross: expect.any(Object),
            orderMain: expect.any(Object),
          })
        );
      });
    });

    describe('getPaymentOrderMain', () => {
      it('should return NewPaymentOrderMain for default PaymentOrderMain initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPaymentOrderMainFormGroup(sampleWithNewData);

        const paymentOrderMain = service.getPaymentOrderMain(formGroup) as any;

        expect(paymentOrderMain).toMatchObject(sampleWithNewData);
      });

      it('should return NewPaymentOrderMain for empty PaymentOrderMain initial value', () => {
        const formGroup = service.createPaymentOrderMainFormGroup();

        const paymentOrderMain = service.getPaymentOrderMain(formGroup) as any;

        expect(paymentOrderMain).toMatchObject({});
      });

      it('should return IPaymentOrderMain', () => {
        const formGroup = service.createPaymentOrderMainFormGroup(sampleWithRequiredData);

        const paymentOrderMain = service.getPaymentOrderMain(formGroup) as any;

        expect(paymentOrderMain).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPaymentOrderMain should not enable id FormControl', () => {
        const formGroup = service.createPaymentOrderMainFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPaymentOrderMain should disable id FormControl', () => {
        const formGroup = service.createPaymentOrderMainFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
