import { ICart } from 'app/entities/cart/cart.model';

export interface IProductInCart {
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
  cart?: Pick<ICart, 'id'> | null;
}

export type NewProductInCart = Omit<IProductInCart, 'id'> & { id: null };
