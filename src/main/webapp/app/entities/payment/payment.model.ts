import dayjs from 'dayjs/esm';
import { PaymentStatusEnum } from 'app/entities/enumerations/payment-status-enum.model';

export interface IPayment {
  id: number;
  name?: string | null;
  priceNet?: number | null;
  vat?: number | null;
  priceGross?: number | null;
  paymentStatusEnum?: PaymentStatusEnum | null;
  createTime?: dayjs.Dayjs | null;
  updateTime?: dayjs.Dayjs | null;
}

export type NewPayment = Omit<IPayment, 'id'> & { id: null };
