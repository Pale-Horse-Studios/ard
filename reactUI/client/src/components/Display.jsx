import React from "react";

const Display = ({ line, type }) => {
  return <p className={type}>{line}</p>;
};

export default Display;
