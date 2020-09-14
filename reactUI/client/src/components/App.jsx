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
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange({ target }) {
    this.setState({ command: target.value });
  }

  handleSubmit(event) {
    event.preventDefault();
    // Send GET Request to /command/?userInput=
    const { command } = this.state;
    axios
      .get(`/command/${command}`)
      .then(({ data }) => {
        // Update player & room
        let { response } = data;
        let prompt = this.cleanUpResponse(response);
        this.setState({ prompt });
      })
      .catch((err) => {
        console.log(err);
      });

    this.initialize();
  }

  initialize() {
    this.setState({ command: "" });
  }

  cleanUpResponse(res) {
    // \u001b[ => build color chart
    // \n => split and display line by line
    const pattern = /[[]\d{1,2}[m]/gi;
    res = res.replace(pattern, "");
    let strArr = res.split("\n");
    return strArr;
  }

  componentDidMount() {
    // Loading intro from server
    axios
      .get("/intro")
      .then(({ data }) => {
        let { response } = data;
        let prompt = this.cleanUpResponse(response);
        this.setState({ prompt });
      })
      .catch((err) => {
        console.log("Error fetching data from server: ", err);
      });
  }

  render() {
    const msg = this.state.prompt;
    return (
      <div>
        <h1 className="title">ARD</h1>

        <div className="mainScreen">
          {msg.length > 0
            ? msg.map((line, idx) => <Display key={idx} line={line} />)
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
