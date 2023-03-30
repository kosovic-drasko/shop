import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { OrderMainFormService } from './order-main-form.service';
import { OrderMainService } from '../service/order-main.service';
import { IOrderMain } from '../order-main.model';

import { OrderMainUpdateComponent } from './order-main-update.component';

describe('OrderMain Management Update Component', () => {
  let comp: OrderMainUpdateComponent;
  let fixture: ComponentFixture<OrderMainUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let orderMainFormService: OrderMainFormService;
  let orderMainService: OrderMainService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [OrderMainUpdateComponent],
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
      .overrideTemplate(OrderMainUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrderMainUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    orderMainFormService = TestBed.inject(OrderMainFormService);
    orderMainService = TestBed.inject(OrderMainService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const orderMain: IOrderMain = { id: 456 };

      activatedRoute.data = of({ orderMain });
      comp.ngOnInit();

      expect(comp.orderMain).toEqual(orderMain);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrderMain>>();
      const orderMain = { id: 123 };
      jest.spyOn(orderMainFormService, 'getOrderMain').mockReturnValue(orderMain);
      jest.spyOn(orderMainService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderMain });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orderMain }));
      saveSubject.complete();

      // THEN
      expect(orderMainFormService.getOrderMain).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(orderMainService.update).toHaveBeenCalledWith(expect.objectContaining(orderMain));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrderMain>>();
      const orderMain = { id: 123 };
      jest.spyOn(orderMainFormService, 'getOrderMain').mockReturnValue({ id: null });
      jest.spyOn(orderMainService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderMain: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: orderMain }));
      saveSubject.complete();

      // THEN
      expect(orderMainFormService.getOrderMain).toHaveBeenCalled();
      expect(orderMainService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IOrderMain>>();
      const orderMain = { id: 123 };
      jest.spyOn(orderMainService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ orderMain });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(orderMainService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
