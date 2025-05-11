import React from 'react';
import './Input.css';

/**
 * Input component for rendering different types of input fields.
 * @param {string} inputType - The type of the input field (e.g., text, password).
 * @param {string} inputName - The name attribute for the input field.
 * @param {string} placeholder - Placeholder text for the input field.
 * @param {string} value - Controlled value of the input field.
 * @param {string} defaultValue - Default value for the input field.
 * @param {function} onChangeAction - Function to handle changes in the input field.
 * @param {string} inputPattern - Pattern for validating the input value.
 * @param {string} className - Additional CSS classes for styling.
 * @param {boolean} isRequired - Indicates if the input field is required.
 * @param {number} max - Maximum length of the input value.
 */
function Input({ inputType, inputName, placeholder, value, defaultValue, onChangeAction, inputPattern, className, isRequired, max}) {
  return (
    <input
        maxLength={max}
        required={isRequired}
        type={inputType}
        name={inputName}
        value={value}
        defaultValue={defaultValue}
        className={className}
        pattern={inputPattern}
        placeholder={placeholder}
        onChange={onChangeAction} />
  )
}

export default Input;