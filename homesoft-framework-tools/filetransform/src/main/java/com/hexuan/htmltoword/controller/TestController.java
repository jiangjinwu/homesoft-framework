package com.hexuan.htmltoword.controller;

import com.hexuan.htmltoword.constants.HtmlToWordConStants;
import com.hexuan.htmltoword.util.word.WordUtils;
import com.hexuan.htmltoword.util.word.XWPFDocumentUtil;
import fr.opensagres.poi.xwpf.converter.core.BasicURIResolver;
import fr.opensagres.poi.xwpf.converter.core.FileImageExtractor;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 正常接口返回通用返回对象，这里只是为了演示word与html的互相转换返回的String
 */
@RestController
public class TestController {

    @Value("${word.src}")
    private String exportWordSrc;

    @Value("${res.word}")
    private String resWord;

    @Value("${app.html.url.head}")
    private String htmlUrlHead;


    /**
     * 文件上传
     *
     * @param file     上传文件
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        String message = "success";
        if (!file.isEmpty()) {
            StringBuilder context = new StringBuilder();
            try {
                if (file.getOriginalFilename().endsWith(HtmlToWordConStants.DOC)) {
                    docToHtml(file);
                } else if (file.getOriginalFilename().endsWith(HtmlToWordConStants.DOCX)) {
                    docxToHtml(file);
                } else {
                    message = "格式有误，只允许doc,docx的word文件";
                    return message;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                message = "操作失败";
                return message;
            }
            String content = readFileByLines(resWord + HtmlToWordConStants.DEMO_HTML);

            System.out.println(content);
        }
        return "ok";
    }

    /**
     * 逐行读取文件
     *
     * @param fileName
     * @return
     */
    public static String readFileByLines(String fileName) {
        FileInputStream file = null;
        BufferedReader reader = null;
        InputStreamReader inputFileReader = null;
        String content = "";
        String tempString = null;
        try {
            file = new FileInputStream(fileName);
            inputFileReader = new InputStreamReader(file, "utf-8");
            reader = new BufferedReader(inputFileReader);
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                content += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return content;
    }

    /**
     * doc转html 输出到资源/word/demo.html
     *
     * @param file
     * @throws TransformerException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    void docToHtml(MultipartFile file) throws TransformerException, IOException, ParserConfigurationException {
        HWPFDocument hwpfDocument = new HWPFDocument(file.getInputStream());
        WordToHtmlConverter converter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        //设置存储图片的管理者--使用匿名内部类实现 该类实现了PicturesManager接口，实现了其中的savePicture方法
        converter.setPicturesManager(new PicturesManager() {
            FileOutputStream out = null;

            //在下面的processDocument方法内部会调用该方法 用于存储word中的图片文件
            @Override
            public String savePicture(byte[] bytes, PictureType pictureType, String name, float width, float height) {
                String imgName = String.valueOf(System.currentTimeMillis());
                try {
                    //单个图片的保存
                    out = new FileOutputStream(resWord + "image/" + imgName + ".jpg");
                    out.write(bytes);
                } catch (IOException exception) {
                    exception.printStackTrace();
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //这里要返回给操作者（HtmlDocumentFacade）一个存储的路径 用于生成Html时定位到图片资源
                return imgName + ".jpg";
            }
        });
        //使用外观模式，将hwpfDocument文档对象设置给HtmlDocumentFacade中的Document属性
        converter.processDocument(hwpfDocument);
        //获取转换器中的document文档
        Document htmlDocument = converter.getDocument();
        //充当文档对象模型 （DOM） 树形式的转换源树的持有者  -- 源树
        DOMSource domSource = new DOMSource(htmlDocument);
        //转换器 该对象用于将源树转换为结果树
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        //设置输出时的以什么方式输出，也可说是结果树的文件类型 可以是html/xml/text或者是一些扩展前三者的扩展类型
        transformer.setOutputProperty(OutputKeys.METHOD, "html");
        //设置一些必要的属性 设置输出时候的编码为utf-8
        transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        //转换 将输入的源树转换为结果树并且输出到streamResult中
        transformer.transform(domSource, new StreamResult(new File(resWord + HtmlToWordConStants.DEMO_HTML)));
    }

    /**
     * docx转html 输出到资源/word/demo.html
     *
     * @param file
     * @throws IOException
     */
    void docxToHtml(MultipartFile file) throws IOException {
        OutputStreamWriter outputStreamWriter = null;
        XWPFDocument document = new XWPFDocument(file.getInputStream());
        XHTMLOptions options = XHTMLOptions.create();
        // 存放图片的文件夹
        options.setExtractor(new FileImageExtractor(new File(resWord + "image/" + System.currentTimeMillis())));
        // html中图片的路径
        options.URIResolver(new BasicURIResolver(System.currentTimeMillis() + "/"));
        outputStreamWriter = new OutputStreamWriter(new FileOutputStream(resWord + HtmlToWordConStants.DEMO_HTML), "utf-8");
        XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
        xhtmlConverter.convert(document, outputStreamWriter, options);
        outputStreamWriter.close();
    }

    /**
     * 导出 word 文档
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/exportWord")
    public String exportWord(HttpServletRequest request, HttpServletResponse response) throws
            Exception {
        String content = "<html><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><style type=\"text/css\">.b1{white-space-collapsing:preserve;}.b2{margin: 1.0in 1.25in 1.0in 1.25in;}.p1{text-align:justify;hyphenate:auto;font-family:Times New Roman;font-size:10pt;}</style><meta content=\"Student\" name=\"author\"></head><body class=\"b1 b2\"><p class=\"p1\"><span>我的测试文档！</span></p><p class=\"p1\"></p><p class=\"p1\"><img src=\"1659941077600.jpg\" style=\"width:2.7777777in;height:2.7777777in;vertical-align:text-bottom;\"></p><p class=\"p1\"></p><p class=\"p1\"><span>正文部分</span></p><p class=\"p1\"><span>吧啦吧啦啦啦啦阿拉啦啦</span></p><p class=\"p1\"></p><p class=\"p1\"><img src=\"1659941077601.jpg\" style=\"width:1.3083333in;height:1.3083333in;vertical-align:text-bottom;\"></p></body></html>\n";
        //由于刚刚导入解析存储的是相对路径，所以导出时要加上图片资源的前缀，我这里直接存储在了自定义的静态资源目录中
        content = replaceImgSrc(content);
        InputStream in = null;
        XWPFDocument doc = null;
        in = Thread.currentThread().getContextClassLoader().getResourceAsStream(exportWordSrc);
        OPCPackage srcPackage = OPCPackage.open(in);
        doc = new XWPFDocument(srcPackage);

        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> param = new HashMap<>(16);
        param.put("content", content);
        mapList.add(param);
        String s = "";
        XWPFDocumentUtil.wordInsertRitchText(doc, mapList);
        // 将docx输出
        WordUtils.exportWordList(doc, "导出文件名" + HtmlToWordConStants.DOCX, request, response);
        //因为上面已经使用response返回了文件，浏览器认为已经收到响应，不需要再次发送响应对象，否则会报错
        return null;
    }

    /**
     * 将html中src的相对路径增加服务器资源前缀
     *
     * @param htmlBody
     * @return
     */
    public String replaceImgSrc(String htmlBody) {
        org.jsoup.nodes.Document document = Jsoup.parse(htmlBody);
        Elements nodes = document.select("img");
        int nodeLenth = nodes.size();
        for (int i = 0; i < nodeLenth; i++) {
            Element e = nodes.get(i);
            String src = e.attr("src");
            if (StringUtils.isNotBlank(src)) {
                e.attr("src", htmlUrlHead + src);
            }
        }
        if (htmlBody.contains(HtmlToWordConStants.HTML_ELEMENT)) {
            return document.toString();
        } else {
            return document.select("body>*").toString();
        }
    }

}
