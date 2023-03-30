import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPaymentCart } from '../payment-cart.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../payment-cart.test-samples';

import { PaymentCartService } from './payment-cart.service';

const requireRestSample: IPaymentCart = {
  ...sampleWithRequiredData,
};

describe('PaymentCart Service', () => {
  let service: PaymentCartService;
  let httpMock: HttpTestingController;
  let expectedResult: IPaymentCart | IPaymentCart[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PaymentCartService);
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

    it('should create a PaymentCart', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const paymentCart = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(paymentCart).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PaymentCart', () => {
      const paymentCart = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(paymentCart).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PaymentCart', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PaymentCart', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PaymentCart', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPaymentCartToCollectionIfMissing', () => {
      it('should add a PaymentCart to an empty array', () => {
        const paymentCart: IPaymentCart = sampleWithRequiredData;
        expectedResult = service.addPaymentCartToCollectionIfMissing([], paymentCart);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentCart);
      });

      it('should not add a PaymentCart to an array that contains it', () => {
        const paymentCart: IPaymentCart = sampleWithRequiredData;
        const paymentCartCollection: IPaymentCart[] = [
          {
            ...paymentCart,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPaymentCartToCollectionIfMissing(paymentCartCollection, paymentCart);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PaymentCart to an array that doesn't contain it", () => {
        const paymentCart: IPaymentCart = sampleWithRequiredData;
        const paymentCartCollection: IPaymentCart[] = [sampleWithPartialData];
        expectedResult = service.addPaymentCartToCollectionIfMissing(paymentCartCollection, paymentCart);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentCart);
      });

      it('should add only unique PaymentCart to an array', () => {
        const paymentCartArray: IPaymentCart[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const paymentCartCollection: IPaymentCart[] = [sampleWithRequiredData];
        expectedResult = service.addPaymentCartToCollectionIfMissing(paymentCartCollection, ...paymentCartArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const paymentCart: IPaymentCart = sampleWithRequiredData;
        const paymentCart2: IPaymentCart = sampleWithPartialData;
        expectedResult = service.addPaymentCartToCollectionIfMissing([], paymentCart, paymentCart2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentCart);
        expect(expectedResult).toContain(paymentCart2);
      });

      it('should accept null and undefined values', () => {
        const paymentCart: IPaymentCart = sampleWithRequiredData;
        expectedResult = service.addPaymentCartToCollectionIfMissing([], null, paymentCart, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentCart);
      });

      it('should return initial array if no PaymentCart is added', () => {
        const paymentCartCollection: IPaymentCart[] = [sampleWithRequiredData];
        expectedResult = service.addPaymentCartToCollectionIfMissing(paymentCartCollection, undefined, null);
        expect(expectedResult).toEqual(paymentCartCollection);
      });
    });

    describe('comparePaymentCart', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePaymentCart(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePaymentCart(entity1, entity2);
        const compareResult2 = service.comparePaymentCart(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePaymentCart(entity1, entity2);
        const compareResult2 = service.comparePaymentCart(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePaymentCart(entity1, entity2);
        const compareResult2 = service.comparePaymentCart(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
