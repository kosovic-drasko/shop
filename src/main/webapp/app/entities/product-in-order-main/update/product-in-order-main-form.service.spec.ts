import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../product-in-order-main.test-samples';

import { ProductInOrderMainFormService } from './product-in-order-main-form.service';

describe('ProductInOrderMain Form Service', () => {
  let service: ProductInOrderMainFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductInOrderMainFormService);
  });

  describe('Service methods', () => {
    describe('createProductInOrderMainFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProductInOrderMainFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            category: expect.any(Object),
            name: expect.any(Object),
            quantity: expect.any(Object),
            priceNet: expect.any(Object),
            vat: expect.any(Object),
            priceGross: expect.any(Object),
            totalPriceNet: expect.any(Object),
            totalPriceGross: expect.any(Object),
            stock: expect.any(Object),
            description: expect.any(Object),
            image: expect.any(Object),
            productId: expect.any(Object),
            orderMain: expect.any(Object),
          })
        );
      });

      it('passing IProductInOrderMain should create a new form with FormGroup', () => {
        const formGroup = service.createProductInOrderMainFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            category: expect.any(Object),
            name: expect.any(Object),
            quantity: expect.any(Object),
            priceNet: expect.any(Object),
            vat: expect.any(Object),
            priceGross: expect.any(Object),
            totalPriceNet: expect.any(Object),
            totalPriceGross: expect.any(Object),
            stock: expect.any(Object),
            description: expect.any(Object),
            image: expect.any(Object),
            productId: expect.any(Object),
            orderMain: expect.any(Object),
          })
        );
      });
    });

    describe('getProductInOrderMain', () => {
      it('should return NewProductInOrderMain for default ProductInOrderMain initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProductInOrderMainFormGroup(sampleWithNewData);

        const productInOrderMain = service.getProductInOrderMain(formGroup) as any;

        expect(productInOrderMain).toMatchObject(sampleWithNewData);
      });

      it('should return NewProductInOrderMain for empty ProductInOrderMain initial value', () => {
        const formGroup = service.createProductInOrderMainFormGroup();

        const productInOrderMain = service.getProductInOrderMain(formGroup) as any;

        expect(productInOrderMain).toMatchObject({});
      });

      it('should return IProductInOrderMain', () => {
        const formGroup = service.createProductInOrderMainFormGroup(sampleWithRequiredData);

        const productInOrderMain = service.getProductInOrderMain(formGroup) as any;

        expect(productInOrderMain).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProductInOrderMain should not enable id FormControl', () => {
        const formGroup = service.createProductInOrderMainFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProductInOrderMain should disable id FormControl', () => {
        const formGroup = service.createProductInOrderMainFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
