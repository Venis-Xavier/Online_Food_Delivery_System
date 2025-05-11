import React, { useState, useEffect } from 'react';
import PortalLayout from '../../../components/layout/PortalLayout/PortalLayout';
import { Col, Container, Pagination, Row } from 'react-bootstrap';
import Select from '../../../components/common/Select/Select';
import { agentOrderStatus } from '../../../data/options';
import OrderItems from '../../../components/features/OrderItems/OrderItems';
import UserDetailsCard from '../../../components/features/UserDetails/UserDetailsCard';
import {
  calculateDeliveryCharge,
  calculateSubTotal,
  calculateTaxes,
  fetchOrderSummary,
  formatDate,
  placeOrder
} from '../../../utils/app_utils';
import PaymentSummary from '../../../components/features/PaymentSummary/PaymentSummary';
import { COMPLETED } from '../../../data/enums';
import StatusCard from '../../../components/features/StatusCard/StatusCard';

function HomeScreen() {
  const [orderSummary, setOrderSummary] = useState([]);
  const [selectedStatus, setSelectedStatus] = useState(agentOrderStatus[0].value);
  const [isLoading, setIsLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);

  const pageSize = 1;

  const fetchOrders = async (status) => {
    setIsLoading(true);
    try {
      const response = await fetchOrderSummary(status);
      setOrderSummary(response.data);
    } catch (error) {
      console.error("Error fetching orders: ", error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchOrders(selectedStatus);
  }, [selectedStatus]);

  const handleStatusChange = (status) => {
    setSelectedStatus(status);
  };

  const completeOrder = async (orderId) => {
    const response = await placeOrder({ orderId, status: COMPLETED });
    await fetchOrders(selectedStatus);

    if (response) {
      alert("Order Completed!");
    } else {
      alert("Unable to Complete Order at the moment!");
    }

  };

  const paginatedData = orderSummary.slice((currentPage - 1) * pageSize, currentPage * pageSize);

  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  return (
    <PortalLayout role="AGENT">
      <Container>
        <Select
          options={agentOrderStatus}
          onChangeAction={(e) => handleStatusChange(e.target.value)}
          value={selectedStatus}
        />

        {isLoading ? (
          <p>Loading...</p>
        ) : (
          paginatedData.map((order) => (
            <React.Fragment key={order.orderId}>
              <Row>
                <Col md={8}>
                  <OrderItems
                    data={order.orderedItems.map((item, idx) => ({
                      'S. No': idx + 1,
                      'Item Name': item.itemName,
                      Quantity: item.quantity || 1,
                    }))}
                    headers={['S. No', 'Item Name', 'Quantity']}
                  />
                  <Row>
                    <Col>
                      <UserDetailsCard
                        name={order.restaurantDetails?.name || 'Not Assigned'}
                        contact={order.restaurantDetails?.contact || 'Not Assigned'}
                        address={order.restaurantDetails?.address || 'Not Assigned'}
                        userId={order.restaurantDetails?.userId || ''}
                        cardTitle="Ordered From"
                      />
                    </Col>
                    <Col>
                      <UserDetailsCard
                        name={order.customerDetails.name}
                        contact={order.customerDetails.contact}
                        address={order.customerDetails.address}
                        userId={order.customerDetails.userId}
                        cardTitle="Ordered By"
                      />
                    </Col>
                  </Row>
                </Col>
                <Col>
                  <Row>
                    <StatusCard
                      cardTitle={"Order Status"}
                      deliveryStatus={order.deliverydetails?.deliveryStatus}
                      orderDate={formatDate(order.orderedAt)} />
                  </Row>
                  <Row>
                    <PaymentSummary
                      cardTitle={"Order Summary"}
                      deliveryFee={calculateDeliveryCharge(order.billAmount)}
                      grandTotal={order.billAmount}
                      subTotal={calculateSubTotal(order.billAmount)}
                      taxes={calculateTaxes(order.billAmount)}
                      onAccept={() => completeOrder(order.orderId)}
                      isAgent={true}
                      isCompleted={order.orderStatus === COMPLETED}
                    />
                  </Row>
                </Col>
              </Row>
            </React.Fragment>
          ))
        )}

        <Pagination className='center'>
          {[...Array(Math.ceil(orderSummary.length / pageSize)).keys()].map((page) => (
            <Pagination.Item
              key={page}
              active={page + 1 === currentPage}
              onClick={() => handlePageChange(page + 1)}
            >
              {page + 1}
            </Pagination.Item>
          ))}
        </Pagination>
      </Container>
    </PortalLayout>
  );
}

export default HomeScreen;
