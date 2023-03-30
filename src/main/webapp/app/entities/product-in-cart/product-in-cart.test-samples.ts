import { IProductInCart, NewProductInCart } from './product-in-cart.model';

export const sampleWithRequiredData: IProductInCart = {
  id: 51501,
};

export const sampleWithPartialData: IProductInCart = {
  id: 3997,
  name: 'Sausages User-friendly Analyst',
  vat: 96782,
  priceGross: 40186,
  totalPriceNet: 1456,
  totalPriceGross: 68417,
};

export const sampleWithFullData: IProductInCart = {
  id: 95377,
  category: 'deploy cyan green',
  name: 'Implementation Dam deposit',
  quantity: 11738,
  priceNet: 68050,
  vat: 20104,
  priceGross: 25850,
  totalPriceNet: 32224,
  totalPriceGross: 81335,
  stock: 81195,
  description: '../fake-data/blob/hipster.txt',
  image: '../fake-data/blob/hipster.png',
  imageContentType: 'unknown',
  productId: 49972,
};

export const sampleWithNewData: NewProductInCart = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
