import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../proba.test-samples';

import { ProbaFormService } from './proba-form.service';

describe('Proba Form Service', () => {
  let service: ProbaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProbaFormService);
  });

  describe('Service methods', () => {
    describe('createProbaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProbaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            naziv: expect.any(Object),
          })
        );
      });

      it('passing IProba should create a new form with FormGroup', () => {
        const formGroup = service.createProbaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            naziv: expect.any(Object),
          })
        );
      });
    });

    describe('getProba', () => {
      it('should return NewProba for default Proba initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createProbaFormGroup(sampleWithNewData);

        const proba = service.getProba(formGroup) as any;

        expect(proba).toMatchObject(sampleWithNewData);
      });

      it('should return NewProba for empty Proba initial value', () => {
        const formGroup = service.createProbaFormGroup();

        const proba = service.getProba(formGroup) as any;

        expect(proba).toMatchObject({});
      });

      it('should return IProba', () => {
        const formGroup = service.createProbaFormGroup(sampleWithRequiredData);

        const proba = service.getProba(formGroup) as any;

        expect(proba).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProba should not enable id FormControl', () => {
        const formGroup = service.createProbaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProba should disable id FormControl', () => {
        const formGroup = service.createProbaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
