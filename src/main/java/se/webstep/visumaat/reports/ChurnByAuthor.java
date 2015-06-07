package se.webstep.visumaat.reports;

import java.util.Arrays;
import java.util.List;

/**
 * Creates a Churn by author report
 */
public class ChurnByAuthor extends MaatRunner {

    @Override
    protected String getFileName() {
        return "churnByAuthor.log";
    }

    @Override
    protected List<String> getArgs() {
        return Arrays.asList("-a", "author-churn");
    }
}
