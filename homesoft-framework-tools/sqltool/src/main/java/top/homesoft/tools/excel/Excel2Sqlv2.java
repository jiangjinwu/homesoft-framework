package top.homesoft.tools.excel;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Excel2Sqlv2 {

    public static void main(String[] args) throws Exception {
        File file = new File("F:\\work\\项目\\美年\\销售客户绑定关系.xlsx");

        String fileName = "F:\\work\\项目\\美年\\销售客户绑定关系.xlsx";
        fileName ="F:\\work\\项目\\美年\\销售客户绑定关系 - 副本.xlsx";
        fileName ="F:\\work\\项目\\通用\\省市区编码.xlsx";


       // fileName ="F:\\work\\项目\\美年\\销售客户绑定关系.xlsx";

        String prefix =" insert into t_area(province_code,province_name,city_code,city_name,district_code,district_name) values";
        String template ="('$0','$1','$2','$3','$4','$5')";

        Excel2Sqlv2 excel2Sqlv2 = new Excel2Sqlv2();
        List<String>  sqlList =  excel2Sqlv2.importExcel(fileName,prefix,template,2);

        for (int i = 0; i <sqlList.size() ; i++) {
            logger.info("-------- 第{}批-----------------",  i );
            logger.info("sqlList:{}", sqlList.get(i));
        }

    }

    public   List<String> importExcel( String fileName,String prefix , String template,Integer startRowIndex){

        List<String> sqlList = new ArrayList<>();
        //获取文件
        try {

            int batchSize =1000;
            try {

                //生成属性和列对应关系的map，Map<类属性名，对应一行的第几列>
                LinkedHashMap<Integer, String> alias  = new LinkedHashMap<>();
                alias.put(1,"lock_mobile");
                alias.put(2,"referee_mobile");
                alias.put(3,"referee_mobile");
                alias.put(4,"referee_mobile");
                alias.put(5,"referee_mobile");
                alias.put(6,"referee_mobile");
                List<List<Object>> dataList = generateColumnPropertyMap( fileName,alias,startRowIndex,true);
                //根据指定的映射关系进行转换
                logger.info("result:",dataList);
                Integer[] aList =  alias.keySet().toArray(new Integer[0]);

                StringBuilder sb = new StringBuilder();
                Integer index =0;
                for (int i = 0; i < dataList.size(); i++) {
                    index++;
                    List<Object> data = dataList.get(i);
                    if(index%batchSize==1){
                        sb.append( prefix);
                    }

                    logger.info("index:{}",i);
                    String tempSql = getSingleValueParse(aList, template, data);

                    if(StringUtils.isNotEmpty(tempSql)) {
                        if (index % batchSize != 0 && index != dataList.size()) {
                            sb.append(tempSql).append(",");
                        } else if (index % batchSize == 0 || index == dataList.size()) {
                            sb.append(tempSql).append(";");
                            sqlList.add(sb.toString());
                            sb.delete(0, sb.length());
                        }
                    }
                }

                logger.info("sql:{}",sb.toString());

            } catch (Exception e) {
                e.printStackTrace();

            }



        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导入Excel错误",e);
        }
        //使用poi进行遍历
        //遍历excel里的数据
        //写入到mysql数据库
        return sqlList;
    }


    public static void process(String filename) throws Exception {
        LinkedHashMap<Integer, String> alias  = new LinkedHashMap<>();

        List<Map<String,Object>> dataMapList = new ArrayList<>();
        alias.put(1,"lock_mobile");
        alias.put(3,"referee_mobile");
        Excel07SaxReader reader = new Excel07SaxReader(new RowHandler() {
            @Override
            public void handle(int sheetIndex, int rowIndex, List<Object> rowList) {
                System.out.println( rowList);
                Map<String,Object> data = new HashMap<>();
                alias.keySet().stream().forEach(key->{
                    data.put(alias.get(key), rowList.get(key));
                });
                dataMapList.add(data);
            }
        });

        reader.read(filename);

    }


    private static String getSingleValueParse(Integer[] aList, String template,List<Object> data) {
        String tempSql = tempSql = new String(template);
        for (int j = 0; j < aList.length ; j++) {

            if(data.size()<aList.length){
                logger.warn("data error:{}",data);
            }else if(Objects.nonNull(data.get(j))) {
                tempSql = tempSql.replace("$"+j, data.get(j).toString());
            }else{
                logger.error("data:{}",data);
                return null;
                //tempSql = tempSql.replace("$"+j, null);
            }
        }
        return tempSql;
    }

    private   List<List<Object>> generateColumnPropertyMap( String fileName,LinkedHashMap<Integer, String> alias,Integer startRowIndex,Boolean skipNull) {
        List<List<Object>> dataMapList = new ArrayList<>();
        List<Integer>  columnIndexList = alias.keySet().stream().collect(Collectors.toList());
        Excel07SaxReader reader = new Excel07SaxReader(new RowHandler() {
            @Override
            public void handle(int sheetIndex, int rowIndex, List<Object> rowList) {
                if (rowIndex >= startRowIndex) {
                    if(rowIndex%1000==0){
                        logger.info("已读取{}行",rowIndex);
                    }

                    List<Object> dataList = checkAvaliable(rowList,columnIndexList,skipNull);
                    if(CollectionUtil.isNotEmpty(dataList)){
                        dataMapList.add(dataList);
                    }else{
                        logger.info("第{}行包含null 跳过",rowIndex);
                    }
                }
            }
        });
        reader.read(fileName,0);
        return dataMapList;
    }


    List<Object> checkAvaliable(List<Object> rowList, List<Integer>  columnIndexList,Boolean skipNull ){

        Boolean avaliable = true;

        if (!skipNull) {
        for (int i = 0; i < columnIndexList.size(); i++) {
         if (Objects.isNull(rowList.get(columnIndexList.get(i)))) {
             avaliable = false;
             break;
         }
        }
        }else{
            avaliable = true;
        }
        if(avaliable){
            List<Object> data = new ArrayList<>(columnIndexList.size());
            for (int i = 0; i < columnIndexList.size(); i++) {
                  data.add(rowList.get(columnIndexList.get(i)));
            }
            return data;
        }
        return null;
    }

}
