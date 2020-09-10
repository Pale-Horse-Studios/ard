package com.mwl.util;

public enum Colors {

    RED("[31m", "[41m"),
    GREEN("[32m", "[42m"),
    YELLOW("[33m", "[43m"),
    BLUE("[34m", "[44m"),
    MAGENTA("[35m", "[45m"),
    CYAN("[36m", "[46m"),
    LIGHTGRAY("[37m", "[47m"),
    LIGHTRED("[91m", "[101m"),
    LIGHTGREEN("[92m", "[102m"),
    LIGHTYELLOW("[93m", "[103m"),
    LIGHTBLUE("[94m", "[104m"),
    LIGHTMAGENTA("[95m", "[105m"),
    LIGHTCYAN("[96m", "[106m");

    String foreground;
    String background;
    String end;
    String escapeCode = "\033";

    Colors(String foreground, String background) {
        this.foreground = escapeCode + foreground;
        this.background = escapeCode + background;
        this.end = escapeCode + "[0m";
    }

    /**
     * Changes the passed string to have terminal escape codes injected into the front and back of string, allowing
     * string to be printed in color in terminals.
     *
     * @param output string to be changed to have color
     * @return colored output string
     */
    public String toColor(String output) {
        return foreground + output + end;
    }

    /**
     * Highlights the passed string to have background as the enum color.
     *
     * @param output string to be highlighted
     * @return highlighted string
     */
    public String highlight(String output) {
        return background + output + end;
    }

    /**
     * Changes the text to be the color of the enum, and then flips the text color and background color. (Similar to
     * highlighting but invert the two colors).
     *
     * @param output string to be negated
     * @return negated string
     */
    public String negative(String output) {
        return escapeCode + "[7m" + toColor(output) + end;
    }

    public String negative(int output) {
        return negative("" + output);
    }
}
