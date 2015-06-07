package se.webstep.visumaat.reports;

import java.util.Arrays;
import java.util.List;

/**
 * Creates a Churn by entity report
 */
public class ChurnByEntity extends MaatRunner {

    @Override
    protected String getFileName() {
        return "churnByEntity.log";
    }

    @Override
    protected List<String> getArgs() {
        return Arrays.asList("-a", "entity-churn");
    }
}
