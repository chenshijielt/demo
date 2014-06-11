package com.cisco.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by chenshijie on 6/11/14.
 */
public class BaseVo implements Serializable {

    private int id;

    private Date createDate;

    public void setId(int id) {
        this.id = id;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public Date getCreateDate() {
        return createDate;
    }
}
