import React from 'react';
import "./CheckOutCard.css"
import Button from '../../common/Button/Button';
import { placeOrder } from '../../../utils/app_utils';
import { PLACED } from '../../../data/enums';

function CheckOutCard({cardTitle, subTotal, deliveryFee, taxes, grandTotal, orderId, buttonText, updateParent}) {

    const confirmOrder = async () => {
        const response = await placeOrder({
            orderId,
            status: PLACED
        });
        if(response){
            alert("Order Placed Successfully!");
        } else {
            alert("Unable to Place Order at the moment!");
        }
        updateParent();
    };

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
                <span>Other Charges</span>
                <span className="bill-amount">₹{taxes?.toFixed(2)}</span>
            </div>
            <hr />
            <div className="bill-row total-row">
                <strong>Grand Total</strong>
                <strong className="bill-amount">₹{grandTotal?.toFixed(2)}</strong>
            </div>
            <Button 
                className="checkout-button"
                buttonText={buttonText}
                onClickAction={confirmOrder} />
        </div>
    )
}

export default CheckOutCard;