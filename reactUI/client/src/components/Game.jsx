import React from "react";

const Game = ({ coord, elements, idx, player, playerCoord }) => {
  let { items, monsters, chest } = coord;
  // if (items.length > 0 && monsters.length === 0) {
  //   return (
  //     <div className="x-axis boxContainer">
  //       {elements.map((el) => {
  //         if (playerCoord.x === idx && playerCoord.y === el) {
  //           changed = true;
  //           return (
  //             <div key={`${idx},${el}`} className="box playerChar" id="player">
  //               <h6>Lv.{player.lv}</h6>
  //               <h6>{player.name}</h6>
  //             </div>
  //           );
  //         }
  //         if (chest !== null && chest.x === idx && chest.y === el) {
  //           changed = true;
  //           return (
  //             <div key={`${idx},${el}`} className="box chest" id="chest">
  //               <h6>Chest</h6>
  //             </div>
  //           );
  //         }
  //         for (let i = 0; i < items.length; i++) {
  //           if (items[i].x === idx && items[i].y === el) {
  //             changed = true;
  //             console.log(items, items[i]);
  //             return (
  //               <div
  //                 key={`${idx},${el}`}
  //                 className="box item"
  //                 id={items[i].name}
  //               >
  //                 <h6>{items[i].name}</h6>
  //               </div>
  //             );
  //           } else {
  //             return (
  //               <div
  //                 key={`${idx},${el}`}
  //                 className="box empty"
  //                 id={`${idx},${el}`}
  //               ></div>
  //             );
  //           }
  //         }
  //       })}
  //     </div>
  //   );
  // }
  // else {
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
        if (chest !== null && chest.x === idx && chest.y === el) {
          return (
            <div key={`${idx},${el}`} className="box chest" id="chest">
              <h6>Chest</h6>
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
  //}
};

export default Game;
