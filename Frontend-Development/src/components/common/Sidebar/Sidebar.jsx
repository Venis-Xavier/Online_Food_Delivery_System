import './Sidebar.css';
import React from 'react';

function Sidebar({ pages }) {
  return (
    <aside className="sidebar">
      <ul className="sidebar-list">
        {pages.map((page, index) => (
          <li key={index}>
            <a href={page.link}>
              <i className={page.icon}></i>
              {page.label}
            </a>
          </li>
        ))}
      </ul>
    </aside>
  )
}

export default Sidebar;