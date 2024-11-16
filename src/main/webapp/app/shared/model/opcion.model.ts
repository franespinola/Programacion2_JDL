import { IPersonalizacion } from 'app/shared/model/personalizacion.model';

export interface IOpcion {
  id?: number;
  idExterno?: number;
  codigo?: string;
  nombre?: string;
  descripcion?: string;
  precioAdicional?: number;
  personalizacion?: IPersonalizacion;
}

export const defaultValue: Readonly<IOpcion> = {};
