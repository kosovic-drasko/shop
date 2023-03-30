import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaymentOrderMain, NewPaymentOrderMain } from '../payment-order-main.model';

export type PartialUpdatePaymentOrderMain = Partial<IPaymentOrderMain> & Pick<IPaymentOrderMain, 'id'>;

export type EntityResponseType = HttpResponse<IPaymentOrderMain>;
export type EntityArrayResponseType = HttpResponse<IPaymentOrderMain[]>;

@Injectable({ providedIn: 'root' })
export class PaymentOrderMainService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payment-order-mains');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(paymentOrderMain: NewPaymentOrderMain): Observable<EntityResponseType> {
    return this.http.post<IPaymentOrderMain>(this.resourceUrl, paymentOrderMain, { observe: 'response' });
  }

  update(paymentOrderMain: IPaymentOrderMain): Observable<EntityResponseType> {
    return this.http.put<IPaymentOrderMain>(
      `${this.resourceUrl}/${this.getPaymentOrderMainIdentifier(paymentOrderMain)}`,
      paymentOrderMain,
      { observe: 'response' }
    );
  }

  partialUpdate(paymentOrderMain: PartialUpdatePaymentOrderMain): Observable<EntityResponseType> {
    return this.http.patch<IPaymentOrderMain>(
      `${this.resourceUrl}/${this.getPaymentOrderMainIdentifier(paymentOrderMain)}`,
      paymentOrderMain,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaymentOrderMain>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentOrderMain[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPaymentOrderMainIdentifier(paymentOrderMain: Pick<IPaymentOrderMain, 'id'>): number {
    return paymentOrderMain.id;
  }

  comparePaymentOrderMain(o1: Pick<IPaymentOrderMain, 'id'> | null, o2: Pick<IPaymentOrderMain, 'id'> | null): boolean {
    return o1 && o2 ? this.getPaymentOrderMainIdentifier(o1) === this.getPaymentOrderMainIdentifier(o2) : o1 === o2;
  }

  addPaymentOrderMainToCollectionIfMissing<Type extends Pick<IPaymentOrderMain, 'id'>>(
    paymentOrderMainCollection: Type[],
    ...paymentOrderMainsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const paymentOrderMains: Type[] = paymentOrderMainsToCheck.filter(isPresent);
    if (paymentOrderMains.length > 0) {
      const paymentOrderMainCollectionIdentifiers = paymentOrderMainCollection.map(
        paymentOrderMainItem => this.getPaymentOrderMainIdentifier(paymentOrderMainItem)!
      );
      const paymentOrderMainsToAdd = paymentOrderMains.filter(paymentOrderMainItem => {
        const paymentOrderMainIdentifier = this.getPaymentOrderMainIdentifier(paymentOrderMainItem);
        if (paymentOrderMainCollectionIdentifiers.includes(paymentOrderMainIdentifier)) {
          return false;
        }
        paymentOrderMainCollectionIdentifiers.push(paymentOrderMainIdentifier);
        return true;
      });
      return [...paymentOrderMainsToAdd, ...paymentOrderMainCollection];
    }
    return paymentOrderMainCollection;
  }
}
