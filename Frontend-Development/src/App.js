import './App.css';
import { Route, Routes } from 'react-router-dom';

import LoginScreen from './pages/auth/Login/LoginScreen'
import RegisterScreen from './pages/auth/Register/RegisterScreen'
import OrderDetailsScreen from './pages/manager/Order/OrderDetailsScreen';
import OrderStatusScreen from './pages/customer/Order/OrderStatusScreen';
import RestaurantScreen from './pages/customer/Restaurant/RestaurantScreen';
import CartScreen from './pages/customer/Cart/CartScreen';
import HomeScreen from './pages/agent/Home/HomeScreen';
import ManagerMenuScreen from './pages/manager/Menu/ManagerMenuScreen';
import CustomerMenuScreen from './pages/customer/Menu/CustomerMenuScreen';
import ProtectedRoute from './context/ProtectedRoute';


function App() {

  return (
    <Routes>
      <Route path="/" element={<LoginScreen />} />
      <Route path="/register" element={<RegisterScreen />} />
      <Route path="/manager/menu" element={
        <ProtectedRoute permittedRole="MANAGER">
          <ManagerMenuScreen />
        </ProtectedRoute>
      } />
      <Route path="/manager/order" element={
        <ProtectedRoute permittedRole="MANAGER">
          <OrderDetailsScreen />
        </ProtectedRoute>
      } />
      <Route path="/customer/menu" element={
        <ProtectedRoute permittedRole="CUSTOMER">
          <CustomerMenuScreen />
        </ProtectedRoute>
      } />
      <Route path="/customer/order" element={
        <ProtectedRoute permittedRole="CUSTOMER">
          <OrderStatusScreen />
        </ProtectedRoute>
      } />
      <Route path="/customer/restaurants" element={
        <ProtectedRoute permittedRole="CUSTOMER">
          <RestaurantScreen />
        </ProtectedRoute>
      } />
      <Route path="/customer/cart" element={
        <ProtectedRoute permittedRole="CUSTOMER">
          <CartScreen />
        </ProtectedRoute>
      } />
      <Route path="/agent/home" element={
        <ProtectedRoute permittedRole="AGENT">
          <HomeScreen />
        </ProtectedRoute>
      } />
    </Routes>
  );
}

export default App;
