import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getDispositivos } from 'app/entities/dispositivo/dispositivo.reducer';
import { getEntities as getVentas } from 'app/entities/venta/venta.reducer';
import { createEntity, getEntity, updateEntity } from './adicional.reducer';

export const AdicionalUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dispositivos = useAppSelector(state => state.dispositivo.entities);
  const ventas = useAppSelector(state => state.venta.entities);
  const adicionalEntity = useAppSelector(state => state.adicional.entity);
  const loading = useAppSelector(state => state.adicional.loading);
  const updating = useAppSelector(state => state.adicional.updating);
  const updateSuccess = useAppSelector(state => state.adicional.updateSuccess);

  const handleClose = () => {
    navigate('/adicional');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getDispositivos({}));
    dispatch(getVentas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.idExterno !== undefined && typeof values.idExterno !== 'number') {
      values.idExterno = Number(values.idExterno);
    }
    if (values.precio !== undefined && typeof values.precio !== 'number') {
      values.precio = Number(values.precio);
    }
    if (values.precioGratis !== undefined && typeof values.precioGratis !== 'number') {
      values.precioGratis = Number(values.precioGratis);
    }

    const entity = {
      ...adicionalEntity,
      ...values,
      dispositivo: dispositivos.find(it => it.id.toString() === values.dispositivo?.toString()),
      ventas: mapIdList(values.ventas),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...adicionalEntity,
          dispositivo: adicionalEntity?.dispositivo?.id,
          ventas: adicionalEntity?.ventas?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="servidorApiApp.adicional.home.createOrEditLabel" data-cy="AdicionalCreateUpdateHeading">
            <Translate contentKey="servidorApiApp.adicional.home.createOrEditLabel">Create or edit a Adicional</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="adicional-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('servidorApiApp.adicional.idExterno')}
                id="adicional-idExterno"
                name="idExterno"
                data-cy="idExterno"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('servidorApiApp.adicional.nombre')}
                id="adicional-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('servidorApiApp.adicional.descripcion')}
                id="adicional-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('servidorApiApp.adicional.precio')}
                id="adicional-precio"
                name="precio"
                data-cy="precio"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('servidorApiApp.adicional.precioGratis')}
                id="adicional-precioGratis"
                name="precioGratis"
                data-cy="precioGratis"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: -1, message: translate('entity.validation.min', { min: -1 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="adicional-dispositivo"
                name="dispositivo"
                data-cy="dispositivo"
                label={translate('servidorApiApp.adicional.dispositivo')}
                type="select"
                required
              >
                <option value="" key="0" />
                {dispositivos
                  ? dispositivos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                label={translate('servidorApiApp.adicional.venta')}
                id="adicional-venta"
                data-cy="venta"
                type="select"
                multiple
                name="ventas"
              >
                <option value="" key="0" />
                {ventas
                  ? ventas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/adicional" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AdicionalUpdate;
