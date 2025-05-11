import React, { useCallback } from 'react';
import "./CartItem.css";
import Button from '../../common/Button/Button';
import { Card, Col, FormControl, Row } from 'react-bootstrap';
import Img from '../../../assets/bgImg4.jpg';
import { addItemToCart } from '../../../utils/app_utils';
import debounce from 'lodash.debounce'; // Import lodash debounce for optimization

function CartItem({ itemId, itemImg, itemName, itemDesc, itemPrice, quantity, showOptions, restaurantId, onQuantityChange, onRemove}) {
  const handleQuantityChange = useCallback(
    debounce(async (newQuantity) => {
      await addItemToCart({ itemId, restaurantId, quantity: newQuantity });
      onQuantityChange(); // Refresh the cart after updating the backend
    }, 500), // Adjust debounce delay as needed
    [itemId, restaurantId, onQuantityChange]
  );

  return (
    <Card className="cart-item-card center">
      <Row className="align-items-center">
        {/* Image Column */}
        <Col className="center vertical">
          <img src={itemImg ? `data:image/jpeg;base64,${itemImg}` : Img} alt={itemName} className="menu-img" />
        </Col>

        {/* Details Column */}
        <Col md={4} className="">
          <div className="item-details">
            <h3>{itemName}</h3>
            <p>{itemDesc}</p>
          </div>
        </Col>

        {/* Quantity Column */}
        <Col className="center vertical">
          <p><b>Quantity</b></p>
          <FormControl
            type="number"
            value={quantity}
            min={1}
            max={15}
            className="quantity-input"
            onChange={(e) => {
              const newQuantity = Math.max(parseInt(e.target.value, 10) || 1, 1);
              handleQuantityChange(newQuantity);
            }}
          />
        </Col>

        {/* Remove Button Column */}
        {showOptions ? (
          <Col className="center vertical">
            <h5 className="item-price">₹{itemPrice}</h5>
            <Button
              buttonIcon="bi bi-trash"
              buttonText="Remove"
              onClickAction={onRemove} // Call parent-provided delete handler
            />
          </Col>
        ) : (
          <Col className="center vertical">
            <p><b>Price</b></p>
            <FormControl
              type="number"
              value={quantity}
              min="1"
              className="quantity-input"
            />
          </Col>
        )}
      </Row>
    </Card>
  );
}

export default CartItem;
