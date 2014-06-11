package com.cisco.poi;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;

@SuppressWarnings("deprecation")//表示不检测过期的方法
public class Layouter {

    public static void buildReport(HSSFSheet worksheet,int startRowIndex,
                                   int startColIndex){
        //设置列宽
        worksheet.setColumnWidth(0, 5200);
        worksheet.setColumnWidth(1, 5200);

        buildTitle(worksheet,startRowIndex,startColIndex);
        buildHeaders(worksheet,startRowIndex,startColIndex);
    }

    private static void buildHeaders(HSSFSheet worksheet, int startRowIndex,
                                     int startColIndex) {
        // Header字体
        Font font = worksheet.getWorkbook().createFont();
        font.setBoldweight((short)Font.BOLDWEIGHT_BOLD);
        //font.setColor(HSSFColor.BLUE.index);//设置字体颜色


        // 单元格样式
        HSSFCellStyle headerCellStyle = worksheet.getWorkbook()
                .createCellStyle();
        //headerCellStyle.setFillForegroundColor(HSSFColor.GREY_80_PERCENT.index);//前景色
        //headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_80_PERCENT.index);//背景色
        //headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);  //设置填充方式

        headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headerCellStyle.setWrapText(true);
        headerCellStyle.setFont(font);
        headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);
        headerCellStyle.setBorderTop(CellStyle.BORDER_THIN);

        // 创建字段标题
        HSSFRow rowHeader = worksheet.createRow((short) startRowIndex + 2);
        rowHeader.setHeight((short) 500);

        HSSFCell cell1 = rowHeader.createCell(startColIndex + 0);
        cell1.setCellValue("院系ID");
        cell1.setCellStyle(headerCellStyle);

        HSSFCell cell2 = rowHeader.createCell(startColIndex + 1);
        cell2.setCellValue("院名");
        cell2.setCellStyle(headerCellStyle);

    }

    private static void buildTitle(HSSFSheet worksheet, int startRowIndex,
                                   int startColIndex) {
        //报表标题字体
        Font fontTitle = worksheet.getWorkbook().createFont();
        fontTitle.setBoldweight((short)Font.BOLDWEIGHT_BOLD);
        fontTitle.setFontHeight((short)280);

        //标题单元格格式
        HSSFCellStyle cellStyleTitle = worksheet.getWorkbook().createCellStyle();
        cellStyleTitle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyleTitle.setWrapText(true);
        cellStyleTitle.setFont(fontTitle);

        HSSFRow rowTitle = worksheet.createRow((short)startRowIndex);
        rowTitle.setHeight((short)500);
        HSSFCell cellTitle = rowTitle.createCell(startColIndex);
        cellTitle.setCellValue("院系列表");
        cellTitle.setCellStyle(cellStyleTitle);

        worksheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));//标题合并列

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        HSSFRow dateTitle = worksheet.createRow((short) startRowIndex + 1);
        HSSFCell cellDate = dateTitle.createCell(startColIndex);
        cellDate.setCellValue("这个报表创建于: "+ dateFormat.format(date));
    }
}