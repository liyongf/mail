package com.sdzk.buss.web.common.utils;

import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.web.common.task.ReportGroupTask;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.sql.Time;
import java.util.*;

@Controller
@RequestMapping("/UtilTest")
public class UtilTest {

    @RequestMapping("/semanticSimilarityTest.do")
    @ResponseBody
    public ApiResultJson semanticSimilarityTest(String src) {
        if(StringUtil.isNotEmpty(src)){
            long startTime = System.currentTimeMillis();    //获取开始时间
            Map<String, String> result= new LinkedHashMap <>();
            result.put("输入句子：",src);
            Vector<String> T = SemanticSimilarityUtil.participle(src);
            result.put("输入分词：","" + T);
            double maxSimilarity = 0.0;
            String maxId = null;
            for (String key : SemanticSimilarityUtil.dsPartMap.keySet()) {
                Vector<String> cur_T = SemanticSimilarityUtil.dsPartMap.get(key);
                try{
                    double similarity = SemanticSimilarityUtil.getSimilarity(T,cur_T);
                    if(similarity>maxSimilarity){
                        maxSimilarity = similarity;
                        maxId = key;
                    }
                }catch(Exception e){
                    return new ApiResultJson(ApiResultJson.CODE_202,"匹配异常",null);
                }
            }
            if(null!=maxId){
                SystemService systemService = (SystemService) ApplicationContextUtil.getContext().getBean("systemService");
                TBDangerSourceEntity dse = systemService.getEntity(TBDangerSourceEntity.class,maxId);
                result.put("输出句子：",dse.getYeMhazardDesc());
                result.put("输出分词：","" + SemanticSimilarityUtil.dsPartMap.get(maxId));
            }
            long endTime = System.currentTimeMillis();    //获取结束时间
            result.put("计算时间：",(endTime - startTime) + "ms");
            return new ApiResultJson(result);
        }
        else {
            return new ApiResultJson(ApiResultJson.CODE_202,"输入为空",null);
        }
    }

    public static void main(String[] args) {
        System.out.println("cccccc");
        //List<TBDangerSourceEntity> list = systemService.getListByCriteriaQuery(cq,false);
        //reportGroupTas;k.reportHiddenDanger();
        //Time t = Time("1:2:3");

    }

}
