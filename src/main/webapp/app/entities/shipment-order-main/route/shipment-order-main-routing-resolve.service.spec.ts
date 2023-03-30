import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IShipmentOrderMain } from '../shipment-order-main.model';
import { ShipmentOrderMainService } from '../service/shipment-order-main.service';

import { ShipmentOrderMainRoutingResolveService } from './shipment-order-main-routing-resolve.service';

describe('ShipmentOrderMain routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ShipmentOrderMainRoutingResolveService;
  let service: ShipmentOrderMainService;
  let resultShipmentOrderMain: IShipmentOrderMain | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(ShipmentOrderMainRoutingResolveService);
    service = TestBed.inject(ShipmentOrderMainService);
    resultShipmentOrderMain = undefined;
  });

  describe('resolve', () => {
    it('should return IShipmentOrderMain returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultShipmentOrderMain = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultShipmentOrderMain).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultShipmentOrderMain = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultShipmentOrderMain).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IShipmentOrderMain>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultShipmentOrderMain = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultShipmentOrderMain).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
