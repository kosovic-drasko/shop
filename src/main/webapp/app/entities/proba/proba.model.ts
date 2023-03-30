export interface IProba {
  id: number;
  naziv?: string | null;
}

export type NewProba = Omit<IProba, 'id'> & { id: null };
