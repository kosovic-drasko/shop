import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IProba } from '../proba.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../proba.test-samples';

import { ProbaService } from './proba.service';

const requireRestSample: IProba = {
  ...sampleWithRequiredData,
};

describe('Proba Service', () => {
  let service: ProbaService;
  let httpMock: HttpTestingController;
  let expectedResult: IProba | IProba[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProbaService);
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

    it('should create a Proba', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const proba = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(proba).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Proba', () => {
      const proba = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(proba).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Proba', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Proba', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Proba', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addProbaToCollectionIfMissing', () => {
      it('should add a Proba to an empty array', () => {
        const proba: IProba = sampleWithRequiredData;
        expectedResult = service.addProbaToCollectionIfMissing([], proba);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(proba);
      });

      it('should not add a Proba to an array that contains it', () => {
        const proba: IProba = sampleWithRequiredData;
        const probaCollection: IProba[] = [
          {
            ...proba,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addProbaToCollectionIfMissing(probaCollection, proba);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Proba to an array that doesn't contain it", () => {
        const proba: IProba = sampleWithRequiredData;
        const probaCollection: IProba[] = [sampleWithPartialData];
        expectedResult = service.addProbaToCollectionIfMissing(probaCollection, proba);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(proba);
      });

      it('should add only unique Proba to an array', () => {
        const probaArray: IProba[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const probaCollection: IProba[] = [sampleWithRequiredData];
        expectedResult = service.addProbaToCollectionIfMissing(probaCollection, ...probaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const proba: IProba = sampleWithRequiredData;
        const proba2: IProba = sampleWithPartialData;
        expectedResult = service.addProbaToCollectionIfMissing([], proba, proba2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(proba);
        expect(expectedResult).toContain(proba2);
      });

      it('should accept null and undefined values', () => {
        const proba: IProba = sampleWithRequiredData;
        expectedResult = service.addProbaToCollectionIfMissing([], null, proba, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(proba);
      });

      it('should return initial array if no Proba is added', () => {
        const probaCollection: IProba[] = [sampleWithRequiredData];
        expectedResult = service.addProbaToCollectionIfMissing(probaCollection, undefined, null);
        expect(expectedResult).toEqual(probaCollection);
      });
    });

    describe('compareProba', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareProba(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareProba(entity1, entity2);
        const compareResult2 = service.compareProba(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareProba(entity1, entity2);
        const compareResult2 = service.compareProba(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareProba(entity1, entity2);
        const compareResult2 = service.compareProba(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
