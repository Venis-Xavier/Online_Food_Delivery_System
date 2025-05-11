import './Footer.css';
import React from 'react';

function Footer({ content }) {
    return (
      <footer className="footer">
        <div className='footer-content'></div>{content}
      </footer>
    );
}

export default Footer;