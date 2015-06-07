package se.webstep.visumaat.reports;

import java.util.Arrays;
import java.util.List;

/**
 * Creates a Logical coupling report
 */
public class LogicalCoupling extends MaatRunner {

    @Override
    protected String getFileName() {
        return "logicalCoupling.log";
    }

    @Override
    protected List<String> getArgs() {
        return Arrays.asList("-a", "coupling");
    }
}
