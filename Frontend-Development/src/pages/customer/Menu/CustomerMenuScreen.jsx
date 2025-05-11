import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import PortalLayout from '../../../components/layout/PortalLayout/PortalLayout';
import './CustomerMenuScreen.css';
import MenuItem from '../../../components/features/MenuItem/MenuItem';
import { postData } from '../../../utils/api_utils';
import { DEFAULT, MENU } from '../../../utils/endpoints';
import { addItemToCart } from '../../../utils/app_utils';
import { Col, Container, Form, Row } from 'react-bootstrap';

function CustomerMenuScreen() {

  const location = useLocation();
  const navigate = useNavigate();

  const  restaurantId = location.state;
  const [menuItems, setMenuItems] = useState([]);  
  const [disabledItems, setDisabledItems] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    const fetchMenuItems = async () => {
      if (!restaurantId) {
        navigate('/customer/restaurants');
        return;
      }

      const response = await postData(MENU, DEFAULT, { restaurantId });
      setMenuItems(response.data?.menuItems || []);
    };

    fetchMenuItems(); 
  }, [restaurantId, navigate]);

  const handleButtonClick = async (itemId, itemName) => {
    const response = await addItemToCart({itemId, restaurantId, quantity: 1});
    if(!response){
      alert("Please Clear the Cart to add new items!");
      return;
    }
    setDisabledItems((prev) => [...prev, itemId]);
  };

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
  };

  const filteredMenuItems = menuItems?.filter((menu) =>
    menu?.itemName?.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <PortalLayout role="CUSTOMER">
      <div className="menu-screen">
        <div className="menu-container">
        <Container>
          <Row>
            <Col></Col>
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
          {filteredMenuItems.map((item) => (
            <MenuItem
              key={item.itemId}
              isVeg={item.veg}
              itemId={item.itemId}
              itemName={item.itemName}
              itemPrice={item.price}
              description={item.itemDesc}
              itemImg={item.itemImg}
              onClickAction={() => handleButtonClick(item.itemId, item.itemName)}
              isDisabled={disabledItems.includes(item.itemId)}
              
            />
          ))}
        </div>
      </div>
    </PortalLayout>
  );
}

export default CustomerMenuScreen;
