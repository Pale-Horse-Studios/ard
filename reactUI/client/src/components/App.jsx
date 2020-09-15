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

    this.helpMenu = React.createRef();

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.helpSubmit = this.helpSubmit.bind(this);
    this.hideHelp = this.hideHelp.bind(this);
  }

  handleChange({ target }) {
    this.setState({ command: target.value });
  }

  handleSubmit(event) {
    event.preventDefault();
    // Send GET Request to /command/?command
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

  helpSubmit(e) {
    e.preventDefault();
    e.stopPropogation();
    this.helpMenu.current.className = "show-center";
  }

  hideHelp(e) {
    e.preventDefault();
    this.helpMenu.current.className = "hidden";
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
      <div onClick={this.hideHelp}>
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
        <form onSubmit={this.helpSubmit} className="userInput">
          <button>Help</button>
        </form>
        <div ref={this.helpMenu} className="hidden">
          <h2>Game Help</h2>
          <div>
            <h3>Story Details</h3>
            <p>
              You are trapped in a random dungeon of randomness. Keep your
              health above zero and defeat Boss Bezos to win. Boss Bezos spawns
              somewhere on the map, every time you pick up a magical stone in
              any room, Bezos will show up in that room! You need special items
              to defeat Bezos. They are sword, shield, potion, ...
            </p>
            <h4>Rooms</h4>
            <p>
              Rooms are the places where player might see items or/and monsters.
              Rooms are created instantly as player moves in any direction.
            </p>
            <h4>Monsters</h4>
            <p>
              Monsters are randomly generated in each room. You can fight the
              monsters with the "fight" command.
            </p>
            <h4>Items</h4>
            <p>
              Items are randomly generated things player can pickup or drop off
              in each room. There are certain upgraded items (magical stones)
              that you can only pickup after you have adventures more than 5
              rooms.
            </p>
          </div>
          <div>
            <h3>Game Controls</h3>
            <p>
              To play the game, you type commands into the console. There are a
              plethora of commands to choose from, here are a few options. To
              call any of these, it is [command] [option]
            </p>
            <h4>Move</h4>
            <p>
              To move around the game you type "move [direction]". Directions
              are north, south, west, east.
            </p>
            <h4>Pickup</h4>
            <p>
              To pick up an item you type "pickup [item]". If the item is in the
              room and can be picked up, it is added to your inventory and
              removed from the room.
            </p>
            <h4>Drop</h4>
            <p>
              to drop an item from your inventory you type "drop [item]", and if
              the item can be dropped, it will be removed from your inventory
              and into the room.
            </p>
            <h4>Fight</h4>
            <p>
              Type "fight [monster]" to fight a monster. The order of player
              fighting monster and monster attacking player is randomized for
              each round.
            </p>
            <h4>Use Power</h4>
            <p>
              Type "use power" to use the special power of the hero you have
              chosen. You must have the [power stone] in player inventory to use
              your special power.
            </p>
          </div>
        </div>
      </div>
    );
  }
}

export default App;
