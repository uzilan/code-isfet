package se.webstep.visumaat.reports;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrganizationalMetrics extends MaatRunner {

    @Override
    protected String getFileName() {
        return "organizationalMetrics.log";
    }

    private Map<Path, Metrics> map = new HashMap<>();

    public OrganizationalMetrics() {

        try {
            List<String> rows = FileUtils.readLines(report);

            rows.stream()
                    .skip(1)
                    .map(Metrics::new)
                    .sorted((m1, m2) -> m2.path.getNameCount() - m1.path.getNameCount())
                    .forEach(m -> {
                        map.put(m.path, m);
                        addParent(m);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addParent(Metrics metrics) {
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
        addParent(parentMetrics);
    }

    private class Metrics {

        Metrics(String row) {
            String[] split = row.split(",");
            this.path = Paths.get(split[0]);
            this.nAuthors = Integer.valueOf(split[1]);
            this.nRevs = Integer.valueOf(split[2]);
        }

        public Metrics(Path path) {
            this.path = path;
        }

        Path path;
        int nAuthors;
        int nRevs;
        List<Metrics> children = new ArrayList<>();
    }

    public enum MetricsType {authors, revs}

    public String authorsMetrics(MetricsType metricsType) {

        JSONObject root = new JSONObject();
        root.put("name", "root");

        JSONArray children = new JSONArray();
        root.put("children", children);

        map.values().stream()
                .filter(m -> m.path.getNameCount() == 1)
                .forEach(m -> addJson(m, children, metricsType));

        return root.toString();
    }

    private void addJson(Metrics m, JSONArray children, MetricsType metricsType) {
        JSONObject node = new JSONObject();
        node.put("name", m.path.getFileName());
        children.put(node);

        if (m.children.isEmpty()) {
            node.put("size", metricsType == MetricsType.authors ? m.nAuthors : m.nRevs);
        } else {
            JSONArray childrenChildren = new JSONArray();
            node.put("children", childrenChildren);
            m.children.forEach(c -> addJson(c, childrenChildren, metricsType));
        }
    }
}
