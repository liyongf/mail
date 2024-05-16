package com.sdzk.buss.web.common.task;



import org.jeecgframework.core.util.HttpClientUtils;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/12/19.
 */
@Service("reportSumNumAllTask")
public class ReportSumNumAllTask {

    @Autowired
    private SystemService systemService;
    public void reportSumNum(){

//        SystemService  systemService = (SystemService) ApplicationContextUtil.getContext().getBean("systemService");

        String calculateDangerSource = "select count(1) from t_b_risk_identification where is_del <>'1'";
        List<BigInteger>  sumDangerSourceList = systemService.findListbySql(calculateDangerSource);
        String  sumDangerSource = sumDangerSourceList.get(0).toString();

        String calculateHiddenDanger = "select count(1) from t_b_hidden_danger_exam";
        List<BigInteger> sumHiddenDangerList = systemService.findListbySql(calculateHiddenDanger);
        String sumHiddenDanger = sumHiddenDangerList.get(0).toString();

        String calculateMajorHiddenDanger = "select count(1) from t_b_hidden_danger_exam";
        List<BigInteger> sumMajorHiddenDangerList = systemService.findListbySql(calculateMajorHiddenDanger);
        String sumMajorHiddenDanger = sumMajorHiddenDangerList.get(0).toString();

        String calculateThreeViolations = "select count(1) from t_b_three_violations";
        List<BigInteger> sumThreeViolationsList = systemService.findListbySql(calculateThreeViolations);
        String sumThreeViolations = sumThreeViolationsList.get(0).toString();

        Map<String,String> sendMap = new HashMap<>();

        sendMap.put("sumDangerSource",sumDangerSource);
        sendMap.put("sumHiddenDanger",sumHiddenDanger);
        sendMap.put("sumThreeViolations",sumThreeViolations);
        sendMap.put("sumMajorHiddenDanger",sumMajorHiddenDanger);

        List<Object> sendList = new ArrayList<>();

        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("reportSumNumUrl");

        sendList.add(sendMap);

        String reportContent= JSONHelper.toJSONString(sendList);

        Map<String,String> paraMap = new HashMap<>();
        paraMap.put("reportContent",reportContent);
        paraMap.put("mineCode", mineCode);


        long start = System.currentTimeMillis();
        org.jeecgframework.core.util.LogUtil.info("===================定时上报汇总数据开始===================");

        //上报年度风险辨识
        try {
            HttpClientUtils.post(url, paraMap, "UTF-8");
        }catch (Exception e) {
            org.jeecgframework.core.util.LogUtil.error("定时上报汇总数据失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报汇总数据结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("总耗时"+times+"毫秒。");
    }
}
