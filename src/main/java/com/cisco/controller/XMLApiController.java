package com.cisco.controller;

import com.cisco.service.XmlapiService;
import com.cisco.vo.XMLApi;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by chenshijie on 6/11/14.
 */
@Controller
@RequestMapping("/XMLApi")
public class XMLApiController {

    @Autowired
    private XmlapiService xmlapiService;

    private static Logger logger = Logger.getLogger("controller");

    @RequestMapping(value = "/report.html")
    public ModelAndView getReport(){
        logger.info("index");
        return new ModelAndView("/xmlapi/report");
    }

    @RequestMapping(value = "/read.html", method = RequestMethod.POST)
    public ModelAndView getReadReport(@RequestParam MultipartFile file)
            throws IOException {
        List<XMLApi> list = xmlapiService.readReport(file.getInputStream());
        xmlapiService.insertXmlapi(list);
        return new ModelAndView("/xmlapi/addedReport");
    }

    @RequestMapping(value = "/export.html", method = RequestMethod.GET)
    public void getXLS(HttpServletResponse response) {
        xmlapiService.exportXLS(response);
    }

    @RequestMapping(value = "/exportEmpty.html", method = RequestMethod.GET)
    public void getXLSEmpty(HttpServletResponse response) {
        xmlapiService.exportXLSEmpty(response);
    }

    @RequestMapping(value = "search.html",method = RequestMethod.GET)
    public ModelAndView searchData(){
        return new ModelAndView("/xmlapi/search");
    }
}
