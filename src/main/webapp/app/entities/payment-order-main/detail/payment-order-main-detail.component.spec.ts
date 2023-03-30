import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaymentOrderMainDetailComponent } from './payment-order-main-detail.component';

describe('PaymentOrderMain Management Detail Component', () => {
  let comp: PaymentOrderMainDetailComponent;
  let fixture: ComponentFixture<PaymentOrderMainDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaymentOrderMainDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ paymentOrderMain: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PaymentOrderMainDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PaymentOrderMainDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load paymentOrderMain on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.paymentOrderMain).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
