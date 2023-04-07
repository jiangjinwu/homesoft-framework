package top.homesoft.tools.file.sql;

import java.io.*;

public class MergeSql {

    public static void main(String[] args) {

        String dirPath ="C:\\Users\\user\\Desktop\\sqloutput";
        String targetPath =dirPath+"/mergeSql";
        File targetDir = new File(targetPath);
        if(!targetDir.exists()){
            targetDir.mkdir();
        }
        File dir = new File(dirPath);
        dir.listFiles();
        File[] files = dir.listFiles();
        //foreach遍历数组
        for (File file : files) {
         if(file.isFile()) {
             splitFileByLine(file.getPath(), targetPath);
         }
        }


    }
    public static  void  splitFileByLine(String sourceFilePath, String targetDirectoryPath) {
        String sourceFileName = sourceFilePath.substring(sourceFilePath.lastIndexOf(File.separator) + 1, sourceFilePath.lastIndexOf("."));//源文件名
        String splitFileName = targetDirectoryPath + File.separator + sourceFileName + ".sql";//切割后的文件名
        File targetDirectory = new File(targetDirectoryPath);
        if (!targetDirectory.exists()) {
            targetDirectory.mkdirs();
        }

        PrintWriter pw = null;//字符输出流
        String tempLine;
        int lineNum = 0;//本次行数累计 , 达到rows开辟新文件

        String template = null;
        String rowContent = null;
        String preFix = null;
        Integer spliteCount = 3000;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFilePath)))) {
            pw = new PrintWriter(splitFileName);
            while ((tempLine = br.readLine()) != null) {
                lineNum++;
                if(lineNum == 1 || lineNum % spliteCount ==1){
                    pw.write("-- ---小批-----------\n");
                    preFix = tempLine.substring(0, tempLine.indexOf("VALUES")+6);
                    pw.write(preFix);
                    if(lineNum != 1  && lineNum % spliteCount ==0){
                        pw.write(rowContent + ",\n");
                    }
                }else{
                    if(lineNum % spliteCount ==spliteCount){
                        pw.write(rowContent + ";\n");
                    }else{
                        pw.write(rowContent + ",\n");
                    }

                }
                rowContent = tempLine.replace(preFix,"");
                rowContent=rowContent.substring(0,rowContent.length()-1);

            }
            pw.write(rowContent + "; \n");
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
