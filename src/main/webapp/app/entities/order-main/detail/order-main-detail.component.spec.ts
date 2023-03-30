import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrderMainDetailComponent } from './order-main-detail.component';

describe('OrderMain Management Detail Component', () => {
  let comp: OrderMainDetailComponent;
  let fixture: ComponentFixture<OrderMainDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OrderMainDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ orderMain: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(OrderMainDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(OrderMainDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load orderMain on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.orderMain).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
