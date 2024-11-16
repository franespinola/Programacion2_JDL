import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './venta.reducer';

export const VentaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ventaEntity = useAppSelector(state => state.venta.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ventaDetailsHeading">
          <Translate contentKey="servidorApiApp.venta.detail.title">Venta</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.id}</dd>
          <dt>
            <span id="fechaVenta">
              <Translate contentKey="servidorApiApp.venta.fechaVenta">Fecha Venta</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.fechaVenta ? <TextFormat value={ventaEntity.fechaVenta} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="precioFinal">
              <Translate contentKey="servidorApiApp.venta.precioFinal">Precio Final</Translate>
            </span>
          </dt>
          <dd>{ventaEntity.precioFinal}</dd>
          <dt>
            <Translate contentKey="servidorApiApp.venta.dispositivo">Dispositivo</Translate>
          </dt>
          <dd>{ventaEntity.dispositivo ? ventaEntity.dispositivo.id : ''}</dd>
          <dt>
            <Translate contentKey="servidorApiApp.venta.personalizaciones">Personalizaciones</Translate>
          </dt>
          <dd>
            {ventaEntity.personalizaciones
              ? ventaEntity.personalizaciones.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {ventaEntity.personalizaciones && i === ventaEntity.personalizaciones.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="servidorApiApp.venta.adicionales">Adicionales</Translate>
          </dt>
          <dd>
            {ventaEntity.adicionales
              ? ventaEntity.adicionales.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {ventaEntity.adicionales && i === ventaEntity.adicionales.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/venta" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/venta/${ventaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VentaDetail;
