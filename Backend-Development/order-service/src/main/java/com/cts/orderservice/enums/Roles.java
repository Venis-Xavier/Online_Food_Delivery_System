package com.cts.orderservice.enums;


	/**
	 * Represents the roles of the user.
	 * Roles can be AGENT, CUSTOMER, MANAGER.
	 * 
	 * @author Harish Raju M R
	 * @since 22 Feb 2025
	 */

public enum Roles {

    /*
     * The role is Delivery Agent.
     * Delivery Agent can view assigned orders, handle payment status, and update delivery status.
     */
    AGENT,

     /*
     * The role is Customer.
     * Customer can order the food from the restaurant, track the delivery status and order status.
     */
    CUSTOMER,

     /*
     * The role is Restaurant Manager.
     * Manager can view the ordered food, and update the order status.
     * Manager can also view the payment status.
     */
    MANAGER

}
