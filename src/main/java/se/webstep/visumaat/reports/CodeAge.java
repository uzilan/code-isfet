package se.webstep.visumaat.reports;

import java.util.Arrays;
import java.util.List;

/**
 * Creates a Code age report
 */
public class CodeAge extends MaatRunner {

    @Override
    protected String getFileName() {
        return "codeAge.log";
    }

    @Override
    protected List<String> getArgs() {
        return Arrays.asList("-a", "age");
    }
}
