import axios from 'axios';

import configureStore from 'redux-mock-store';
import thunk from 'redux-thunk';
import sinon from 'sinon';

import reducer, {
  createEntity,
  deleteEntity,
  getImages,
  getImag,
  updateEntity,
  partialUpdateEntity,
  reset,
} from './blog-image.reducer';
import { EntityState } from 'app/shared/reducers/reducer.utils';
import { IBlogImage, defaultValue } from 'app/shared/model/blog-image.model';

describe('Entities reducer tests', () => {
  function isEmpty(element): boolean {
    if (element instanceof Array) {
      return element.length === 0;
    } else {
      return Object.keys(element).length === 0;
    }
  }

  const initialState: EntityState<IBlogImage> = {
    loading: false,
    errorMessage: null,
    entities: [],
    entity: defaultValue,
    updating: false,
    updateSuccess: false,
  };

  function testInitialState(state) {
    expect(state).toMatchObject({
      loading: false,
      errorMessage: null,
      updating: false,
      updateSuccess: false,
    });
    expect(isEmpty(state.entities));
    expect(isEmpty(state.entity));
  }

  function testMultipleTypes(types, payload, testFunction, error?) {
    types.forEach(e => {
      testFunction(reducer(undefined, { type: e, payload, error }));
    });
  }

  describe('Common', () => {
    it('should return the initial state', () => {
      testInitialState(reducer(undefined, { type: '' }));
    });
  });

  describe('Requests', () => {
    it('should set state to loading', () => {
      testMultipleTypes([getImages.pending.type, getImag.pending.type], {}, state => {
        expect(state).toMatchObject({
          errorMessage: null,
          updateSuccess: false,
          loading: true,
        });
      });
    });

    it('should set state to updating', () => {
      testMultipleTypes(
        [createEntity.pending.type, updateEntity.pending.type, partialUpdateEntity.pending.type, deleteEntity.pending.type],
        {},
        state => {
          expect(state).toMatchObject({
            errorMessage: null,
            updateSuccess: false,
            updating: true,
          });
        }
      );
    });

    it('should reset the state', () => {
      expect(reducer({ ...initialState, loading: true }, reset())).toEqual({
        ...initialState,
      });
    });
  });

  describe('Failures', () => {
    it('should set a message in errorMessage', () => {
      testMultipleTypes(
        [
          getImages.rejected.type,
          getImag.rejected.type,
          createEntity.rejected.type,
          updateEntity.rejected.type,
          partialUpdateEntity.rejected.type,
          deleteEntity.rejected.type,
        ],
        'some message',
        state => {
          expect(state).toMatchObject({
            errorMessage: 'error message',
            updateSuccess: false,
            updating: false,
          });
        },
        {
          message: 'error message',
        }
      );
    });
  });

  describe('Successes', () => {
    it('should fetch all entities', () => {
      const payload = { data: [{ 1: 'fake1' }, { 2: 'fake2' }] };
      expect(
        reducer(undefined, {
          type: getImages.fulfilled.type,
          payload,
        })
      ).toEqual({
        ...initialState,
        loading: false,
        entities: payload.data,
      });
    });

    it('should fetch a single entity', () => {
      const payload = { data: { 1: 'fake1' } };
      expect(
        reducer(undefined, {
          type: getImag.fulfilled.type,
          payload,
        })
      ).toEqual({
        ...initialState,
        loading: false,
        entity: payload.data,
      });
    });

    it('should create/update entity', () => {
      const payload = { data: 'fake payload' };
      expect(
        reducer(undefined, {
          type: createEntity.fulfilled.type,
          payload,
        })
      ).toEqual({
        ...initialState,
        updating: false,
        updateSuccess: true,
        entity: payload.data,
      });
    });

    it('should delete entity', () => {
      const payload = 'fake payload';
      const toTest = reducer(undefined, {
        type: deleteEntity.fulfilled.type,
        payload,
      });
      expect(toTest).toMatchObject({
        updating: false,
        updateSuccess: true,
      });
    });
  });

  describe('Actions', () => {
    let store;

    const resolvedObject = { value: 'whatever' };
    beforeEach(() => {
      const mockStore = configureStore([thunk]);
      store = mockStore({});
      axios.get = sinon.stub().returns(Promise.resolve(resolvedObject));
      axios.post = sinon.stub().returns(Promise.resolve(resolvedObject));
      axios.put = sinon.stub().returns(Promise.resolve(resolvedObject));
      axios.patch = sinon.stub().returns(Promise.resolve(resolvedObject));
      axios.delete = sinon.stub().returns(Promise.resolve(resolvedObject));
    });

    it('dispatches FETCH_BLOGIMAGE_LIST actions', async () => {
      const expectedActions = [
        {
          type: getImages.pending.type,
        },
        {
          type: getImages.fulfilled.type,
          payload: resolvedObject,
        },
      ];
      await store.dispatch(getImages({}));
      expect(store.getActions()[0]).toMatchObject(expectedActions[0]);
      expect(store.getActions()[1]).toMatchObject(expectedActions[1]);
    });

    it('dispatches FETCH_BLOGIMAGE actions', async () => {
      const expectedActions = [
        {
          type: getImag.pending.type,
        },
        {
          type: getImag.fulfilled.type,
          payload: resolvedObject,
        },
      ];
      await store.dispatch(getImag(42666));
      expect(store.getActions()[0]).toMatchObject(expectedActions[0]);
      expect(store.getActions()[1]).toMatchObject(expectedActions[1]);
    });

    it('dispatches CREATE_BLOGIMAGE actions', async () => {
      const expectedActions = [
        {
          type: createEntity.pending.type,
        },
        {
          type: getImages.pending.type,
        },
        {
          type: createEntity.fulfilled.type,
          payload: resolvedObject,
        },
      ];
      await store.dispatch(createEntity({ id: 456 }));
      expect(store.getActions()[0]).toMatchObject(expectedActions[0]);
      expect(store.getActions()[1]).toMatchObject(expectedActions[1]);
      expect(store.getActions()[2]).toMatchObject(expectedActions[2]);
    });

    it('dispatches UPDATE_BLOGIMAGE actions', async () => {
      const expectedActions = [
        {
          type: updateEntity.pending.type,
        },
        {
          type: getImages.pending.type,
        },
        {
          type: updateEntity.fulfilled.type,
          payload: resolvedObject,
        },
      ];
      await store.dispatch(updateEntity({ id: 456 }));
      expect(store.getActions()[0]).toMatchObject(expectedActions[0]);
      expect(store.getActions()[1]).toMatchObject(expectedActions[1]);
      expect(store.getActions()[2]).toMatchObject(expectedActions[2]);
    });

    it('dispatches PARTIAL_UPDATE_BLOGIMAGE actions', async () => {
      const expectedActions = [
        {
          type: partialUpdateEntity.pending.type,
        },
        {
          type: getImages.pending.type,
        },
        {
          type: partialUpdateEntity.fulfilled.type,
          payload: resolvedObject,
        },
      ];
      await store.dispatch(partialUpdateEntity({ id: 123 }));
      expect(store.getActions()[0]).toMatchObject(expectedActions[0]);
      expect(store.getActions()[1]).toMatchObject(expectedActions[1]);
      expect(store.getActions()[2]).toMatchObject(expectedActions[2]);
    });

    it('dispatches DELETE_BLOGIMAGE actions', async () => {
      const expectedActions = [
        {
          type: deleteEntity.pending.type,
        },
        {
          type: getImages.pending.type,
        },
        {
          type: deleteEntity.fulfilled.type,
          payload: resolvedObject,
        },
      ];
      await store.dispatch(deleteEntity(42666));
      expect(store.getActions()[0]).toMatchObject(expectedActions[0]);
      expect(store.getActions()[1]).toMatchObject(expectedActions[1]);
      expect(store.getActions()[2]).toMatchObject(expectedActions[2]);
    });

    it('dispatches RESET actions', async () => {
      const expectedActions = [reset()];
      await store.dispatch(reset());
      expect(store.getActions()).toEqual(expectedActions);
    });
  });
});
