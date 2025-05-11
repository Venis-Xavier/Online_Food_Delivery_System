import './LoginScreen.css';
import React, { useEffect, useState } from 'react';
import BaseLayout from '../../../components/layout/BaseLayout/BaseLayout';
import Input from '../../../components/common/Input/Input';
import Select from '../../../components/common/Select/Select';
import { roles } from '../../../data/options';
import Button from '../../../components/common/Button/Button';
import { Card, Container } from 'react-bootstrap';
import { NavLink, useNavigate } from 'react-router-dom';
import { authenticateUser, getUserRole } from '../../../utils/app_utils';

function LoginScreen() {

    const navigate = useNavigate();

    const [payload, setPayload] = useState({
        email: '',
        password: '',
        role: 'CUSTOMER',
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
        const success = await authenticateUser(payload);
        if (success) {
            const role = getUserRole();
            if (role === 'CUSTOMER') {
                navigate('/customer/restaurants');
            } else if (role === 'MANAGER') {
                navigate('/manager/order');
            } else if (role === 'AGENT') {
                navigate('/agent/home');
            } else {
                alert('Forbidden access');
            }
        } else {
            alert('Authentication failed');
        }
    };

    useEffect(() => {
        localStorage.removeItem('token');
    }, []);

    return (
        <BaseLayout>
            <Container fluid className='login-screen'>
                <Card className='card center vertical'>
                    <h2>Login</h2>
                    <div>
                        <Input
                            isRequired={true}
                            inputType="email"
                            inputName="email"
                            placeholder="Enter Email Address"
                            onChangeAction={handleInputChange}
                        />
                    </div>
                    <div>
                        <Input
                            isRequired={true}
                            inputType="password"
                            inputName="password"
                            placeholder="Enter Password"
                            onChangeAction={handleInputChange}
                        />
                    </div>
                    <div>
                        <Select
                            options={roles}
                            onChangeAction={handleRoleChange}
                        />
                    </div>
                    <div>
                        <Button
                            buttonText="Let's Eat"
                            onClickAction={handleButtonClick}
                            buttonIcon="bi bi-box-arrow-in-right"
                        />
                    </div>
                    <small><br />Not a user? <NavLink to="/register">Register</NavLink></small>
                </Card>
            </Container>
        </BaseLayout>
    );
}

export default LoginScreen;
