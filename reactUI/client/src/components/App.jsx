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
      // combine room & currentRoom
      room: null,
      currentRoom: null,
      playerLoaded: false,
      characterSelected: false,
      question: false,
      help: false,
      gameOver: false,
      nameSubmitted: false,
      gameScreen: null,
      screen: [],
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleKeyPress = this.handleKeyPress.bind(this);
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

  handleKeyPress(event) {
    const arrowRegex = /Arrow(Up|Down|Left|Right)/;
    let arrowPressed = event.key.match(arrowRegex);
    let helpNav = ["n", "p", "x"];
    let gameOverChoice = ["y", "n"];

    let { helpIdx, help, gameOver, nameSubmitted } = this.state;

    if (arrowPressed) {
      let direction = arrowPressed[1].toLowerCase();
      this.checkObstacle(direction);
    }

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
    } else if (nameSubmitted && gameOverChoice.includes(key)) {
      let path;
      if (key === "n") {
        window.location.href = "http://www.amazon.com";
      } else {
        window.location.href = "http://127.0.0.1:5000/";
        // axios
        //   .get("/playAgain")
        //   .then(({ data }) => {
        //     console.log("restarting the game...");
        //   })
        //   .catch((err) => console.log(err));
      }
    }
  }

  checkObstacle(direction) {
    let { player, room } = this.state;
    let x = parseInt(player.x);
    let y = parseInt(player.y);
    if (direction === "up") {
      y += 1;
    } else if (direction === "down") {
      y -= 1;
    } else if (direction === "left") {
      x -= 1;
    } else if (direction === "right") {
      x += 1;
    }

    let obstacle = false;
    let isItem = false;
    let command;

    for (let i = 1; i < room.length; i++) {
      if (parseInt(room[i]["x"]) === x && parseInt(room[i]["y"]) === y) {
        obstacle = true;
        if (room[i].type === "item") {
          command = `pickup ${room[i].name}`;
          isItem = true;
        } else if (room[i].type === "monster") {
          command = `fight ${room[i].type}`;
        } else if (room[i].type === "chest") {
          command = `unlock ${room[i].name}`;
        }
      }
    }

    if (obstacle && !isItem) {
      axios
        .get(`/command/${command}`)
        .then(({ data }) => {
          // Update player & room
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
          }
        })
        .catch((err) => {
          console.log(err);
        });
    } else if (obstacle && isItem) {
      axios
        .get(`/command/${command}`)
        .then(({ data }) => {
          // Update player & room
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
          }
          axios
            .get(`/nav/${direction}`)
            .then(({ data }) => {
              let { playerInfo, response } = data;
              this.setState({ player: playerInfo, prompt: [response] });
            })
            .catch((err) => {
              console.log(err);
            });
        })
        .catch((err) => {
          console.log(err);
        });
    } else {
      axios
        .get(`/nav/${direction}`)
        .then(({ data }) => {
          let { playerInfo, response } = data;
          this.setState({ player: playerInfo, prompt: [response] });
        })
        .catch((err) => {
          console.log(err);
        });
    }
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
        this.setState({ nameSubmitted: true });
      } else {
        route = "command";
      }
      axios
        .get(`/${route}/${command}`)
        .then(({ data }) => {
          // Update player & room
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
          }
        })
        .catch((err) => {
          console.log(err);
        });
    }

    this.initialize();
  }

  updateStatus() {
    axios
      .get(`/stat`)
      .then(({ data }) => {
        // Update player & room
        const { playerInfo, roomInfo, currentRoom } = data;
        this.loadGameScreen({ x: roomInfo[0].x, y: roomInfo[0].y });
        this.setState({
          player: playerInfo,
          room: roomInfo,
          playerLoaded: true,
          currentRoom,
        });
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
    document.body.onkeydown = this.handleKeyPress;
    const msg = this.state.prompt;
    let {
      playerLoaded,
      banner,
      bannerDisplayed,
      help,
      player,
      currentRoom,
      room,
      screen,
      helpIdx,
      gameOver,
      nameSubmitted,
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
          {playerLoaded && room !== null && !gameOver ? (
            <div className="mainScreen">
              <div className="main-left">
                {screen.length > 0 ? (
                  <div className="gameScreen">
                    {screen.map((elements, idx) => (
                      <Game
                        key={`${idx}, ${elements[0]}`}
                        idx={idx}
                        elements={elements}
                        currentRoom={currentRoom}
                        player={player}
                        room={room}
                      />
                    ))}
                  </div>
                ) : null}
              </div>
              <div className="main-right">
                <div className="status">
                  <Player player={player} />
                  <Room room={currentRoom} />
                </div>
              </div>
            </div>
          ) : playerLoaded && gameOver ? (
            <div className="mainScreen game-over" id="game-over">
              <h1>GAME OVER</h1>
              {nameSubmitted ? (
                <div className="game-over">
                  <p id="over">Would you like to play again?</p>
                  <p id="over">Press [Y] for YES or [N] for NO</p>
                </div>
              ) : null}
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
