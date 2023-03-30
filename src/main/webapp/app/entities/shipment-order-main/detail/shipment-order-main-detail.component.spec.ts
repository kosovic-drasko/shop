import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShipmentOrderMainDetailComponent } from './shipment-order-main-detail.component';

describe('ShipmentOrderMain Management Detail Component', () => {
  let comp: ShipmentOrderMainDetailComponent;
  let fixture: ComponentFixture<ShipmentOrderMainDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShipmentOrderMainDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ shipmentOrderMain: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ShipmentOrderMainDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ShipmentOrderMainDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load shipmentOrderMain on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.shipmentOrderMain).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
