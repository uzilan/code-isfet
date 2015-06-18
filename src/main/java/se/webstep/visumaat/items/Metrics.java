package se.webstep.visumaat.items;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Metrics extends Item {

    public Metrics(String row) {
        String[] split = row.split(",");
        this.path = Paths.get(split[0]);
        this.nAuthors = Integer.valueOf(split[1]);
        this.nRevs = Integer.valueOf(split[2]);
    }

    public Metrics(Path path) {
        super(path);
    }

    public int nAuthors;
    public int nRevs;
    public List<Metrics> children = new ArrayList<>();
}