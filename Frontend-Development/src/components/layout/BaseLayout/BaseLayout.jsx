import './BaseLayout.css'
import React from 'react'
import Footer from '../../common/Footer/Footer'
import Header from '../../common/Header/Header';

function BaseLayout({ children }) {
  return (
    <>
      <Header showUserName={false}/>
        <main className='content'>
            {children}
        </main>
        <Footer content="Online Food Delivery System - POD 2"/>
    </>
  )
}

export default BaseLayout;