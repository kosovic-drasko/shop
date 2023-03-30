import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaymentCartFormService } from './payment-cart-form.service';
import { PaymentCartService } from '../service/payment-cart.service';
import { IPaymentCart } from '../payment-cart.model';
import { ICart } from 'app/entities/cart/cart.model';
import { CartService } from 'app/entities/cart/service/cart.service';

import { PaymentCartUpdateComponent } from './payment-cart-update.component';

describe('PaymentCart Management Update Component', () => {
  let comp: PaymentCartUpdateComponent;
  let fixture: ComponentFixture<PaymentCartUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentCartFormService: PaymentCartFormService;
  let paymentCartService: PaymentCartService;
  let cartService: CartService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PaymentCartUpdateComponent],
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
      .overrideTemplate(PaymentCartUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentCartUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paymentCartFormService = TestBed.inject(PaymentCartFormService);
    paymentCartService = TestBed.inject(PaymentCartService);
    cartService = TestBed.inject(CartService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Cart query and add missing value', () => {
      const paymentCart: IPaymentCart = { id: 456 };
      const cart: ICart = { id: 60904 };
      paymentCart.cart = cart;

      const cartCollection: ICart[] = [{ id: 46222 }];
      jest.spyOn(cartService, 'query').mockReturnValue(of(new HttpResponse({ body: cartCollection })));
      const additionalCarts = [cart];
      const expectedCollection: ICart[] = [...additionalCarts, ...cartCollection];
      jest.spyOn(cartService, 'addCartToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ paymentCart });
      comp.ngOnInit();

      expect(cartService.query).toHaveBeenCalled();
      expect(cartService.addCartToCollectionIfMissing).toHaveBeenCalledWith(
        cartCollection,
        ...additionalCarts.map(expect.objectContaining)
      );
      expect(comp.cartsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const paymentCart: IPaymentCart = { id: 456 };
      const cart: ICart = { id: 15921 };
      paymentCart.cart = cart;

      activatedRoute.data = of({ paymentCart });
      comp.ngOnInit();

      expect(comp.cartsSharedCollection).toContain(cart);
      expect(comp.paymentCart).toEqual(paymentCart);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentCart>>();
      const paymentCart = { id: 123 };
      jest.spyOn(paymentCartFormService, 'getPaymentCart').mockReturnValue(paymentCart);
      jest.spyOn(paymentCartService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentCart });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentCart }));
      saveSubject.complete();

      // THEN
      expect(paymentCartFormService.getPaymentCart).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(paymentCartService.update).toHaveBeenCalledWith(expect.objectContaining(paymentCart));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentCart>>();
      const paymentCart = { id: 123 };
      jest.spyOn(paymentCartFormService, 'getPaymentCart').mockReturnValue({ id: null });
      jest.spyOn(paymentCartService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentCart: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentCart }));
      saveSubject.complete();

      // THEN
      expect(paymentCartFormService.getPaymentCart).toHaveBeenCalled();
      expect(paymentCartService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentCart>>();
      const paymentCart = { id: 123 };
      jest.spyOn(paymentCartService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentCart });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paymentCartService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCart', () => {
      it('Should forward to cartService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cartService, 'compareCart');
        comp.compareCart(entity, entity2);
        expect(cartService.compareCart).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
