import dayjs from 'dayjs';
import { IDispositivo } from 'app/shared/model/dispositivo.model';
import { IPersonalizacion } from 'app/shared/model/personalizacion.model';
import { IAdicional } from 'app/shared/model/adicional.model';

export interface IVenta {
  id?: number;
  fechaVenta?: dayjs.Dayjs;
  precioFinal?: number;
  dispositivo?: IDispositivo;
  personalizaciones?: IPersonalizacion[] | null;
  adicionales?: IAdicional[] | null;
}

export const defaultValue: Readonly<IVenta> = {};
