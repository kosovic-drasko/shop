import dayjs from 'dayjs/esm';
import { ProductCategoryEnum } from 'app/entities/enumerations/product-category-enum.model';

export interface IProduct {
  id: number;
  productCategoryEnum?: ProductCategoryEnum | null;
  name?: string | null;
  quantity?: number | null;
  priceNet?: number | null;
  vat?: number | null;
  priceGross?: number | null;
  stock?: number | null;
  description?: string | null;
  createTime?: dayjs.Dayjs | null;
  updateTime?: dayjs.Dayjs | null;
  image?: string | null;
  imageContentType?: string | null;
}

export type NewProduct = Omit<IProduct, 'id'> & { id: null };
