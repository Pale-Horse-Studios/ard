import React from "react";

const Help = ({ content, idx, size }) => {
  return (
    <div className="help">
      <h2 className="help-title">{content.title}</h2>
      <h3 className="help-desc">{content.description}</h3>
      <div className="help-inst">
        <h3>[N]: Next Page</h3>
        <h3>[P]: Prev Page</h3>
        <h3>[X]: Exit</h3>
      </div>
    </div>
  );
};

export default Help;
