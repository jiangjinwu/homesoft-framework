package top.homesoft.tools.excel;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

@Slf4j
public class ExcelExport {

    public static void main(String[] args) throws IOException {
       // ExcelExport.Excel2007AboveOperate("e:\\f1.xlsx");
        ExcelExport excelExport = new ExcelExport();
        //excelExport.write("f:\\area.json",5,6,2);

        excelExport.writeJsonIgnoreDuplicate("f:\\area-province.json",1,2,2);
       // excelExport.writeJsonIgnoreDuplicate("f:\\area-city.json",3,4,2);
    }


    public static void Excel2007AboveOperate(String filePath) throws IOException {
                  XSSFWorkbook workbook1 = new XSSFWorkbook(new FileInputStream(new File(filePath)));
                  SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook1, 100);
          //            Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(filePath)));
                  Sheet first = sxssfWorkbook.getSheetAt(0);
                 for (int i = 0; i < 100000; i++) {
                          Row row = first.createRow(i);
                          for (int j = 0; j < 11; j++) {
                                 if(i == 0) {
                                          // 首行
                                         row.createCell(j).setCellValue("column" + j);
                                     } else {
                                          // 数据
                                         if (j == 0) {
                                                 CellUtil.createCell(row, j, String.valueOf(i));
                                              } else
                                                  CellUtil.createCell(row, j, String.valueOf(Math.random()));
                                      }
                              }
                      }
                  FileOutputStream out = new FileOutputStream("e:\\workbook.xlsx");
                  sxssfWorkbook.write(out);
                  out.close();
             }


             public void write(String outputFile,Integer keyIndex,Integer valueIndex,Integer startRowIndex) throws IOException {
                 String fileName ="F:\\work\\项目\\通用\\省市区编码.xlsx";
                 BufferedWriter bw=new BufferedWriter(new FileWriter(outputFile));
                 Excel07SaxReader reader = new Excel07SaxReader(new RowHandler() {
                     @SneakyThrows
                     @Override
                     public void handle(int sheetIndex, int rowIndex, List<Object> rowList) {
                         if (rowIndex >= startRowIndex) {
                             if(rowIndex%1000==0){
                                 logger.info("已读取{}行",rowIndex);
                             }
                             if(rowIndex == rowList.size()-1){
                                 bw.write(String.format("  %s : '%s'"+System.getProperty("line.separator"),rowList.get(keyIndex),rowList.get(valueIndex)));
                             }else{
                                 bw.write(String.format("  %s : '%s' ,"+System.getProperty("line.separator"),rowList.get(keyIndex),rowList.get(valueIndex)));
                             }
                         }
                     }
                 });

                 bw.write("{"+System.getProperty("line.separator"));
                 reader.read(fileName,0);
                 bw.write("}");
                 bw.flush();
                 bw.close();
             }



    public void writeJsonIgnoreDuplicate(String outputFile,Integer keyIndex,Integer valueIndex,Integer startRowIndex) throws IOException {
        String fileName ="F:\\work\\项目\\通用\\省市区编码.xlsx";
        BufferedWriter bw=new BufferedWriter(new FileWriter(outputFile));

        Map<Object,Object> dataMap = new HashMap<>();

        List<Object> keyList = new ArrayList<>();
        Excel07SaxReader reader = new Excel07SaxReader(new RowHandler() {
            @SneakyThrows
            @Override
            public void handle(int sheetIndex, int rowIndex, List<Object> rowList) {
                if (rowIndex >= startRowIndex) {
                    if(rowIndex%1000==0){
                        logger.info("已读取{}行",rowIndex);
                    }

                    Object value = dataMap.get(rowList.get(keyIndex));
                    if(Objects.isNull(value)){
                        keyList.add(rowList.get(keyIndex));
                        dataMap.put(rowList.get(keyIndex),rowList.get(valueIndex));
                    }
                }
            }
        });

        bw.write("{"+System.getProperty("line.separator"));
        reader.read(fileName,0);


        for (int i = 0; i < keyList.size(); i++) {
            Object key = keyList.get(i);
            if(i <=keyList.size()-1){
                bw.write(String.format("  %s : '%s' ,"+System.getProperty("line.separator"),key,dataMap.get(key)));
            }else{
                bw.write(String.format("  %s : '%s'"+System.getProperty("line.separator"),key,dataMap.get(key)));
            }
        }

        bw.write("}");
        bw.flush();
        bw.close();
    }
}
