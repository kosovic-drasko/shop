import { IProductInOrderMain, NewProductInOrderMain } from './product-in-order-main.model';

export const sampleWithRequiredData: IProductInOrderMain = {
  id: 92858,
};

export const sampleWithPartialData: IProductInOrderMain = {
  id: 12498,
  name: 'Ukraine',
  priceNet: 74609,
  vat: 35369,
  totalPriceNet: 46140,
  totalPriceGross: 32168,
  stock: 78614,
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
};

export const sampleWithFullData: IProductInOrderMain = {
  id: 75068,
  category: 'synergies Handcrafted throughput',
  name: 'Maryland Music',
  quantity: 2054,
  priceNet: 59650,
  vat: 32422,
  priceGross: 36811,
  totalPriceNet: 96755,
  totalPriceGross: 85947,
  stock: 50310,
  description: '../fake-data/blob/hipster.txt',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
  productId: 7543,
};

export const sampleWithNewData: NewProductInOrderMain = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
