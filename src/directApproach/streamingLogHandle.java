package directApproach;

import streamingLogHandle.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import logfileclusteringversion3.ClassScanner;
import logfileclusteringversion3.VerifyWords3;

class streamingLogHandle {

    public void process() {
        String directory = "D:/major_stuffs/majorProject/backupLogFiles";
        ProcessStreamingLog processLog = new ProcessStreamingLog();
        processLog.fileProcess(directory, new ClassScanner(), new VerifyWords3());
    }

    public static void main(String args[]) {
        streamingLogHandle logHandle = new streamingLogHandle();
        logHandle.process();
    }
}

class ProcessStreamingLog {

    public void fileProcess(String directory, ClassScanner scanner, VerifyWords3 verify) {
        try {
            String logDirectory;
            String logline;
            String fileDirectory = directory;
            Scanner scan;
            File f1 = new File(fileDirectory);

            if (f1.isDirectory()) {
                String s[] = f1.list();

                for (int i = 0; i < s.length; i++) {

                    logDirectory = fileDirectory + '/' + s[i];//sets the path of the multiple log files in the given directory.
                    scan = scanner.scannerFunction(logDirectory);
                    while (scan.hasNextLine()) {
                        String orginalLogLine = scan.nextLine();
                        logline = verify.parseAll(orginalLogLine);
                        lineManupulator manupulate = new lineManupulator();
                        manupulate.setlogLine(logline);
                        manupulate.patternGenerator();


                        clusterReCommender recommender = new clusterReCommender();
                        recommender.reCommendation(manupulate.getnewLogLine(), logline);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }
}
