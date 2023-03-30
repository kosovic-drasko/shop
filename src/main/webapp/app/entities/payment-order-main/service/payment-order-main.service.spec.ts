import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPaymentOrderMain } from '../payment-order-main.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../payment-order-main.test-samples';

import { PaymentOrderMainService } from './payment-order-main.service';

const requireRestSample: IPaymentOrderMain = {
  ...sampleWithRequiredData,
};

describe('PaymentOrderMain Service', () => {
  let service: PaymentOrderMainService;
  let httpMock: HttpTestingController;
  let expectedResult: IPaymentOrderMain | IPaymentOrderMain[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PaymentOrderMainService);
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

    it('should create a PaymentOrderMain', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const paymentOrderMain = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(paymentOrderMain).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PaymentOrderMain', () => {
      const paymentOrderMain = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(paymentOrderMain).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PaymentOrderMain', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PaymentOrderMain', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PaymentOrderMain', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPaymentOrderMainToCollectionIfMissing', () => {
      it('should add a PaymentOrderMain to an empty array', () => {
        const paymentOrderMain: IPaymentOrderMain = sampleWithRequiredData;
        expectedResult = service.addPaymentOrderMainToCollectionIfMissing([], paymentOrderMain);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentOrderMain);
      });

      it('should not add a PaymentOrderMain to an array that contains it', () => {
        const paymentOrderMain: IPaymentOrderMain = sampleWithRequiredData;
        const paymentOrderMainCollection: IPaymentOrderMain[] = [
          {
            ...paymentOrderMain,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPaymentOrderMainToCollectionIfMissing(paymentOrderMainCollection, paymentOrderMain);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PaymentOrderMain to an array that doesn't contain it", () => {
        const paymentOrderMain: IPaymentOrderMain = sampleWithRequiredData;
        const paymentOrderMainCollection: IPaymentOrderMain[] = [sampleWithPartialData];
        expectedResult = service.addPaymentOrderMainToCollectionIfMissing(paymentOrderMainCollection, paymentOrderMain);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentOrderMain);
      });

      it('should add only unique PaymentOrderMain to an array', () => {
        const paymentOrderMainArray: IPaymentOrderMain[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const paymentOrderMainCollection: IPaymentOrderMain[] = [sampleWithRequiredData];
        expectedResult = service.addPaymentOrderMainToCollectionIfMissing(paymentOrderMainCollection, ...paymentOrderMainArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const paymentOrderMain: IPaymentOrderMain = sampleWithRequiredData;
        const paymentOrderMain2: IPaymentOrderMain = sampleWithPartialData;
        expectedResult = service.addPaymentOrderMainToCollectionIfMissing([], paymentOrderMain, paymentOrderMain2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paymentOrderMain);
        expect(expectedResult).toContain(paymentOrderMain2);
      });

      it('should accept null and undefined values', () => {
        const paymentOrderMain: IPaymentOrderMain = sampleWithRequiredData;
        expectedResult = service.addPaymentOrderMainToCollectionIfMissing([], null, paymentOrderMain, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paymentOrderMain);
      });

      it('should return initial array if no PaymentOrderMain is added', () => {
        const paymentOrderMainCollection: IPaymentOrderMain[] = [sampleWithRequiredData];
        expectedResult = service.addPaymentOrderMainToCollectionIfMissing(paymentOrderMainCollection, undefined, null);
        expect(expectedResult).toEqual(paymentOrderMainCollection);
      });
    });

    describe('comparePaymentOrderMain', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePaymentOrderMain(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePaymentOrderMain(entity1, entity2);
        const compareResult2 = service.comparePaymentOrderMain(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePaymentOrderMain(entity1, entity2);
        const compareResult2 = service.comparePaymentOrderMain(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePaymentOrderMain(entity1, entity2);
        const compareResult2 = service.comparePaymentOrderMain(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
