package com.cisco.service;

import com.cisco.dao.XmlapiDao;
import com.cisco.poi.Layouter;
import com.cisco.poi.Writer;
import com.cisco.poi.xmlapi.FillXmlapi;
import com.cisco.poi.xmlapi.XMLApiLayouter;
import com.cisco.vo.DailyView;
import com.cisco.vo.XMLApi;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshijie on 6/11/14.
 */
@Service("xmlapiService")
public class XmlapiService {

    @Autowired
    private XmlapiDao xmlapiDao;

    private static Logger logger = Logger.getLogger("service");

    //读取报表并插入数据库中
    public List<XMLApi> readReport(InputStream inp) {

        List<XMLApi> XMLApiList = new ArrayList<XMLApi>();

        try {
            String cellStr = null;
            Workbook wb = WorkbookFactory.create(inp);

            int sheetTotal = 0;
            sheetTotal = wb.getNumberOfSheets();
            Sheet sheet = null;
            for(int k=0;k< sheetTotal;k++){

                sheet = wb.getSheetAt(k);

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    XMLApi XMLApi = new XMLApi();
                    XMLApi addXMLApi = new XMLApi();
                    Row row = sheet.getRow(i);
                    if(row == null){
                        continue;
                    }
                    for(int j = 0; j < row.getLastCellNum(); j++){
                        Cell cell = row.getCell(j);
                        cellStr = ConvertCellStr(cell,cellStr);
                        addXMLApi = addingXmlapi(j, XMLApi, cellStr);
                    }
                    XMLApiList.add(addXMLApi);

                }
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

        return XMLApiList;

    }

    public List<DailyView> getDailyCallTotal(){

        List<DailyView> dailyViewList =  xmlapiDao.getDailyCallTotal();

        return dailyViewList;
    }

    public List<XMLApi> getDailyCallTotalALL(){

        List<XMLApi> viewList =  xmlapiDao.getDailyCallTotalALL();

        return viewList;
    }


    public List<XMLApi> getDailyCallAddCall(){

        List<XMLApi> xmlApiList =  xmlapiDao.getDailyCallAddCall();

        return xmlApiList;
    }
    /**
     * 读取报表的数据后批量插入
     */
    public int[] insertXmlapi(List<XMLApi> list) {
        return xmlapiDao.insertXmlapi(list);

    }

    public void exportXLSEmpty(HttpServletResponse response) {

        // 1.创建一个 workbook
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 2.创建一个 worksheet
        HSSFSheet worksheet = workbook.createSheet("XML-API_calls");

        // 3.定义起始行和列
        int startRowIndex = 0;
        int startColIndex = 0;

        // 4.创建title,data,headers
        Layouter.buildReport(worksheet, startRowIndex, startColIndex);

        // 5.填充数据
        //FillAcademy.fillReport(worksheet, startRowIndex, startColIndex,
        //        getDatasource());

        // 6.设置reponse参数
        String fileName = "XMLApiCalls.xls";
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
        HSSFSheet worksheet = workbook.createSheet("XML-API_calls");

        // 3.定义起始行和列
        int startRowIndex = 0;
        int startColIndex = 0;

        // 4.创建title,data,headers
        XMLApiLayouter xmlapiLayouter = new XMLApiLayouter();
        xmlapiLayouter.buildReport(worksheet, startRowIndex, startColIndex);

        // 5.填充数据
        FillXmlapi fillXmlapi = new FillXmlapi();
        fillXmlapi.fillReport(worksheet, startRowIndex, startColIndex,
                getDatasource());

        // 6.设置reponse参数
        String fileName = "XMLApiCalls.xls";
        response.setHeader("Content-Disposition", "inline; filename="
                + fileName);
        // 确保发送的当前文本格式
        response.setContentType("application/vnd.ms-excel");

        // 7. 输出流
        Writer.write(response, worksheet);
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

    private XMLApi addingXmlapi(int j, XMLApi XMLApi, String cellStr) {
        switch(j){
//            case 0:
//                XMLApi.setId(null);
            case 0:
                XMLApi.setApiType(cellStr.trim());
                break;
            case 1:
                XMLApi.setRequestCount(Long.parseLong(cellStr.trim().split("\\.")[0]));
                break;
            case 2:
                XMLApi.setDistinctSite(Long.parseLong(cellStr.trim().split("\\.")[0]));
                break;
            case 3:
                XMLApi.setDistinctUser(Long.parseLong(cellStr.trim().split("\\.")[0]));
                break;
            case 4:
                XMLApi.setTargetDate(new Date(Date.parse(cellStr.trim())));
                break;
        }
        return XMLApi;
    }

    /**
     * 从数据库获得所有的Academy信息.
     */
    private List<XMLApi> getDatasource() {
        return xmlapiDao.getXmlapi();
    }
}
