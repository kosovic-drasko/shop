import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductInCart, NewProductInCart } from '../product-in-cart.model';

export type PartialUpdateProductInCart = Partial<IProductInCart> & Pick<IProductInCart, 'id'>;

export type EntityResponseType = HttpResponse<IProductInCart>;
export type EntityArrayResponseType = HttpResponse<IProductInCart[]>;

@Injectable({ providedIn: 'root' })
export class ProductInCartService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/product-in-carts');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(productInCart: NewProductInCart): Observable<EntityResponseType> {
    return this.http.post<IProductInCart>(this.resourceUrl, productInCart, { observe: 'response' });
  }

  update(productInCart: IProductInCart): Observable<EntityResponseType> {
    return this.http.put<IProductInCart>(`${this.resourceUrl}/${this.getProductInCartIdentifier(productInCart)}`, productInCart, {
      observe: 'response',
    });
  }

  partialUpdate(productInCart: PartialUpdateProductInCart): Observable<EntityResponseType> {
    return this.http.patch<IProductInCart>(`${this.resourceUrl}/${this.getProductInCartIdentifier(productInCart)}`, productInCart, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductInCart>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductInCart[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProductInCartIdentifier(productInCart: Pick<IProductInCart, 'id'>): number {
    return productInCart.id;
  }

  compareProductInCart(o1: Pick<IProductInCart, 'id'> | null, o2: Pick<IProductInCart, 'id'> | null): boolean {
    return o1 && o2 ? this.getProductInCartIdentifier(o1) === this.getProductInCartIdentifier(o2) : o1 === o2;
  }

  addProductInCartToCollectionIfMissing<Type extends Pick<IProductInCart, 'id'>>(
    productInCartCollection: Type[],
    ...productInCartsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const productInCarts: Type[] = productInCartsToCheck.filter(isPresent);
    if (productInCarts.length > 0) {
      const productInCartCollectionIdentifiers = productInCartCollection.map(
        productInCartItem => this.getProductInCartIdentifier(productInCartItem)!
      );
      const productInCartsToAdd = productInCarts.filter(productInCartItem => {
        const productInCartIdentifier = this.getProductInCartIdentifier(productInCartItem);
        if (productInCartCollectionIdentifiers.includes(productInCartIdentifier)) {
          return false;
        }
        productInCartCollectionIdentifiers.push(productInCartIdentifier);
        return true;
      });
      return [...productInCartsToAdd, ...productInCartCollection];
    }
    return productInCartCollection;
  }
}
