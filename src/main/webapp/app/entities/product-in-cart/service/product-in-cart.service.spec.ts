import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProductInCart } from '../product-in-cart.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../product-in-cart.test-samples';

import { ProductInCartService } from './product-in-cart.service';

const requireRestSample: IProductInCart = {
  ...sampleWithRequiredData,
};

describe('ProductInCart Service', () => {
  let service: ProductInCartService;
  let httpMock: HttpTestingController;
  let expectedResult: IProductInCart | IProductInCart[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductInCartService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ProductInCart', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const productInCart = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(productInCart).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductInCart', () => {
      const productInCart = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(productInCart).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductInCart', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductInCart', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProductInCart', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProductInCartToCollectionIfMissing', () => {
      it('should add a ProductInCart to an empty array', () => {
        const productInCart: IProductInCart = sampleWithRequiredData;
        expectedResult = service.addProductInCartToCollectionIfMissing([], productInCart);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productInCart);
      });

      it('should not add a ProductInCart to an array that contains it', () => {
        const productInCart: IProductInCart = sampleWithRequiredData;
        const productInCartCollection: IProductInCart[] = [
          {
            ...productInCart,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProductInCartToCollectionIfMissing(productInCartCollection, productInCart);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductInCart to an array that doesn't contain it", () => {
        const productInCart: IProductInCart = sampleWithRequiredData;
        const productInCartCollection: IProductInCart[] = [sampleWithPartialData];
        expectedResult = service.addProductInCartToCollectionIfMissing(productInCartCollection, productInCart);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productInCart);
      });

      it('should add only unique ProductInCart to an array', () => {
        const productInCartArray: IProductInCart[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const productInCartCollection: IProductInCart[] = [sampleWithRequiredData];
        expectedResult = service.addProductInCartToCollectionIfMissing(productInCartCollection, ...productInCartArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const productInCart: IProductInCart = sampleWithRequiredData;
        const productInCart2: IProductInCart = sampleWithPartialData;
        expectedResult = service.addProductInCartToCollectionIfMissing([], productInCart, productInCart2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productInCart);
        expect(expectedResult).toContain(productInCart2);
      });

      it('should accept null and undefined values', () => {
        const productInCart: IProductInCart = sampleWithRequiredData;
        expectedResult = service.addProductInCartToCollectionIfMissing([], null, productInCart, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productInCart);
      });

      it('should return initial array if no ProductInCart is added', () => {
        const productInCartCollection: IProductInCart[] = [sampleWithRequiredData];
        expectedResult = service.addProductInCartToCollectionIfMissing(productInCartCollection, undefined, null);
        expect(expectedResult).toEqual(productInCartCollection);
      });
    });

    describe('compareProductInCart', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProductInCart(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProductInCart(entity1, entity2);
        const compareResult2 = service.compareProductInCart(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProductInCart(entity1, entity2);
        const compareResult2 = service.compareProductInCart(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProductInCart(entity1, entity2);
        const compareResult2 = service.compareProductInCart(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
