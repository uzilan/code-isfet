package se.webstep.visumaat.reports;

import org.json.JSONArray;
import org.json.JSONObject;
import se.webstep.visumaat.items.LogParser;
import se.webstep.visumaat.items.Metrics;

import java.nio.file.Path;
import java.util.Map;

public class OrganizationalMetrics extends MaatRunner {

    @Override
    protected String getFileName() {
        return "organizationalMetrics.log";
    }

    private Map<Path, Metrics> map;

    public OrganizationalMetrics() {
        map = LogParser.parse(report);
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
