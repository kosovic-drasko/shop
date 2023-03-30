import { IShipmentOrderMain, NewShipmentOrderMain } from './shipment-order-main.model';

export const sampleWithRequiredData: IShipmentOrderMain = {
  id: 52141,
  firstName: 'Erling',
  lastName: 'Anderson',
  street: 'Rogahn Centers',
  postalCode: 'Front-line',
  city: 'Palm Springs',
  country: 'Tuvalu',
  phoneToTheReceiver: 'Ukraine Avon',
};

export const sampleWithPartialData: IShipmentOrderMain = {
  id: 55178,
  firstName: 'Rolando',
  lastName: 'Gerlach',
  street: 'Dickinson Drive',
  postalCode: 'Frozen innovate',
  city: 'Hauckfort',
  country: 'Sao Tome and Principe',
  phoneToTheReceiver: 'multimedia Borders interactive',
};

export const sampleWithFullData: IShipmentOrderMain = {
  id: 9471,
  firstName: 'Flavio',
  lastName: 'Ward',
  street: 'Mitchell Prairie',
  postalCode: 'Architect capacitor ',
  city: 'Jefferson City',
  country: 'Romania',
  phoneToTheReceiver: 'modular Future-proofed',
  firm: 'withdrawal',
  taxNumber: 'SDD Panama Dynamic',
};

export const sampleWithNewData: NewShipmentOrderMain = {
  firstName: 'Rachelle',
  lastName: 'Waelchi',
  street: 'Rau Knoll',
  postalCode: 'Loan Marketing',
  city: 'Fort Pierce',
  country: 'Morocco',
  phoneToTheReceiver: 'transmitting',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
