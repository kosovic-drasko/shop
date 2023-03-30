import { IOrderMain } from 'app/entities/order-main/order-main.model';

export interface IProductInOrderMain {
  id: number;
  category?: string | null;
  name?: string | null;
  quantity?: number | null;
  priceNet?: number | null;
  vat?: number | null;
  priceGross?: number | null;
  totalPriceNet?: number | null;
  totalPriceGross?: number | null;
  stock?: number | null;
  description?: string | null;
  image?: string | null;
  imageContentType?: string | null;
  productId?: number | null;
  orderMain?: Pick<IOrderMain, 'id'> | null;
}

export type NewProductInOrderMain = Omit<IProductInOrderMain, 'id'> & { id: null };
