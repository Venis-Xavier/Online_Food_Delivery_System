import React from 'react';
import './Select.css';

function Select({ options, onChangeAction }) {
    return (
        <select onChange={onChangeAction}>
          {options.map((option, index) => (
            <option key={index} value={option.value}>
              {option.label}
            </option>
          ))}
        </select>
    );
}

export default Select;