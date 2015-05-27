package se.webstep.visumaat;

import org.apache.commons.io.FileUtils;
import se.webstep.visumaat.reports.OrganizationalMetrics;
import se.webstep.visumaat.reports.VcsLog;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

/**
 * A class which helps visualizing results of running the fabulous code-maat reports.
 *
 * @see <a href="https://github.com/adamtornhill/code-maat">https://github.com/adamtornhill/code-maat</a>
 */
public class Visumaat {

    public static String maatPath;

    /**
     * Creates the VCS logfile and all the reports, and defines the REST paths to their JSON representations
     */
    public Visumaat() {

        staticFileLocation("/public");

        Properties props = readProperties();

        VcsLog vcsLog = new VcsLog(props);
//        Summary summary = new Summary();
        OrganizationalMetrics organizationalMetrics = new OrganizationalMetrics();
//        LogicalCoupling logicalCoupling = new LogicalCoupling();
//        CodeAge codeAge = new CodeAge();
//        AbsoluteChurn absoluteChurn = new AbsoluteChurn();
//        ChurnByAuthor churnByAuthor = new ChurnByAuthor();
//        ChurnByEntity churnByEntity = new ChurnByEntity();
//        EntityOwnership entityOwnership = new EntityOwnership();
//        EntityEffort entityEffort = new EntityEffort();

        get("/metrics/authors", (request, response) -> {
            return organizationalMetrics.authorsMetrics();
        });
    }

    private Properties readProperties() {
        Properties props = new Properties();
        try {
            props.load(FileUtils.openInputStream(new File(Constants.PROPERTIES_FILE_PATH)));

            maatPath = props.getProperty("maat");
            if (maatPath == null) {
                throw new RuntimeException("Location of code_maat jar file was not defined in the configuration file (config.txt)");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public static void main(String[] args) {
        new Visumaat();
    }
}