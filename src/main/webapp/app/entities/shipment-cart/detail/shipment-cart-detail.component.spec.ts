import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShipmentCartDetailComponent } from './shipment-cart-detail.component';

describe('ShipmentCart Management Detail Component', () => {
  let comp: ShipmentCartDetailComponent;
  let fixture: ComponentFixture<ShipmentCartDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ShipmentCartDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ shipmentCart: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ShipmentCartDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ShipmentCartDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load shipmentCart on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.shipmentCart).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
