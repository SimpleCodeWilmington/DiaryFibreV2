import React, { useLayoutEffect } from 'react';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { logout } from 'app/shared/reducers/authentication';
import { Redirect, RouteComponentProps } from 'react-router-dom';

export const Logout = (props: RouteComponentProps<any>) => {
  const logoutUrl = useAppSelector(state => state.authentication.logoutUrl);
  const dispatch = useAppDispatch();

  useLayoutEffect(() => {
    dispatch(logout());
    if (logoutUrl) {
      window.location.href = logoutUrl;
    }
  });


  const { location } = props;
  const { from } = (location.state as any) || { from: { pathname: '/', search: location.search } };
  if (logout) {
    return <Redirect to={from} />;
  }


  return (
    <div className="p-5">
      <h4>Logged out successfully!</h4>
    </div>
  );
};

export default Logout;
