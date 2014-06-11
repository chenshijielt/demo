package com.cisco.vo;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by root on 6/10/14.
 */
@Component
public class Academy extends BaseVo implements Serializable{
    private Long academyId;
    private String academy;

    public Long getAcademyId() {
        return academyId;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademyId(Long academyId) {
        this.academyId = academyId;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }
}
