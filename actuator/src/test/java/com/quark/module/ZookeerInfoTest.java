package com.quark.module;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 2018/3/1/001.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ZookeerInfoTest {

    @Test
    public void zkConnTest() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper("192.168.194.130:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
            }
        });
        ZooKeeper.States zooKeeperState = zooKeeper.getState();
        System.out.println("---------" + zooKeeperState);
    }

    @Test
    public void test2() {
        Fu fu = new Zi();
        fu.invoke();
    }


    //从excel中读取数据生成java bean 、baseResultMapper in xml files、 column alias
    @Test
    public void excelToJava() throws IOException {
        FileInputStream is = new FileInputStream("C:\\Users\\Administrator\\Desktop\\才米接口改造 V1.0.xlsx");
        XSSFWorkbook excel = new XSSFWorkbook(is);
        List<String> sheetNameList = new ArrayList<>();
        List<XSSFSheet> currentSheetList = new ArrayList<>();
//        调用接口数据格式
        sheetNameList.add("出--新增绑卡");
        sheetNameList.add("出--新增客户");
        sheetNameList.add("出--新增合约");
        for (int i = 0; i < excel.getNumberOfSheets() - 1; i++) {
            if (sheetNameList.contains(excel.getSheetAt(i).getSheetName())) {
//                currentSheet = excel.getSheetAt(i);
                currentSheetList.add(excel.getSheetAt(i));
            }
        }
        for (int k = 0; k < currentSheetList.size(); k++) {
            XSSFSheet currentSheet = currentSheetList.get(k);

            Iterator<Row> rowIterator = currentSheet.iterator();
            //开始行和结束行
            int startRow = 0;
            int endRow = 0;
            //创建一个保存需要行的list
            List<Integer> allRows = new ArrayList<>();
            //创建一个需要不填字段的row list
            List<Integer> nonRequiredRows = new ArrayList<>();
            //创建一个需要获取的column的list
            List<Integer> requiredColumn = new ArrayList<>();
            Set<Integer> requiredColumnSet = new HashSet<>();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.iterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();//next方法在一个循环中只能调用一次
                    //枚举类型 中重写了equals方法 内部使用==比较
                    if (CellType.STRING.equals(cell.getCellTypeEnum())) {
                        //以带有data_list 的下一行为开始行
                        if ("data_list".equals(cell.getStringCellValue())) {
                            startRow = cell.getRowIndex() + 1;
                        }
                        // 以带有返回参数 的上一行为结束行
                        if ("返回参数".equals(cell.getStringCellValue())) {
                            endRow = cell.getRowIndex() - 1;
//                    break;
                        }
                        if ("不传".equals(cell.getStringCellValue())) {
                            nonRequiredRows.add(cell.getRowIndex());
                        }
                        if ("参数".equals(cell.getStringCellValue())) {
                            requiredColumn.add(cell.getColumnIndex());
                        }
                        if ("类型".equals(cell.getStringCellValue())) {
                            requiredColumn.add(cell.getColumnIndex());
                        }
                        if ("字段描述".equals(cell.getStringCellValue())) {
                            requiredColumn.add(cell.getColumnIndex());
                        }
                        if ("字段".equals(cell.getStringCellValue())) {
                            requiredColumn.add(cell.getColumnIndex());
                        }
                        if ("备注".equals(cell.getStringCellValue())) {
                            requiredColumn.add(cell.getColumnIndex());
                        }
                        if ("表".equals(cell.getStringCellValue())) {
                            requiredColumn.add(cell.getColumnIndex());
                        }
                    }
                }
            }
            //对保存column的list  从小到大排序
            Collections.sort(requiredColumn);
            requiredColumnSet.addAll(requiredColumn);
            requiredColumn.clear();
            requiredColumn.addAll(requiredColumnSet);
            for (int i = startRow; i <= endRow; i++) {
                allRows.add(i);
            }
            //删除带有'不填'字段的行
            allRows.removeAll(nonRequiredRows);
            //封装数据到list  不同的列之间用 ; 隔开
            List<String> myList = new ArrayList<>();
            for (int i = 0; i < allRows.size(); i++) {
                XSSFRow row = currentSheet.getRow(allRows.get(i));
                StringBuffer stringBuffer = new StringBuffer();
                //指定行第一列不能为空
                if (!"".equals(row.getCell(requiredColumn.get(0)).getStringCellValue())) {
                    for (int j = 0; j < requiredColumn.size(); j++) {
                        //拼接字符
                        stringBuffer.append(row.getCell(requiredColumn.get(j)).getStringCellValue() + ";");
                    }
                    myList.add(stringBuffer.toString());
                }
            }
            //list中的数据格式 ：参数;属性类型;字段描述;表名;数据库字段;数据库类型;备注
            //合并
            StringBuffer allContent = new StringBuffer();
            //java实体符串
            StringBuffer javaEntity = new StringBuffer();
            //mapper映射符串
            StringBuffer mapper = new StringBuffer();
            //sql 字符串
            //java实体 格式：private 类型 字段 // 注释
            StringBuffer sql = new StringBuffer();
            for (int i = 0; i < myList.size(); i++) {
                String[] array = myList.get(i).split(";");
                if("string".equalsIgnoreCase(array[1])){
                    array[1] = array[1].replace("s","S");
                }
                javaEntity.append("//"+array[2].replace("\n","\r\n//"));
                javaEntity.append("\r\n");//注释和代码之间换行
                javaEntity.append("private  " + (array[1].equals("") ? "String" : array[1]) + " " + array[0] + ";\r\n");
            }
            //mapper 映射  格式：<result column="USER_NO" property="字段" jdbcType="数据库类型" />
            for (int i = 0; i < myList.size(); i++) {
                String[] array = myList.get(i).split(";");
                String jdbcTypeConverter = array[5].toUpperCase();
                if ("".equals(jdbcTypeConverter) || jdbcTypeConverter.contains("VARCHAR") || jdbcTypeConverter.contains("varchar")
                        || jdbcTypeConverter.contains("char") || jdbcTypeConverter.contains("CHAR")) {
                    jdbcTypeConverter = "VARCHAR";
                } else if (jdbcTypeConverter.contains("NUMBER") || jdbcTypeConverter.contains("number")) {
                    jdbcTypeConverter = "DECIMAL";
                }
                mapper.append("<result column=\"" + array[0].toUpperCase() + "\"" + " property=\"" + array[0] + "\"" + " jdbcType=" + "\"" + jdbcTypeConverter + "\" />\r\n");
            }
            //sql 语句 格式
            // 表名.原字段名 别名(属性名大写)
            for (int i = 0; i < myList.size(); i++) {
                String[] array = myList.get(i).split(";");
                if ("".equals(array[4])) {
                    array[4] = "     ";
                }
                String prefix = "";
                if(!"".equals(array[3])){
                    prefix=array[3]+".";
                }
                sql.append(prefix+array[4].toUpperCase() + "  " + array[0].toUpperCase() + ",\r\n");
            }
            String sqlSubs = sql.substring(0, sql.lastIndexOf(","));
            allContent.append(javaEntity + "\r\n" + "\r\n" + "\r\n" + mapper + "\r\n" + "\r\n" + "\r\n" + sqlSubs);
            String path = "C:\\Users\\Administrator\\Desktop\\" + currentSheet.getSheetName() + ".txt";
            File file = new File(path);
            FileWriter fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(allContent.toString());
            bw.close();
            fw.close();
        }
    }

    //从excel表中读取java 字段 生成set方法
    @Test
    public void excelToBeanMethod() throws IOException {
        FileInputStream is = new FileInputStream("C:\\Users\\Administrator\\Desktop\\才米接口改造 V1.0.xlsx");
        XSSFWorkbook excel = new XSSFWorkbook(is);
        List<String> sheetNameList = new ArrayList<>();
        List<XSSFSheet> currentSheetList = new ArrayList<>();
//        调用接口数据格式
        sheetNameList.add("入--产品及产品利率");
        for (int i = 0; i < excel.getNumberOfSheets() - 1; i++) {
            if (sheetNameList.contains(excel.getSheetAt(i).getSheetName())) {
//                currentSheet = excel.getSheetAt(i);
                currentSheetList.add(excel.getSheetAt(i));
            }
        }
        for (int k = 0; k < currentSheetList.size(); k++) {
            XSSFSheet currentSheet = currentSheetList.get(k);

            Iterator<Row> rowIterator = currentSheet.iterator();
            //开始行和结束行
            int startRow = 0;
            int endRow = 0;
            //创建一个保存需要行的list
            List<Integer> allRows = new ArrayList<>();
            //创建一个需要不填字段的row list
            List<Integer> nonRequiredRows = new ArrayList<>();
            //创建一个需要获取的column的list
            List<Integer> requiredColumn = new ArrayList<>();
            Set<Integer> requiredColumnSet = new HashSet<>();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.iterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();//next方法在一个循环中只能调用一次
                    //枚举类型 中重写了equals方法 内部使用==比较
                    if (CellType.STRING.equals(cell.getCellTypeEnum())) {
                        //以带有data_list 的下一行为开始行
                        if ("产品：".equals(cell.getStringCellValue())) {
                            startRow = cell.getRowIndex() + 2;
                        }
                        // 以带有返回参数 的上一行为结束行
                        if ("利率：".equals(cell.getStringCellValue())) {
                            endRow = cell.getRowIndex() - 3;
//                    break;
                        }
                        if (cell.getStringCellValue().contains("暂无字段")||cell.getStringCellValue().contains("暂无此字段")) {
                            nonRequiredRows.add(cell.getRowIndex());
                        }
                        if ("待填".equals(cell.getStringCellValue())) {
                            nonRequiredRows.add(cell.getRowIndex());
                        }
                        if ("字段".equals(cell.getStringCellValue())) {
                            requiredColumn.add(cell.getColumnIndex());
                        }
                        if ("参数".equals(cell.getStringCellValue())) {
                            requiredColumn.add(cell.getColumnIndex());
                        }
                    }
                }
            }
            //对保存column的list  从小到大排序
            Collections.sort(requiredColumn);
            requiredColumnSet.addAll(requiredColumn);
            requiredColumn.clear();
            requiredColumn.addAll(requiredColumnSet);
            for (int i = startRow; i <= endRow; i++) {
                allRows.add(i);
            }
            //删除带有'不填'字段的行
            allRows.removeAll(nonRequiredRows);
            //封装数据stringtbuffer
            StringBuffer setMethod = new StringBuffer();
            for (int i = 0; i < allRows.size(); i++) {
                XSSFRow row = currentSheet.getRow(allRows.get(i));
                StringBuffer rowSb = new StringBuffer("tProductKuake.set");
                //指定行第一列不能为空
                if (!"".equals(row.getCell(requiredColumn.get(0)).getStringCellValue())) {
                        String mapValue = row.getCell(requiredColumn.get(0)).getStringCellValue();
                        String columnValue = row.getCell(requiredColumn.get(1)).getStringCellValue();
                        //拼接格式  set属性（map.get("参数")）
                        //把字段处理成java bean 中的属性
                        String[] array = columnValue.split("_");
                        for(int g = 0 ; g < array.length ;g++){
                            rowSb.append(array[g].substring(0, 1).toUpperCase());
                            if(array[g].length()>1) {
                                rowSb.append(array[g].substring(1));
                            }
                    }
                    rowSb.append("(map.get(\""+mapValue+"\"))\r\n");
                }
                setMethod.append(rowSb);
            }
            String path = "C:\\Users\\Administrator\\Desktop\\" + currentSheet.getSheetName() + ".txt";
            File file = new File(path);
            FileWriter fw = new FileWriter(path, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(setMethod.toString());
            bw.close();
            fw.close();
        }
    }
}
