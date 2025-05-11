import React from 'react';
import './Model.css';
import Button from '../Button/Button';

function Model({ isOpen, onClose, onContinue, title, children, showCancel = true, showContinue = true, showEditButton = false, onEditClick }) {

  if (!isOpen) {
    return null;
  }

  return (
    <div className='modal-overlay' aria-hidden={!isOpen}>
      <div className='modal-content' role='dialog' aria-labelledby='modal-title'>
        <div className='modal-header'>
          <h2 id='modal-title' className='modal-title'>{title}</h2>
          {/* <button className='modal-close' onClick={onClose} aria-label='Close'>&times;</button> */}
        </div>
        <div className='modal-body'>
          {children}
        </div>
        <div className='modal-footer'>
          {(showCancel || showEditButton) && (
            <Button onClickAction={onClose} buttonText={"Cancel"}/>
          )}
          {showContinue && (
            <Button onClickAction={onContinue} buttonText={"Continue"}/>
          )}
          {showEditButton && (
            <Button onClickAction={onEditClick} buttonText={"Edit"}/>
          )}
        </div>
      </div>
    </div>
  );
}

export default Model;