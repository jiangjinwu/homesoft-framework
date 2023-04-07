package top.homesoft.tools.file;


import java.io.*;

public class SpliteFile {
    private static int THOUSAND_TWENTY_FOUR = 1024; // one thousand and twenty-four


    public static void main(String[] args) {

        splitFileByLine("C:\\Users\\user\\Desktop\\t_bank_branch.sql", "C:\\Users\\user\\Desktop\\sqloutput", 3000);
    }

    public static int splitFileByLine(String sourceFilePath, String targetDirectoryPath, int rows) {
        String sourceFileName = sourceFilePath.substring(sourceFilePath.lastIndexOf(File.separator) + 1, sourceFilePath.lastIndexOf("."));//源文件名
        String splitFileName = targetDirectoryPath + File.separator + sourceFileName + "-%s.sql";//切割后的文件名
        File targetDirectory = new File(targetDirectoryPath);
        if (!targetDirectory.exists()) {
            targetDirectory.mkdirs();
        }

        PrintWriter pw = null;//字符输出流
        String tempLine;
        int lineNum = 0;//本次行数累计 , 达到rows开辟新文件
        int splitFileIndex = 1;//当前文件索引

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFilePath)))) {
            pw = new PrintWriter(String.format(splitFileName, splitFileIndex));
            while ((tempLine = br.readLine()) != null) {
                if (lineNum > 0 && lineNum % rows == 0) {//需要换新文件
                    pw.flush();
                    pw.close();
                    pw = new PrintWriter(String.format(splitFileName, ++splitFileIndex));
                }
                pw.write(tempLine + "\n");
                lineNum++;
            }
            return splitFileIndex;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            if (null != pw) {
                pw.flush();
                pw.close();
            }
        }

    }
}
