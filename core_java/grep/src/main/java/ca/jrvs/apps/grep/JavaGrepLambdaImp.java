package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp {




    @Override
    public List<File> listFiles(String rootDir) {
        File file = new File(rootDir);
        File[] list_files = file.listFiles();
        List<File> actual_files = new ArrayList<File>();
        if (list_files == null) {
            return actual_files;
        } else {
            Stream<File> stream_list_files_1 = Stream.of(list_files);
            Stream<File> stream_list_files_2 = Stream.of(list_files);
            actual_files.addAll(stream_list_files_1.filter(i -> i.isFile()).collect(Collectors.toList()));
            stream_list_files_2.filter(i->i.isDirectory()).forEach(i->actual_files.addAll(listFiles(i.toString())));
        }

        return actual_files;

    }

    public List<String> readLines(File inputFile) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        return br.lines().flatMap(i->Stream.of(i.split("\\."))).filter(i->i.toString().length()!=0).map(i->i.toString()+".").collect(Collectors.toList());
    }

}
