import React from "react";
import axios from "axios";
import Display from "./Display.jsx";
import Player from "./Player.jsx";
import Room from "./Room.jsx";
import Help from "./Help.jsx";
import { menu } from "./sample.json";

class App extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      prompt: [],
      command: "",
      banner: "",
      bannerDisplayed: false,
      player: {},
      room: {},
      playerLoaded: false,
      characterSelected: false,
      question: false,
      help: false,
      gameOver: false,
      coord: [
        {
          player: { x: 0, y: 2 },
          items: { x: 2, y: 2 },
          monsters: { x: 4, y: 0 }, // Is boss a monster?
          boss: null,
        },
      ],
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange({ target }) {
    this.setState({ command: target.value });
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
    const msg = this.state.prompt;
    let {
      playerLoaded,
      banner,
      bannerDisplayed,
      help,
      player,
      room,
      coord,
    } = this.state;
    return (
      <div className="main">
        <h1 className="title">A.R.D.</h1>

        <div className="mainScreen">
          <div className="helpScreen">
            {help
              ? menu.map((content, idx) => <Help id={idx} content={content} />)
              : null}
          </div>
          <div className="message">
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
            {playerLoaded ? <Game coord={coord} x={5} y={5} /> : null}
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
