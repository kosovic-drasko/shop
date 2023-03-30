import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProductInOrderMainService } from '../service/product-in-order-main.service';

import { ProductInOrderMainComponent } from './product-in-order-main.component';

describe('ProductInOrderMain Management Component', () => {
  let comp: ProductInOrderMainComponent;
  let fixture: ComponentFixture<ProductInOrderMainComponent>;
  let service: ProductInOrderMainService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'product-in-order-main', component: ProductInOrderMainComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [ProductInOrderMainComponent],
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
      .overrideTemplate(ProductInOrderMainComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductInOrderMainComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ProductInOrderMainService);

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
    expect(comp.productInOrderMains?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to productInOrderMainService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getProductInOrderMainIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getProductInOrderMainIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
