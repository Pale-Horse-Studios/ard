package com.mwl.util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ColorsTest {

    @Test
    public void toColor() {
        for (Colors color : Colors.values()) {
            System.out.println(color.toColor("Here is the color " + color));
        }
    }

    @Test
    public void negative_withColors() {
        for (Colors color : Colors.values()) {
            System.out.println(color.negative("Here is the negative of " + color));
        }
    }

    @Test
    public void test_highlight() {
        for (Colors color : Colors.values()) {
            System.out.println(color.highlight("Highlight this text with " + color));
        }
    }

    @Test
    public void combo() {
        System.out.println(Colors.LIGHTBLUE.highlight(Colors.RED.toColor("Welp here we go again")));
    }

    @Test
    public void emptyStrings() {
        for (Colors color : Colors.values()) {
            System.out.println(color.negative("     "));
        }
    }
}