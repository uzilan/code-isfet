package se.webstep.visumaat.reports;

import java.util.Arrays;
import java.util.List;

/**
 * Creates an Absolute churn report
 */
public class AbsoluteChurn extends MaatRunner {

    @Override
    protected String getFileName() {
        return "absoluteChurn.log";
    }

    @Override
    protected List<String> getArgs() {
        return Arrays.asList("-a", "abs-churn");
    }
}
