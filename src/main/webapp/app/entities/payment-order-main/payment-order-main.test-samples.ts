import { IPaymentOrderMain, NewPaymentOrderMain } from './payment-order-main.model';

export const sampleWithRequiredData: IPaymentOrderMain = {
  id: 34213,
  name: 'Market',
  priceNet: 75909,
  vat: 1650,
  priceGross: 91033,
};

export const sampleWithPartialData: IPaymentOrderMain = {
  id: 31989,
  name: 'Rupiah',
  priceNet: 96721,
  vat: 96981,
  priceGross: 82691,
};

export const sampleWithFullData: IPaymentOrderMain = {
  id: 833,
  name: 'convergence Stravenue Moldovan',
  priceNet: 37307,
  vat: 86051,
  priceGross: 34114,
};

export const sampleWithNewData: NewPaymentOrderMain = {
  name: 'moderator Agent',
  priceNet: 34312,
  vat: 45285,
  priceGross: 28,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
