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

        // 1.����һ�� workbook
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 2.����һ�� worksheet
        HSSFSheet worksheet = workbook.createSheet("Acacemy");

        // 3.������ʼ�к���
        int startRowIndex = 0;
        int startColIndex = 0;

        // 4.����title,data,headers
        Layouter.buildReport(worksheet, startRowIndex, startColIndex);

        // 5.�������
        //FillAcademy.fillReport(worksheet, startRowIndex, startColIndex,
        //        getDatasource());

        // 6.����reponse����
        String fileName = "AcademyReport.xls";
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
        HSSFSheet worksheet = workbook.createSheet("Acacemy");

        // 3.������ʼ�к���
        int startRowIndex = 0;
        int startColIndex = 0;

        // 4.����title,data,headers
        Layouter.buildReport(worksheet, startRowIndex, startColIndex);

        // 5.�������
        FillAcademy.fillReport(worksheet, startRowIndex, startColIndex,
                getDatasource());

        // 6.����reponse����
        String fileName = "AcademyReport.xls";
        response.setHeader("Content-Disposition", "inline; filename="
                + fileName);
        // ȷ�����͵ĵ�ǰ�ı���ʽ
        response.setContentType("application/vnd.ms-excel");

        // 7. �����
        Writer.write(response, worksheet);
    }

    /**
     * �����ݿ������е�Academy��Ϣ.
     */
    private List<Academy> getDatasource() {
        return poiDao.getAcademy();
    }
    /**
     * ��ȡ��������ݺ���������
     */
    public int[] insertAcademy(List<Academy> list) {
        return poiDao.insertAcademy(list);

    }


    //��ȡ�����������ݿ���
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
                logger.info("û��������");
            }
        }

        return academyList;

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