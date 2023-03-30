import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../product-in-cart.test-samples';

import { ProductInCartFormService } from './product-in-cart-form.service';

describe('ProductInCart Form Service', () => {
  let service: ProductInCartFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProductInCartFormService);
  });

  describe('Service methods', () => {
    describe('createProductInCartFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProductInCartFormGroup();

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
            cart: expect.any(Object),
          })
        );
      });

      it('passing IProductInCart should create a new form with FormGroup', () => {
        const formGroup = service.createProductInCartFormGroup(sampleWithRequiredData);

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
            cart: expect.any(Object),
          })
        );
      });
    });

    describe('getProductInCart', () => {
      it('should return NewProductInCart for default ProductInCart initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProductInCartFormGroup(sampleWithNewData);

        const productInCart = service.getProductInCart(formGroup) as any;

        expect(productInCart).toMatchObject(sampleWithNewData);
      });

      it('should return NewProductInCart for empty ProductInCart initial value', () => {
        const formGroup = service.createProductInCartFormGroup();

        const productInCart = service.getProductInCart(formGroup) as any;

        expect(productInCart).toMatchObject({});
      });

      it('should return IProductInCart', () => {
        const formGroup = service.createProductInCartFormGroup(sampleWithRequiredData);

        const productInCart = service.getProductInCart(formGroup) as any;

        expect(productInCart).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProductInCart should not enable id FormControl', () => {
        const formGroup = service.createProductInCartFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProductInCart should disable id FormControl', () => {
        const formGroup = service.createProductInCartFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
