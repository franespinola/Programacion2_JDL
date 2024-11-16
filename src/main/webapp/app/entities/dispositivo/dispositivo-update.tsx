import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { Moneda } from 'app/shared/model/enumerations/moneda.model';
import { createEntity, getEntity, updateEntity } from './dispositivo.reducer';

export const DispositivoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dispositivoEntity = useAppSelector(state => state.dispositivo.entity);
  const loading = useAppSelector(state => state.dispositivo.loading);
  const updating = useAppSelector(state => state.dispositivo.updating);
  const updateSuccess = useAppSelector(state => state.dispositivo.updateSuccess);
  const monedaValues = Object.keys(Moneda);

  const handleClose = () => {
    navigate('/dispositivo');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
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
    if (values.precioBase !== undefined && typeof values.precioBase !== 'number') {
      values.precioBase = Number(values.precioBase);
    }

    const entity = {
      ...dispositivoEntity,
      ...values,
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
          moneda: 'ARS',
          ...dispositivoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="servidorApiApp.dispositivo.home.createOrEditLabel" data-cy="DispositivoCreateUpdateHeading">
            <Translate contentKey="servidorApiApp.dispositivo.home.createOrEditLabel">Create or edit a Dispositivo</Translate>
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
                  id="dispositivo-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('servidorApiApp.dispositivo.idExterno')}
                id="dispositivo-idExterno"
                name="idExterno"
                data-cy="idExterno"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('servidorApiApp.dispositivo.codigo')}
                id="dispositivo-codigo"
                name="codigo"
                data-cy="codigo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('servidorApiApp.dispositivo.nombre')}
                id="dispositivo-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('servidorApiApp.dispositivo.descripcion')}
                id="dispositivo-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('servidorApiApp.dispositivo.precioBase')}
                id="dispositivo-precioBase"
                name="precioBase"
                data-cy="precioBase"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  min: { value: 0, message: translate('entity.validation.min', { min: 0 }) },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('servidorApiApp.dispositivo.moneda')}
                id="dispositivo-moneda"
                name="moneda"
                data-cy="moneda"
                type="select"
              >
                {monedaValues.map(moneda => (
                  <option value={moneda} key={moneda}>
                    {translate(`servidorApiApp.Moneda.${moneda}`)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/dispositivo" replace color="info">
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

export default DispositivoUpdate;
