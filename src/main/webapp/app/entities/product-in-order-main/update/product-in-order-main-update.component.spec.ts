import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductInOrderMainFormService } from './product-in-order-main-form.service';
import { ProductInOrderMainService } from '../service/product-in-order-main.service';
import { IProductInOrderMain } from '../product-in-order-main.model';
import { IOrderMain } from 'app/entities/order-main/order-main.model';
import { OrderMainService } from 'app/entities/order-main/service/order-main.service';

import { ProductInOrderMainUpdateComponent } from './product-in-order-main-update.component';

describe('ProductInOrderMain Management Update Component', () => {
  let comp: ProductInOrderMainUpdateComponent;
  let fixture: ComponentFixture<ProductInOrderMainUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productInOrderMainFormService: ProductInOrderMainFormService;
  let productInOrderMainService: ProductInOrderMainService;
  let orderMainService: OrderMainService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProductInOrderMainUpdateComponent],
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
      .overrideTemplate(ProductInOrderMainUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductInOrderMainUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productInOrderMainFormService = TestBed.inject(ProductInOrderMainFormService);
    productInOrderMainService = TestBed.inject(ProductInOrderMainService);
    orderMainService = TestBed.inject(OrderMainService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call OrderMain query and add missing value', () => {
      const productInOrderMain: IProductInOrderMain = { id: 456 };
      const orderMain: IOrderMain = { id: 52095 };
      productInOrderMain.orderMain = orderMain;

      const orderMainCollection: IOrderMain[] = [{ id: 48605 }];
      jest.spyOn(orderMainService, 'query').mockReturnValue(of(new HttpResponse({ body: orderMainCollection })));
      const additionalOrderMains = [orderMain];
      const expectedCollection: IOrderMain[] = [...additionalOrderMains, ...orderMainCollection];
      jest.spyOn(orderMainService, 'addOrderMainToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productInOrderMain });
      comp.ngOnInit();

      expect(orderMainService.query).toHaveBeenCalled();
      expect(orderMainService.addOrderMainToCollectionIfMissing).toHaveBeenCalledWith(
        orderMainCollection,
        ...additionalOrderMains.map(expect.objectContaining)
      );
      expect(comp.orderMainsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const productInOrderMain: IProductInOrderMain = { id: 456 };
      const orderMain: IOrderMain = { id: 92374 };
      productInOrderMain.orderMain = orderMain;

      activatedRoute.data = of({ productInOrderMain });
      comp.ngOnInit();

      expect(comp.orderMainsSharedCollection).toContain(orderMain);
      expect(comp.productInOrderMain).toEqual(productInOrderMain);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductInOrderMain>>();
      const productInOrderMain = { id: 123 };
      jest.spyOn(productInOrderMainFormService, 'getProductInOrderMain').mockReturnValue(productInOrderMain);
      jest.spyOn(productInOrderMainService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productInOrderMain });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productInOrderMain }));
      saveSubject.complete();

      // THEN
      expect(productInOrderMainFormService.getProductInOrderMain).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(productInOrderMainService.update).toHaveBeenCalledWith(expect.objectContaining(productInOrderMain));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductInOrderMain>>();
      const productInOrderMain = { id: 123 };
      jest.spyOn(productInOrderMainFormService, 'getProductInOrderMain').mockReturnValue({ id: null });
      jest.spyOn(productInOrderMainService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productInOrderMain: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productInOrderMain }));
      saveSubject.complete();

      // THEN
      expect(productInOrderMainFormService.getProductInOrderMain).toHaveBeenCalled();
      expect(productInOrderMainService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductInOrderMain>>();
      const productInOrderMain = { id: 123 };
      jest.spyOn(productInOrderMainService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productInOrderMain });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productInOrderMainService.update).toHaveBeenCalled();
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
