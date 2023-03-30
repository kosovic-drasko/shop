import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IShipmentCart, NewShipmentCart } from '../shipment-cart.model';

export type PartialUpdateShipmentCart = Partial<IShipmentCart> & Pick<IShipmentCart, 'id'>;

export type EntityResponseType = HttpResponse<IShipmentCart>;
export type EntityArrayResponseType = HttpResponse<IShipmentCart[]>;

@Injectable({ providedIn: 'root' })
export class ShipmentCartService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/shipment-carts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(shipmentCart: NewShipmentCart): Observable<EntityResponseType> {
    return this.http.post<IShipmentCart>(this.resourceUrl, shipmentCart, { observe: 'response' });
  }

  update(shipmentCart: IShipmentCart): Observable<EntityResponseType> {
    return this.http.put<IShipmentCart>(`${this.resourceUrl}/${this.getShipmentCartIdentifier(shipmentCart)}`, shipmentCart, {
      observe: 'response',
    });
  }

  partialUpdate(shipmentCart: PartialUpdateShipmentCart): Observable<EntityResponseType> {
    return this.http.patch<IShipmentCart>(`${this.resourceUrl}/${this.getShipmentCartIdentifier(shipmentCart)}`, shipmentCart, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IShipmentCart>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShipmentCart[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getShipmentCartIdentifier(shipmentCart: Pick<IShipmentCart, 'id'>): number {
    return shipmentCart.id;
  }

  compareShipmentCart(o1: Pick<IShipmentCart, 'id'> | null, o2: Pick<IShipmentCart, 'id'> | null): boolean {
    return o1 && o2 ? this.getShipmentCartIdentifier(o1) === this.getShipmentCartIdentifier(o2) : o1 === o2;
  }

  addShipmentCartToCollectionIfMissing<Type extends Pick<IShipmentCart, 'id'>>(
    shipmentCartCollection: Type[],
    ...shipmentCartsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const shipmentCarts: Type[] = shipmentCartsToCheck.filter(isPresent);
    if (shipmentCarts.length > 0) {
      const shipmentCartCollectionIdentifiers = shipmentCartCollection.map(
        shipmentCartItem => this.getShipmentCartIdentifier(shipmentCartItem)!
      );
      const shipmentCartsToAdd = shipmentCarts.filter(shipmentCartItem => {
        const shipmentCartIdentifier = this.getShipmentCartIdentifier(shipmentCartItem);
        if (shipmentCartCollectionIdentifiers.includes(shipmentCartIdentifier)) {
          return false;
        }
        shipmentCartCollectionIdentifiers.push(shipmentCartIdentifier);
        return true;
      });
      return [...shipmentCartsToAdd, ...shipmentCartCollection];
    }
    return shipmentCartCollection;
  }
}
