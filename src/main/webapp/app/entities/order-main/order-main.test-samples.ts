import dayjs from 'dayjs/esm';

import { OrderMainStatusEnum } from 'app/entities/enumerations/order-main-status-enum.model';

import { IOrderMain, NewOrderMain } from './order-main.model';

export const sampleWithRequiredData: IOrderMain = {
  id: 19195,
};

export const sampleWithPartialData: IOrderMain = {
  id: 87670,
  buyerLastName: 'Norway systems Home',
  buyerEmail: 'deploy',
  amountOfCartGross: 12398,
  amountOfShipmentNet: 60676,
  amountOfShipmentGross: 53131,
  amountOfOrderNet: 17189,
  amountOfOrderGross: 6816,
  orderMainStatus: OrderMainStatusEnum['Canceled'],
  updateTime: dayjs('2023-03-29T06:24'),
};

export const sampleWithFullData: IOrderMain = {
  id: 80223,
  buyerLogin: 'Glens',
  buyerFirstName: 'Chips Garden interactive',
  buyerLastName: 'Lari state',
  buyerEmail: 'redundant Usability',
  buyerPhone: 'productize Metal',
  amountOfCartNet: 4193,
  amountOfCartGross: 37256,
  amountOfShipmentNet: 64162,
  amountOfShipmentGross: 8597,
  amountOfOrderNet: 72205,
  amountOfOrderGross: 60180,
  orderMainStatus: OrderMainStatusEnum['Sent'],
  createTime: dayjs('2023-03-29T14:48'),
  updateTime: dayjs('2023-03-29T14:06'),
};

export const sampleWithNewData: NewOrderMain = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
