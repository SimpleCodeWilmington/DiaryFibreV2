import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBlogPost } from 'app/shared/model/blog-post.model';
import { getEntities, getEntity } from './myblogs.reducer';
import { size } from 'lodash';

export const MyBlogs = (props: RouteComponentProps<{ url: string }>) => {

  const dispatch = useAppDispatch();
  const account = useAppSelector(state => state.authentication.account);

  // TEST 1
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  
  const blogPostList = useAppSelector(state => state.blogPost.entities);
  const loading = useAppSelector(state => state.blogPost.loading);
  const totalItems = useAppSelector(state => state.blogPost.totalItems);
  const blogPostEntity = useAppSelector(state => state.blogPost.entity);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);


  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { match } = props;
  
  return (
    <Row>
    <div>
      <h2 id="blog-post-heading" data-cy="BlogPostHeading">
        <div> BLOG POSTS</div>
        {/* <Translate contentKey="diaryFibreApp.blogPost.home.title">Tests</Translate> */}
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="diaryFibreApp.blogPost.home.refreshListLabel">Refresh</Translate>
          </Button>
          <Link to="/blog-post/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="diaryFibreApp.blogPost.home.createLabel">New Post</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {blogPostList && blogPostList.length > 0 ? (
          <Row responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('dateTime')}>
                  <Translate contentKey="diaryFibreApp.blogPost.dateTime">Date Time</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>

            <br></br>
            <br></br>


            <tbody>
              {blogPostList.map((blogPost, i) => (
                <div key={`entity-${i}`} data-cy="entityTable">

                  <p>{blogPost.dateTime ? <TextFormat type="date" value={blogPost.dateTime} format={APP_DATE_FORMAT} /> : null}</p>
                  <h3>{blogPost.title}</h3>
                  <p>{blogPost.text}</p>

                  {/* <td>{blogPost.blogtext ? <Link to={`/blog-text/${blogPost.blogtext.id}`}>{blogPost.blogtext.tex}</Link> : ''}</td> */}
                  {/* <td>{blogPost.blog ? <Link to={`/blog/${blogPost.blog.id}`}>{blogPost.blog.id}</Link> : ''}</td> */}

                    <div className="btn-group flex-btn-group-container">
  
                      <Button
                        tag={Link}
                        to={`/blog-post/${blogPost.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/blog-post/${blogPost.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
  

  
                  <hr
                    style={{
                        color: "darkcyan",
                        height: 10,
                    }}  
                  />

                  <br></br>
                  <br></br>
                </div>
              ))}
            </tbody>
      
          </Row>

        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="diaryFibreApp.blogPost.home.notFound">No Blog Posts found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={blogPostList && blogPostList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
 
       
        
    </Row>
  );
};

export default MyBlogs;

