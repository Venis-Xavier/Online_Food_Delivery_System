import React from 'react';
import { Card } from 'react-bootstrap';
import './AuthCard.css';

function AuthCard({ children }) {
  return (
    <Card className='card-component'>
      <Card.Body className='vertical center'>{children}</Card.Body>
    </Card>
  );
}

export default AuthCard;