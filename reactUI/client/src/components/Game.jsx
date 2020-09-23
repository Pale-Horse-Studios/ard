import React from "react";

const Game = ({ elements, idx, player, room }) => {
  let character;
  if (player.name.toLowerCase().includes("iron")) {
    character = "iron-man";
  } else {
    character = "wolverine";
  }
  return (
    <div className="x-axis boxContainer">
      {elements.map((el) => {
        let changed = false;
        if (parseInt(player.x) === idx && parseInt(player.y) === el) {
          changed = true;
          return (
            <div
              key={`${idx},${el}`}
              className="box playerChar"
              id={character}
            ></div>
          );
        }
        // Starting from index 1. Index 0 is general room information.
        for (let i = 1; i < room.length; i++) {
          if (parseInt(room[i]["x"]) === idx && parseInt(room[i]["y"]) === el) {
            changed = true;
            let type;
            if (
              room[i].type === "chest" &&
              room[i].name.toLowerCase().includes("broke")
            ) {
              type = "broken-chest";
            } else {
              type = room[i].type;
            }
            return (
              <div
                key={`${idx},${el}`}
                className={`box ${type}`}
                id={type}
              ></div>
            );
          }
        }
        if (!changed) {
          return <div key={`${idx},${el}`} className="box empty"></div>;
        }
      })}
    </div>
  );
};

export default Game;
