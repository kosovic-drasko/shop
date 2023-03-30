import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProba } from '../proba.model';
import { ProbaService } from '../service/proba.service';

@Injectable({ providedIn: 'root' })
export class ProbaRoutingResolveService implements Resolve<IProba | null> {
  constructor(protected service: ProbaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProba | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((proba: HttpResponse<IProba>) => {
          if (proba.body) {
            return of(proba.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
