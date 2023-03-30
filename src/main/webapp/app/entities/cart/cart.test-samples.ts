import { ICart, NewCart } from './cart.model';

export const sampleWithRequiredData: ICart = {
  id: 50053,
  amountOfCartNet: 85678,
  amountOfCartGross: 91768,
  amountOfShipmentNet: 79348,
  amountOfShipmentGross: 97396,
  amountOfOrderNet: 2200,
  amountOfOrderGross: 12705,
};

export const sampleWithPartialData: ICart = {
  id: 57535,
  amountOfCartNet: 76027,
  amountOfCartGross: 77073,
  amountOfShipmentNet: 11346,
  amountOfShipmentGross: 64900,
  amountOfOrderNet: 95950,
  amountOfOrderGross: 79008,
};

export const sampleWithFullData: ICart = {
  id: 40652,
  amountOfCartNet: 97080,
  amountOfCartGross: 54586,
  amountOfShipmentNet: 32173,
  amountOfShipmentGross: 46460,
  amountOfOrderNet: 67085,
  amountOfOrderGross: 37959,
};

export const sampleWithNewData: NewCart = {
  amountOfCartNet: 29108,
  amountOfCartGross: 78777,
  amountOfShipmentNet: 97600,
  amountOfShipmentGross: 31328,
  amountOfOrderNet: 68098,
  amountOfOrderGross: 13184,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
