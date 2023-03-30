import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProbaFormService } from './proba-form.service';
import { ProbaService } from '../service/proba.service';
import { IProba } from '../proba.model';

import { ProbaUpdateComponent } from './proba-update.component';

describe('Proba Management Update Component', () => {
  let comp: ProbaUpdateComponent;
  let fixture: ComponentFixture<ProbaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let probaFormService: ProbaFormService;
  let probaService: ProbaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProbaUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProbaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProbaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    probaFormService = TestBed.inject(ProbaFormService);
    probaService = TestBed.inject(ProbaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const proba: IProba = { id: 456 };

      activatedRoute.data = of({ proba });
      comp.ngOnInit();

      expect(comp.proba).toEqual(proba);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProba>>();
      const proba = { id: 123 };
      jest.spyOn(probaFormService, 'getProba').mockReturnValue(proba);
      jest.spyOn(probaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ proba });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: proba }));
      saveSubject.complete();

      // THEN
      expect(probaFormService.getProba).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(probaService.update).toHaveBeenCalledWith(expect.objectContaining(proba));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProba>>();
      const proba = { id: 123 };
      jest.spyOn(probaFormService, 'getProba').mockReturnValue({ id: null });
      jest.spyOn(probaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ proba: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: proba }));
      saveSubject.complete();

      // THEN
      expect(probaFormService.getProba).toHaveBeenCalled();
      expect(probaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProba>>();
      const proba = { id: 123 };
      jest.spyOn(probaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ proba });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(probaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
