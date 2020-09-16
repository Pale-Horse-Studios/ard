import React from "react";
import ReactDOM from "react-dom";
import axios from "axios";
import Display from "./Display.jsx";

class App extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      prompt: [],
      command: "",
      status: [],
      ascii: false,
      characterSelected: false,
      isQuestion: false,
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
    let route;
    route = this.state.characterSelected ? "character" : "command";
    const userInput = this.state.command;
    axios
      .get(`/${route}/${userInput}`)
      .then(({ data }) => {
        // Update player & room
        let { response, characterSelected } = data;
        let prompt = this.cleanUpResponse(response);
        this.setState({
          ascii: false,
          prompt,
          characterSelected,
        });
        if (!characterSelected) {
          this.updateStatus();
        }
      })
      .catch((err) => {
        console.log(err);
      });

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
        let { response, characterSelected } = data;
        let prompt = this.cleanUpResponse(response);
        this.setState({
          ascii: true,
          prompt,
          characterSelected,
        });
      })
      .catch((err) => {
        console.log("Error fetching data from server: ", err);
      });
  }

  render() {
    const msg = this.state.prompt;
    let { ascii, status } = this.state;
    let counter = 1;
    return (
      <div className="main">
        <h1 className="title">A.R.D.</h1>

        <div className="mainScreen">
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
            {status.length > 0
              ? <div className="status">
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
              : null}

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
