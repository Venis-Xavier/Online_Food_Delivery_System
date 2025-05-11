import React from 'react';

import "./RestaurantCard.css";

import Button from '../../common/Button/Button';
import { Card, Row } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

function RestaurantCard({ id, image, name, address }) {

  const navigate = useNavigate();

  const handleButtonClick = () => {
      navigate('/customer/menu', { state: id })
  };

  return (
    <Card key={id} className="restaurant-card">
      <div className="restaurant-image-container">
        <img
          src={`data:image/jpeg;base64,${image}`}
          alt={name}
          className="restaurant-image"
        />
      </div>
      <Card.Body className="restaurant-info">
        <Card.Title className="restaurant-name">{name}</Card.Title>
        <Row className="restaurant-details">
          <p>{address}</p>
        </Row>

        <Button
          buttonText="View Menu"
          className="view-menu-button"
          onClickAction={handleButtonClick} />

      </Card.Body>
    </Card>
  )
}

export default RestaurantCard