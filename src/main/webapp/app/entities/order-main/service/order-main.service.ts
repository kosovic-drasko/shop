import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrderMain, NewOrderMain } from '../order-main.model';

export type PartialUpdateOrderMain = Partial<IOrderMain> & Pick<IOrderMain, 'id'>;

type RestOf<T extends IOrderMain | NewOrderMain> = Omit<T, 'createTime' | 'updateTime'> & {
  createTime?: string | null;
  updateTime?: string | null;
};

export type RestOrderMain = RestOf<IOrderMain>;

export type NewRestOrderMain = RestOf<NewOrderMain>;

export type PartialUpdateRestOrderMain = RestOf<PartialUpdateOrderMain>;

export type EntityResponseType = HttpResponse<IOrderMain>;
export type EntityArrayResponseType = HttpResponse<IOrderMain[]>;

@Injectable({ providedIn: 'root' })
export class OrderMainService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/order-mains');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(orderMain: NewOrderMain): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderMain);
    return this.http
      .post<RestOrderMain>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(orderMain: IOrderMain): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderMain);
    return this.http
      .put<RestOrderMain>(`${this.resourceUrl}/${this.getOrderMainIdentifier(orderMain)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(orderMain: PartialUpdateOrderMain): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(orderMain);
    return this.http
      .patch<RestOrderMain>(`${this.resourceUrl}/${this.getOrderMainIdentifier(orderMain)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestOrderMain>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestOrderMain[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrderMainIdentifier(orderMain: Pick<IOrderMain, 'id'>): number {
    return orderMain.id;
  }

  compareOrderMain(o1: Pick<IOrderMain, 'id'> | null, o2: Pick<IOrderMain, 'id'> | null): boolean {
    return o1 && o2 ? this.getOrderMainIdentifier(o1) === this.getOrderMainIdentifier(o2) : o1 === o2;
  }

  addOrderMainToCollectionIfMissing<Type extends Pick<IOrderMain, 'id'>>(
    orderMainCollection: Type[],
    ...orderMainsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const orderMains: Type[] = orderMainsToCheck.filter(isPresent);
    if (orderMains.length > 0) {
      const orderMainCollectionIdentifiers = orderMainCollection.map(orderMainItem => this.getOrderMainIdentifier(orderMainItem)!);
      const orderMainsToAdd = orderMains.filter(orderMainItem => {
        const orderMainIdentifier = this.getOrderMainIdentifier(orderMainItem);
        if (orderMainCollectionIdentifiers.includes(orderMainIdentifier)) {
          return false;
        }
        orderMainCollectionIdentifiers.push(orderMainIdentifier);
        return true;
      });
      return [...orderMainsToAdd, ...orderMainCollection];
    }
    return orderMainCollection;
  }

  protected convertDateFromClient<T extends IOrderMain | NewOrderMain | PartialUpdateOrderMain>(orderMain: T): RestOf<T> {
    return {
      ...orderMain,
      createTime: orderMain.createTime?.toJSON() ?? null,
      updateTime: orderMain.updateTime?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restOrderMain: RestOrderMain): IOrderMain {
    return {
      ...restOrderMain,
      createTime: restOrderMain.createTime ? dayjs(restOrderMain.createTime) : undefined,
      updateTime: restOrderMain.updateTime ? dayjs(restOrderMain.updateTime) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestOrderMain>): HttpResponse<IOrderMain> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestOrderMain[]>): HttpResponse<IOrderMain[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
