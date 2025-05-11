import React, { useState, useEffect } from 'react';
import { Alert } from 'react-bootstrap';


const AutoCloseAlert = ({ variant, message, duration }) => {
    const [show, setShow] = useState(true);
  
    useEffect(() => {
      const timer = setTimeout(() => {
        setShow(false);
      }, duration);
  
      return () => clearTimeout(timer);
    }, [duration]);
  
    if (!show) {
      return null;
    }
  
    return (
      <Alert variant={variant} onClose={() => setShow(false)} dismissible>
        {message}
      </Alert>
    );
  };

export default AutoCloseAlert;
  