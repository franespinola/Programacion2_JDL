import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './dispositivo.reducer';

export const Dispositivo = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const dispositivoList = useAppSelector(state => state.dispositivo.entities);
  const loading = useAppSelector(state => state.dispositivo.loading);
  const links = useAppSelector(state => state.dispositivo.links);
  const updateSuccess = useAppSelector(state => state.dispositivo.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="dispositivo-heading" data-cy="DispositivoHeading">
        <Translate contentKey="servidorApiApp.dispositivo.home.title">Dispositivos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="servidorApiApp.dispositivo.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/dispositivo/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="servidorApiApp.dispositivo.home.createLabel">Create new Dispositivo</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={dispositivoList ? dispositivoList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {dispositivoList && dispositivoList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="servidorApiApp.dispositivo.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('idExterno')}>
                    <Translate contentKey="servidorApiApp.dispositivo.idExterno">Id Externo</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('idExterno')} />
                  </th>
                  <th className="hand" onClick={sort('codigo')}>
                    <Translate contentKey="servidorApiApp.dispositivo.codigo">Codigo</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('codigo')} />
                  </th>
                  <th className="hand" onClick={sort('nombre')}>
                    <Translate contentKey="servidorApiApp.dispositivo.nombre">Nombre</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('nombre')} />
                  </th>
                  <th className="hand" onClick={sort('descripcion')}>
                    <Translate contentKey="servidorApiApp.dispositivo.descripcion">Descripcion</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('descripcion')} />
                  </th>
                  <th className="hand" onClick={sort('precioBase')}>
                    <Translate contentKey="servidorApiApp.dispositivo.precioBase">Precio Base</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('precioBase')} />
                  </th>
                  <th className="hand" onClick={sort('moneda')}>
                    <Translate contentKey="servidorApiApp.dispositivo.moneda">Moneda</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('moneda')} />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {dispositivoList.map((dispositivo, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/dispositivo/${dispositivo.id}`} color="link" size="sm">
                        {dispositivo.id}
                      </Button>
                    </td>
                    <td>{dispositivo.idExterno}</td>
                    <td>{dispositivo.codigo}</td>
                    <td>{dispositivo.nombre}</td>
                    <td>{dispositivo.descripcion}</td>
                    <td>{dispositivo.precioBase}</td>
                    <td>
                      <Translate contentKey={`servidorApiApp.Moneda.${dispositivo.moneda}`} />
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/dispositivo/${dispositivo.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/dispositivo/${dispositivo.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          onClick={() => (window.location.href = `/dispositivo/${dispositivo.id}/delete`)}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="servidorApiApp.dispositivo.home.notFound">No Dispositivos found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Dispositivo;
