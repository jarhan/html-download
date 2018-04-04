import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.*;

public class ooc1_2 {

    private static final Logger log = Logger.getLogger(ooc1_2.class.getName());
    private String[] args = null;
    private Options options = new Options();

    public ooc1_2(String[] args) {

        this.args = args;

        options.addOption("h", "help", false, "Show help.");
        options.addOption("a", "total-num-files", false, "The total number of files");
        options.addOption("b", "total-num-dirs", false, "Total number of directory");
        options.addOption("c", "total-unique-exts", false, "Total number of unique file extensions.");
        options.addOption("d", "list-exts", false, "List all unique file extensions.\n" +
                "Do not list duplicates.");
        Option ext = Option.builder()
                .longOpt("num-ext")
                .desc( "List total number of file for specified extension EXT."  )
                .hasArg()
                .argName( "EXT" )
                .build();
        options.addOption( ext );
        Option option = Option.builder("f")
                .desc( "Path to the documentation folder.\n" +
                        "This is a required argument."  )
                .hasArg()
                .argName( "path-to-folder" )
                .build();
        options.addOption( option );

    }

    public void parse() {
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd;

        ooc1_1 walker = new ooc1_1();

        try {

            cmd = parser.parse(options, args);

            if (cmd.hasOption("f")) {
//                System.out.println("in f");
                String path = cmd.getOptionValue("f");
                System.out.println("The documentation folder: " + path);
//                log.log(Level.INFO, "Using cli argument -f=" + path);
                File file = new File(path);

                try {
                    walker.count(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                log.log(Level.SEVERE, "Missing f option");
                help();
            }

            if (cmd.hasOption("h"))
                help();

            if (cmd.hasOption("a")) {
//                System.out.println("in a");
                System.out.println("    Total number of files: " + walker.countFiles);
            }

            if (cmd.hasOption("b")) {
//                System.out.println("in b");
                System.out.println("    Total number of directory: " + walker.countDir);
            }

            if (cmd.hasOption("c")) {
//                System.out.println("in c");
                System.out.println("    Total number of unique file extensions: " + walker.fileExt.size());
            }

            if (cmd.hasOption("d")) {
//                System.out.println("in d");
                System.out.println("    List all unique file extensions: " + walker.fileExt.keySet());
            }

            if (cmd.hasOption("num-ext")) {
//                System.out.println("in e");
                String ext = cmd.getOptionValue("num-ext");
                System.out.println("    List total number of file for specified extension " + ext + ": "
                        + walker.fileExt.get(ext));
            }

        } catch (ParseException e) {
            log.log(Level.SEVERE, "Failed to parse command line properties", e);
            help();
        }
    }

    private void help() {
        // This prints out some help
        HelpFormatter formatter = new HelpFormatter();

        formatter.printHelp("Main", options);
        System.exit(0);
    }

    public static void main(String[] args) {
//        args = new String[]{"-help"};
        args = new String[]{"-b", "-f=/Users/JarHan/Desktop/selling", "-a", "--num-ext=jpeg"};
//        for (String i: args
//             ) {
//            System.out.println(i);
//        }
        new ooc1_2(args).parse();
    }
}
