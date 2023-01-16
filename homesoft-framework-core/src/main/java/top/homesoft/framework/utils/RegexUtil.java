package top.homesoft.framework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {


//    public String match(){
//        Pattern p = Pattern.compile("summary_day\\.\\d+[0-9]\\.\\d{17}");
//        Matcher m = p.matcher("ipva.summary_day.20210621.20210622000205860.B20C41AB-0A17-46DB-9177-817533B8E551.1624291336921.txt");
//
//        m.matches()
//    }


    public boolean matchFileContent(String content){
        Pattern p = Pattern.compile("summary_day\\.\\d+[0-9]\\.\\d{17}");
        Matcher matcher = p.matcher(content);
        return matcher.matches();
    }
}
