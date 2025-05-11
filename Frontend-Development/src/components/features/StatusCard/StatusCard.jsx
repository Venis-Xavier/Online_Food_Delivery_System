import React from 'react'
import { Card, Col, Row } from 'react-bootstrap'

function StatusCard({ orderDate, deliveryStatus, cardTitle }) {
    return (
        <Card className="user-card mb-2">
            <h4>
                {cardTitle}
            </h4>
            <Row className='user-card-row'>
                <Col>
                    <div>
                        <strong>Ordered At:</strong>
                        <p>{orderDate}</p>
                    </div>
                </Col>
                <Col>
                    <div>
                        <strong>Delivery Status:</strong>
                        <p>{deliveryStatus}</p>
                    </div>
                </Col>
            </Row>
        </Card>
    )
}

export default StatusCard