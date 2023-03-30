export interface ICart {
  id: number;
  amountOfCartNet?: number | null;
  amountOfCartGross?: number | null;
  amountOfShipmentNet?: number | null;
  amountOfShipmentGross?: number | null;
  amountOfOrderNet?: number | null;
  amountOfOrderGross?: number | null;
}

export type NewCart = Omit<ICart, 'id'> & { id: null };
