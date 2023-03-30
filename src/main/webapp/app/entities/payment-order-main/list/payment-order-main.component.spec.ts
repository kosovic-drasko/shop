import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { PaymentOrderMainService } from '../service/payment-order-main.service';

import { PaymentOrderMainComponent } from './payment-order-main.component';

describe('PaymentOrderMain Management Component', () => {
  let comp: PaymentOrderMainComponent;
  let fixture: ComponentFixture<PaymentOrderMainComponent>;
  let service: PaymentOrderMainService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'payment-order-main', component: PaymentOrderMainComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [PaymentOrderMainComponent],
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
      .overrideTemplate(PaymentOrderMainComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentOrderMainComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PaymentOrderMainService);

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
    expect(comp.paymentOrderMains?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to paymentOrderMainService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getPaymentOrderMainIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getPaymentOrderMainIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
