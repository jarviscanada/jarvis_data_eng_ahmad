package ca.jrvs.apps.practice;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExcImp implements RegexExc {

    public boolean matchJpeg(String filename){
        Pattern ptn = Pattern.compile("\\w+\\.(jpeg|jpg)$", Pattern.CASE_INSENSITIVE);
        Matcher match = ptn.matcher(filename);
        if(match.find()){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean matchIp(String ip){
        Pattern ptn = Pattern.compile("^(\\d{1,4}\\.)(\\d{1,4}\\.)(\\d{1,4}\\.)(\\d{1,4})$", Pattern.CASE_INSENSITIVE);
        Matcher match = ptn.matcher(ip);
        if(match.find()){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isEmptyLine(String line){
        Pattern ptn = Pattern.compile("^\\s*$");
        Matcher match = ptn.matcher(line);
        if(match.find()){
            return true;
        }
        else{
            return false;
        }
    }


}
