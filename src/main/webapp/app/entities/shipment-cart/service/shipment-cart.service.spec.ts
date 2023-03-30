import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IShipmentCart } from '../shipment-cart.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../shipment-cart.test-samples';

import { ShipmentCartService } from './shipment-cart.service';

const requireRestSample: IShipmentCart = {
  ...sampleWithRequiredData,
};

describe('ShipmentCart Service', () => {
  let service: ShipmentCartService;
  let httpMock: HttpTestingController;
  let expectedResult: IShipmentCart | IShipmentCart[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ShipmentCartService);
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

    it('should create a ShipmentCart', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const shipmentCart = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(shipmentCart).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ShipmentCart', () => {
      const shipmentCart = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(shipmentCart).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ShipmentCart', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ShipmentCart', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ShipmentCart', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addShipmentCartToCollectionIfMissing', () => {
      it('should add a ShipmentCart to an empty array', () => {
        const shipmentCart: IShipmentCart = sampleWithRequiredData;
        expectedResult = service.addShipmentCartToCollectionIfMissing([], shipmentCart);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shipmentCart);
      });

      it('should not add a ShipmentCart to an array that contains it', () => {
        const shipmentCart: IShipmentCart = sampleWithRequiredData;
        const shipmentCartCollection: IShipmentCart[] = [
          {
            ...shipmentCart,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addShipmentCartToCollectionIfMissing(shipmentCartCollection, shipmentCart);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ShipmentCart to an array that doesn't contain it", () => {
        const shipmentCart: IShipmentCart = sampleWithRequiredData;
        const shipmentCartCollection: IShipmentCart[] = [sampleWithPartialData];
        expectedResult = service.addShipmentCartToCollectionIfMissing(shipmentCartCollection, shipmentCart);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shipmentCart);
      });

      it('should add only unique ShipmentCart to an array', () => {
        const shipmentCartArray: IShipmentCart[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const shipmentCartCollection: IShipmentCart[] = [sampleWithRequiredData];
        expectedResult = service.addShipmentCartToCollectionIfMissing(shipmentCartCollection, ...shipmentCartArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const shipmentCart: IShipmentCart = sampleWithRequiredData;
        const shipmentCart2: IShipmentCart = sampleWithPartialData;
        expectedResult = service.addShipmentCartToCollectionIfMissing([], shipmentCart, shipmentCart2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(shipmentCart);
        expect(expectedResult).toContain(shipmentCart2);
      });

      it('should accept null and undefined values', () => {
        const shipmentCart: IShipmentCart = sampleWithRequiredData;
        expectedResult = service.addShipmentCartToCollectionIfMissing([], null, shipmentCart, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(shipmentCart);
      });

      it('should return initial array if no ShipmentCart is added', () => {
        const shipmentCartCollection: IShipmentCart[] = [sampleWithRequiredData];
        expectedResult = service.addShipmentCartToCollectionIfMissing(shipmentCartCollection, undefined, null);
        expect(expectedResult).toEqual(shipmentCartCollection);
      });
    });

    describe('compareShipmentCart', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareShipmentCart(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareShipmentCart(entity1, entity2);
        const compareResult2 = service.compareShipmentCart(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareShipmentCart(entity1, entity2);
        const compareResult2 = service.compareShipmentCart(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareShipmentCart(entity1, entity2);
        const compareResult2 = service.compareShipmentCart(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
