import React from 'react';
import { Card, Row, Col } from 'react-bootstrap';
import "./UserDetailsCard.css";

const UserDetailsCard = ({ userId, name, contact, address, cardTitle }) => {
    return (
        <Card className="user-card">
                <h4>
                    {cardTitle}
                </h4>
                <Row className='user-card-row'>
                    <Col>
                        <div>
                            <strong>Name:</strong>
                            <p>{name}</p>
                        </div>
                    </Col>
                    <Col>
                        <div>
                            <strong>Contact:</strong>
                            <p>{contact}</p>
                        </div>
                    </Col>
                </Row>
                <Row>
                    <div className="my-2">
                        <strong>Address:</strong>
                        <p>{address}</p>
                    </div>
                </Row>
        </Card>
    );
};

export default UserDetailsCard;
