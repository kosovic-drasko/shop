import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ShipmentOrderMainFormService } from './shipment-order-main-form.service';
import { ShipmentOrderMainService } from '../service/shipment-order-main.service';
import { IShipmentOrderMain } from '../shipment-order-main.model';
import { IOrderMain } from 'app/entities/order-main/order-main.model';
import { OrderMainService } from 'app/entities/order-main/service/order-main.service';

import { ShipmentOrderMainUpdateComponent } from './shipment-order-main-update.component';

describe('ShipmentOrderMain Management Update Component', () => {
  let comp: ShipmentOrderMainUpdateComponent;
  let fixture: ComponentFixture<ShipmentOrderMainUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let shipmentOrderMainFormService: ShipmentOrderMainFormService;
  let shipmentOrderMainService: ShipmentOrderMainService;
  let orderMainService: OrderMainService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ShipmentOrderMainUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ShipmentOrderMainUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ShipmentOrderMainUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    shipmentOrderMainFormService = TestBed.inject(ShipmentOrderMainFormService);
    shipmentOrderMainService = TestBed.inject(ShipmentOrderMainService);
    orderMainService = TestBed.inject(OrderMainService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call OrderMain query and add missing value', () => {
      const shipmentOrderMain: IShipmentOrderMain = { id: 456 };
      const orderMain: IOrderMain = { id: 29409 };
      shipmentOrderMain.orderMain = orderMain;

      const orderMainCollection: IOrderMain[] = [{ id: 10713 }];
      jest.spyOn(orderMainService, 'query').mockReturnValue(of(new HttpResponse({ body: orderMainCollection })));
      const additionalOrderMains = [orderMain];
      const expectedCollection: IOrderMain[] = [...additionalOrderMains, ...orderMainCollection];
      jest.spyOn(orderMainService, 'addOrderMainToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ shipmentOrderMain });
      comp.ngOnInit();

      expect(orderMainService.query).toHaveBeenCalled();
      expect(orderMainService.addOrderMainToCollectionIfMissing).toHaveBeenCalledWith(
        orderMainCollection,
        ...additionalOrderMains.map(expect.objectContaining)
      );
      expect(comp.orderMainsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const shipmentOrderMain: IShipmentOrderMain = { id: 456 };
      const orderMain: IOrderMain = { id: 91524 };
      shipmentOrderMain.orderMain = orderMain;

      activatedRoute.data = of({ shipmentOrderMain });
      comp.ngOnInit();

      expect(comp.orderMainsSharedCollection).toContain(orderMain);
      expect(comp.shipmentOrderMain).toEqual(shipmentOrderMain);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipmentOrderMain>>();
      const shipmentOrderMain = { id: 123 };
      jest.spyOn(shipmentOrderMainFormService, 'getShipmentOrderMain').mockReturnValue(shipmentOrderMain);
      jest.spyOn(shipmentOrderMainService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipmentOrderMain });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shipmentOrderMain }));
      saveSubject.complete();

      // THEN
      expect(shipmentOrderMainFormService.getShipmentOrderMain).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(shipmentOrderMainService.update).toHaveBeenCalledWith(expect.objectContaining(shipmentOrderMain));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipmentOrderMain>>();
      const shipmentOrderMain = { id: 123 };
      jest.spyOn(shipmentOrderMainFormService, 'getShipmentOrderMain').mockReturnValue({ id: null });
      jest.spyOn(shipmentOrderMainService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipmentOrderMain: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shipmentOrderMain }));
      saveSubject.complete();

      // THEN
      expect(shipmentOrderMainFormService.getShipmentOrderMain).toHaveBeenCalled();
      expect(shipmentOrderMainService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipmentOrderMain>>();
      const shipmentOrderMain = { id: 123 };
      jest.spyOn(shipmentOrderMainService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipmentOrderMain });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(shipmentOrderMainService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareOrderMain', () => {
      it('Should forward to orderMainService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(orderMainService, 'compareOrderMain');
        comp.compareOrderMain(entity, entity2);
        expect(orderMainService.compareOrderMain).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
