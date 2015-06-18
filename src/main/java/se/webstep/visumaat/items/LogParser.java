package se.webstep.visumaat.items;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lanuz on 2015-06-18.
 */
public class LogParser {

    public static Map<Path, Metrics> parse(File report) {
        Map<Path, Metrics> map = new HashMap<>();

        try {
            List<String> rows = FileUtils.readLines(report);

            rows.stream()
                    .skip(1)
                    .map(Metrics::new)
                    .sorted((m1, m2) -> m2.path.getNameCount() - m1.path.getNameCount())
                    .forEach(m -> {
                        map.put(m.path, m);
                        addParent(m, map);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    private static void addParent(Metrics metrics, Map<Path, Metrics> map) {
        Path parent = metrics.path.getParent();
        if (parent == null) {
            return;
        }

        if (map.containsKey(parent)) {
            map.get(parent).children.add(metrics);
            return;
        }

        Metrics parentMetrics = new Metrics(parent);
        parentMetrics.children.add(metrics);
        map.put(parent, parentMetrics);
        addParent(parentMetrics, map);
    }
}
