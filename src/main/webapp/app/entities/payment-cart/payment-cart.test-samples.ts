import { PaymentStatusEnum } from 'app/entities/enumerations/payment-status-enum.model';

import { IPaymentCart, NewPaymentCart } from './payment-cart.model';

export const sampleWithRequiredData: IPaymentCart = {
  id: 92145,
  name: 'Human Product Chair',
  priceNet: 87209,
  vat: 35920,
  priceGross: 22743,
};

export const sampleWithPartialData: IPaymentCart = {
  id: 50451,
  name: 'Chicken Berkshire',
  priceNet: 79270,
  vat: 58764,
  priceGross: 73865,
  paymentStatusEnum: PaymentStatusEnum['WaitingForBankTransfer'],
};

export const sampleWithFullData: IPaymentCart = {
  id: 39678,
  name: 'Representative',
  priceNet: 72089,
  vat: 33567,
  priceGross: 34921,
  paymentStatusEnum: PaymentStatusEnum['PreparationForShipping'],
};

export const sampleWithNewData: NewPaymentCart = {
  name: 'Personal Ergonomic',
  priceNet: 62516,
  vat: 98817,
  priceGross: 92023,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
