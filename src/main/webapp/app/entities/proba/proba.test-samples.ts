import { IProba, NewProba } from './proba.model';

export const sampleWithRequiredData: IProba = {
  id: 74957,
};

export const sampleWithPartialData: IProba = {
  id: 66585,
  naziv: 'SQL',
};

export const sampleWithFullData: IProba = {
  id: 12185,
  naziv: 'Principal Orchestrator',
};

export const sampleWithNewData: NewProba = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
