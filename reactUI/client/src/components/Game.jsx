import React from "react";

const Game = ({ coord, elements, idx, player, playerCoord, currentRoom }) => {
  return (
    <div className="x-axis boxContainer">
      {elements.map((el) => {
        if (playerCoord.x === idx && playerCoord.y === el) {
          return (
            <div key={`${idx},${el}`} className="box playerChar" id="player">
              <h6>Lv.{player.lv}</h6>
              <h6>{player.name}</h6>
            </div>
          );
        }
        if (
          currentRoom.chest !== null &&
          coord.chest.x === idx &&
          coord.chest.y === el
        ) {
          let name = currentRoom.chest.broken ? "Broken Chest" : "Chest";
          return (
            <div key={`${idx},${el}`} className="box chest" id="chest">
              <h6>{name}</h6>
            </div>
          );
        } else {
          return (
            <div
              key={`${idx},${el}`}
              className="box empty"
              id={`${idx},${el}`}
            ></div>
          );
        }
      })}
    </div>
  );
};

export default Game;
