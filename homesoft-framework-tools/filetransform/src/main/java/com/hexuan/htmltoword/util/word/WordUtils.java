package com.hexuan.htmltoword.util.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 导出 word 工具类
 *
 * @author hexuan.wang
 */
public class WordUtils {

    /**
     * 导出word
     *
     * @param doc      word 模版地址
     * @param fileName 文件名
     * @param request
     * @param response
     */
    public static void exportWordList(XWPFDocument doc, String fileName, HttpServletRequest request, HttpServletResponse response) {
        Assert.notNull(fileName, "导出文件名不能为空");
        Assert.isTrue(fileName.endsWith(".docx"), "word导出请使用docx格式");
        try {
            String userAgent = request.getHeader("user-agent").toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
            }
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            OutputStream out = response.getOutputStream();
            doc.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
