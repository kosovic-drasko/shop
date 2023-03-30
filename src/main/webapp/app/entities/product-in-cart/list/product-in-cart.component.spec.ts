import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProductInCartService } from '../service/product-in-cart.service';

import { ProductInCartComponent } from './product-in-cart.component';

describe('ProductInCart Management Component', () => {
  let comp: ProductInCartComponent;
  let fixture: ComponentFixture<ProductInCartComponent>;
  let service: ProductInCartService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'product-in-cart', component: ProductInCartComponent }]), HttpClientTestingModule],
      declarations: [ProductInCartComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(ProductInCartComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductInCartComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ProductInCartService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.productInCarts?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to productInCartService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getProductInCartIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getProductInCartIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
