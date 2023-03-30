import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOrderMain } from '../order-main.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../order-main.test-samples';

import { OrderMainService, RestOrderMain } from './order-main.service';

const requireRestSample: RestOrderMain = {
  ...sampleWithRequiredData,
  createTime: sampleWithRequiredData.createTime?.toJSON(),
  updateTime: sampleWithRequiredData.updateTime?.toJSON(),
};

describe('OrderMain Service', () => {
  let service: OrderMainService;
  let httpMock: HttpTestingController;
  let expectedResult: IOrderMain | IOrderMain[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(OrderMainService);
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

    it('should create a OrderMain', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const orderMain = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(orderMain).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OrderMain', () => {
      const orderMain = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(orderMain).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OrderMain', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OrderMain', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OrderMain', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOrderMainToCollectionIfMissing', () => {
      it('should add a OrderMain to an empty array', () => {
        const orderMain: IOrderMain = sampleWithRequiredData;
        expectedResult = service.addOrderMainToCollectionIfMissing([], orderMain);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orderMain);
      });

      it('should not add a OrderMain to an array that contains it', () => {
        const orderMain: IOrderMain = sampleWithRequiredData;
        const orderMainCollection: IOrderMain[] = [
          {
            ...orderMain,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOrderMainToCollectionIfMissing(orderMainCollection, orderMain);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OrderMain to an array that doesn't contain it", () => {
        const orderMain: IOrderMain = sampleWithRequiredData;
        const orderMainCollection: IOrderMain[] = [sampleWithPartialData];
        expectedResult = service.addOrderMainToCollectionIfMissing(orderMainCollection, orderMain);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orderMain);
      });

      it('should add only unique OrderMain to an array', () => {
        const orderMainArray: IOrderMain[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const orderMainCollection: IOrderMain[] = [sampleWithRequiredData];
        expectedResult = service.addOrderMainToCollectionIfMissing(orderMainCollection, ...orderMainArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const orderMain: IOrderMain = sampleWithRequiredData;
        const orderMain2: IOrderMain = sampleWithPartialData;
        expectedResult = service.addOrderMainToCollectionIfMissing([], orderMain, orderMain2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(orderMain);
        expect(expectedResult).toContain(orderMain2);
      });

      it('should accept null and undefined values', () => {
        const orderMain: IOrderMain = sampleWithRequiredData;
        expectedResult = service.addOrderMainToCollectionIfMissing([], null, orderMain, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(orderMain);
      });

      it('should return initial array if no OrderMain is added', () => {
        const orderMainCollection: IOrderMain[] = [sampleWithRequiredData];
        expectedResult = service.addOrderMainToCollectionIfMissing(orderMainCollection, undefined, null);
        expect(expectedResult).toEqual(orderMainCollection);
      });
    });

    describe('compareOrderMain', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOrderMain(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareOrderMain(entity1, entity2);
        const compareResult2 = service.compareOrderMain(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareOrderMain(entity1, entity2);
        const compareResult2 = service.compareOrderMain(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareOrderMain(entity1, entity2);
        const compareResult2 = service.compareOrderMain(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
