import './Button.css'
import React from 'react';

function Button({buttonText, onClickAction, buttonIcon, className, disabled}) {
  return (
    <button className={className} onClick={onClickAction} disabled={disabled}><i className={buttonIcon}></i> &nbsp;{buttonText}</button>
  )
}

export default Button;