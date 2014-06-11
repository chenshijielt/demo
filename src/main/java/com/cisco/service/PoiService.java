package com.cisco.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cisco.dao.PoiDao;
import com.cisco.vo.Academy;
import com.cisco.poi.FillAcademy;
import com.cisco.poi.Layouter;
import com.cisco.poi.Writer;

@Service("poiService")
@Transactional
public class PoiService {

    @Autowired
    private PoiDao poiDao;
    private static Logger logger = Logger.getLogger("service");

    public void exportXLSEmpty(HttpServletResponse response) {

        // 1.创建一个 workbook
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 2.创建一个 worksheet
        HSSFSheet worksheet = workbook.createSheet("Acacemy");

        // 3.定义起始行和列
        int startRowIndex = 0;
        int startColIndex = 0;

        // 4.创建title,data,headers
        Layouter.buildReport(worksheet, startRowIndex, startColIndex);

        // 5.填充数据
        //FillAcademy.fillReport(worksheet, startRowIndex, startColIndex,
        //        getDatasource());

        // 6.设置reponse参数
        String fileName = "AcademyReport.xls";
        response.setHeader("Content-Disposition", "inline; filename="
                + fileName);
        // 确保发送的当前文本格式
        response.setContentType("application/vnd.ms-excel");

        // 7. 输出流
        Writer.write(response, worksheet);
    }


    //读取数据库并导出报表
    public void exportXLS(HttpServletResponse response) {

        // 1.创建一个 workbook
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 2.创建一个 worksheet
        HSSFSheet worksheet = workbook.createSheet("Acacemy");

        // 3.定义起始行和列
        int startRowIndex = 0;
        int startColIndex = 0;

        // 4.创建title,data,headers
        Layouter.buildReport(worksheet, startRowIndex, startColIndex);

        // 5.填充数据
        FillAcademy.fillReport(worksheet, startRowIndex, startColIndex,
                getDatasource());

        // 6.设置reponse参数
        String fileName = "AcademyReport.xls";
        response.setHeader("Content-Disposition", "inline; filename="
                + fileName);
        // 确保发送的当前文本格式
        response.setContentType("application/vnd.ms-excel");

        // 7. 输出流
        Writer.write(response, worksheet);
    }

    /**
     * 从数据库获得所有的Academy信息.
     */
    private List<Academy> getDatasource() {
        return poiDao.getAcademy();
    }
    /**
     * 读取报表的数据后批量插入
     */
    public int[] insertAcademy(List<Academy> list) {
        return poiDao.insertAcademy(list);

    }


    //读取报表并插入数据库中
    public List<Academy> readReport(InputStream inp) {

        List<Academy> academyList = new ArrayList<Academy>();

        try {
            String cellStr = null;
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            for (int i = 3; i <= sheet.getLastRowNum(); i++) {
                Academy academy = new Academy();
                Academy addAcademy = new Academy();
                Row row = sheet.getRow(i);
                if(row == null){
                    continue;
                }
                for(int j = 0; j < row.getLastCellNum(); j++){
                    Cell cell = row.getCell(j);
                    cellStr = ConvertCellStr(cell,cellStr);
                    addAcademy = addingAcademy(j,academy,cellStr);
                }
                academyList.add(addAcademy);

            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inp != null){
                try {
                    inp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                logger.info("没有数据流");
            }
        }

        return academyList;

    }

    /**
     * 把单元格内的类型转换至String类型
     */
    private String ConvertCellStr(Cell cell, String cellStr) {
        switch (cell.getCellType()) {

            case Cell.CELL_TYPE_STRING:
                // 读取String
                cellStr = cell.getStringCellValue().toString();
                break;

            case Cell.CELL_TYPE_BOOLEAN:
                // 得到Boolean对象的方法
                cellStr = String.valueOf(cell.getBooleanCellValue());
                break;

            case Cell.CELL_TYPE_NUMERIC:

                // 先看是否是日期格式
                if (DateUtil.isCellDateFormatted(cell)) {

                    // 读取日期格式
                    cellStr = cell.getDateCellValue().toString();

                } else {

                    // 读取数字
                    cellStr = String.valueOf(cell.getNumericCellValue());
                }
                break;

            case Cell.CELL_TYPE_FORMULA:
                // 读取公式
                cellStr = cell.getCellFormula().toString();
                break;
        }
        return cellStr;
    }

    private Academy addingAcademy(int j, Academy academy, String cellStr) {
        switch(j){
            case 0:
                academy.setAcademyId(null);
            case 1:
                academy.setAcademy(cellStr);
        }
        return academy;
    }


}