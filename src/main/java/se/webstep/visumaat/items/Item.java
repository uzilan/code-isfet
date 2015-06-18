package se.webstep.visumaat.items;

import java.nio.file.Path;

public abstract class Item {

    public Path path;

    public Item() {
    }

    public Item(Path path) {
        this.path = path;
    }
}
