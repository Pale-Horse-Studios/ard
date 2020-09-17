import React from "react";

const Player = ({ player }) => {
  let name = player.name.toUpperCase();
  return (
    <div className="player">
      <div className="playerInfo">
        <h4 className="status-title">------- {name} -------</h4>
        <div className="playerInfo" id="playerInfo">
          <p className="playerInfo">HP: {player.life}</p>
          <p className="playerInfo">LV: {player.lv}</p>
          <p className="playerInfo">SCORE: {player.score}</p>
        </div>
      </div>
      <div className="playerInv">
        <h4 className="status-title">------- INVENTORY -------</h4>
        <p className="playerInv">{player.inv}</p>
      </div>
    </div>
  );
};

export default Player;
