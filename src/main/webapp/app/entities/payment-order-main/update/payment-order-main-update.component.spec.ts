import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaymentOrderMainFormService } from './payment-order-main-form.service';
import { PaymentOrderMainService } from '../service/payment-order-main.service';
import { IPaymentOrderMain } from '../payment-order-main.model';
import { IOrderMain } from 'app/entities/order-main/order-main.model';
import { OrderMainService } from 'app/entities/order-main/service/order-main.service';

import { PaymentOrderMainUpdateComponent } from './payment-order-main-update.component';

describe('PaymentOrderMain Management Update Component', () => {
  let comp: PaymentOrderMainUpdateComponent;
  let fixture: ComponentFixture<PaymentOrderMainUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentOrderMainFormService: PaymentOrderMainFormService;
  let paymentOrderMainService: PaymentOrderMainService;
  let orderMainService: OrderMainService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PaymentOrderMainUpdateComponent],
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
      .overrideTemplate(PaymentOrderMainUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentOrderMainUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paymentOrderMainFormService = TestBed.inject(PaymentOrderMainFormService);
    paymentOrderMainService = TestBed.inject(PaymentOrderMainService);
    orderMainService = TestBed.inject(OrderMainService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call OrderMain query and add missing value', () => {
      const paymentOrderMain: IPaymentOrderMain = { id: 456 };
      const orderMain: IOrderMain = { id: 27832 };
      paymentOrderMain.orderMain = orderMain;

      const orderMainCollection: IOrderMain[] = [{ id: 5854 }];
      jest.spyOn(orderMainService, 'query').mockReturnValue(of(new HttpResponse({ body: orderMainCollection })));
      const additionalOrderMains = [orderMain];
      const expectedCollection: IOrderMain[] = [...additionalOrderMains, ...orderMainCollection];
      jest.spyOn(orderMainService, 'addOrderMainToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paymentOrderMain });
      comp.ngOnInit();

      expect(orderMainService.query).toHaveBeenCalled();
      expect(orderMainService.addOrderMainToCollectionIfMissing).toHaveBeenCalledWith(
        orderMainCollection,
        ...additionalOrderMains.map(expect.objectContaining)
      );
      expect(comp.orderMainsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const paymentOrderMain: IPaymentOrderMain = { id: 456 };
      const orderMain: IOrderMain = { id: 46955 };
      paymentOrderMain.orderMain = orderMain;

      activatedRoute.data = of({ paymentOrderMain });
      comp.ngOnInit();

      expect(comp.orderMainsSharedCollection).toContain(orderMain);
      expect(comp.paymentOrderMain).toEqual(paymentOrderMain);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentOrderMain>>();
      const paymentOrderMain = { id: 123 };
      jest.spyOn(paymentOrderMainFormService, 'getPaymentOrderMain').mockReturnValue(paymentOrderMain);
      jest.spyOn(paymentOrderMainService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentOrderMain });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentOrderMain }));
      saveSubject.complete();

      // THEN
      expect(paymentOrderMainFormService.getPaymentOrderMain).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(paymentOrderMainService.update).toHaveBeenCalledWith(expect.objectContaining(paymentOrderMain));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentOrderMain>>();
      const paymentOrderMain = { id: 123 };
      jest.spyOn(paymentOrderMainFormService, 'getPaymentOrderMain').mockReturnValue({ id: null });
      jest.spyOn(paymentOrderMainService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentOrderMain: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentOrderMain }));
      saveSubject.complete();

      // THEN
      expect(paymentOrderMainFormService.getPaymentOrderMain).toHaveBeenCalled();
      expect(paymentOrderMainService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentOrderMain>>();
      const paymentOrderMain = { id: 123 };
      jest.spyOn(paymentOrderMainService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentOrderMain });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paymentOrderMainService.update).toHaveBeenCalled();
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
