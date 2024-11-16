import { IDispositivo } from 'app/shared/model/dispositivo.model';
import { IVenta } from 'app/shared/model/venta.model';

export interface IAdicional {
  id?: number;
  idExterno?: number;
  nombre?: string;
  descripcion?: string;
  precio?: number;
  precioGratis?: number;
  dispositivo?: IDispositivo;
  ventas?: IVenta[] | null;
}

export const defaultValue: Readonly<IAdicional> = {};
