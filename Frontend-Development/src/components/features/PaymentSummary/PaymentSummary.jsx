import React from 'react';
import "./PaymentSummary.css"
import Button from '../../common/Button/Button';
import { Col, Row } from 'react-bootstrap';

function PaymentSummary({ cardTitle, subTotal, deliveryFee, taxes, grandTotal, onAccept, onDecline, status, textStyle, isAgent, isCompleted }) {
    return (
        <div className="bill-details-card">
            <h4>{cardTitle}</h4>
            <div className="bill-row">
                <span>Sub Total</span>
                <span className="bill-amount">₹{subTotal?.toFixed(2)}</span>
            </div>
            <div className="bill-row">
                <span>Delivery Fee</span>
                <span className="bill-amount">₹{deliveryFee?.toFixed(2)}</span>
            </div>
            <div className="bill-row">
                <span>GST & Other Charges</span>
                <span className="bill-amount">₹{taxes?.toFixed(2)}</span>
            </div>
            <hr />
            <div className="bill-row total-row">
                <strong>Grand Total</strong>
                <strong className="bill-amount">₹{grandTotal?.toFixed(2)}</strong>
            </div>
            {isAgent && !isCompleted && (
                <Button
                className="checkout-button"
                buttonText={"Complete Order"}
                onClickAction={onAccept} />
            )}
            {!isAgent && status === "PLACED" && (
                <Row>
                <Col>

                    <Button
                        className="checkout-button"
                        buttonText={"Decline"}
                        onClickAction={onDecline} />
                </Col>

                <Col>

                    <Button
                        className="checkout-button"
                        buttonText={"Accept"}
                        onClickAction={onAccept} />
                </Col>
            </Row>
            )}

            {!isAgent && status !== "PLACED" && (
                <h3 className='text-center' style={textStyle}>{status}</h3>
            )}
            
        </div>
    )
}

export default PaymentSummary;