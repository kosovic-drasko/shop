import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { OrderMainService } from '../service/order-main.service';

import { OrderMainComponent } from './order-main.component';

describe('OrderMain Management Component', () => {
  let comp: OrderMainComponent;
  let fixture: ComponentFixture<OrderMainComponent>;
  let service: OrderMainService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'order-main', component: OrderMainComponent }]), HttpClientTestingModule],
      declarations: [OrderMainComponent],
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
      .overrideTemplate(OrderMainComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(OrderMainComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(OrderMainService);

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
    expect(comp.orderMains?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to orderMainService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getOrderMainIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getOrderMainIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
