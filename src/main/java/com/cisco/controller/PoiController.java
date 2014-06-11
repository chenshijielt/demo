package com.cisco.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cisco.vo.Academy;
import com.cisco.service.PoiService;

@Controller
@RequestMapping("/insmgr")
public class PoiController {

    private static Logger logger = Logger.getLogger("controller");

    @Autowired
    private PoiService poiService;

    @RequestMapping(value = "/report.html")
    public ModelAndView getReport(){
        logger.info("index");
        return new ModelAndView("/insmgr/report");
    }

    @RequestMapping(value = "/exportEmpty.html", method = RequestMethod.GET)
    public void getXLSEmpty(HttpServletResponse response) {
        poiService.exportXLSEmpty(response);
    }

    @RequestMapping(value = "/export.html", method = RequestMethod.GET)
    public void getXLS(HttpServletResponse response) {
        poiService.exportXLS(response);
    }

    @RequestMapping(value = "/read.html", method = RequestMethod.POST)
    public ModelAndView getReadReport(@RequestParam MultipartFile file)
            throws IOException {
        List<Academy> list = poiService.readReport(file.getInputStream());
        poiService.insertAcademy(list);
        return new ModelAndView("/insmgr/addedReport");

    }

}