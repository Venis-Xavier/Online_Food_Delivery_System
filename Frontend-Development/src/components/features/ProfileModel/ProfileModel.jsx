import React, { useEffect, useState } from 'react';
import { Form, Row, Col, Button } from 'react-bootstrap';
import Model from '../../common/Model/Model';
import { updateProfile } from '../../../utils/app_utils';

function ProfileModel({ isOpen, title, profile, onClose }) {
    const [userProfile, setUserProfile] = useState(profile || {});
    const [readOnly, setReadOnly] = useState(true);
    const [initialProfile, setInitialProfile] = useState(profile || {});

    useEffect(() => {
        setUserProfile(profile || {});
        setInitialProfile(profile || {});
    }, [profile]);

    const handleInputChange = (field, value) => {
        setUserProfile((prevItem) => ({
            ...prevItem,
            [field]: value,
        }));
    };

    const handleEditClick = () => {
        setReadOnly(false);
    };

    const handleContinue = async() => {
        const response = await updateProfile(userProfile);
        if(response){
            setInitialProfile(userProfile);
            alert("Profile Updated!");
        } else {
            alert("Unable to Update Profile at the moment!");
        }
        setReadOnly(true);
    };

    const handleCancel = () => {
        setUserProfile(initialProfile);
        setReadOnly(true);
        onClose();
    };

    return (
        <Model
            isOpen={isOpen}
            title={title}
            onClose={handleCancel}
            onContinue={handleContinue}
            onEditClick={handleEditClick}
            showEditButton={readOnly}
            showCancel={!readOnly}
            showContinue={!readOnly}
        >
            <Form>
                <Form.Group controlId="userName">
                    <Form.Label><b>User Name:</b></Form.Label>
                    <Form.Control
                        type="text"
                        name="name"
                        value={userProfile?.name || ''}
                        onChange={(e) => handleInputChange('name', e.target.value)}
                        readOnly={readOnly}
                    />
                </Form.Group>

                <Row>
                    <Col md={5}>
                        <Form.Group controlId="phone">
                            <Form.Label><b>Contact:</b></Form.Label>
                            <Form.Control
                                type="text"
                                name="phone"
                                min={1}
                                value={userProfile?.phone || ''}
                                onChange={(e) => handleInputChange('phone', e.target.value)}
                                readOnly={readOnly}
                            />
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group controlId="email">
                            <Form.Label><b>Email:</b></Form.Label>
                            <Form.Control
                                type="email"
                                name="email"
                                value={userProfile?.email || ''}
                                onChange={(e) => handleInputChange('email', e.target.value)}
                                readOnly={readOnly}
                            />
                        </Form.Group>
                    </Col>
                </Row>

                <Form.Group controlId="address">
                    <Form.Label><b>Address:</b></Form.Label>
                    <Form.Control
                        type="text"
                        name="address"
                        value={userProfile?.address || ''}
                        onChange={(e) => handleInputChange('address', e.target.value)}
                        readOnly={readOnly}
                    />
                </Form.Group>
                {!readOnly && (
                    <Row>
                    <Col>
                        <Form.Group controlId="password">
                            <Form.Label><b>Password:</b></Form.Label>
                            <Form.Control
                                type="password"
                                name="password"
                                value={userProfile?.password || ''}
                                onChange={(e) => handleInputChange('password', e.target.value)}
                            />
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group controlId="confirmPwd">
                            <Form.Label><b>Re-Type Password:</b></Form.Label>
                            <Form.Control
                                type="password"
                                name="confirmPwd"
                                value={userProfile?.confirmPwd || ''}
                                onChange={(e) => handleInputChange('confirmPwd', e.target.value)}
                            />
                        </Form.Group>
                    </Col>
                </Row>                
                )}

            </Form>
        </Model>
    );
}

export default ProfileModel;
