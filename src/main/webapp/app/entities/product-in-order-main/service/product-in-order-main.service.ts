import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductInOrderMain, NewProductInOrderMain } from '../product-in-order-main.model';

export type PartialUpdateProductInOrderMain = Partial<IProductInOrderMain> & Pick<IProductInOrderMain, 'id'>;

export type EntityResponseType = HttpResponse<IProductInOrderMain>;
export type EntityArrayResponseType = HttpResponse<IProductInOrderMain[]>;

@Injectable({ providedIn: 'root' })
export class ProductInOrderMainService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/product-in-order-mains');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(productInOrderMain: NewProductInOrderMain): Observable<EntityResponseType> {
    return this.http.post<IProductInOrderMain>(this.resourceUrl, productInOrderMain, { observe: 'response' });
  }

  update(productInOrderMain: IProductInOrderMain): Observable<EntityResponseType> {
    return this.http.put<IProductInOrderMain>(
      `${this.resourceUrl}/${this.getProductInOrderMainIdentifier(productInOrderMain)}`,
      productInOrderMain,
      { observe: 'response' }
    );
  }

  partialUpdate(productInOrderMain: PartialUpdateProductInOrderMain): Observable<EntityResponseType> {
    return this.http.patch<IProductInOrderMain>(
      `${this.resourceUrl}/${this.getProductInOrderMainIdentifier(productInOrderMain)}`,
      productInOrderMain,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductInOrderMain>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductInOrderMain[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProductInOrderMainIdentifier(productInOrderMain: Pick<IProductInOrderMain, 'id'>): number {
    return productInOrderMain.id;
  }

  compareProductInOrderMain(o1: Pick<IProductInOrderMain, 'id'> | null, o2: Pick<IProductInOrderMain, 'id'> | null): boolean {
    return o1 && o2 ? this.getProductInOrderMainIdentifier(o1) === this.getProductInOrderMainIdentifier(o2) : o1 === o2;
  }

  addProductInOrderMainToCollectionIfMissing<Type extends Pick<IProductInOrderMain, 'id'>>(
    productInOrderMainCollection: Type[],
    ...productInOrderMainsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const productInOrderMains: Type[] = productInOrderMainsToCheck.filter(isPresent);
    if (productInOrderMains.length > 0) {
      const productInOrderMainCollectionIdentifiers = productInOrderMainCollection.map(
        productInOrderMainItem => this.getProductInOrderMainIdentifier(productInOrderMainItem)!
      );
      const productInOrderMainsToAdd = productInOrderMains.filter(productInOrderMainItem => {
        const productInOrderMainIdentifier = this.getProductInOrderMainIdentifier(productInOrderMainItem);
        if (productInOrderMainCollectionIdentifiers.includes(productInOrderMainIdentifier)) {
          return false;
        }
        productInOrderMainCollectionIdentifiers.push(productInOrderMainIdentifier);
        return true;
      });
      return [...productInOrderMainsToAdd, ...productInOrderMainCollection];
    }
    return productInOrderMainCollection;
  }
}
