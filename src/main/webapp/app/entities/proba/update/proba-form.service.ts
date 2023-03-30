import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProba, NewProba } from '../proba.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProba for edit and NewProbaFormGroupInput for create.
 */
type ProbaFormGroupInput = IProba | PartialWithRequiredKeyOf<NewProba>;

type ProbaFormDefaults = Pick<NewProba, 'id'>;

type ProbaFormGroupContent = {
  id: FormControl<IProba['id'] | NewProba['id']>;
  naziv: FormControl<IProba['naziv']>;
};

export type ProbaFormGroup = FormGroup<ProbaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProbaFormService {
  createProbaFormGroup(proba: ProbaFormGroupInput = { id: null }): ProbaFormGroup {
    const probaRawValue = {
      ...this.getFormDefaults(),
      ...proba,
    };
    return new FormGroup<ProbaFormGroupContent>({
      id: new FormControl(
        { value: probaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      naziv: new FormControl(probaRawValue.naziv),
    });
  }

  getProba(form: ProbaFormGroup): IProba | NewProba {
    return form.getRawValue() as IProba | NewProba;
  }

  resetForm(form: ProbaFormGroup, proba: ProbaFormGroupInput): void {
    const probaRawValue = { ...this.getFormDefaults(), ...proba };
    form.reset(
      {
        ...probaRawValue,
        id: { value: probaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProbaFormDefaults {
    return {
      id: null,
    };
  }
}
