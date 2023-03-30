import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductInCartFormService } from './product-in-cart-form.service';
import { ProductInCartService } from '../service/product-in-cart.service';
import { IProductInCart } from '../product-in-cart.model';
import { ICart } from 'app/entities/cart/cart.model';
import { CartService } from 'app/entities/cart/service/cart.service';

import { ProductInCartUpdateComponent } from './product-in-cart-update.component';

describe('ProductInCart Management Update Component', () => {
  let comp: ProductInCartUpdateComponent;
  let fixture: ComponentFixture<ProductInCartUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productInCartFormService: ProductInCartFormService;
  let productInCartService: ProductInCartService;
  let cartService: CartService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProductInCartUpdateComponent],
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
      .overrideTemplate(ProductInCartUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductInCartUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productInCartFormService = TestBed.inject(ProductInCartFormService);
    productInCartService = TestBed.inject(ProductInCartService);
    cartService = TestBed.inject(CartService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Cart query and add missing value', () => {
      const productInCart: IProductInCart = { id: 456 };
      const cart: ICart = { id: 69092 };
      productInCart.cart = cart;

      const cartCollection: ICart[] = [{ id: 14978 }];
      jest.spyOn(cartService, 'query').mockReturnValue(of(new HttpResponse({ body: cartCollection })));
      const additionalCarts = [cart];
      const expectedCollection: ICart[] = [...additionalCarts, ...cartCollection];
      jest.spyOn(cartService, 'addCartToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ productInCart });
      comp.ngOnInit();

      expect(cartService.query).toHaveBeenCalled();
      expect(cartService.addCartToCollectionIfMissing).toHaveBeenCalledWith(
        cartCollection,
        ...additionalCarts.map(expect.objectContaining)
      );
      expect(comp.cartsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const productInCart: IProductInCart = { id: 456 };
      const cart: ICart = { id: 47130 };
      productInCart.cart = cart;

      activatedRoute.data = of({ productInCart });
      comp.ngOnInit();

      expect(comp.cartsSharedCollection).toContain(cart);
      expect(comp.productInCart).toEqual(productInCart);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductInCart>>();
      const productInCart = { id: 123 };
      jest.spyOn(productInCartFormService, 'getProductInCart').mockReturnValue(productInCart);
      jest.spyOn(productInCartService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productInCart });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productInCart }));
      saveSubject.complete();

      // THEN
      expect(productInCartFormService.getProductInCart).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(productInCartService.update).toHaveBeenCalledWith(expect.objectContaining(productInCart));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductInCart>>();
      const productInCart = { id: 123 };
      jest.spyOn(productInCartFormService, 'getProductInCart').mockReturnValue({ id: null });
      jest.spyOn(productInCartService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productInCart: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productInCart }));
      saveSubject.complete();

      // THEN
      expect(productInCartFormService.getProductInCart).toHaveBeenCalled();
      expect(productInCartService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductInCart>>();
      const productInCart = { id: 123 };
      jest.spyOn(productInCartService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productInCart });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productInCartService.update).toHaveBeenCalled();
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
