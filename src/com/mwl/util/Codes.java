package com.mwl.util;

public enum Codes {

    Monster("\u2648 ", Colors.RED),
    Room("\u22a1 ", Colors.YELLOW),
    Player("\u261b ", Colors.BLUE),
    Life("\u2665 ", Colors.LIGHTRED),
    Item("\u2200 ", Colors.LIGHTCYAN),
    Left("\u201f", Colors.MAGENTA),
    Right("\u201d", Colors.MAGENTA),
    Level("\u21a5", Colors.LIGHTBLUE),
    Chest("\u223a", Colors.CYAN),
    Score("\u2713", Colors.GREEN);

    String code;
    Colors color;

    Codes(String unicode, Colors color) {
        this.code = unicode;
        this.color = color;
    }

    /**
     * Get the unicode associated with this enum
     *
     * @return
     */
    public String getCode() {
        return withColor(code);
    }

    /**
     * Method to change a string to be printable in color according to it's Colors enum.
     *
     * @param output string to change into colored version
     * @return colored output
     */
    public String withColor(String output) {
        return color.toColor(output);
    }

    public String withColor(int output) {
        return withColor("" + output);
    }

    /**
     * Get the color associated with this enum
     *
     * @return
     */
    public Colors getColor() {
        return color;
    }
}
