package com.cisco.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chenshijie on 6/12/14.
 */
public class DailyView {
    private int api_call_count;

    private long total_count;

    private long max_request_count;

    private Date target_date;

    public void setApi_call_count(int api_call_count) {
        this.api_call_count = api_call_count;
    }

    public void setTotal_count(long total_count) {
        this.total_count = total_count;
    }

    public void setMax_request_count(long max_request_count) {
        this.max_request_count = max_request_count;
    }

    public void setTarget_date(Date target_date) {
        this.target_date = target_date;
    }

    public int getApi_call_count() {
        return api_call_count;
    }

    public long getTotal_count() {
        return total_count;
    }

    public long getMax_request_count() {
        return max_request_count;
    }

    public Date getTarget_date() {
        return target_date;
    }

}
