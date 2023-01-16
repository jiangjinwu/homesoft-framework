package top.homesoft.tools.file.sql;

import java.io.*;

public class MergeSql {

    public static void main(String[] args) {
        splitFileByLine("C:\\Users\\user\\Desktop\\user_import\\t_user_import-6.txt","C:\\Users\\user\\Desktop\\user_import\\sql");
    }
    public static  void  splitFileByLine(String sourceFilePath, String targetDirectoryPath) {
        String sourceFileName = sourceFilePath.substring(sourceFilePath.lastIndexOf(File.separator) + 1, sourceFilePath.lastIndexOf("."));//源文件名
        String splitFileName = targetDirectoryPath + File.separator + sourceFileName + "-.sql";//切割后的文件名
        File targetDirectory = new File(targetDirectoryPath);
        if (!targetDirectory.exists()) {
            targetDirectory.mkdirs();
        }

        PrintWriter pw = null;//字符输出流
        String tempLine;
        int lineNum = 0;//本次行数累计 , 达到rows开辟新文件

        String template = null;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFilePath)))) {
            pw = new PrintWriter(splitFileName);
            while ((tempLine = br.readLine()) != null) {
                if(lineNum == 0){
                    template = tempLine.substring(0, tempLine.indexOf("VALUES")+6);
                }else {
                    tempLine= tempLine.replace(template,"").replace(";",",");
                }
                pw.write(tempLine + "\n");
                lineNum++;
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (null != pw) {
                pw.flush();
                pw.close();
            }
        }

    }
}
