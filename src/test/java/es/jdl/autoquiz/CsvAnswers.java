package es.jdl.autoquiz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Read CSV containing correct answers
 */
public class CsvAnswers {

    public static void main(String[] args) throws IOException {
        File inputCsv;
        if (args.length == 0) {
            usage();
            return;
        } else {
            inputCsv = new File(args[0]);
            Properties conf = new Properties();
            conf.load(new FileReader(args[1]));

        }
    }

    private static void usage() {
        System.err.println("Usage: java " + CsvAnswers.class.getName() + " <input-csv> <config-file>");
        System.exit(-1);
    }
}
