import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ProbaFormService, ProbaFormGroup } from './proba-form.service';
import { IProba } from '../proba.model';
import { ProbaService } from '../service/proba.service';

@Component({
  selector: 'jhi-proba-update',
  templateUrl: './proba-update.component.html',
})
export class ProbaUpdateComponent implements OnInit {
  isSaving = false;
  proba: IProba | null = null;

  editForm: ProbaFormGroup = this.probaFormService.createProbaFormGroup();

  constructor(
    protected probaService: ProbaService,
    protected probaFormService: ProbaFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proba }) => {
      this.proba = proba;
      if (proba) {
        this.updateForm(proba);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const proba = this.probaFormService.getProba(this.editForm);
    if (proba.id !== null) {
      this.subscribeToSaveResponse(this.probaService.update(proba));
    } else {
      this.subscribeToSaveResponse(this.probaService.create(proba));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProba>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(proba: IProba): void {
    this.proba = proba;
    this.probaFormService.resetForm(this.editForm, proba);
  }
}
