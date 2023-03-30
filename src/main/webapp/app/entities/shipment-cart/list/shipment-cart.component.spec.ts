import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ShipmentCartService } from '../service/shipment-cart.service';

import { ShipmentCartComponent } from './shipment-cart.component';

describe('ShipmentCart Management Component', () => {
  let comp: ShipmentCartComponent;
  let fixture: ComponentFixture<ShipmentCartComponent>;
  let service: ShipmentCartService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'shipment-cart', component: ShipmentCartComponent }]), HttpClientTestingModule],
      declarations: [ShipmentCartComponent],
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
      .overrideTemplate(ShipmentCartComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ShipmentCartComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ShipmentCartService);

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
    expect(comp.shipmentCarts?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to shipmentCartService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getShipmentCartIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getShipmentCartIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
