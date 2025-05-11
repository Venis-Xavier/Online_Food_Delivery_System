import React from 'react';
import './MenuItem.css';
import Button from '../../common/Button/Button';
import { Card, Col, Row } from 'react-bootstrap';
import Img from '../../../assets/bgImg4.jpg';

const MenuItem = ({ itemId, itemName, itemImg, isVeg, itemPrice, description, onClickAction, showOptions, onDeleteAction, isDisabled }) => {

    return (
        <Card key={itemId} className="cart-item-card" >
            <Row className="align-items-center">
                {/* Image Column */}
                <Col className="center">
                    <img src={itemImg ? `data:image/jpeg;base64,${itemImg}` : Img} alt={itemName} className="menu-img" />
                </Col>
                {/* Details Column */}
                <Col md={6}>
                    <div className="item-details">
                        <h3 className="item-name">
                            {isVeg ? '🌱' : '🍖'} {itemName}
                        </h3>
                        <p>{description}</p>
                    </div>
                </Col>
                {/* Remove Button Column */}
                {showOptions ? (
                    <Col className="center vertical">

                        <Button
                            buttonIcon="bi bi-pen"
                            buttonText="Update"
                            onClickAction={onClickAction}
                        />
                        <Button
                            buttonIcon="bi bi-trash"
                            buttonText="Delete&nbsp; "
                            onClickAction={onDeleteAction}
                        />

                    </Col>
                ) :
                    <Col className="center vertical">

                        <h5>₹{itemPrice}</h5>
                        {!isDisabled ? (
                            <Button
                            buttonIcon="bi bi-cart"
                            buttonText="Add to Cart"
                            onClickAction={onClickAction}
                            disabled={isDisabled}
                            />
                        ) : (
                            <Button
                            buttonIcon="bi bi-check"
                            buttonText="Added"
                            onClickAction={onClickAction}
                            disabled={isDisabled}
                            />
                        )}
                        
                    </Col>}
            </Row>
        </Card>
    );
};

export default MenuItem;
