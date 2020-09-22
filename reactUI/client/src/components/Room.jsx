import React from "react";

const Room = ({ room }) => {
  return (
    <div className="roomContainer">
      <h5 className="status-title"> {`> ROOM ${room.id} <`}</h5>
      <div className="roomInfo">
        <p className="room">DESCRIPTION: {room.desc} </p>
        <p className="room">ITEMS: {room.items}</p>
        <p className="room">MONSTERS: {room.monsters}</p>
        <p className="room">CHEST: {room.chest}</p>
      </div>
    </div>
  );
};

export default Room;
