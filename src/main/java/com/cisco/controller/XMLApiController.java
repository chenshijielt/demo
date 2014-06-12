package com.cisco.controller;

import com.cisco.service.XmlapiService;
import com.cisco.vo.DailyView;
import com.cisco.vo.XMLApi;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @RequestMapping(value = "/search.html",method = RequestMethod.GET)
    public ModelAndView searchData(){
        return new ModelAndView("/xmlapi/search");
    }

    @RequestMapping( value = "/chart/dailyTotal/api_call_count")
    public void showXMLApiDailyCall_api_call_count(HttpServletResponse response){
        List<DailyView> dailyViewList = xmlapiService.getDailyCallTotal();

        ChartRenderingInfo info = new ChartRenderingInfo();
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        String series1 = "api_call_count";

        for(DailyView dailyView:dailyViewList){
            dataSet.addValue(dailyView.getApi_call_count(),series1,getDateYYYYMMDD(dailyView.getTarget_date()));
        }
        String title = "api_call_count";
        String xBarTitle = "date";
        String yBarTitle = "count";

        JFreeChart chart = ChartFactory.createLineChart(title, xBarTitle, yBarTitle, dataSet, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plot = chart.getCategoryPlot();
        //设置jfreechart序列图曲线颜色
        CategoryItemRenderer xyLineRenderer = (CategoryItemRenderer) plot.getRenderer();
        xyLineRenderer.setSeriesPaint(0, new Color(0, 0, 255));
        plot.setRangeGridlinesVisible(true); //是否显示格子线
        plot.setBackgroundAlpha(0.3f); //设置背景透明度

        Font labelFont = new Font("Arial", Font.TRUETYPE_FONT, 8);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelFont(labelFont);//X轴坐标上数值字体
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        Integer width = 500;
        int count =dataSet.getRowCount();
        if (count > 6) {
            width = 500 + (count - 6) * 20;
        }

        BufferedImage image = chart.createBufferedImage(width, 300, info);

        try {
            ServletOutputStream os = response.getOutputStream();
            ImageIO.write(image, "PNG", os);
        } catch (IOException ignore) {
        }
    }

    @RequestMapping( value = "/chart/dailyTotal/api_call_total")
    public void showXMLApiDailyCall_api_call_total(HttpServletResponse response){
        List<DailyView> dailyViewList = xmlapiService.getDailyCallTotal();

        ChartRenderingInfo info = new ChartRenderingInfo();
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        String series1 = "api_call_total";

        for(DailyView dailyView:dailyViewList){
            dataSet.addValue(dailyView.getTotal_count(),series1,getDateYYYYMMDD(dailyView.getTarget_date()));
        }
        String title = "api_call_total";
        String xBarTitle = "date";
        String yBarTitle = "hit";

        JFreeChart chart = ChartFactory.createLineChart(title, xBarTitle, yBarTitle, dataSet, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plot = chart.getCategoryPlot();
        //设置jfreechart序列图曲线颜色
        CategoryItemRenderer xyLineRenderer = (CategoryItemRenderer) plot.getRenderer();
        xyLineRenderer.setSeriesPaint(0, new Color(0, 255, 0));
        plot.setRangeGridlinesVisible(true); //是否显示格子线
        plot.setBackgroundAlpha(0.3f); //设置背景透明度

        Font labelFont = new Font("Arial", Font.TRUETYPE_FONT, 8);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelFont(labelFont);//X轴坐标上数值字体
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        Integer width = 500;
        int count =dataSet.getRowCount();
        if (count > 6) {
            width = 500 + (count - 6) * 20;
        }

        BufferedImage image = chart.createBufferedImage(width, 300, info);

        try {
            ServletOutputStream os = response.getOutputStream();
            ImageIO.write(image, "PNG", os);
        } catch (IOException ignore) {
        }
    }

    @RequestMapping(value = "/search_daily_total",method = RequestMethod.GET)
    public ModelAndView searchDailyCallTotal(Model model){

        List<DailyView> dailyViewList = xmlapiService.getDailyCallTotal();
        model.addAttribute("dailytotal",dailyViewList);
        List<XMLApi> xmlApiList = xmlapiService.getDailyCallAddCall();
        model.addAttribute("dailyaddcall",xmlApiList);

        return new ModelAndView("/xmlapi/dailytotal");
    }

    public String getDateYYYYMMDD(Date date){
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");

        return sp.format(date);
    }
}
