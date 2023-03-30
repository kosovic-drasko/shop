import dayjs from 'dayjs/esm';

import { ProductCategoryEnum } from 'app/entities/enumerations/product-category-enum.model';

import { IProduct, NewProduct } from './product.model';

export const sampleWithRequiredData: IProduct = {
  id: 77672,
  productCategoryEnum: ProductCategoryEnum['Vitamins'],
  name: 'Fresh',
  priceNet: 785602,
  vat: 38,
  stock: 943627,
  description: '../fake-data/blob/hipster.txt',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
};

export const sampleWithPartialData: IProduct = {
  id: 12835,
  productCategoryEnum: ProductCategoryEnum['Vitamins'],
  name: 'Interface Table',
  priceNet: 3045,
  vat: 48,
  priceGross: 82094,
  stock: 769058,
  description: '../fake-data/blob/hipster.txt',
  updateTime: dayjs('2023-03-29T13:51'),
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
};

export const sampleWithFullData: IProduct = {
  id: 85387,
  productCategoryEnum: ProductCategoryEnum['Minerals'],
  name: 'Account experiences Garden',
  quantity: 22675,
  priceNet: 133726,
  vat: 91,
  priceGross: 58283,
  stock: 761632,
  description: '../fake-data/blob/hipster.txt',
  createTime: dayjs('2023-03-29T09:07'),
  updateTime: dayjs('2023-03-30T02:39'),
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
};

export const sampleWithNewData: NewProduct = {
  productCategoryEnum: ProductCategoryEnum['Vitamins'],
  name: 'Incredible',
  priceNet: 991520,
  vat: 91,
  stock: 190600,
  description: '../fake-data/blob/hipster.txt',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
