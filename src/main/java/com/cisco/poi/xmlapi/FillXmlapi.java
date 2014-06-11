package com.cisco.poi.xmlapi;

import com.cisco.poi.FillBase;
import com.cisco.vo.XMLApi;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellStyle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by chenshijie on 6/11/14.
 */
public  class FillXmlapi implements FillBase<XMLApi> {

    @Override
    public void fillReport(HSSFSheet worksheet, int startRowIndex, int startColIndex, List<XMLApi> datasource) {
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

            // Retrieve the api_type value
            HSSFCell cell1 = row.createCell(startColIndex+0);
            cell1.setCellValue(datasource.get(i-2).getApiType());
            cell1.setCellStyle(bodyCellStyle);

            // Retrieve the request_count  value
            HSSFCell cell2 = row.createCell(startColIndex+1);
            cell2.setCellValue(datasource.get(i-2).getRequestCount());
            cell2.setCellStyle(bodyCellStyle);

            // Retrieve the distinct_site   value
            HSSFCell cell3 = row.createCell(startColIndex+2);
            cell3.setCellValue(datasource.get(i-2).getDistinctSite());
            cell3.setCellStyle(bodyCellStyle);

            // Retrieve the request_count  value
            HSSFCell cell4 = row.createCell(startColIndex+3);
            cell4.setCellValue(datasource.get(i-2).getDistinctUser());
            cell4.setCellStyle(bodyCellStyle);

            // Retrieve the target_date  value
            HSSFCell cell5 = row.createCell(startColIndex+4);
            cell5.setCellValue(getStringDateYYYYMMDD(datasource.get(i-2).getTargetDate()));
            cell5.setCellStyle(bodyCellStyle);
        }
    }

    private String getStringDateYYYYMMDD(Date date){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(date);
    }
}
