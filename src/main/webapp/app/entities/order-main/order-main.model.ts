import dayjs from 'dayjs/esm';
import { OrderMainStatusEnum } from 'app/entities/enumerations/order-main-status-enum.model';

export interface IOrderMain {
  id: number;
  buyerLogin?: string | null;
  buyerFirstName?: string | null;
  buyerLastName?: string | null;
  buyerEmail?: string | null;
  buyerPhone?: string | null;
  amountOfCartNet?: number | null;
  amountOfCartGross?: number | null;
  amountOfShipmentNet?: number | null;
  amountOfShipmentGross?: number | null;
  amountOfOrderNet?: number | null;
  amountOfOrderGross?: number | null;
  orderMainStatus?: OrderMainStatusEnum | null;
  createTime?: dayjs.Dayjs | null;
  updateTime?: dayjs.Dayjs | null;
}

export type NewOrderMain = Omit<IOrderMain, 'id'> & { id: null };
