import { IDispositivo } from 'app/shared/model/dispositivo.model';
import { IVenta } from 'app/shared/model/venta.model';

export interface IPersonalizacion {
  id?: number;
  idExterno?: number;
  nombre?: string;
  descripcion?: string;
  dispositivo?: IDispositivo;
  ventas?: IVenta[] | null;
}

export const defaultValue: Readonly<IPersonalizacion> = {};
