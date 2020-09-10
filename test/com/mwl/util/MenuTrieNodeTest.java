package com.mwl.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MenuTrieNodeTest {
    MenuTrieNode root;
    MenuTrieNode child;
    
    
    @Before
    public void setUp() {
        root = new MenuTrieNode("Title", "Description");
        child = new MenuTrieNode("child", "description");
    }

    @Test
    public void addChild() {
        assertEquals(0, root.getChildren().size());
        root.addChild(new MenuTrieNode("child1", "Description"));
        assertEquals(1, root.getChildren().size());
    }

    @Test
    public void addChild_Multiple() {
        assertEquals(0, root.getChildren().size());
        for (int i = 0; i < 5; i++) {
            root.addChild(new MenuTrieNode("child" + i, "descritpion"));
        }
        assertEquals(5, root.getChildren().size());
    }

    @Test
    public void addChild_doesNothing_withNull() {
        assertEquals(0, root.getChildren().size());
        root.addChild(null);
        assertEquals(0, root.getChildren().size());
    }

    @Test
    public void getChild_inBounds_0() {
        root.addChild(child);
        assertEquals(child, root.getChild(0));
    }

    @Test
    public void getChild_inBounds_last() {
        for (int i = 0; i < 5; i++) {
            root.addChild(new MenuTrieNode("title" + i, ""));
        }
        root.addChild(child);
        assertEquals(child, root.getChild(5));
    }

    @Test (expected = IllegalArgumentException.class)
    public void getChild_outBounds_negative() {
        root.addChild(child);
        root.getChild(-1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void getChild_outBounds_Positive() {
        root.addChild(child);
        root.getChild(100);
    }
}