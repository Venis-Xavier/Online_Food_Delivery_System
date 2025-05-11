import React, { useState } from 'react';
import { Form, Row, Col } from 'react-bootstrap';
import Model from '../../common/Model/Model';


function MenuModel({ isOpen, title, selectedItem: initialSelectedItem, onClose, onContinue, isCreate}) {
    const [selectedItem, setSelectedItem] = useState(initialSelectedItem || {});
    const [selectedFile, setSelectedFile] = useState(null);

    const handleInputChange = (field, value) => {
        setSelectedItem((prevItem) => ({
            ...prevItem,
            [field]: value,
        }));
    };

    const handleFileChange = (e) => {
        setSelectedFile(e.target.files[0]); 
    };

    const handleContinue = () => {
        const formData = new FormData();
        formData.append('itemId', selectedItem.itemId || '')
        formData.append('itemName', selectedItem.itemName || '');
        formData.append('itemDesc', selectedItem.itemDesc || '');
        formData.append('price', selectedItem.price || 100);
        formData.append('isAvailable', selectedItem.isAvailable || false);
        formData.append('isVeg', selectedItem.isVeg || false);

        if (selectedFile) {
            formData.append('itemImage', selectedFile); 
        }

        onContinue(formData, isCreate);
    };

    return (
        <Model
            isOpen={isOpen}
            title={title}
            onClose={onClose}
            onContinue={handleContinue}
        >
            <Form>
                <Form.Group controlId="itemName">
                    <Form.Label><b>Item Name:</b></Form.Label>
                    <Form.Control
                        type="text"
                        name="itemName"
                        value={selectedItem.itemName || ''}
                        onChange={(e) => handleInputChange('itemName', e.target.value)}
                    />
                </Form.Group>

                <Form.Group controlId="description">
                    <Form.Label><b>Description:</b></Form.Label>
                    <Form.Control
                        type="text"
                        name="description"
                        value={selectedItem.itemDesc || ''}
                        onChange={(e) => handleInputChange('itemDesc', e.target.value)}
                    />
                </Form.Group>
                <Row>
                    <Col md={4}>
                        <Form.Group controlId="price">
                            <Form.Label><b>Item Price:</b></Form.Label>
                            <Form.Control
                                type="number"
                                name="price"
                                min={1}
                                value={selectedItem.price || ''}
                                onChange={(e) => handleInputChange('price', e.target.value)}
                            />
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group controlId="file">
                            <Form.Label><b>Item Image:</b></Form.Label>
                            <Form.Control
                                type="file"
                                name="itemImage"
                                onChange={handleFileChange}
                            />
                        </Form.Group>
                    </Col>
                </Row>



                <Row>
                    <Col>
                        <Form.Check
                            type="switch"
                            id="available-switch"
                            label="Available"
                            checked={selectedItem.isAvailable || false}
                            onChange={(e) => handleInputChange('isAvailable', e.target.checked)}
                        />
                    </Col>
                    <Col>
                        <Form.Check
                            type="switch"
                            id="vegetarian-switch"
                            label="Vegetarian"
                            checked={selectedItem.isVeg || false}
                            onChange={(e) => handleInputChange('isVeg', e.target.checked)}
                        />
                    </Col>
                </Row>
            </Form>
        </Model>
    );
}

export default MenuModel;
