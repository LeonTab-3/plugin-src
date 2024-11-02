package org.example.gemplugin;

public class Gem {
    private String name;
    private int level;

    // Constructor to initialize with name and level
    public Gem(String name, int level) {
        this.name = name;
        this.level = level;
    }

    // Constructor to initialize with just name (defaults to level 1)
    public Gem(String name) {
        this(name, 1); // Default level to 1
    }

    // Get the gem's name
    public String getName() {
        return name;
    }

    // Get the gem's current level
    public int getLevel() {
        return level;
    }

    // Upgrade the gem by incrementing its level
    public void upgrade() {
        level++;
    }
}
