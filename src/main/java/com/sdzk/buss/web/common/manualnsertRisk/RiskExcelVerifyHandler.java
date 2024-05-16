package com.sdzk.buss.web.common.manualnsertRisk;

import com.sdzk.buss.web.common.manualnsertRisk.RiskImportHelper;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;

import java.util.Map;

/**
 * Created by lenovo on 2018/12/5.
 */
public class RiskExcelVerifyHandler implements IExcelVerifyHandler<RiskImportHelper> {

    private Map<String, String> userMap;
    private Map<String, String> departMap;
    public RiskExcelVerifyHandler( Map<String, String> userMap, Map<String, String> departMap) {
        this.userMap=userMap;
        this.departMap=departMap;
    }
    @Override
    public ExcelVerifyHanlderResult verifyHandler(RiskImportHelper obj) {
        StringBuilder builder = new StringBuilder();
        boolean success = true;
        String controlUnit = obj.getControlUnit();
        String topController = obj.getTopController();

        String controller = obj.getController();
        String topControlLevel = obj.getTopControlLevel();

        String profession =  obj.getProfession();
        String riskCate =obj.getRiskCate();
        String hRiskLevel= obj.gethRiskLevel();

        if(StringUtil.isEmpty(profession)){
            builder.append(" 专业不能为空 |");
        }else{
            if(DicUtil.getTypeCodeByName("major", profession).equals("")){
                builder.append(" 专业["+profession+"] 不存在");
            }
        }
        if(StringUtil.isEmpty(riskCate)){
            builder.append(" 风险类型不能为空 |");
        }else{
            if(DicUtil.getTypeCodeByName("risk_type", riskCate).equals("")){
                builder.append(" 风险类型["+riskCate+"] 不存在");
            }
        }
        if(StringUtil.isEmpty(hRiskLevel)){
            builder.append(" 危害因素等级不能为空 |");
        }else{
            if(DicUtil.getTypeCodeByName("factors_level", hRiskLevel).equals("")){
                builder.append(" 危害因素等级["+hRiskLevel+"] 不存在");
            }
        }

        if(StringUtil.isEmpty(controlUnit)){
            builder.append(" 管控单位不能为空 |");
        }else{
            if(departMap.get(controlUnit)==null){
                builder.append(" 管控单位["+controlUnit+"] 不存在");
            }
        }


        if(StringUtil.isEmpty(topController)){
            builder.append(" 最高管控责任人不能为空 |");
        }

        if(StringUtil.isEmpty(controller)){
            builder.append(" 管控责任人不能为空 |");
        }
        if(controller!=null){
            for(int i=0;i<controller.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",").length;i++){
                String name = controller.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",")[i];
                if(userMap.get(name)==null){
                    builder.append(" 管控责任人["+name+"] 不存在 |");
                }
            }
        }

        if(StringUtil.isEmpty(topControlLevel)){
            builder.append(" 最高管控层级不能为空 |");
        }

        if(topControlLevel!=null){
            for(int i=0;i<topControlLevel.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",").length;i++){
                String ids="";
                String name = topControlLevel.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",")[i];
                if(name!=null){
                    String id=DicUtil.getTypeCodeByName("identifi_mange_level",name);
                    if(id!=null&&id.length()>0){
                        if(ids == ""){
                            ids = ids + id;
                        }else{
                            ids = ids + "," + id;
                        }
                    }else{
                        builder.append(" 最高管控层级["+name+"]不存在。");
                    }
                }
            }
        }
//        if(StringUtil.isEmpty(obj.getAddressName())){
////            builder.append("地点名称不能为空");
//        }
//        try{
//            DateUtils.date_sdf.parse(obj.getAssessDate());
//        }catch (Exception e){
//            obj.setAssessDate(null);
////            builder.append("评估日期格式不准确！");
//        }
//        try{
//            DateUtils.date_sdf.parse(obj.getTerminateDate());
//        }catch (Exception e){
//            obj.setTerminateDate(null);
////            builder.append("评估日期格式不准确！");
//        }
        if(StringUtil.isNotEmpty(builder.toString())){
            success = false;
        }
        return new ExcelVerifyHanlderResult(success,builder.toString());
    }

}
