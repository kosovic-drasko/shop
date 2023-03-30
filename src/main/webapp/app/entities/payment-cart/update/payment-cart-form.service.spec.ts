import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../payment-cart.test-samples';

import { PaymentCartFormService } from './payment-cart-form.service';

describe('PaymentCart Form Service', () => {
  let service: PaymentCartFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PaymentCartFormService);
  });

  describe('Service methods', () => {
    describe('createPaymentCartFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPaymentCartFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            priceNet: expect.any(Object),
            vat: expect.any(Object),
            priceGross: expect.any(Object),
            paymentStatusEnum: expect.any(Object),
            cart: expect.any(Object),
          })
        );
      });

      it('passing IPaymentCart should create a new form with FormGroup', () => {
        const formGroup = service.createPaymentCartFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            priceNet: expect.any(Object),
            vat: expect.any(Object),
            priceGross: expect.any(Object),
            paymentStatusEnum: expect.any(Object),
            cart: expect.any(Object),
          })
        );
      });
    });

    describe('getPaymentCart', () => {
      it('should return NewPaymentCart for default PaymentCart initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createPaymentCartFormGroup(sampleWithNewData);

        const paymentCart = service.getPaymentCart(formGroup) as any;

        expect(paymentCart).toMatchObject(sampleWithNewData);
      });

      it('should return NewPaymentCart for empty PaymentCart initial value', () => {
        const formGroup = service.createPaymentCartFormGroup();

        const paymentCart = service.getPaymentCart(formGroup) as any;

        expect(paymentCart).toMatchObject({});
      });

      it('should return IPaymentCart', () => {
        const formGroup = service.createPaymentCartFormGroup(sampleWithRequiredData);

        const paymentCart = service.getPaymentCart(formGroup) as any;

        expect(paymentCart).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPaymentCart should not enable id FormControl', () => {
        const formGroup = service.createPaymentCartFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPaymentCart should disable id FormControl', () => {
        const formGroup = service.createPaymentCartFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
