package com.sdzk.buss.api.controller;

import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.api.service.ApiServiceI;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.util.EhcacheUtil;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 同步数据字典, 人员, 地点, 部门信息接口
 */
@Controller
@RequestMapping("/mobile/mobileSysDataController")
public class MobileSysDataController {
    @Autowired
    private ApiServiceI apiService;
    @Autowired
    private SystemService systemService;

    private String sessionCache="sessionCache";

    /**
     * 同步数据字典, 人员, 地点, 部门信息
     *
     * @param token
     * @param sessionId         用户当前登录的sessionid
     * @return <br/>
     * {	"message": "请求成功",<br/>
            "data": {<br/>
             &"locationList": [{<b style="color:red;">地点列表</b><br/>
                    "id": "402880f45e78f2b8015e78fc83010001",<b style="color:red;">地点id</b><br/>
                    "address": "2222"<b style="color:red;">地点名称</b><br/>
                }],<br/>
                "dicList": [{<b style="color:red;">数据字典列表</b><br/>
                    "typecode": "1",<b style="color:red;">字典编码(存储用)</b><br/>
                    "typename": "冲击地压",<b style="color:red;">字典名称(显示用)</b><br/>
                    "typegroupcode": "hiddenType"<b style="color:red;">字典分类-examType=检查类型,workShift=班次,hiddenCate=隐患类别,hiddenLevel=隐患等级,hiddenType=隐患类型,handelStatus=隐患类型</b><br/>
                }],<br/>
                "departList": [{<b style="color:red;">部门列表</b><br/>
                    "id": "0b443262592f7dc4015933ba6ac11ebf",<b style="color:red;">部门id</b><br/>
                    "departname": "副总"<b style="color:red;">部门名称</b><br/>
                }],<br/>
                "userList": [{<b style="color:red;">用户列表</b><br/>
                    "id": "402880e65e749769015e7498ae820001",<b style="color:red;">用户id</b><br/>
                    "realname": "guoteng"<b style="color:red;">用户名称</b><br/>
                }]<br/>
            },<br/>
            "code": "200"<br/>
        }<br/>
     */
    @RequestMapping("/syncData")
    @ResponseBody
    public ApiResultJson syncData(String token, HttpServletRequest request){
        try {
            String sessionId = request.getParameter("sessionId");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            Map<String, List<Map<String, Object>>> result = new HashMap<>();
            String sql = "DELETE FROM t_b_risk_manage WHERE risk_id NOT IN (SELECT id FROM t_b_risk_identification)";
            systemService.executeSql(sql);
            /*sql = "DELETE FROM t_b_risk_manage_task WHERE risk_id NOT IN (SELECT id FROM t_b_risk_identification)";
            systemService.executeSql(sql);
            sql = "DELETE FROM t_b_risk_manage_hazard_factor WHERE risk_id NOT IN (SELECT id FROM t_b_risk_identification)";
            systemService.executeSql(sql);*/
            /**同步数据字典**/
            List<Map<String, Object>> dicList = apiService.mobileSyncDataDic();
            /**同步用户信息**/
            List<Map<String, Object>> userList = apiService.mobileSyncDataUser();
            /**同步部门信息**/
            List<Map<String, Object>> departList = apiService.mobileSyncDataDepart();
            /**同步地点信息**/
            List<Map<String, Object>> locationList = apiService.mobileSyncDataLocation();

            //待删除片段 TODO 开始
            /**同步岗  位信息**/
            List<Map<String, Object>> postNameList = apiService.mobileSyncDataPostName();
            /**同步风险信息**/
            List<Map<String, Object>> riskList = apiService.mobileSyncDataRisk();
            /**同步我的管控清单**/
            List<Map<String, Object>> riskManageList = apiService.mobileSyncDataRiskManage(user);
            /**同步我的管控清单关联的危害因素**/
            List<Map<String, Object>> riskManageHazardFactorList = apiService.mobileSyncDataRiskManageHazardFactor(user);
            /**同步我的管控任务**/
            List<Map<String, Object>> riskManageTaskList = apiService.mobileSyncDataRiskManageTask(user);
            /**同步我的管控任务关联的风险**/
            List<Map<String, Object>> riskManageTaskRiskList = apiService.mobileSyncDataRiskManageTaskRisk(user);
            /**同步我的管控任务关联的风险的危害因素**/
            List<Map<String, Object>> riskManageTaskHazardFactorList = apiService.mobileSyncDataRiskManageTaskHazardFactor(user);
            //待删除片段 TODO 结束


            result.put("dicList", dicList);
            result.put("userList", userList);
            result.put("departList", departList);
            result.put("locationList", locationList);

            //待删除片段 TODO 开始
            result.put("postNameList", postNameList);
            result.put("riskList", riskList);
            result.put("riskManageList", riskManageList);
            result.put("riskManageHazardFactorList", riskManageHazardFactorList);
            result.put("riskManageTaskList", riskManageTaskList);
            result.put("riskManageTaskRiskList", riskManageTaskRiskList);
            result.put("riskManageTaskHazardFactorList", riskManageTaskHazardFactorList);
            //待删除片段 TODO 结束

            return new ApiResultJson(result);
        } catch (Exception e) {
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

    /**
     * app更新
     * @param request
     * @param response
     */
    //TODO
    @RequestMapping("/appUpdate")
    public void appUpdate(HttpServletRequest request, HttpServletResponse response)  throws Exception{

        response.setContentType("application/x-msdownload;charset=utf-8");
        response.setHeader("Content-disposition", "attachment; filename="+ new String("双防系统APP.apk".getBytes("utf-8"), "ISO8859-1"));

        InputStream inputStream = null;
        OutputStream outputStream=null;
        try {
            String fileurl = "C:/Users/dell/Desktop/手机/app-debug.apk";
            inputStream = new BufferedInputStream(new FileInputStream(fileurl));
            outputStream = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            response.flushBuffer();
        } catch (Exception e) {
            LogUtil.error("--通过流的方式获取文件异常--"+e.getMessage());
        }finally{
            if(inputStream!=null){
                inputStream.close();
            }
            if(outputStream!=null){
                outputStream.close();
            }
        }
    }

}