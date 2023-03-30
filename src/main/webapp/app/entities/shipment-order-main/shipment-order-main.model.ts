import { IOrderMain } from 'app/entities/order-main/order-main.model';

export interface IShipmentOrderMain {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  street?: string | null;
  postalCode?: string | null;
  city?: string | null;
  country?: string | null;
  phoneToTheReceiver?: string | null;
  firm?: string | null;
  taxNumber?: string | null;
  orderMain?: Pick<IOrderMain, 'id'> | null;
}

export type NewShipmentOrderMain = Omit<IShipmentOrderMain, 'id'> & { id: null };
