import { ICart } from 'app/entities/cart/cart.model';
import { PaymentStatusEnum } from 'app/entities/enumerations/payment-status-enum.model';

export interface IPaymentCart {
  id: number;
  name?: string | null;
  priceNet?: number | null;
  vat?: number | null;
  priceGross?: number | null;
  paymentStatusEnum?: PaymentStatusEnum | null;
  cart?: Pick<ICart, 'id'> | null;
}

export type NewPaymentCart = Omit<IPaymentCart, 'id'> & { id: null };
