import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaymentCartDetailComponent } from './payment-cart-detail.component';

describe('PaymentCart Management Detail Component', () => {
  let comp: PaymentCartDetailComponent;
  let fixture: ComponentFixture<PaymentCartDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaymentCartDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ paymentCart: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PaymentCartDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PaymentCartDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load paymentCart on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.paymentCart).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
