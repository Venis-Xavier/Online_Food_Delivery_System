import React, { useState } from 'react';
import './Header.css';
import { Container, Nav, Navbar } from 'react-bootstrap';
import ProfileModel from '../../features/ProfileModel/ProfileModel';
import NavDropdown from 'react-bootstrap/NavDropdown';
import Logo from '../../../assets/image.png';

function Header({ user, showUserName = true }) {

  const [showModal, setShowModal] = useState(false);

  const handleProfileClick = () => {
    setShowModal(true);
  }

  const handleCloseModal = () => {
    setShowModal(false);
  }

  const handleLogOut = () => {
    localStorage.removeItem('token');
    window.location.reload();
  }

  return (
    <>
      <Navbar className="bg-body-tertiary header">
        {showUserName && (
          <Container>

            <Navbar.Brand href="#home">
              <img
                alt=""
                src={Logo}
                width="32"
                height="32"
                className="d-inline-block align-top"
              />{'  '}
              Food Pilot
            </Navbar.Brand>

            <Nav>
              <NavDropdown title={`Hello, ${user?.name} 👋`} id="nav-dropdown">
                <NavDropdown.Item onClick={handleProfileClick}>Profile</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item onClick={handleLogOut}>Log Out</NavDropdown.Item>
              </NavDropdown>
            </Nav>
          </Container>
        )}
      </Navbar>

      {showModal && (
        <ProfileModel
          isOpen={showModal}
          title={"Profile"}
          profile={user}
          onClose={handleCloseModal} />
      )}
    </>
  );
}

export default Header;