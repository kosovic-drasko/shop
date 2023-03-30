import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProductInOrderMain } from '../product-in-order-main.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../product-in-order-main.test-samples';

import { ProductInOrderMainService } from './product-in-order-main.service';

const requireRestSample: IProductInOrderMain = {
  ...sampleWithRequiredData,
};

describe('ProductInOrderMain Service', () => {
  let service: ProductInOrderMainService;
  let httpMock: HttpTestingController;
  let expectedResult: IProductInOrderMain | IProductInOrderMain[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductInOrderMainService);
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

    it('should create a ProductInOrderMain', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const productInOrderMain = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(productInOrderMain).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductInOrderMain', () => {
      const productInOrderMain = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(productInOrderMain).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductInOrderMain', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductInOrderMain', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ProductInOrderMain', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProductInOrderMainToCollectionIfMissing', () => {
      it('should add a ProductInOrderMain to an empty array', () => {
        const productInOrderMain: IProductInOrderMain = sampleWithRequiredData;
        expectedResult = service.addProductInOrderMainToCollectionIfMissing([], productInOrderMain);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productInOrderMain);
      });

      it('should not add a ProductInOrderMain to an array that contains it', () => {
        const productInOrderMain: IProductInOrderMain = sampleWithRequiredData;
        const productInOrderMainCollection: IProductInOrderMain[] = [
          {
            ...productInOrderMain,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProductInOrderMainToCollectionIfMissing(productInOrderMainCollection, productInOrderMain);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductInOrderMain to an array that doesn't contain it", () => {
        const productInOrderMain: IProductInOrderMain = sampleWithRequiredData;
        const productInOrderMainCollection: IProductInOrderMain[] = [sampleWithPartialData];
        expectedResult = service.addProductInOrderMainToCollectionIfMissing(productInOrderMainCollection, productInOrderMain);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productInOrderMain);
      });

      it('should add only unique ProductInOrderMain to an array', () => {
        const productInOrderMainArray: IProductInOrderMain[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const productInOrderMainCollection: IProductInOrderMain[] = [sampleWithRequiredData];
        expectedResult = service.addProductInOrderMainToCollectionIfMissing(productInOrderMainCollection, ...productInOrderMainArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const productInOrderMain: IProductInOrderMain = sampleWithRequiredData;
        const productInOrderMain2: IProductInOrderMain = sampleWithPartialData;
        expectedResult = service.addProductInOrderMainToCollectionIfMissing([], productInOrderMain, productInOrderMain2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productInOrderMain);
        expect(expectedResult).toContain(productInOrderMain2);
      });

      it('should accept null and undefined values', () => {
        const productInOrderMain: IProductInOrderMain = sampleWithRequiredData;
        expectedResult = service.addProductInOrderMainToCollectionIfMissing([], null, productInOrderMain, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productInOrderMain);
      });

      it('should return initial array if no ProductInOrderMain is added', () => {
        const productInOrderMainCollection: IProductInOrderMain[] = [sampleWithRequiredData];
        expectedResult = service.addProductInOrderMainToCollectionIfMissing(productInOrderMainCollection, undefined, null);
        expect(expectedResult).toEqual(productInOrderMainCollection);
      });
    });

    describe('compareProductInOrderMain', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProductInOrderMain(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProductInOrderMain(entity1, entity2);
        const compareResult2 = service.compareProductInOrderMain(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProductInOrderMain(entity1, entity2);
        const compareResult2 = service.compareProductInOrderMain(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProductInOrderMain(entity1, entity2);
        const compareResult2 = service.compareProductInOrderMain(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
