import React, { useEffect, useState } from 'react';
import PortalLayout from '../../../components/layout/PortalLayout/PortalLayout';
import { Col, Container, Form, Row } from 'react-bootstrap';
import './RestaurantScreen.css';
import RestaurantCard from '../../../components/features/RestaurantCard/RestaurantCard';
import { RESTAURANT, VIEW } from '../../../utils/endpoints';
import { getData } from '../../../utils/api_utils';

function RestaurantScreen() {
  const [restaurants, setRestaurants] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    const fetchRestaurants = async () => {
      const response = await getData(RESTAURANT, VIEW);
      setRestaurants(response?.data);
    };

    fetchRestaurants();
  }, []);

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
  };

  const filteredRestaurants = restaurants?.filter((restaurant) =>
    restaurant?.restaurantName?.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <PortalLayout role="CUSTOMER">
      <div className="restaurant-screen">
        <Container>
          <Row>
            <Col lg={4}>
            <Form.Control
                  type="text"
                  placeholder="Search"
                  className="mr-sm-2"
                value={searchTerm}
                onChange={handleSearch}
                />
            </Col>
          </Row>
        </Container>
        {filteredRestaurants?.length > 0 && (
          <>
            {Array.from({ length: Math.ceil(restaurants.length / 3) }).map((_, rowIndex) => (
              <Row key={rowIndex} style={{ marginBottom: '20px' }}>
                {filteredRestaurants
                  .slice(rowIndex * 3, rowIndex * 3 + 3) 
                  .map((restaurant) => (
                    <Col key={restaurant?.restaurantId} md={4}> 
                      <RestaurantCard
                        id={restaurant?.restaurantId}
                        image={restaurant?.restaurantImg}
                        name={restaurant?.restaurantName}
                        rating={restaurant?.rating}
                        prepTime={restaurant?.prepTime}
                        cuisine={restaurant?.cuisine}
                        address={restaurant?.address || "Ganapathy, Coimbatore"}
                      />
                    </Col>
                  ))}
              </Row>
            ))}
          </>
        )}
      </div>
    </PortalLayout>
  );
}

export default RestaurantScreen;
