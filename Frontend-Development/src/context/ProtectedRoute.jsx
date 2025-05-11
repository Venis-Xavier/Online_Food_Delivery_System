import React from 'react';
 import { Navigate, useLocation } from 'react-router-dom';
 import { getUserRole, isAuthenticated } from '../utils/app_utils';
 
 const ProtectedRoute = ({ children, permittedRole }) => {
   const userRole = getUserRole();
   const authenticated = isAuthenticated();
   const location = useLocation();
 
   if (!authenticated || userRole !== permittedRole) {
     return <Navigate to="/" state={{ from: location }} />;
   }
 
   return children;
 };
 
 export default ProtectedRoute;