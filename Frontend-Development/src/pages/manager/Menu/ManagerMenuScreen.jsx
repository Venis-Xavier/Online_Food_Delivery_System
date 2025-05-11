import React, { useEffect, useState } from 'react';
import PortalLayout from '../../../components/layout/PortalLayout/PortalLayout';
import MenuItem from '../../../components/features/MenuItem/MenuItem';
import { ALL, MENU } from '../../../utils/endpoints';
import { getData } from '../../../utils/api_utils';
import Button from '../../../components/common/Button/Button';
import MenuModel from '../../../components/features/MenuModel/MenuModel';
import { createMenuItem, deleteMenuItem, updateMenuItem, uploadRestaurantImage } from '../../../utils/app_utils';
import Model from '../../../components/common/Model/Model';
import { Form } from 'react-bootstrap';

function ManagerMenuScreen() {
  const [menu, setMenu] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [showImgModal, setShowImgModal] = useState(false);
  const [selectedItem, setSelectedItem] = useState(null);
  const [selectedFile, setSelectedFile] = useState(null);

  const fetchMenu = async () => {
    const response = await getData(MENU, ALL);
    setMenu(response?.data);
  };

  useEffect(() => {
    fetchMenu();
  }, []);

  const handleEditItem = (item) => {
    setSelectedItem(item);
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setShowImgModal(false);
    setSelectedItem(null);
  };

  const handleSaveChanges = async (payload, isCreate) => {
    isCreate
      ? await createMenuItem(payload)
      : await updateMenuItem(payload);
    fetchMenu();
    setShowModal(false);
  };

  const handleAddMenu = () => {
    setSelectedItem({});
    setShowModal(true);
  };

  const handleRestaurantImage = () => {
    setShowImgModal(true);
  }

  const deleteButtonClick = async (itemId) => {
    await deleteMenuItem({ itemId });
    fetchMenu();
  }

  const uploadImage = async (event) => {
    const formData = new FormData();
    formData.append('image', selectedFile);
    const response = await uploadRestaurantImage(formData);

    if(response){
      alert("Image Uploaded Successfully!");
      setShowImgModal(false);
    } else{
      alert("Unable to Upload Image at the moment!");
    }

  }

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]); 
};


  return (
    <PortalLayout role="MANAGER">
      <div className="menu-screen">
        <div className="menu-container">
          <div className='center m-3 gap-3'>
            <Button
              buttonText={"Add a Menu"}
              buttonIcon={"bi bi-plus"}
              onClickAction={() => handleAddMenu()} />

            <Button
              buttonText={"Edit Restaurant Image"}
              buttonIcon={"bi bi-image"}
              onClickAction={() => handleRestaurantImage()} />
          </div>
          {menu?.map((item) => (
            <MenuItem
              key={item.itemId}
              isVeg={item.veg}
              itemId={item.itemId}
              itemName={item.itemName}
              itemPrice={item.price}
              description={item.itemDesc}
              itemImg={item.itemImg}
              showOptions={true}
              onClickAction={() => handleEditItem(item)}
              onDeleteAction={() => deleteButtonClick(item.itemId)}
            />
          ))}
        </div>
      </div>

      {/* Modal Component */}
      {showModal && (
        <MenuModel
          isOpen={showModal}
          title={selectedItem?.itemName ? 'Edit Item' : 'New Item'}
          onClose={handleCloseModal}
          onContinue={(payload, isCreate) => handleSaveChanges(payload, isCreate)}
          selectedItem={selectedItem}
          isCreate={selectedItem?.itemName ? false : true} />
      )}

      {showImgModal && (
        <Model
          isOpen={showImgModal}
          title={"Edit Image"}
          onClose={handleCloseModal}
          onContinue={uploadImage}>
          <Form.Group controlId="file">
            <Form.Label><b>Restaurant Image:</b></Form.Label>
            <Form.Control
              type="file"
              name="restaurantImg"
              onChange={handleFileChange}
            />
          </Form.Group>
        </Model>
      )}

    </PortalLayout>
  );
}

export default ManagerMenuScreen;
