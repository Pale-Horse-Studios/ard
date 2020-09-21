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
      player: {},
      room: null,
      playerLoaded: false,
      characterSelected: false,
      question: false,
      help: false,
      gameOver: false,
      gameScreen: { x: 6, y: 6 },
      screen: [],
      coord: {
        items: null,
        //monsters: null, // Is boss a monster?
        boss: null,
        chest: null,
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

    let { coord, gameScreen, room } = this.state;
    let playerPosition = coord.player;

    if (arrowPressed) {
      switch (arrowPressed[1].toLowerCase()) {
        case "up":
          if (this.boundaryCheck(playerPosition.y, gameScreen.y)) {
            playerPosition.y += 1;
          } else {
            console.log("can't proceed... Y-boundary");
          }
          break;
        case "down":
          if (this.boundaryCheck(playerPosition.y, 0)) {
            playerPosition.y -= 1;
          } else {
            console.log("can't proceed... Y-boundary");
          }
          break;
        case "left":
          if (this.boundaryCheck(playerPosition.x, 0)) {
            playerPosition.x -= 1;
          } else {
            console.log("can't proceed... X-boundary");
          }
          break;
        case "right":
          if (this.boundaryCheck(playerPosition.x, gameScreen.x)) {
            playerPosition.x += 1;
          } else {
            console.log("can't proceed... X-boundary");
          }
          break;
        default:
          console.log("Please check the boundaries");
      }
    }

    coord.player = playerPosition;
    this.setState({ coord });
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
        const { playerInfo, roomInfo } = data;
        this.setState({
          player: playerInfo,
          room: roomInfo,
          playerLoaded: true,
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

  generateRandomCoords() {
    let { room } = this.state;
    let coords = [];
    if (room !== null) {
      for (let key in room) {
        if (!room[key].includes("no") && !room[key].includes("present")) {
        }
      }
    }
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
    let { gameScreen } = this.state;
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
        this.loadGameScreen(gameScreen);
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
      room,
      coord,
      screen,
    } = this.state;
    return (
      <div className="main">
        <h1 className="title">A.R.D.</h1>
        <div className="mainScreen">
          <div className="helpScreen">
            {help
              ? menu.children.map((content, idx) => (
                  <Help
                    key={content.cat}
                    idx={idx}
                    size={menu.children.length}
                    content={content}
                  />
                ))
              : null}
          </div>
          <div className="message">
            <div className="banner">
              {banner.length > 0 && !bannerDisplayed
                ? banner.map((line, idx) => {
                    return (
                      <Display
                        key={idx + line}
                        idx={idx}
                        type="ascii-line"
                        line={line}
                      />
                    );
                  })
                : null}
            </div>
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
            {playerLoaded && screen.length > 0 ? (
              <div className="gameScreen">
                {screen.map((elements, idx) => (
                  <Game
                    key={`${idx}, ${elements[0]}`}
                    idx={idx}
                    coord={coord}
                    elements={elements}
                    playerLoc={player}
                    room={room}
                  />
                ))}
              </div>
            ) : null}
          </div>
          {playerLoaded ? (
            <div className="status">
              <Player player={player} />
              <Room room={room} />
            </div>
          ) : null}
          <form onSubmit={this.handleSubmit} className="userInput">
            <input
              type="text"
              value={this.state.command}
              onChange={this.handleChange}
              placeholder="Enter your command here..."
            />
            <button>Enter</button>
          </form>
        </div>
      </div>
    );
  }
}

export default App;
