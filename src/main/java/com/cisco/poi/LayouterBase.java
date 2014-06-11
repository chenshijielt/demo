package com.cisco.poi;

import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * Created by chenshijie on 6/11/14.
 */
public interface LayouterBase {

    void buildReport(HSSFSheet worksheet,int startRowIndex,int startColIndex);

    void buildHeaders(HSSFSheet worksheet, int startRowIndex,int startColIndex);

    void buildTitle(HSSFSheet worksheet, int startRowIndex,int startColIndex);


}
