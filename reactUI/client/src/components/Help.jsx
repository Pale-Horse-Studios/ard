import React from "react";

const Help = (props) => {
  let { helpContent }= props.content;
  return (
  <div className="help">
    <h3>{helpContent.title}</h3>
    <p>{helpContent.desc}</p>
    {props.id === 0 ? <h2>Type "CLOSE" to exit</h2> : null}
  </div>
  );
}

export default Help;