import React, { useCallback, useEffect, useState } from 'react';
import PortalLayout from '../../../components/layout/PortalLayout/PortalLayout';
import { Col, Row } from 'react-bootstrap';
import './CartScreen.css';
import CheckOutCard from '../../../components/features/CheckOutCard/CheckOutCard';
import CartItem from '../../../components/features/CartItem/CartItem';
import { ORDER, VIEW } from '../../../utils/endpoints';
import { postData } from '../../../utils/api_utils';
import { IN_CART } from '../../../data/enums';import { calculateDeliveryCharge, calculateSubTotal, calculateTaxes, removeCartItem } from '../../../utils/app_utils';
import Loader from '../../../components/common/Loader/Loader';

function CartScreen() {
  const [cart, setCart] = useState(null);
  const [restaurantId, setRestaurantId] = useState('');
  const [billDetails, setBillDetails] = useState({
    billAmount: 0,
    taxes: 0,
    deliveryCharge: 0,
    subTotal: 0,
  });
  const [isLoading, setIsLoading] = useState(true); 

  const fetchCartItems = useCallback(async () => {
    setIsLoading(true);
    try {
      const response = await postData(ORDER, VIEW, IN_CART, true);
      const cartData = response?.data[0] || {};
      console.log(cartData);
      setCart(cartData);
      setRestaurantId(cartData?.restaurantDetails?.userId || '');
      updateBillDetails(cartData.billAmount || 0);
    } catch (error) {
      console.error('Error fetching cart items:', error);
    } finally {
      setIsLoading(false);
    }
  }, []);

  const updateBillDetails = (amount) => {
    const updatedTaxes = calculateTaxes(amount);
    const updatedDeliveryCharge = calculateDeliveryCharge(amount);
    const updatedSubTotal = calculateSubTotal(amount);

    setBillDetails({
      billAmount: amount,
      taxes: updatedTaxes,
      deliveryCharge: updatedDeliveryCharge,
      subTotal: updatedSubTotal,
    });
  };

  useEffect(() => {
    fetchCartItems();
  }, [fetchCartItems]);

  const removeFromCart = async (cartId) => {
    await removeCartItem(cartId);
    fetchCartItems();
  }

  if (isLoading) {
    return (
      <PortalLayout role="CUSTOMER">
        <Loader />
      </PortalLayout>
    );
  }

  return (
    <PortalLayout role="CUSTOMER">
      <div className="cart-screen">
        <div className="cart-items-container">
          <Row>
            <Col md={9}>
              {!cart?.orderedItems || cart.orderedItems.length === 0 ? (
                <div className="empty-cart">
                  <p>Your cart is empty!</p>
                </div>
              ) : (
                cart.orderedItems.map((item) => (
                  <CartItem
                    key={item.itemId}
                    itemId={item.itemId}
                    itemName={item.itemName}
                    itemDesc={item.itemDesc}
                    itemImg={item.itemImg}
                    itemPrice={(item.price * item.quantity).toFixed(2)}
                    quantity={item.quantity}
                    restaurantId={restaurantId}
                    showOptions={true}
                    onQuantityChange={fetchCartItems}
                    onRemove={() => {removeFromCart(item.cartId)}}
                  />
                ))
              )}
            </Col>
            <Col>
              <CheckOutCard
                orderId={cart.orderId}
                cardTitle="Order Summary"
                subTotal={billDetails.subTotal}
                deliveryFee={billDetails.deliveryCharge}
                taxes={billDetails.taxes}
                grandTotal={billDetails.billAmount}
                buttonText={"Place Order"}
                updateParent={fetchCartItems}
              />
            </Col>
          </Row>
        </div>
      </div>
    </PortalLayout>
  );
}

export default CartScreen;
