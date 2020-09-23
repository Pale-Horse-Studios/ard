import React from "react";

const Room = ({ room }) => {
  let { items, monsters, chest } = room;
  let chestPresent = chest === null ? false : true;
  return (
    <div className="roomContainer">
      <h5 className="status-title"> {`> ROOM ${room.id} <`}</h5>
      <div className="roomInfo">
        <p className="room">DESCRIPTION: {room.description} </p>
        <div className="status-item">
          <p className="room">ITEMS:</p>
          {items.length > 0 ? (
            items.map((item, idx) => (
              <p key={idx} className="room">
                {item}
              </p>
            ))
          ) : (
            <p className="room">No Item present in this room</p>
          )}
        </div>
        <div className="status-item">
          <p className="room">MONSTERS:</p>
          {monsters.length > 0 ? (
            monsters.map((monster, idx) => (
              <p key={idx} className="room">
                {`${monster.name}(HP: ${monster.life})`}
              </p>
            ))
          ) : (
            <p className="room">No Monster present in this room</p>
          )}
        </div>
        <div className="status-item">
          <p className="room">CHEST:</p>
          {!chestPresent ? (
            <p className="room">No chest present in this room.</p>
          ) : !chest.broken ? (
            <p className="room">Treasure Chest</p>
          ) : (
            <p className="room">Broken Chest</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default Room;
