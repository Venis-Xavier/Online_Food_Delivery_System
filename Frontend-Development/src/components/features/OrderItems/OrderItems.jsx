import React from 'react';
import { Table } from 'react-bootstrap';
import './OrderItems.css'

const OrderItems = ({ headers, data }) => {
  return (
    <div className="table-responsive">
        <h5>Order Items</h5>
      <Table>
        <thead>
          <tr>
            {headers.map((header, index) => (
              <th  className="text-center" key={index}  style={index === 0 || index === 2  ? { width: "20%" } : {}} >{header}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {data.length > 0 ? (
            data.map((row, rowIndex) => (
              <tr key={rowIndex}>
                {headers.map((header, cellIndex) => (
                  <td  className="text-center" key={cellIndex}>{row[header]}</td>
                ))}
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={headers.length} className="text-center">
                No data available
              </td>
            </tr>
          )}
        </tbody>
      </Table>
    </div>
  );
};

export default OrderItems;
