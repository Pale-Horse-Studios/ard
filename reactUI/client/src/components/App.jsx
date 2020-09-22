import React from "react";
import axios from "axios";
import Game from "./Game.jsx";
import Display from "./Display.jsx";
import Player from "./Player.jsx";
import Room from "./Room.jsx";
import Help from "./Help.jsx";
import { menu } from "./help.json";

class App extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      prompt: [],
      command: "",
      banner: "",
      bannerDisplayed: false,
      helpIdx: 0,
      player: {},
      playerCoord: { x: null, y: null },
      // combine room & currentRoom
      room: null,
      currentRoom: null,
      playerLoaded: false,
      characterSelected: false,
      question: false,
      help: false,
      gameOver: false,
      gameScreen: null,
      screen: [],
      coord: {
        items: [],
        monsters: [],
        // Is boss a monster?
        //boss: null,
        chest: {},
      },
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.navigate = this.navigate.bind(this);
  }

  handleChange({ target }) {
    this.setState({ command: target.value });
  }

  // Need to add obstacle check!!!!!!!!!!!!!!!!!!
  boundaryCheck(position, limit) {
    if (limit === 0) {
      return position > 0 ? true : false;
    } else {
      return position < limit - 1 ? true : false;
    }
  }

  navigate(event) {
    const arrowRegex = /Arrow(Up|Down|Left|Right)/;
    let arrowPressed = event.key.match(arrowRegex);
    let helpNav = ["n", "p", "x"];

    let { playerCoord, gameScreen, helpIdx, help } = this.state;
    let playerPosition = playerCoord;

    let key = event.key.toLowerCase();
    if (help && helpNav.includes(key)) {
      if (key === "n") {
        if (helpIdx < menu.children.length - 1) {
          helpIdx += 1;
        } else {
          helpIdx = 0;
        }
      } else if (key === "p") {
        if (helpIdx > 0) {
          helpIdx -= 1;
        } else {
          helpIdx = menu.children.length - 1;
        }
      } else {
        help = false;
      }
      this.setState({ helpIdx, help });
    }

    if (arrowPressed) {
      switch (arrowPressed[1].toLowerCase()) {
        case "up":
          if (this.boundaryCheck(playerPosition.y, gameScreen.y)) {
            playerPosition.y += 1;
          } else {
            this.setState({ prompt: ["Banging my head against a wall..."] });
          }
          break;
        case "down":
          if (this.boundaryCheck(playerPosition.y, 0)) {
            playerPosition.y -= 1;
          } else {
            this.setState({
              prompt: ["Banging my head against a wall...Are you serious?"],
            });
          }
          break;
        case "left":
          if (this.boundaryCheck(playerPosition.x, 0)) {
            playerPosition.x -= 1;
          } else {
            this.setState({
              prompt: [
                "Banging my head against a wall... Can't fix stupidity.",
              ],
            });
          }
          break;
        case "right":
          if (this.boundaryCheck(playerPosition.x, gameScreen.x)) {
            playerPosition.x += 1;
          } else {
            this.setState({
              prompt: ["Dumber than a rock... Literally..."],
            });
          }
          break;
        default:
          console.log("Please check the boundaries");
      }
    }

    playerCoord = playerPosition;
    this.setState({ playerCoord });
  }

  handleSubmit(event) {
    event.preventDefault();
    // Send GET Request to /command/?command
    let { command, characterSelected, question, gameOver } = this.state;
    if (command.trim().toLowerCase().includes("help")) {
      this.setState({ help: true });
    } else if (command.trim().toLowerCase().includes("close")) {
      this.setState({ help: false });
    } else {
      let route;
      if (characterSelected) {
        route = "character";
      } else if (question) {
        route = "answer";
      } else if (gameOver) {
        route = "score";
      } else {
        route = "command";
      }
      axios
        .get(`/${route}/${command}`)
        .then(({ data }) => {
          // Update player & room
          console.log(data);
          let { response, characterSelected, question, gameOver } = data;
          let prompt = this.cleanUpResponse(response);
          this.setState({
            prompt,
            characterSelected,
            question,
            bannerDisplayed: true,
            gameOver,
          });
          if (!characterSelected && !question && !gameOver) {
            this.updateStatus();
          } else {
            this.generateRandomCoords(false);
          }
        })
        .catch((err) => {
          console.log(err);
        });
    }

    this.initialize();
  }

  updateStatus() {
    let prevRoom = this.state.currentRoom;
    axios
      .get(`/stat`)
      .then(({ data }) => {
        // Update player & room
        const { playerInfo, roomInfo, currentRoom } = data;
        this.setState({
          player: playerInfo,
          room: roomInfo,
          playerLoaded: true,
          currentRoom,
        });
        if (prevRoom === null) {
          // Initial random load
          this.generateRandomCoords(true);
        } else if (prevRoom.id !== currentRoom.id) {
          this.generateRandomCoords(true);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  }

  initialize() {
    this.setState({ command: "" });
  }

  cleanUpResponse(res) {
    // \u001b[ => build color chart
    const pattern = /[[]\d{1,2}[m]/gi;
    res = res.replace(pattern, "");
    return res.split("\n");
  }

  generateRandomCoords(needNewRoom) {
    let gameScreen;
    if (needNewRoom) {
      gameScreen = { x: this.getRandomInt(), y: this.getRandomInt() };
    } else {
      gameScreen = this.state.gameScreen;
    }

    let { playerCoord, currentRoom, coord } = this.state;

    playerCoord.x = Math.floor(gameScreen.x / 2);
    playerCoord.y = Math.floor(gameScreen.y / 2);

    if (needNewRoom) {
      this.loadGameScreen(gameScreen);

      coord = {
        items: [],
        monsters: [],
        chest: {},
      };

      currentRoom.items.forEach((item) => {
        let newItem = {};
        newItem.name = item;
        newItem.x = this.getRandomInt(0, gameScreen.x);
        newItem.y = this.getRandomInt(0, gameScreen.y);
        coord.items.push(newItem);
      });

      currentRoom.monsters.forEach((monster) => {
        let newMonster = {};
        newMonster.name = monster;
        newMonster.x = this.getRandomInt(0, gameScreen.x);
        newMonster.y = this.getRandomInt(0, gameScreen.y);
        coord.monsters.push(newMonster);
      });

      if (currentRoom.chest !== null) {
        coord.chest.x = this.getRandomInt(0, gameScreen.x);
        coord.chest.y = this.getRandomInt(0, gameScreen.y);
      }
    }

    this.setState({
      gameScreen,
      playerCoord,
      coord,
    });
  }

  loadGameScreen({ x, y }) {
    let screen = [];
    for (let i = 0; i < x; i++) {
      let xAxis = [];
      for (let j = 0; j < y; j++) {
        xAxis.push(j);
      }
      screen.push(xAxis);
    }
    this.setState({ screen });
  }

  getRandomInt(min = 5, max = 10) {
    return Math.floor(Math.random() * (max - min) + min);
  }

  componentDidMount() {
    // Loading intro from server
    axios
      .get("/intro")
      .then(({ data }) => {
        let { response, banner, characterSelected, question } = data;
        let prompt = this.cleanUpResponse(response);
        banner = this.cleanUpResponse(banner);
        this.setState({
          prompt,
          banner,
          characterSelected,
          question,
        });
      })
      .catch((err) => {
        console.log("Error fetching data from server: ", err);
      });
  }

  render() {
    document.body.onkeydown = this.navigate;
    const msg = this.state.prompt;
    let {
      playerLoaded,
      banner,
      bannerDisplayed,
      help,
      player,
      currentRoom,
      room,
      coord,
      screen,
      playerCoord,
      helpIdx,
    } = this.state;

    return (
      <div className="main">
        <h1 className="title">A.R.D.</h1>
        <div className="display">
          <div className="info">
            {help ? (
              <div className="helpScreen">
                {menu.children.map((content, idx) =>
                  helpIdx === idx ? (
                    <Help key={content.cat} id={idx} content={content} />
                  ) : null
                )}
              </div>
            ) : null}

            <div className="message">
              {banner.length > 0 && !bannerDisplayed
                ? banner.map((line, idx) => {
                    return (
                      <div className="banner">
                        <Display
                          key={idx + line}
                          idx={idx}
                          type="ascii-line"
                          line={line}
                        />
                      </div>
                    );
                  })
                : null}
              {msg.length > 0
                ? msg.map((line, idx) => {
                    return (
                      <Display
                        key={idx + line}
                        idx={idx}
                        type="line"
                        line={line}
                      />
                    );
                  })
                : null}
            </div>
          </div>
          {playerLoaded ? (
            <div className="mainScreen">
              <div className="main-left">
                {screen.length > 0 ? (
                  <div className="gameScreen">
                    {screen.map((elements, idx) => (
                      <Game
                        key={`${idx}, ${elements[0]}`}
                        idx={idx}
                        coord={coord}
                        elements={elements}
                        currentRoom={currentRoom}
                        player={player}
                        playerCoord={playerCoord}
                      />
                    ))}
                  </div>
                ) : null}
              </div>
              <div className="main-right">
                <div className="status">
                  <Player player={player} />
                  <Room room={room} />
                </div>
              </div>
            </div>
          ) : null}
          <form onSubmit={this.handleSubmit} className="userInput">
            <input
              type="text"
              value={this.state.command}
              onChange={this.handleChange}
              placeholder="Enter your command here..."
              required
            />
            <button>Enter</button>
          </form>
        </div>
      </div>
    );
  }
}

export default App;
