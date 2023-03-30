import dayjs from 'dayjs/esm';

import { PaymentStatusEnum } from 'app/entities/enumerations/payment-status-enum.model';

import { IPayment, NewPayment } from './payment.model';

export const sampleWithRequiredData: IPayment = {
  id: 47537,
  name: 'connect RSS eco-centric',
  priceNet: 6380,
  vat: 44,
};

export const sampleWithPartialData: IPayment = {
  id: 77766,
  name: 'Cotton',
  priceNet: 9107,
  vat: 71,
  priceGross: 18283,
  paymentStatusEnum: PaymentStatusEnum['PreparationForShipping'],
  createTime: dayjs('2023-03-29T21:22'),
};

export const sampleWithFullData: IPayment = {
  id: 16557,
  name: 'methodical Serbian generating',
  priceNet: 5202,
  vat: 61,
  priceGross: 72474,
  paymentStatusEnum: PaymentStatusEnum['PreparationForShipping'],
  createTime: dayjs('2023-03-30T00:38'),
  updateTime: dayjs('2023-03-30T00:34'),
};

export const sampleWithNewData: NewPayment = {
  name: 'Principal infomediaries',
  priceNet: 8588,
  vat: 46,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
