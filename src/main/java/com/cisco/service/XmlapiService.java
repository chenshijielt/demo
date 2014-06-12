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

    //��ȡ�����������ݿ���
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
                logger.info("û��������");
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
     * ��ȡ��������ݺ���������
     */
    public int[] insertXmlapi(List<XMLApi> list) {
        return xmlapiDao.insertXmlapi(list);

    }

    public void exportXLSEmpty(HttpServletResponse response) {

        // 1.����һ�� workbook
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 2.����һ�� worksheet
        HSSFSheet worksheet = workbook.createSheet("XML-API_calls");

        // 3.������ʼ�к���
        int startRowIndex = 0;
        int startColIndex = 0;

        // 4.����title,data,headers
        Layouter.buildReport(worksheet, startRowIndex, startColIndex);

        // 5.�������
        //FillAcademy.fillReport(worksheet, startRowIndex, startColIndex,
        //        getDatasource());

        // 6.����reponse����
        String fileName = "XMLApiCalls.xls";
        response.setHeader("Content-Disposition", "inline; filename="
                + fileName);
        // ȷ�����͵ĵ�ǰ�ı���ʽ
        response.setContentType("application/vnd.ms-excel");

        // 7. �����
        Writer.write(response, worksheet);
    }

    //��ȡ���ݿⲢ��������
    public void exportXLS(HttpServletResponse response) {

        // 1.����һ�� workbook
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 2.����һ�� worksheet
        HSSFSheet worksheet = workbook.createSheet("XML-API_calls");

        // 3.������ʼ�к���
        int startRowIndex = 0;
        int startColIndex = 0;

        // 4.����title,data,headers
        XMLApiLayouter xmlapiLayouter = new XMLApiLayouter();
        xmlapiLayouter.buildReport(worksheet, startRowIndex, startColIndex);

        // 5.�������
        FillXmlapi fillXmlapi = new FillXmlapi();
        fillXmlapi.fillReport(worksheet, startRowIndex, startColIndex,
                getDatasource());

        // 6.����reponse����
        String fileName = "XMLApiCalls.xls";
        response.setHeader("Content-Disposition", "inline; filename="
                + fileName);
        // ȷ�����͵ĵ�ǰ�ı���ʽ
        response.setContentType("application/vnd.ms-excel");

        // 7. �����
        Writer.write(response, worksheet);
    }

    /**
     * �ѵ�Ԫ���ڵ�����ת����String����
     */
    private String ConvertCellStr(Cell cell, String cellStr) {
        switch (cell.getCellType()) {

            case Cell.CELL_TYPE_STRING:
                // ��ȡString
                cellStr = cell.getStringCellValue().toString();
                break;

            case Cell.CELL_TYPE_BOOLEAN:
                // �õ�Boolean����ķ���
                cellStr = String.valueOf(cell.getBooleanCellValue());
                break;

            case Cell.CELL_TYPE_NUMERIC:

                // �ȿ��Ƿ������ڸ�ʽ
                if (DateUtil.isCellDateFormatted(cell)) {

                    // ��ȡ���ڸ�ʽ
                    cellStr = cell.getDateCellValue().toString();

                } else {

                    // ��ȡ����
                    cellStr = String.valueOf(cell.getNumericCellValue());
                }
                break;

            case Cell.CELL_TYPE_FORMULA:
                // ��ȡ��ʽ
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
     * �����ݿ������е�Academy��Ϣ.
     */
    private List<XMLApi> getDatasource() {
        return xmlapiDao.getXmlapi();
    }
}
