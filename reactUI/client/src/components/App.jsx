import React from "react";
import axios from "axios";
import Display from "./Display.jsx";
import Help from "./Help.jsx";
import { helpContent } from "./sample.json";

class App extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      prompt: [],
      command: "",
      status: [],
      ascii: false,
      characterSelected: false,
      question: false,
      help: false,
      gameOver: false,
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
          console.log(data);
          // Update player & room
          let { response, characterSelected, question, gameOver } = data;
          let prompt = this.cleanUpResponse(response);
          this.setState({
            ascii: false,
            prompt,
            characterSelected,
            question,
            gameOver,
          });
          console.log(this.state.gameOver + " : " + gameOver);
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
    const currentStatus = "look around";
    axios
      .get(`/command/${currentStatus}`)
      .then(({ data }) => {
        // Update player & room
        let { response } = data;
        let status = this.cleanUpResponse(response);
        this.setState({
          status,
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
        let { response, characterSelected, question } = data;
        let prompt = this.cleanUpResponse(response);
        this.setState({
          ascii: true,
          prompt,
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
    let { ascii, status, help } = this.state;
    let counter = 1;
    return (
      <div className="main">
        <h1 className="title">A.R.D.</h1>

        <div className="mainScreen">
          <div className="helpScreen">
            {help
              ? helpContent.map((content) => <Help content={content} />)
              : null}
          </div>
          <div className="message">
            {msg.length > 0
              ? msg.map((line, idx) => {
                  return (
                    <Display
                      key={idx + line}
                      idx={idx}
                      line={line}
                      length={msg.length}
                      ascii={ascii}
                    />
                  );
                })
              : null}
          </div>
          {status.length > 0 ? (
            <div className="status">
              <h3 className="status-title">--------- S T A T U S ---------</h3>
              {status.map((line, idx) => (
                <Display
                  key={idx + line}
                  idx={idx}
                  line={line}
                  length={msg.length}
                  ascii={ascii}
                />
              ))}
            </div>
          ) : null}
        </div>
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
    );
  }
}

export default App;
