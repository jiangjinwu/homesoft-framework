package com.hexuan.htmltoword.util.word;

import com.hexuan.htmltoword.constants.HtmlToWordConStants;
import com.hexuan.htmltoword.enums.ElementEnum;
import com.hexuan.htmltoword.enums.TitleFontEnum;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hexuan.wang
 */
public class HtmlUtils {

    /**
     * 给document添加指定元素
     */
    public static void addElement(Document document) {
        if (ObjectUtils.isEmpty(document)) {
            throw new NullPointerException("不允许为空的对象添加元素");
        }
        Elements elements = document.getAllElements();
        for (Element e : elements) {
            String attrName = ElementEnum.getValueByCode(e.tag().getName());
            if (!StringUtils.isEmpty(attrName)) {
                e.attr(HtmlToWordConStants.COMMONATTR, attrName);
            }
        }
    }

    private static ArrayList<String> getWidthHeight(String style, String wRegex, String hRegex) {
        String height = "";
        String width = "";
        Pattern p = Pattern.compile(wRegex);
        Matcher m = p.matcher(style);
        if (m.find()) {
            width = m.group(0).trim();
        }
        p = Pattern.compile(hRegex);
        m = p.matcher(style);
        if (m.find()) {
            height = m.group(0).trim();
        }
        if ("".equals(width)) {
            return null;
        }
        ArrayList<String> list = new ArrayList<>();
        list.add(width);
        list.add(height);
        return list;
    }

    /**
     * 将富文本内容写入到Word
     * 因富文本样式种类繁多，不能一一枚举，目前实现了H1、H2、H3、段落、图片、表格枚举
     *
     * @param ritchText 富文本内容
     * @param doc       需要写入富文本内容的Word 写入图片和表格需要用到
     * @param paragraph
     */
    public static void resolveHtml(String ritchText, XWPFDocument doc, XWPFParagraph paragraph) {
        Document document = Jsoup.parseBodyFragment(ritchText, "UTF-8");
        try {
            // 添加固定元素
            HtmlUtils.addElement(document);
            Elements elements = document.select("[" + HtmlToWordConStants.COMMONATTR + "]");
            for (Element em : elements) {
                XmlCursor xmlCursor = paragraph.getCTP().newCursor();
                switch (em.attr(HtmlToWordConStants.COMMONATTR)) {
                    case "title":
                        break;
                    case "subtitle":
                        break;
                    case "imgurl":
                        String src = em.attr("src");
                        String width = "";
                        String height = "";
                        /**
                         * 如果img标签存在style属性，获取style中的width,height
                         * 如果存在width属性直接获取
                         * 如果以上都未获取到先获取图片到原始比例，给图片设置默认常量宽度HtmlToWordConStants.IMG_WIDTH，再根据原始图片比例缩放设置高度
                         */
                        String style = em.attr("style");
                        if (style != null && !"".equals(style)) {
                            String ptWidthRegex = "(?<=width:).*?pt";
                            String ptHeightRegex = "(?<=height:).*?pt";
                            String pxWidthRegex = "(?<=width:).*?px";
                            String pxHeightRegex = "(?<=height:).*?px";
                            String inWidthRegex = "(?<=width:).*?in";
                            String inHeightRegex = "(?<=height:).*?in";
                            //对应样式中的px,pt,in单位并进行转换
                            ArrayList<String> ptList = getWidthHeight(style, ptWidthRegex, ptHeightRegex);
                            ArrayList<String> pxList = getWidthHeight(style, pxWidthRegex, pxHeightRegex);
                            ArrayList<String> inList = getWidthHeight(style, inWidthRegex, inHeightRegex);
                            if (ptList != null) {
                                width = ptList.get(0);
                                height = ptList.get(1);
                            }
                            if (pxList != null) {
                                width = pxList.get(0);
                                height = pxList.get(1);
                            }
                            if (inList != null) {
                                String w = inList.get(0).substring(0, inList.get(0).length() - 2);
                                String h = inList.get(1).substring(0, inList.get(1).length() - 2);
                                width = Double.parseDouble(w) * 96 + "in";
                                height = Double.parseDouble(h) * 96 + "in";
                            }
                            if (width != "") {
                                width = width.substring(0, width.length() - 2);
                                height = height.substring(0, height.length() - 2);
                            }

                        } else if (width == "" && em.attr("width") != null && !"".equals(em.attr("width"))) {
                            width = em.attr("width");
                            height = em.attr("height");
                            if ("px".equals(width.substring(width.length() - 2)) || "pt".equals(width.substring(width.length() - 2))) {
                                width = width.substring(0, width.length() - 2);
                                height = height.substring(0, height.length() - 2);
                            }
                        }
                        if (width == "") {
                            System.out.println(src);
                            int[] imageSize = getImgWH(src);
                            if (imageSize != null) {
                                width = Integer.toString(imageSize[0]);
                                height = Integer.toString(imageSize[1]);
                            }
                            double scale = Double.parseDouble(width) / Double.parseDouble(HtmlToWordConStants.IMG_WIDTH);
                            width = HtmlToWordConStants.IMG_WIDTH;
                            height = String.valueOf(Double.parseDouble(height) / scale);
                        }
                        try {
                            System.out.println(src);
                            URL url = new URL(src);
                            URLConnection uc = url.openConnection();
                            InputStream inputStream = uc.getInputStream();
                            XWPFParagraph imgurlparagraph = doc.insertNewParagraph(xmlCursor);
                            ParagraphStyleUtil.setImageCenter(imgurlparagraph);
//                            System.out.println(width + ":" + height);
                            imgurlparagraph.createRun().addPicture(inputStream, XWPFDocument.PICTURE_TYPE_PNG, "图片.jpeg", Units.toEMU(Double.parseDouble(width)), Units.toEMU(Double.parseDouble(height)));
                            closeStream(inputStream);
                            File file = new File("picture.jpg");
                            boolean exists = file.exists();
                            if (exists) {
                                file.delete();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    case "imgbase64":
                        break;
                    case "table":
                        XWPFTable xwpfTable = doc.insertNewTbl(xmlCursor);
                        addTable(xwpfTable, em);
                        // 设置表格居中
                        ParagraphStyleUtil.setTableLocation(xwpfTable, "center");
                        // 设置内容居中
                        ParagraphStyleUtil.setCellLocation(xwpfTable, "CENTER", "center");
                        break;
                    case "h1":
                        XWPFParagraph h1paragraph = doc.insertNewParagraph(xmlCursor);
                        XWPFRun xwpfRun_1 = h1paragraph.createRun();
                        xwpfRun_1.setText(em.text());
                        //居中
                        ParagraphStyleUtil.setImageCenter(h1paragraph);
                        // 设置字体
                        ParagraphStyleUtil.setTitle(xwpfRun_1, TitleFontEnum.H1.getTitle());
                        break;
                    case "h2":
                        XWPFParagraph h2paragraph = doc.insertNewParagraph(xmlCursor);
                        XWPFRun xwpfRun_2 = h2paragraph.createRun();
                        xwpfRun_2.setText(em.text());
                        //居中
                        ParagraphStyleUtil.setImageCenter(h2paragraph);
                        // 设置字体
                        ParagraphStyleUtil.setTitle(xwpfRun_2, TitleFontEnum.H2.getTitle());
                        break;
                    case "h3":
                        XWPFParagraph h3paragraph = doc.insertNewParagraph(xmlCursor);
                        XWPFRun xwpfRun_3 = h3paragraph.createRun();
                        xwpfRun_3.setText(em.text());
                        // 设置字体
                        ParagraphStyleUtil.setTitle(xwpfRun_3, TitleFontEnum.H3.getTitle());
                        break;
                    case "paragraph":
                        XWPFParagraph paragraphd = doc.insertNewParagraph(xmlCursor);
                        // 设置段落缩进 4个空格
                        paragraphd.createRun().setText("    " + em.text());
                        break;
                    case "br":
                        XWPFParagraph br = doc.insertNewParagraph(xmlCursor);
                        XWPFRun run = br.createRun();
                        run.addBreak(BreakType.TEXT_WRAPPING);
                        break;
                    case "h7":
                        XWPFParagraph h7paragraph = doc.insertNewParagraph(xmlCursor);
                        XWPFRun xwpfRun_7 = h7paragraph.createRun();
                        xwpfRun_7.setText(em.text());
                        //居左
                        ParagraphStyleUtil.AlignmentRight(h7paragraph);
                        // 设置字体
                        ParagraphStyleUtil.setTitle(xwpfRun_7, TitleFontEnum.H7.getTitle());
                        break;
                    default:
                        XWPFParagraph def = doc.insertNewParagraph(xmlCursor);
                        def.createRun().setText(em.text());
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取远程url图片,得到宽高
     *
     * @param imgurl 图片路径
     * @return [0] 宽  [1]高
     */
    public static int[] getImgWH(String imgurl) {
        boolean b = false;
        try {
            //实例化url
            URL url = new URL(imgurl);
            //载入图片到输入流
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            //实例化存储字节数组
            byte[] bytes = new byte[100];
            //设置写入路径以及图片名称
            OutputStream bos = new FileOutputStream(new File("pic.jpg"));
            int len;
            while ((len = bis.read(bytes)) > 0) {
                bos.write(bytes, 0, len);
            }
            bis.close();
            bos.flush();
            bos.close();
            //关闭输出流
            b = true;
        } catch (Exception e) {
            //如果图片未找到
            b = false;
        }
        int[] a = new int[2];
        //图片存在
        if (b) {
            //得到文件
            File file = new File("pic.jpg");
            BufferedImage bi = null;
            boolean imgwrong = false;
            try {
                //读取图片
                bi = javax.imageio.ImageIO.read(file);
                try {
                    //判断文件图片是否能正常显示,有些图片编码不正确
                    int i = bi.getType();
                    imgwrong = true;
                } catch (Exception e) {
                    imgwrong = false;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (imgwrong) {
                //获得 宽度
                a[0] = bi.getWidth();
                //获得 高度
                a[1] = bi.getHeight();
            } else {
                a = null;
            }
            //删除文件
            file.delete();
        } else {//图片不存在
            a = null;
        }
        return a;

    }

    /**
     * 关闭输入流
     *
     * @param closeables
     */
    public static void closeStream(Closeable... closeables) {
        for (Closeable c : closeables) {
            if (c != null) {
                try {
                    c.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 将富文本的表格转换为Word里面的表格
     */
    private static void addTable(XWPFTable xwpfTable, Element table) {
        Elements trs = table.getElementsByTag("tr");
        // XWPFTableRow 第0行特殊处理
        int rownum = 0;
        for (Element tr : trs) {
            addTableTr(xwpfTable, tr, rownum);
            rownum++;
        }
    }


    /**
     * 将元素里面的tr 提取到 xwpfTabel
     */
    private static void addTableTr(XWPFTable xwpfTable, Element tr, int rownum) {
        Elements tds = tr.getElementsByTag("th").isEmpty() ? tr.getElementsByTag("td") : tr.getElementsByTag("th");
        XWPFTableRow row_1 = null;
        for (int i = 0, j = tds.size(); i < j; i++) {
            if (0 == rownum) {
                // XWPFTableRow 第0行特殊处理,
                XWPFTableRow row_0 = xwpfTable.getRow(0);
                if (i == 0) {
                    row_0.getCell(0).setText(tds.get(i).text());
                } else {
                    row_0.addNewTableCell().setText(tds.get(i).text());
                }
            } else {
                if (i == 0) {
                    // 换行需要创建一个新行
                    row_1 = xwpfTable.createRow();
                    row_1.getCell(i).setText(tds.get(i).text());
                } else {
                    row_1.getCell(i).setText(tds.get(i).text());
                }
            }
        }

    }
}
