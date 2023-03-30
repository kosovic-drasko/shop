import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IShipmentOrderMain, NewShipmentOrderMain } from '../shipment-order-main.model';

export type PartialUpdateShipmentOrderMain = Partial<IShipmentOrderMain> & Pick<IShipmentOrderMain, 'id'>;

export type EntityResponseType = HttpResponse<IShipmentOrderMain>;
export type EntityArrayResponseType = HttpResponse<IShipmentOrderMain[]>;

@Injectable({ providedIn: 'root' })
export class ShipmentOrderMainService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/shipment-order-mains');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(shipmentOrderMain: NewShipmentOrderMain): Observable<EntityResponseType> {
    return this.http.post<IShipmentOrderMain>(this.resourceUrl, shipmentOrderMain, { observe: 'response' });
  }

  update(shipmentOrderMain: IShipmentOrderMain): Observable<EntityResponseType> {
    return this.http.put<IShipmentOrderMain>(
      `${this.resourceUrl}/${this.getShipmentOrderMainIdentifier(shipmentOrderMain)}`,
      shipmentOrderMain,
      { observe: 'response' }
    );
  }

  partialUpdate(shipmentOrderMain: PartialUpdateShipmentOrderMain): Observable<EntityResponseType> {
    return this.http.patch<IShipmentOrderMain>(
      `${this.resourceUrl}/${this.getShipmentOrderMainIdentifier(shipmentOrderMain)}`,
      shipmentOrderMain,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IShipmentOrderMain>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShipmentOrderMain[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getShipmentOrderMainIdentifier(shipmentOrderMain: Pick<IShipmentOrderMain, 'id'>): number {
    return shipmentOrderMain.id;
  }

  compareShipmentOrderMain(o1: Pick<IShipmentOrderMain, 'id'> | null, o2: Pick<IShipmentOrderMain, 'id'> | null): boolean {
    return o1 && o2 ? this.getShipmentOrderMainIdentifier(o1) === this.getShipmentOrderMainIdentifier(o2) : o1 === o2;
  }

  addShipmentOrderMainToCollectionIfMissing<Type extends Pick<IShipmentOrderMain, 'id'>>(
    shipmentOrderMainCollection: Type[],
    ...shipmentOrderMainsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const shipmentOrderMains: Type[] = shipmentOrderMainsToCheck.filter(isPresent);
    if (shipmentOrderMains.length > 0) {
      const shipmentOrderMainCollectionIdentifiers = shipmentOrderMainCollection.map(
        shipmentOrderMainItem => this.getShipmentOrderMainIdentifier(shipmentOrderMainItem)!
      );
      const shipmentOrderMainsToAdd = shipmentOrderMains.filter(shipmentOrderMainItem => {
        const shipmentOrderMainIdentifier = this.getShipmentOrderMainIdentifier(shipmentOrderMainItem);
        if (shipmentOrderMainCollectionIdentifiers.includes(shipmentOrderMainIdentifier)) {
          return false;
        }
        shipmentOrderMainCollectionIdentifiers.push(shipmentOrderMainIdentifier);
        return true;
      });
      return [...shipmentOrderMainsToAdd, ...shipmentOrderMainCollection];
    }
    return shipmentOrderMainCollection;
  }
}
