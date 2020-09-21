import React, { Children } from "react";

const Help = ({ content, idx, size }) => {
  return (
    <div className="help">
      <h4 className="help-title">{content.title}</h4>
      <p className="help-desc">{content.description}</p>
    </div>
  );
};

export default Help;