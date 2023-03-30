import { ICart } from 'app/entities/cart/cart.model';

export interface IShipmentCart {
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
  cart?: Pick<ICart, 'id'> | null;
}

export type NewShipmentCart = Omit<IShipmentCart, 'id'> & { id: null };
