import React from "react";

const Game = ({ coord, elements, idx, player, room }) => {
  let { items, monsters, boss } = coord;
  console.log(room);
  return (
    <div className="x-axis boxContainer">
      {elements.map((el) => {
        if (player.x === idx && player.y === el) {
          return (
            <div key={`${idx},${el}`} className="box player" id="player">
              <h5>{player.name}</h5>
            </div>
          );
        } else if (monsters.x === idx && monsters.y === el) {
          return (
            <div
              key={`${idx},${el}`}
              className="box monster"
              id={`${idx},${el}`}
            >
              <h5>M</h5>
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
