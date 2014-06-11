package com.cisco.poi;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellStyle;

//import com.cisco.vo.Academic;
import com.cisco.vo.Academy;


public class FillAcademy {

    public static void fillReport(HSSFSheet worksheet,int startRowIndex,
                                  int startColIndex,List<Academy> datasource){

        startRowIndex += 2;

        // Create cell style for the body
        HSSFCellStyle bodyCellStyle = worksheet.getWorkbook().createCellStyle();
        bodyCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        bodyCellStyle.setWrapText(false); //是否自动换行.
        bodyCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        bodyCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        bodyCellStyle.setBorderRight(CellStyle.BORDER_THIN);
        bodyCellStyle.setBorderTop(CellStyle.BORDER_THIN);

        for (int i=startRowIndex; i+startRowIndex-2< datasource.size()+2; i++) {
            // Create a new row
            HSSFRow row = worksheet.createRow((short) i+1);

            // Retrieve the id value
            HSSFCell cell1 = row.createCell(startColIndex+0);
            cell1.setCellValue(datasource.get(i-2).getAcademyId());
            cell1.setCellStyle(bodyCellStyle);

            // Retrieve the brand value
            HSSFCell cell2 = row.createCell(startColIndex+1);
            cell2.setCellValue(datasource.get(i-2).getAcademy());
            cell2.setCellStyle(bodyCellStyle);
        }

    }
}