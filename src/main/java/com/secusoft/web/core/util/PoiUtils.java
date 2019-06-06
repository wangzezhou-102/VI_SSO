package com.secusoft.web.core.util;


import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 批量读取文件夹下的excel,生成建表语句
 */
public class PoiUtils {
    public static void main(String[] args) throws IOException {
        // 文件名即表明
        String rootPath = "F:\\testpoi";
        //excal文件路径
        String path = "\\ex2\\";
        //生成sql的路径
        String sqlpath = "\\sql2\\";
        //生成总的sql
        String allsql ="all.sql";
        // 读取文件夹下所有excel文件名
        File files = new File(rootPath + path);
        String[] filelist = files.list();
        StringBuffer s = new StringBuffer();
        List<String> list = new ArrayList<> ();
        List<Map<String,String>>  mapList = new ArrayList<> ();
        for (String fileName : filelist) {

            fileName = fileName.split("\\.")[0];

            Map<String, String> map = null;
            String filePath = rootPath + path + fileName + ".xlsx";
            System.out.println (filePath);
            String notes = "";
            Workbook wb = readExcel(filePath);
            if (wb != null) {
                // 用来存放表中数据
                // 生成pgsql建表语句（sb1拼装前半部分，sb2拼装字段描述部分）
                StringBuffer sb1 = new StringBuffer();
                StringBuffer sb2 = new StringBuffer();
                sb1.append("CREATE TABLE ").append(" `" +fileName.toLowerCase() + "`" ).append("(\n").append(" `object_id` varchar(16) NOT NULL COMMENT '关联外部ID',").append("\n");
                sb2.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8\n");
                //获取第一个sheet
                Sheet sheet = wb.getSheetAt(0);
                //获取最大行数（比excel多一行）
                int rownum = sheet.getPhysicalNumberOfRows();
                //获取第一行
                Row row = sheet.getRow(0);
                //获取最大列数,就是7列
                for (int i = 0; i < rownum; i++) {
                    map = new HashMap<> ();
                    row = sheet.getRow(i);
                    // 根据excel类型判断（获取以后都转为小写）
                    String cellFormatValue = getCellFormatValue (row.getCell (4)).toString ().toLowerCase ().trim ();
                    //表注释
                    notes = getCellFormatValue(row.getCell (1)).toString();
                    if ("blob".equals (cellFormatValue) ) {
                        Cell cell = row.getCell (2);
                        sb1.append (" `" + getCellFormatValue (cell).toString ().toLowerCase()+ "` blob " +"   COMMENT " + "'"+(String) getCellFormatValue (row.getCell (3))+"'");

                    } else if ("date".equals (cellFormatValue) ) {
                        Cell cell = row.getCell(2);
                        sb1.append(" `" + getCellFormatValue(cell).toString().toLowerCase() + "` datetime " +" DEFAULT NULL COMMENT " + "'"+(String) getCellFormatValue (row.getCell (3))+"'");
                    }else if ("int".equals (cellFormatValue)) {
                        Cell cell = row.getCell(2);
                        sb1.append(" `" + getCellFormatValue(cell).toString().toLowerCase() + "` int(10) " +" DEFAULT NULL COMMENT " + "'"+(String) getCellFormatValue (row.getCell (3))+"'");
                    }else {
                        Cell cell = row.getCell (2);
                        sb1.append (" `" + getCellFormatValue (cell).toString ().toLowerCase()+ "` varchar(128) " +" DEFAULT NULL COMMENT " + "'"+(String) getCellFormatValue (row.getCell (3))+"'");

                    }
                    //换行
                    sb1.append(",\n");
                }

                if(!CollectionUtils.isEmpty (map)) {
                    mapList.add (map);
                }
                if(org.apache.commons.lang3.StringUtils.isNotBlank(notes)) {
                    sb2.append(" COMMENT= '" + notes + "';");
                }else {
                    sb2.append(";");
                }
                String s1 = sb1.toString().substring(0, sb1.toString().length()-2 ) + "\n";
                s.append(s1 + sb2 + "\n");
                File file = new File(rootPath + sqlpath + fileName + ".sql");
                System.out.println (s);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter wr = new FileWriter(file);
                //将j里的信息，写入wr文本中
                wr.write(s1 + sb2);
                wr.flush();
            }
        }
        File file = new File(rootPath + sqlpath+allsql);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter wr = new FileWriter(file);
        //将j里的信息，写入wr文本中
        wr.write(s.toString());
        wr.flush();
    }

    /**
     * 将重复的写在文档中
     * @param
     * @return
     */
    public static void createContainsSql(List<Map<String,String>> mapList,String path ) throws IOException {
        File file = new File(path + "dwd_sql1/containsSql.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter wr = new FileWriter(file);
        //将j里的信息，写入wr文本中
        wr.write(mapList.toString ());
        wr.flush();
    }

    
    //读取excel
    public static Workbook readExcel(String filePath) {
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            System.out.println (extString);
            if (".xls".equals(extString)) {
                 wb = new HSSFWorkbook (is);
            } else if (".xlsx".equals(extString)) {
                 wb = new XSSFWorkbook (is);
            } else {
                 wb = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public static  Object getCellFormatValue(Cell cell){
        Object cellValue = null;
        if(cell!=null){
            //判断cell类型
            switch(cell.getCellType()){
                case Cell.CELL_TYPE_NUMERIC:{
                    if(HSSFDateUtil.isCellDateFormatted(cell)){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
                        return sdf.format(date).toString();
                    }

                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case Cell.CELL_TYPE_FORMULA:{
                    //判断cell是否为日期格式
                    if(DateUtil.isCellDateFormatted(cell)){
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    }else{
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:{

                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }
}
