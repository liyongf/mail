package com.sdzk.buss.api.controller;

import com.sdzk.buss.web.common.task.ReportGroupTask;
import org.jeecgframework.core.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  手动上报数据到集团的控制类
 * */
@Controller
@RequestMapping("reportToGroupByPeople")
public class ReportToGroupByPeople extends BaseController {
    @Autowired
    private ReportGroupTask reportGroupTask;

    @RequestMapping(params = "reportAll")
    public void reportAll(){
        reportGroupTask.reportHiddenDanger();
        reportGroupTask.reportInvestigatePlan();
        reportGroupTask.reportMajorHiddenDanger();
        reportGroupTask.reportMajorYearRisk();
        reportGroupTask.reportSpecialEvaluation();
        reportGroupTask.reportThreeViolations();
        reportGroupTask.reportYearRisk();
    }

    @RequestMapping(params = "reportYearRisk")
    public void reportYearRisk(){
        reportGroupTask.reportYearRisk();
    }

    @RequestMapping(params = "reportSpecialEvaluation")
    public void reportSpecialEvaluation(){
        reportGroupTask.reportSpecialEvaluation();
    }

    @RequestMapping(params = "reportHiddenDanger")
    public void reportHiddenDanger(){
        reportGroupTask.reportHiddenDanger();
    }

    @RequestMapping(params = "reportMajorHiddenDanger")
    public void reportMajorHiddenDanger(){
        reportGroupTask.reportMajorHiddenDanger();
    }

    @RequestMapping(params = "reportMajorYearRisk")
    public void reportMajorYearRisk(){
        reportGroupTask.reportMajorYearRisk();
    }

    @RequestMapping(params = "reportThreeViolations")
    public void reportThreeViolations(){
        reportGroupTask.reportThreeViolations();
    }

    @RequestMapping(params = "reportInvestigatePlan")
    public void reportInvestigatePlan(){
        reportGroupTask.reportInvestigatePlan();
    }

}
