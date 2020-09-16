import React from "react";

const Display = ({ idx, line, length, ascii }) => {
  let className = "line";
  if (ascii) {
    className += " ascii-line";
  }
  return <p className={className}>{line}</p>;
};

export default Display;
