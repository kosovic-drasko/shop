import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProba, NewProba } from '../proba.model';

export type PartialUpdateProba = Partial<IProba> & Pick<IProba, 'id'>;

export type EntityResponseType = HttpResponse<IProba>;
export type EntityArrayResponseType = HttpResponse<IProba[]>;

@Injectable({ providedIn: 'root' })
export class ProbaService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/probas');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(proba: NewProba): Observable<EntityResponseType> {
    return this.http.post<IProba>(this.resourceUrl, proba, { observe: 'response' });
  }

  update(proba: IProba): Observable<EntityResponseType> {
    return this.http.put<IProba>(`${this.resourceUrl}/${this.getProbaIdentifier(proba)}`, proba, { observe: 'response' });
  }

  partialUpdate(proba: PartialUpdateProba): Observable<EntityResponseType> {
    return this.http.patch<IProba>(`${this.resourceUrl}/${this.getProbaIdentifier(proba)}`, proba, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProba>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProba[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProbaIdentifier(proba: Pick<IProba, 'id'>): number {
    return proba.id;
  }

  compareProba(o1: Pick<IProba, 'id'> | null, o2: Pick<IProba, 'id'> | null): boolean {
    return o1 && o2 ? this.getProbaIdentifier(o1) === this.getProbaIdentifier(o2) : o1 === o2;
  }

  addProbaToCollectionIfMissing<Type extends Pick<IProba, 'id'>>(
    probaCollection: Type[],
    ...probasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const probas: Type[] = probasToCheck.filter(isPresent);
    if (probas.length > 0) {
      const probaCollectionIdentifiers = probaCollection.map(probaItem => this.getProbaIdentifier(probaItem)!);
      const probasToAdd = probas.filter(probaItem => {
        const probaIdentifier = this.getProbaIdentifier(probaItem);
        if (probaCollectionIdentifiers.includes(probaIdentifier)) {
          return false;
        }
        probaCollectionIdentifiers.push(probaIdentifier);
        return true;
      });
      return [...probasToAdd, ...probaCollection];
    }
    return probaCollection;
  }
}
