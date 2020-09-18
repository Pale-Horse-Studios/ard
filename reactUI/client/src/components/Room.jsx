import React from "react";

const Room = ({ room }) => {
  return (
    <div className="room">
      <h4 className="status-title">
        ------- ROOM {room.id}: {room.desc} -------
      </h4>
      <div className="roomInfo">
        <p className="room">ITEMS: {room.items}</p>
        <p className="room">MONSTERS: {room.monsters}</p>
        <p className="room">CHEST: {room.chest}</p>
      </div>
    </div>
  );
};

export default Room;
