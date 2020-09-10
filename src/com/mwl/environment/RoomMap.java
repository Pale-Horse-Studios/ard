package com.mwl.environment;

import com.mwl.characters.Player;
import com.mwl.util.Grammar;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class RoomMap {
    private final Map<Room, Map<Direction, Room>> map; // underlying data structure
    private final Room start; // starting room
    private final Grammar grammar; // grammar object for generating room descriptions
    private final PuzzleMaker puzzles;

    /**
     * Default Constructor
     */
    public RoomMap() {
        map = new HashMap<>();
        grammar = new Grammar();
        puzzles = new PuzzleMaker();
        start = makeNewRoom();
        map.put(start, new HashMap<>());
    }

    /**
     * Gets the map's starting room.
     *
     * @return
     */
    public Room getStart() {
        return start;
    }

    /**
     * Get the size of the map, i.e. the number of rooms on the map at the given moment
     *
     * @return
     */
    public int size() {
        return map.size();
    }

    /**
     * Returns the underlying data structure for Testing purposes
     *
     * @return
     */
    Map<Room, Map<Direction, Room>> getMap() {
        return map;
    }

//    public Set<Room> roomList(){
//        Set<Room> rooms = map.keySet();
//        return rooms;
//    }

    /**
     * Logic for moving around on the game map. Moves in the passed direction from given room, and returns the new room
     * location. If the room moving to has not yet been visited and is a player, creates one and updates the map
     * accordingly. New rooms are linked in the opposite direction. If a monster type, tries the direction and returns
     * same room if that room was not yet created. If room passed is null, throws an IllegalArgumentException.
     *
     * @param currRoom  room moving from
     * @param direction which direction to go through
     * @return room that was moved to
     * @throws IllegalArgumentException thrown if given direction is not applicable, or if currRoom is null
     */
    private Room moveRoom(Room currRoom, Direction direction, String type) throws IllegalArgumentException {
        if (currRoom != null) {
            Map<Direction, Room> options = map.get(currRoom); // get submap for ease

            switch (type) {
                case "player" -> { // player can go where no rooms are created
                    if (!options.containsKey(direction)) { // room hasn't been created yet, make one now
                        Room temp = makeNewRoom();
                        options.put(direction, temp);
                        map.put(temp, new HashMap<>());
                        map.get(temp).put(flipDirection(direction), currRoom);
                    }
                    return options.get(direction);
                }
                case "monster" -> { // monster can only move if room attempting to go has already been created
                    return options.getOrDefault(direction, currRoom);
                }
                default -> {
                    return currRoom;
                }
            }

        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Moves the given character from their current room to room in given direction. If player's current room is null,
     * throws an IllegalArgumentException.
     *
     * @param player    to move rooms
     * @param direction which room to move to
     * @throws IllegalArgumentException
     */
    public void moveCharacter(Player player, Direction direction) throws IllegalArgumentException {
        player.setCurrentRoom(moveRoom(player.getCurrentRoom(), direction, "player"));
    }

    /**
     * Move monster around the map. Pass in which direction they want to move to. If direction is not one that player
     * has already visited, then returns the same room monster was in. Throws IllegalArgumentException if the monster's
     * room is null.
     * @param monster the monster in which to move
     * @param direction which direction to move into
     * @throws IllegalArgumentException
     */
//    public void moveCharacter(Monster monster, Direction direction) throws IllegalArgumentException {
//        monster.setCurrentRoom(moveRoom(monster.getCurrentRoom(), direction, "monster"));
//    }

    /**
     * Returns the opposing direction string
     *
     * @param direction starting direction
     * @return opposite direction
     */
    private Direction flipDirection(Direction direction) {
        return switch (direction) {
            case North -> Direction.South;
            case West -> Direction.East;
            case East -> Direction.West;
            default -> Direction.North; // for north and whatever else
        };

    }

    /**
     * Makes a new room with ID the current size of room.
     *
     * @return
     */
    private Room makeNewRoom() {
        Room result = new Room(grammar.generate_Sentence(), map.size());
        int percentChest = 40;
        if (ThreadLocalRandom.current().nextInt(100) < percentChest) {
            result.setChest(new Chest(puzzles.getRandomPuzzle()));
        }
        return result;
    }
}
