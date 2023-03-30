import { IShipmentCart, NewShipmentCart } from './shipment-cart.model';

export const sampleWithRequiredData: IShipmentCart = {
  id: 19762,
  firstName: 'Jarod',
  lastName: 'Daniel',
  street: 'Stacy Locks',
  postalCode: 'fuchsia invoice',
  city: 'North Lizaview',
  country: 'Thailand',
  phoneToTheReceiver: 'intuitive structure Row',
};

export const sampleWithPartialData: IShipmentCart = {
  id: 64436,
  firstName: 'Clementine',
  lastName: 'Harvey',
  street: 'Leonora Overpass',
  postalCode: 'mint',
  city: 'Port Aron',
  country: 'Marshall Islands',
  phoneToTheReceiver: 'Computer',
  firm: 'Coordinator',
};

export const sampleWithFullData: IShipmentCart = {
  id: 35534,
  firstName: 'Gay',
  lastName: 'Bauch',
  street: 'Toni Square',
  postalCode: 'Fantastic New',
  city: 'Kohlerfurt',
  country: 'Bahamas',
  phoneToTheReceiver: 'Account USB',
  firm: 'discrete',
  taxNumber: 'Fundamental alarm payment',
};

export const sampleWithNewData: NewShipmentCart = {
  firstName: 'Weston',
  lastName: 'Quitzon',
  street: 'Veum Mews',
  postalCode: 'Romania feed Fresh',
  city: 'Escondido',
  country: 'Mozambique',
  phoneToTheReceiver: 'access ivory',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
