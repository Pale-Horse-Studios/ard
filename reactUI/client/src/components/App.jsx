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
      ascii: false,
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
        this.setState({
          ascii: false,
          prompt,
        });
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
    console.log(res);
    const pattern = /[[]\d{1,2}[m]/gi;
    res = res.replace(pattern, "");
    let strArr = res.split("\n");
    console.log(strArr);
    return strArr;
  }

  componentDidMount() {
    // Loading intro from server
    axios
      .get("/intro")
      .then(({ data }) => {
        let { response } = data;
        let prompt = this.cleanUpResponse(response);
        this.setState({ ascii: true, prompt });
      })
      .catch((err) => {
        console.log("Error fetching data from server: ", err);
      });
  }

  render() {
    const msg = this.state.prompt;
    let { ascii } = this.state;
    return (
      <div>
        <h1 className="title">A.R.D.</h1>

        <div className="mainScreen">
          <pre>
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
          </pre>
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
