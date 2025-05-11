import React, { useEffect, useState } from 'react';
import Header from '../../common/Header/Header';
import Sidebar from '../../common/Sidebar/Sidebar';
import Footer from '../../common/Footer/Footer';
import { agentPages, customerPages, managerPages } from '../../../data/pages';
import { getUserProfile } from '../../../utils/app_utils';

function PortalLayout({ children, role}) {

  const [user, setUser] = useState('');

  useEffect(() => {
    const fetchUser = async () => {
      const response = await getUserProfile();
      setUser(response);
    };

    fetchUser();
  }, []);

  return (
    <>
    <Header user={user}/>
    <Sidebar pages={role === "MANAGER" ? managerPages : role === "CUSTOMER" ? customerPages : agentPages} />
    <div className='portal-content'>
        {children}
    </div>
    <Footer content="Online Food Delivery System - POD 2"/>
    </>
  );
}

export default PortalLayout;