package se.webstep.visumaat.reports;

import java.util.Arrays;
import java.util.List;

/**
 * Creates an Entity ownership report
 */
public class EntityOwnership extends MaatRunner {

    @Override
    protected String getFileName() {
        return "entityOwnership.log";
    }

    @Override
    protected List<String> getArgs() {
        return Arrays.asList("-a", "entity-ownership");
    }
}
