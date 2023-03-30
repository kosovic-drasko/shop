import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ShipmentCartFormService } from './shipment-cart-form.service';
import { ShipmentCartService } from '../service/shipment-cart.service';
import { IShipmentCart } from '../shipment-cart.model';
import { ICart } from 'app/entities/cart/cart.model';
import { CartService } from 'app/entities/cart/service/cart.service';

import { ShipmentCartUpdateComponent } from './shipment-cart-update.component';

describe('ShipmentCart Management Update Component', () => {
  let comp: ShipmentCartUpdateComponent;
  let fixture: ComponentFixture<ShipmentCartUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let shipmentCartFormService: ShipmentCartFormService;
  let shipmentCartService: ShipmentCartService;
  let cartService: CartService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ShipmentCartUpdateComponent],
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
      .overrideTemplate(ShipmentCartUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ShipmentCartUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    shipmentCartFormService = TestBed.inject(ShipmentCartFormService);
    shipmentCartService = TestBed.inject(ShipmentCartService);
    cartService = TestBed.inject(CartService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Cart query and add missing value', () => {
      const shipmentCart: IShipmentCart = { id: 456 };
      const cart: ICart = { id: 25822 };
      shipmentCart.cart = cart;

      const cartCollection: ICart[] = [{ id: 5303 }];
      jest.spyOn(cartService, 'query').mockReturnValue(of(new HttpResponse({ body: cartCollection })));
      const additionalCarts = [cart];
      const expectedCollection: ICart[] = [...additionalCarts, ...cartCollection];
      jest.spyOn(cartService, 'addCartToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ shipmentCart });
      comp.ngOnInit();

      expect(cartService.query).toHaveBeenCalled();
      expect(cartService.addCartToCollectionIfMissing).toHaveBeenCalledWith(
        cartCollection,
        ...additionalCarts.map(expect.objectContaining)
      );
      expect(comp.cartsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const shipmentCart: IShipmentCart = { id: 456 };
      const cart: ICart = { id: 38727 };
      shipmentCart.cart = cart;

      activatedRoute.data = of({ shipmentCart });
      comp.ngOnInit();

      expect(comp.cartsSharedCollection).toContain(cart);
      expect(comp.shipmentCart).toEqual(shipmentCart);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipmentCart>>();
      const shipmentCart = { id: 123 };
      jest.spyOn(shipmentCartFormService, 'getShipmentCart').mockReturnValue(shipmentCart);
      jest.spyOn(shipmentCartService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipmentCart });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shipmentCart }));
      saveSubject.complete();

      // THEN
      expect(shipmentCartFormService.getShipmentCart).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(shipmentCartService.update).toHaveBeenCalledWith(expect.objectContaining(shipmentCart));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipmentCart>>();
      const shipmentCart = { id: 123 };
      jest.spyOn(shipmentCartFormService, 'getShipmentCart').mockReturnValue({ id: null });
      jest.spyOn(shipmentCartService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipmentCart: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: shipmentCart }));
      saveSubject.complete();

      // THEN
      expect(shipmentCartFormService.getShipmentCart).toHaveBeenCalled();
      expect(shipmentCartService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IShipmentCart>>();
      const shipmentCart = { id: 123 };
      jest.spyOn(shipmentCartService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ shipmentCart });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(shipmentCartService.update).toHaveBeenCalled();
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
