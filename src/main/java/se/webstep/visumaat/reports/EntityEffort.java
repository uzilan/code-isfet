package se.webstep.visumaat.reports;

import java.util.Arrays;
import java.util.List;

/**
 * Creates an Entity effort report
 */
public class EntityEffort extends MaatRunner {

    @Override
    protected String getFileName() {
        return "entityEffort.log";
    }

    @Override
    protected List<String> getArgs() {
        return Arrays.asList("-a", "entity-effort");
    }
}
