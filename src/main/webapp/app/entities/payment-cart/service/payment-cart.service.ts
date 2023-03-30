import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaymentCart, NewPaymentCart } from '../payment-cart.model';

export type PartialUpdatePaymentCart = Partial<IPaymentCart> & Pick<IPaymentCart, 'id'>;

export type EntityResponseType = HttpResponse<IPaymentCart>;
export type EntityArrayResponseType = HttpResponse<IPaymentCart[]>;

@Injectable({ providedIn: 'root' })
export class PaymentCartService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payment-carts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(paymentCart: NewPaymentCart): Observable<EntityResponseType> {
    return this.http.post<IPaymentCart>(this.resourceUrl, paymentCart, { observe: 'response' });
  }

  update(paymentCart: IPaymentCart): Observable<EntityResponseType> {
    return this.http.put<IPaymentCart>(`${this.resourceUrl}/${this.getPaymentCartIdentifier(paymentCart)}`, paymentCart, {
      observe: 'response',
    });
  }

  partialUpdate(paymentCart: PartialUpdatePaymentCart): Observable<EntityResponseType> {
    return this.http.patch<IPaymentCart>(`${this.resourceUrl}/${this.getPaymentCartIdentifier(paymentCart)}`, paymentCart, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentCart>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentCart[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPaymentCartIdentifier(paymentCart: Pick<IPaymentCart, 'id'>): number {
    return paymentCart.id;
  }

  comparePaymentCart(o1: Pick<IPaymentCart, 'id'> | null, o2: Pick<IPaymentCart, 'id'> | null): boolean {
    return o1 && o2 ? this.getPaymentCartIdentifier(o1) === this.getPaymentCartIdentifier(o2) : o1 === o2;
  }

  addPaymentCartToCollectionIfMissing<Type extends Pick<IPaymentCart, 'id'>>(
    paymentCartCollection: Type[],
    ...paymentCartsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const paymentCarts: Type[] = paymentCartsToCheck.filter(isPresent);
    if (paymentCarts.length > 0) {
      const paymentCartCollectionIdentifiers = paymentCartCollection.map(
        paymentCartItem => this.getPaymentCartIdentifier(paymentCartItem)!
      );
      const paymentCartsToAdd = paymentCarts.filter(paymentCartItem => {
        const paymentCartIdentifier = this.getPaymentCartIdentifier(paymentCartItem);
        if (paymentCartCollectionIdentifiers.includes(paymentCartIdentifier)) {
          return false;
        }
        paymentCartCollectionIdentifiers.push(paymentCartIdentifier);
        return true;
      });
      return [...paymentCartsToAdd, ...paymentCartCollection];
    }
    return paymentCartCollection;
  }
}
