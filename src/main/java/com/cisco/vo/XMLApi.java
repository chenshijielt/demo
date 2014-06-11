package com.cisco.vo;

import java.util.Date;

/**
 * Created by chenshijie on 6/11/14.
 */
public class XMLApi extends BaseVo {

    private String apiType;

    private Long requestCount;

    private Long distinctSite;

    private Long distinctUser;

    private Date targetDate;

    public void setApiType(String apiType) {
        this.apiType = apiType;
    }

    public void setRequestCount(Long requestCount) {
        this.requestCount = requestCount;
    }

    public void setDistinctSite(Long distinctSite) {
        this.distinctSite = distinctSite;
    }

    public void setDistinctUser(Long distinctUser) {
        this.distinctUser = distinctUser;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public String getApiType() {
        return apiType;
    }

    public Long getRequestCount() {
        return requestCount;
    }

    public Long getDistinctSite() {
        return distinctSite;
    }

    public Long getDistinctUser() {
        return distinctUser;
    }

    public Date getTargetDate() {
        return targetDate;
    }
}
