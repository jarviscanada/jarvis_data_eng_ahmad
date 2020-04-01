package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {
    //Setter Functions


    void setRegex(String regex);
    void setRootPath(String rootPath);
    void setOutFile(String outFile);

    //Getter Functions
    String getRegex();
    String getRootPath();
    String getOutFile();

    //Implementation Functions
    List<File> listFiles(String rootDir);
    List<String> readLines(File inputFile) throws IOException;
    void writeToFile(List<String> lines) throws IOException;
    void process() throws IOException;


}
