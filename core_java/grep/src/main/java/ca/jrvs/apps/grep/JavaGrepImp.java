package ca.jrvs.apps.grep;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepImp implements JavaGrep{
    private static Logger LoggerFactory;

    // Private member variables

    private String regex;
    private String rootPath;
    private String outFile;

    //Setter Functions

    @Override
    public void setRegex(String regex){
        this.regex=regex;
    }

    public void setRootPath(String rootPath){
        this.rootPath=rootPath;
    }

    public void setOutFile(String outFile){
        this.outFile=outFile;
    }

    // Getter Functions

    public String getRegex(){
        return regex;
    }

    public String getRootPath(){
        return rootPath;
    }

    public String getOutFile(){
        return outFile;
    }



         public List<File> listFiles(String rootDir) {
            File f = new File(rootDir);
            File list_files[] = f.listFiles();
            List<File> actual_files = new ArrayList<File>();
            if(list_files==null) {
                return actual_files;
            }
            else {

                for (File file_name : list_files) {
                    if (file_name.isFile()) {
                        actual_files.add(file_name);
                    }
                    else {
                        actual_files.addAll(listFiles(file_name.toString()));
                    }

                }
            }
            return actual_files;
        }

         public List<String> readLines(File inputFile) throws IOException {

            List<String> list_str = new ArrayList<String>();
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String stream_string = br.readLine();
            while(stream_string!=null){
                String input_string[] = stream_string.split("\\.");
                for (String str:input_string) {
                    if(str.length()!=0) {
                        list_str.add(str+".");
                    }
                }
                stream_string=br.readLine();
            }
            return list_str;
        }

        boolean containsPattern(String line){
            return line.matches(getRegex());
}

        public List<File> listFilesLambda(String rootDir) {
        File file = new File(rootDir);
        File[] list_files = file.listFiles();
        List<File> actual_files = new ArrayList<File>();
        if (list_files == null) {
            return actual_files;
        } else {
            Stream<File> stream_list_files_1 = Stream.of(list_files);
            Stream<File> stream_list_files_2 = Stream.of(list_files);
            actual_files.addAll(stream_list_files_1.filter(i -> i.isFile()).collect(Collectors.toList()));
            stream_list_files_2.filter(i->i.isDirectory()).forEach(i->actual_files.addAll(listFilesLambda(i.toString())));
        }

return actual_files;

    }

    public List<String> readLinesLambda(File inputFile) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        return br.lines().flatMap(i->Stream.of(i.split("\\."))).filter(i->i.toString().length()!=0).map(i->i.toString()+".").collect(Collectors.toList());
    }

    public void writeToFile(List<String> lines) throws IOException {
        FileWriter fw=new FileWriter(getOutFile());
        for (String str : lines){
            fw.write(str+System.lineSeparator());
        }
        fw.close();
    }

    public void process() throws IOException{
          List<String> matched_lines = new ArrayList<String>();
          int counter = 0;
          System.out.println(getRegex());
          //System.out.println(listFiles(getRootPath()).size());
          for (File file : listFiles(getRootPath())){
              System.out.println("lines : "+readLines(file).size());


              for (String line : readLines(file)){

                  if(line.matches(getRegex())){
                      System.out.println("match: "+ line);
                      matched_lines.add(line);
                  }
              }
          }
          writeToFile(matched_lines);

    }



    public static void main(String[] args) throws IOException {

        if(args.length!=3){
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath OutFile");
        }
        JavaGrepImp jgp = new JavaGrepImp();
        jgp.setRegex(args[0]);
        jgp.setRootPath(args[1]);
        jgp.setOutFile(args[2]);

        jgp.process();
        System.out.println("done...");


        //final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

            //System.out.println(listFilesLambda("/home/anhassan/Documents"));
/*
            File f = new File("/home/anhassan/Documents/wikki_cric.txt");
            List<String> output = new ArrayList<>();
        try {
            output = readLinesLambda(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int counter =0;
        for (String str :output){
            counter++;
            System.out.println(counter+". "+str);
        }
        writeToFile(output);
*/
    }

}

