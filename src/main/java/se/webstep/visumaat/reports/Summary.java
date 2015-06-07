package se.webstep.visumaat.reports;

import java.util.Arrays;
import java.util.List;

/**
 * Creates a Summary report
 */
public class Summary extends MaatRunner {

    @Override
    protected String getFileName() {
        return "summary.log";
    }

    @Override
    protected List<String> getArgs() {
        return Arrays.asList("-a", "summary");
    }
}
