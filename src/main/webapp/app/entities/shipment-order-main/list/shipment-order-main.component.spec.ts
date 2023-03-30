import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ShipmentOrderMainService } from '../service/shipment-order-main.service';

import { ShipmentOrderMainComponent } from './shipment-order-main.component';

describe('ShipmentOrderMain Management Component', () => {
  let comp: ShipmentOrderMainComponent;
  let fixture: ComponentFixture<ShipmentOrderMainComponent>;
  let service: ShipmentOrderMainService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'shipment-order-main', component: ShipmentOrderMainComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [ShipmentOrderMainComponent],
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
      .overrideTemplate(ShipmentOrderMainComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ShipmentOrderMainComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ShipmentOrderMainService);

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
    expect(comp.shipmentOrderMains?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to shipmentOrderMainService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getShipmentOrderMainIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getShipmentOrderMainIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
