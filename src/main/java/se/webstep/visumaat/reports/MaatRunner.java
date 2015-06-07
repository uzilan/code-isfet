package se.webstep.visumaat.reports;

import se.webstep.visumaat.Constants;
import se.webstep.visumaat.Visumaat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A class that runs code-maat reports.
 */
public abstract class MaatRunner {

    protected File report;

    /**
     * Run the code maat report
     */
    public MaatRunner() {
        try {
            report = new File(Constants.REPORTS_PATH + "/" + getFileName());

            List<String> runArgs = new ArrayList<>();

            runArgs.addAll(Arrays.asList(
                    "java",
                    "-jar",
                    Visumaat.maatPath,
                    "-l",
                    Constants.LOGFILE_PATH,
                    "-c",
                    "git"));

            runArgs.addAll(getArgs());

            ProcessBuilder pb = new ProcessBuilder(runArgs);

            pb.redirectOutput(report);

            pb.start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Implement to specify the name of the report file to create
     *
     * @return the name of the report file to create
     */
    protected abstract String getFileName();

    /**
     * Extra arguments to use when running the code maat report. Override if more arguments are needed.
     *
     * @return extra arguments
     */
    protected List<String> getArgs() {
        return Collections.emptyList();
    }
}
