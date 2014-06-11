package com.cisco.poi;

import com.cisco.vo.BaseVo;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.util.List;

/**
 * Created by chenshijie on 6/11/14.
 */
public  interface FillBase<T> {
     void fillReport(HSSFSheet worksheet,int startRowIndex,
                     int startColIndex,List<T> datasource);
}
