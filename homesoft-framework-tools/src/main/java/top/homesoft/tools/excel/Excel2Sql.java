package top.homesoft.tools.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class Excel2Sql {

    public static void main(String[] args){
        File file = new File("F:\\work\\项目\\美年\\销售客户绑定关系.xlsx");
        String prefix =" insert into t_store_user_relation_prelock(store_id,referee_mobile,lock_mobile) values";
        String template ="(996,'referee_mobile','lock_mobile')";
        List<String>  sqlList = Excel2Sql.importExcel(file,prefix,template,1);
        for (int i = 0; i <sqlList.size() ; i++) {
            logger.info("-------- 第{}批-----------------",  i );
            logger.info("sqlList:{}", sqlList.get(i));
        }
    }


    public static List<String> importExcel(File files,String prefix , String template,Integer startRowIndex){

        List<String> sqlList = new ArrayList<>();
        //获取文件
        try {
            XSSFWorkbook wb = new XSSFWorkbook(files);

            int batchSize =1000;
            try {
                XSSFSheet sheet = wb.getSheetAt(0);

                //生成属性和列对应关系的map，Map<类属性名，对应一行的第几列>
                LinkedHashMap<Integer, String> alias  = new LinkedHashMap<>();
                alias.put(1,"lock_mobile");
                alias.put(3,"referee_mobile");

                List<Map<String, String>> dataList = generateColumnPropertyMap(sheet, alias,startRowIndex);
                //根据指定的映射关系进行转换
                logger.info("result:",dataList);
                String[] aList =  alias.values().toArray(new String[0]);

                StringBuilder sb = new StringBuilder();
                Integer index =0;
                for (int i = 0; i < dataList.size(); i++) {
                    index++;
                    Map<String, String> data = dataList.get(i);
                    if(index%batchSize==1){
                        sb.append( prefix);
                    }
                    String tempSql = getSingleValueParse(aList, template, data);

                    if( index%batchSize !=0 && index!=dataList.size()){
                        sb.append(tempSql).append(",");
                    }else if(index % batchSize ==0 ||index==dataList.size() ){
                        sb.append(tempSql).append(";");
                        sqlList.add(sb.toString());
                        sb.delete(0, sb.length());
                    }
                }

                logger.info("sql:{}",sb.toString());

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                wb.close();
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

    private static String getSingleValueParse(String[] aList, String template, Map<String, String> data) {
        String tempSql = tempSql = new String(template);
        for (int j = 0; j < aList.length ; j++) {
            tempSql= tempSql.replace( aList[j], data.get(aList[j]));
        }
        return tempSql;
    }

    private static List<Map<String, String>> generateColumnPropertyMap(XSSFSheet sheet, LinkedHashMap<Integer, String> alias,Integer start) {
        List<Map<String, String>> result = new ArrayList<>();

        Iterator<Row>  rowIterator = sheet.rowIterator();

        Integer index =-1;
        while(rowIterator.hasNext()){
            index++;
            XSSFRow propertyRow = (XSSFRow) rowIterator.next();
            if(index>=start) {
                short firstCellNum = propertyRow.getFirstCellNum();
                short lastCellNum = propertyRow.getLastCellNum();
                Map<String, String> propertyMap = new HashMap<>();
                for (int i = firstCellNum; i < lastCellNum; i++) {
                    Cell cell = propertyRow.getCell(i);
                    if (cell == null) {
                        continue;
                    }
                    String key = alias.get(cell.getColumnIndex());



                    if(StringUtils.isNotEmpty(key)) {
                        String cellValue = "";
                        if(cell.getCellTypeEnum()== CellType.NUMERIC){
                            cellValue = String.valueOf(cell.getNumericCellValue());
                        }else {
                            cellValue = cell.getStringCellValue();
                        }
                        // 对应属性名
                        propertyMap.put(key, cellValue);
                    }
                }
                result.add(propertyMap);
            }
        }

        return result;
    }



}
