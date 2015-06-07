package se.webstep.visumaat.reports;

import org.apache.commons.io.FileUtils;
import se.webstep.visumaat.Constants;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * This class create a log file with an analysis of the events that happened in the given repository during a period of time.
 * The location of the repository, the type of VCS and the period of time must be specified in the config.txt file in the root folder.
 */
public class VcsLog {

    /**
     * Create the log file given the required properties.
     *
     * @param props properties, including the type of VCS (vcs), the location of the repository (repo) and the period of time to analyze (date)
     * @throws IOException          in case a file could not be read or written to
     * @throws InterruptedException in case errors occurred when the logfile command line ran
     */
    public VcsLog(Properties props) {

        String vcs = props.getProperty("vcs");
        String date = props.getProperty("date");
        String repo = props.getProperty("repo");

        if (vcs == null) {
            throw new RuntimeException("VCS was not defined in the configuration file (config.txt)");
        }
        if (date == null) {
            throw new RuntimeException("Date was not defined in the configuration file (config.txt)");
        }
        if (repo == null) {
            throw new RuntimeException("Repository was not defined in the configuration file (config.txt)");
        }

        try {
            File tempFile = File.createTempFile("temp", "log");

            ProcessBuilder pb = new ProcessBuilder(getArgs(vcs, date, tempFile));
            pb.directory(new File(repo));

            // In git and hg case, the output needs to be redirected to a log file. In svn, the log file is already given in the command line.
            if (vcs.equals("git") || vcs.equals("hg")) {
                pb.redirectOutput(tempFile);
            }

            pb.start().waitFor();

            String logWithFnutts = FileUtils.readFileToString(tempFile);
            String logWithoutFnutts = logWithFnutts.replace("'", "");

            File logfile = new File(Constants.LOGFILE_PATH);

            FileUtils.write(logfile, logWithoutFnutts);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return the correct command line for the given VCS and analyze date. In SVN case, write the results to a given temp file.
     *
     * @param vcs      the type of VCS
     * @param date     the period of time to analyze
     * @param tempFile a temporary file to use in the svn command line
     * @return a command line to run
     */
    private List<String> getArgs(String vcs, String date, File tempFile) {

        switch (vcs) {
            case "svn":
                return Arrays.asList("svn",
                        "log", "-v", "--xml", ">", tempFile.getAbsolutePath(), "-r", "{" + date.replace("-", "") + "}:HEAD");
            case "git":
                return Arrays.asList("git",
                        "log", "--pretty=format:'[%h] %aN %ad %s'",
                        "--date=short",
                        "--numstat",
                        "--after=" + date);
            case "hg":
                return Arrays.asList("hg",
                        "log", "--template", "\"rev:", "{rev}", "author:", "{author}", "date:", "{date|shortdate}",
                        "files:\\n{files %'{file}\\n'}\\n\"", "--date", "\">" + date + "\"");
            default:
                throw new RuntimeException("Unsupported VCS: " + vcs);
        }
    }
}
