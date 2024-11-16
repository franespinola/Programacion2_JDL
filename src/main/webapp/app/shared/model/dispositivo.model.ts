import { Moneda } from 'app/shared/model/enumerations/moneda.model';

export interface IDispositivo {
  id?: number;
  idExterno?: number;
  codigo?: string;
  nombre?: string;
  descripcion?: string;
  precioBase?: number;
  moneda?: keyof typeof Moneda;
}

export const defaultValue: Readonly<IDispositivo> = {};
