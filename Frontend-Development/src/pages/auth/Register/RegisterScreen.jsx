import React, { useState } from 'react'
import BaseLayout from '../../../components/layout/BaseLayout/BaseLayout';
import { Card, Col, Container, Form, Row } from 'react-bootstrap';
import Input from '../../../components/common/Input/Input';
import Select from '../../../components/common/Select/Select';
import Button from '../../../components/common/Button/Button';
import { NavLink, useNavigate } from 'react-router-dom';
import { roles } from '../../../data/options';
import { registerUser } from '../../../utils/app_utils';

function RegisterScreen() {
  
  const navigate = useNavigate();

  const [payload, setPayload] = useState({
    name: '',
    email: '',
    phone: '',
    address: '',
    role: 'CUSTOMER',
    password: ''
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setPayload((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleRoleChange = (e) => {
    setPayload((prevState) => ({
      ...prevState,
      role: e.target.value,
    }));
  };

  const handleButtonClick = async () => {
    const success = await registerUser(payload);
    if (success) {
      navigate('/');
    } else {
      alert('Authentication failed');
    }
  };

  return (
    <BaseLayout>
      <Container fluid className='login-screen'>
        <Card className='card center vertical'>
          <h2>Register</h2>
          <Row>
            <Col>
              <div>
                <Input
                  isRequired={true}
                  inputType="text"
                  inputName="email"
                  placeholder="Enter Email Address"
                  onChangeAction={handleInputChange} />
              </div>
              <div>
                <Input
                  isRequired={true}
                  inputType="password"
                  inputName="password"
                  placeholder="Enter Password"
                  onChangeAction={handleInputChange} />
              </div>
              <div>
                <Select
                  options={roles}
                  onChangeAction={handleRoleChange} />
              </div>
            </Col>
            <Col>

              <div>
                <Input
                  isRequired={true}
                  inputType="text"
                  inputName="name"
                  placeholder="Enter Your Name"
                  onChangeAction={handleInputChange} />
              </div>
              <div>
                <Input
                  isRequired={true}
                  inputType="number"
                  inputName="phone"
                  max={9999999999}
                  placeholder="Enter Phone Number"
                  className="no-inc"
                  onChangeAction={handleInputChange} />
              </div>
              <div>
                <Input
                  isRequired={true}
                  inputType="text"
                  inputName="address"
                  placeholder="Enter Address"
                  onChangeAction={handleInputChange} />
              </div>
            </Col>
          </Row>
          {/* <Row>
            <Col><Form.Group controlId="file">
                            <Form.Label><b>Item Image:</b></Form.Label>
                            <Form.Control
                                type="file"
                                name="itemImage"
                                // onChange={handleFileChange}
                            />
                        </Form.Group></Col>
          </Row> */}
          <div>
            <Button
              buttonText="Start Eating"
              buttonIcon="bi bi-box-arrow-in-right"
              onClickAction={handleButtonClick} />
          </div>
          <small><br />Already an user? <NavLink to="/">Login</NavLink></small>
        </Card>
      </Container>
    </BaseLayout>
  )
}

export default RegisterScreen;