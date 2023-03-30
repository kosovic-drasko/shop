import { IOrderMain } from 'app/entities/order-main/order-main.model';

export interface IPaymentOrderMain {
  id: number;
  name?: string | null;
  priceNet?: number | null;
  vat?: number | null;
  priceGross?: number | null;
  orderMain?: Pick<IOrderMain, 'id'> | null;
}

export type NewPaymentOrderMain = Omit<IPaymentOrderMain, 'id'> & { id: null };
