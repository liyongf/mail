package com.sdzk.buss.api.controller;

import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.api.service.ApiServiceI;
import com.sdzk.buss.api.utils.Base64Util;
import com.sdzk.buss.api.utils.CreateFileUtil;
import com.sdzk.buss.api.utils.ReduceImgUtil;
import com.sdzk.buss.web.violations.entity.MobileTBThreeViolationsEntity;
import com.sdzk.buss.web.violations.entity.TBThreeViolationsImgRelEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.pojo.base.TSDocument;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * @user xuran
 * 三违查询,接收录入数据
 */
@Controller
@RequestMapping("/mobile/mobileThreeViolationsController")
public class MobileThreeViolationsController {
    @Autowired
    private SystemService systemService;
    @Autowired
    private ApiServiceI apiService;

    private String sessionCache="sessionCache";


    /**
     *
     * @param token
     * @param sessionId     用户当前登录的sessionid
     * @param page          当前页数
     * @param rows          查询行数
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ApiResultJson list(HttpServletRequest request, HttpServletResponse response){
        //TODO TOKEN验证
        try {
            String sessionId = request.getParameter("sessionId");
            String page = request.getParameter("page");
            String rows = request.getParameter("rows");
            String queryType = request.getParameter("queryType");
            String  vioDate = request.getParameter("vioDate");
            String vioUnits = request.getParameter("vioUnits");
            //防止空值报错
            if(StringUtil.isEmpty(page)){
                page = "1";
            }
            if (StringUtil.isEmpty(rows)){
                rows = "10";
            }

            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            List<Map<String, Object>> result=null;
            StringBuffer sql = new StringBuffer("SELECT v.id, DATE_FORMAT(v.vio_date, '%Y-%m-%d') AS vioDate, a.address AS address , v.vio_people AS vioPeople, v.vio_qualitative AS vioQualitative, v.stop_people AS stopPeople, units.departname AS vioUnit, v.vio_category AS vioCategory , v.vio_level AS vioLevel, findUnits.departname AS findUnit, v.vio_fact_desc AS vioFactDesc, v.remark AS remark , " +
                    "CASE  WHEN v.mobile_id IS NOT NULL THEN '移动端' ELSE '手机端' END AS pcOrMobile FROM t_b_three_violations v LEFT JOIN t_b_address_info a ON v.vio_address = a.id LEFT JOIN t_s_depart units ON v.vio_units = units.ID LEFT JOIN t_s_depart findUnits ON findUnits.ID = v.find_units");

            if(StringUtils.isNotBlank(vioDate)){
                StringBuffer s1 = new StringBuffer(" where v.vio_date='"+vioDate+"'") ;
                sql.append(s1);
            }
            if(StringUtils.isNotBlank(vioUnits)){
                if(StringUtils.isBlank(vioDate)){
                    sql.append(" where v.vio_units='"+vioUnits+"'");
                }else{
                    sql.append(" and v.vio_units='"+vioUnits+"'");
                }
            }
            sql.append(" order by vio_date desc");
            result = systemService.findForJdbc(sql.toString(), Integer.parseInt(page), Integer.parseInt(rows));
            if (result != null && result.size() != 0) {
                for (Map<String, Object> map : result) {
                    map.put("vioCategory", DicUtil.getTypeNameByCode("violaterule_wzfl", (String) map.get("vioCategory")));
                    map.put("vioLevel", DicUtil.getTypeNameByCode("vio_level", (String) map.get("vioLevel")));
                    map.put("vioQualitative", DicUtil.getTypeNameByCode("violaterule_wzdx", (String) map.get("vioQualitative")));

                    /**传送图片*/
                    String threevioId = (String) map.get("id");
                    MobileTBThreeViolationsEntity threevioEntity = systemService.getEntity(MobileTBThreeViolationsEntity.class, threevioId);
                    //组装图片路径报文,格式[{'imgPath':'uploadfile/402880f7604322680160433e7e6a0009/1.jpg','reduceImgPath','uploadfile/402880f7604322680160433e7e6a0009/reduce_1.jpg'},{}]
                    JSONArray fileContent = new JSONArray();
                    String imgSql = "select img_path from t_b_three_violations_img_rel where mobile_threevio_id='"+threevioEntity.getMobileId()+"'";
                    List<String> imgPathList = systemService.findListbySql(imgSql);
                    if(!imgPathList.isEmpty() && imgPathList.size()>0){
                        for(String imgPath:imgPathList){
                            String dir = "threeVioImg/"+threevioEntity.getMobileId()+"/";
                            JSONObject jo = new JSONObject();
                            jo.put("imgPath",dir + imgPath);
                            jo.put("reduceImgPath",dir + "reduce_" + imgPath);

                            fileContent.add(jo);
                        }
                    }
                    map.put("imgContents",fileContent);

                }
            }
            return new ApiResultJson(result);
        } catch (Exception e) {
            LogUtil.error("三违列表查询错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

    /**
     * 查看三违详情
     * @param token
     * @param sessionId     用户当前登录的sessionid
     * @param id            三违id
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ApiResultJson detail(String token, HttpServletRequest request){
        //TODO TOKEN验证
        try {
            String sessionId = request.getParameter("sessionId");
            String id = request.getParameter("id");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            if (StringUtils.isBlank(id)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"请选择要查询的三违记录",null);
            }

            StringBuffer sql = new StringBuffer("select DATE_FORMAT(v.vio_date,'%Y-%m-%d') vioDate, v.shift, a.address address, units.departname vioUnit, v.vio_people vioPeople, v.vio_category vioCategory, v.vio_level vioLevel, v.vio_qualitative vioQualitative, findUnits.departname findUnit, v.stop_people stopPeople, v.vio_fact_desc vioFactDesc, v.remark from t_b_three_violations v LEFT JOIN t_b_address_info a on v.vio_address = a.id LEFT JOIN t_s_depart units on v.vio_units = units.ID LEFT JOIN t_s_depart findUnits on findUnits.ID=v.find_units where v.id='"+id+"'");
            Map<String, Object> result = systemService.findOneForJdbc(sql.toString());
            MobileTBThreeViolationsEntity threevioEntity = systemService.get(MobileTBThreeViolationsEntity.class, id);

            if (result != null) {
                result.put("shift", DicUtil.getTypeNameByCode("workShift", (String) result.get("shift")));
                result.put("vioCategory", DicUtil.getTypeNameByCode("violaterule_wzfl", (String) result.get("vioCategory")));
                result.put("vioLevel", DicUtil.getTypeNameByCode("vio_level", (String) result.get("vioLevel")));
                result.put("vioQualitative", DicUtil.getTypeNameByCode("violaterule_wzdx", (String) result.get("vioQualitative")));

                /**返回给手机端图片*/
                //组装图片路径报文,格式[{'imgPath':'uploadfile/402880f7604322680160433e7e6a0009/1.jpg','reduceImgPath','uploadfile/402880f7604322680160433e7e6a0009/reduce_1.jpg'},{}]
                /*JSONArray fileContent = new JSONArray();
                String imgSql = "select img_path from t_b_three_violations_img_rel where mobile_hidden_id='"+threevioEntity.getMobileId()+"'";
                List<String> imgPathList = systemService.findListbySql(imgSql);
                if(!imgPathList.isEmpty() && imgPathList.size()>0){
                    for(String imgPath:imgPathList){
                        String dir = "uploadfile/"+threevioEntity.getMobileId()+"/";
                        JSONObject jo = new JSONObject();
                        jo.put("imgPath",dir + imgPath);
                        jo.put("reduceImgPath",dir + "reduce_" + imgPath);

                        fileContent.add(jo);
                    }
                }
                result.put("imgContents",fileContent);*/

                return new ApiResultJson(result);
            } else {
                return new ApiResultJson(ApiResultJson.CODE_202,"三违记录不存在或已被删除",null);
            }

        } catch (Exception e) {
            LogUtil.error("三违详情查询错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

    /**
     * 上报录入三违接口
     * @param token
     * @param sessionId         用户当前登录的sessionid
     * @param id                手机端主键
     * @param vioDate           违章时间(格式yyyy-MM-dd)
     * @param shift             班次(对应字典值 workShift)
     * @param addressId         地点id
     * @param vioUnit           违章单位id
     * @param vioPeople         违章人员
     * @param vioCategory       违章分类(对应字典值 violaterule_wzfl)
     * @param vioLevel          三违级别(对应字典值 vio_level)
     * @param vioQualitative    违章定性(对应字典值 violaterule_wzdx)
     * @param findUnit          查处单位id
     * @param stopPeople        制止人
     * @param vioFactDesc       三违事实描述
     * @param remark            备注
     * @return
     */
    @RequestMapping("/addVio")
    @ResponseBody
    public ApiResultJson syncData(String token, String reportContent, HttpServletRequest request){
        //TODO token校验
        try {
            String sessionId = request.getParameter("sessionId");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            JSONArray jsonArray = JSONArray.fromObject(reportContent);
            List<String> successIdsList = new ArrayList<String>();
            if (jsonArray !=null && jsonArray.size() > 0) {
                List<MobileTBThreeViolationsEntity> entities = new ArrayList<>();
                for (int i=0; i<jsonArray.size(); i++) {
                    MobileTBThreeViolationsEntity entity = new MobileTBThreeViolationsEntity();
                    JSONObject object = jsonArray.getJSONObject(i);
                    entity.setMobileId(object.optString("id"));
                    entity.setVioDate(DateUtils.str2Date(object.optString("vioDate"), DateUtils.date_sdf));
                    entity.setShift(object.optString("vioShift"));
                    entity.setVioAddress(object.optString("vioAddress"));
                    //转换责任单位，由名字转换为id
                    entity.setVioUnits(object.optString("vioUnit"));
                    entity.setVioPeople(object.optString("vioPeople"));
                    entity.setVioCategory(object.optString("vioCategory"));
                    entity.setVioLevel(object.optString("vioLevel"));
                    entity.setVioQualitative(object.optString("vioQualitative"));
                    //转换发现单位，由名字转换为id
                    entity.setFindUnits(object.optString("findUnit"));
                    entity.setStopPeople(object.optString("stopPeople"));
                    entity.setVioFactDesc(object.optString("vioFactDesc"));
                    entity.setRemark(object.optString("remark"));
                    entity.setReportStatus("0");
                    entity.setCreateBy(user.getUserName());
                    entity.setCreateName(user.getRealName());
                    entity.setCreateDate(new Date());
                    entities.add(entity);

                    successIdsList.add(entity.getMobileId());
                }
                systemService.batchSave(entities);
            }
            return new ApiResultJson(ApiResultJson.CODE_200,ApiResultJson.CODE_200_MSG, successIdsList);
        } catch (Exception e) {
            LogUtil.error("三违上报错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

    //把责任单位从名字转为ID
    private String departNameToID(String departName){
        String retID = "";

        List<String> vioUnitEntityList = systemService.findListbySql("select id from t_s_depart where delete_flag='0' and departname='"+ departName +"'");
        if (vioUnitEntityList!=null && vioUnitEntityList.size()>0){
            retID = vioUnitEntityList.get(0);
        }
        return retID;
    }


    /**
     *  三违上报图片
     * */
    @RequestMapping("/uploadImg")
    @ResponseBody
    public ApiResultJson uploadImg(String token, HttpServletRequest request, TSDocument document){
        try {
            String sessionId = request.getParameter("sessionId");
            String id = request.getParameter("id");

            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }

            TSType tsType = null;
            List<TSType> types = ResourceUtil.allTypes.get("fieltype".toLowerCase());
            if (types!=null && types.size()>0) {
                for (TSType tSType : types) {
                    if ("threeVioImage".equals(tSType.getTypecode())) {
                        tsType = tSType;
                        document.setBusinessKey(tSType.getTypename());
                        break;
                    }
                }
            }
            document.setStatus(1);
            document.setSubclassname(MyClassLoader.getPackPath(document));
            document.setCreatedate(DateUtils.gettimestamp());
            document.setTSType(tsType);
            document.setDocumentTitle("三违照片");

            document.setTSUser(user);

            UploadFile uploadFile = new UploadFile(request, document);
            uploadFile.setCusPath("files");
            uploadFile.setSwfpath("swfpath");
            systemService.uploadFile(uploadFile);
            return new ApiResultJson("照片上传成功");
        } catch (Exception e) {
            LogUtil.error("照片上传失败",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }


    /**
     * 文件上传
     *
     * @param hiddenId    隐患ID
     * @param fileExtend  文件扩展名名
     * @param fileContent 文件base64字符
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public ApiResultJson uploadFile(String hiddenId, String fileExtend, String fileContent, HttpServletRequest request) {
        try {
            //测试暂时注掉
            /*String sessionId = request.getParameter("sessionId");

            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }*/

            if (StringUtils.isNotBlank(hiddenId) && StringUtils.isNotBlank(fileExtend) && StringUtils.isNotBlank(fileContent)) {
                String sql = "select count(id) from t_b_three_violations where mobile_id='" + hiddenId + "'";
                List<BigInteger> temp = systemService.findListbySql(sql);
                if (temp.isEmpty() || temp.size() == 0 || temp.get(0).intValue() == 0) {
                    return new ApiResultJson(ApiResultJson.CODE_202, "三违不存在", null);
                }

                //重命名文件
                String fileName = getNewFileName() + "." + fileExtend;

                //创建文件夹
                String dir = ResourceUtil.getConfigByName("mobileHiddenImgPath") + "//" + hiddenId;
                CreateFileUtil.createDir(dir);

                //保存文件到本地
                Base64Util.decoderBase64File(fileContent, dir + "//" + fileName);

                //图片压缩
                File srcFile = new File(dir + "//" + fileName);
                ReduceImgUtil.reduceImg(dir + "//" + fileName, dir + "//reduce_" + fileName, ReduceImgUtil.getReduceWidthAndHeight(100, srcFile)[0], ReduceImgUtil.getReduceWidthAndHeight(100, srcFile)[1], null);

                //保存关联关系
                TBThreeViolationsImgRelEntity rel = new TBThreeViolationsImgRelEntity();
                rel.setMobileThreevioId(hiddenId);
                rel.setImgPath(fileName);
                systemService.save(rel);
                return new ApiResultJson(ApiResultJson.CODE_200, "文件上传成功", null);
            } else {
                return new ApiResultJson(ApiResultJson.CODE_400, ApiResultJson.CODE_400_MSG, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResultJson(ApiResultJson.CODE_500, ApiResultJson.CODE_500_MSG+"："+e.getMessage(), null);
        }
    }


    /**
     * 多文件上传
     *
     * @param hiddenId          隐患ID
     * @param fileContent       文件报文
     */
    @RequestMapping("/uploadMultipleFiles")
    @ResponseBody
    public ApiResultJson uploadMultipleFiles(String hiddenId, String fileContent,HttpServletRequest request) throws IOException {
        try {
            //测试暂时注掉
            /*String sessionId = request.getParameter("sessionId");

            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }*/

            if (StringUtils.isNotBlank(hiddenId)) {
                String sql = "select count(id) from t_b_three_violations where mobile_id='" + hiddenId + "'";
                List<BigInteger> temp = systemService.findListbySql(sql);
                if (temp.isEmpty() || temp.size() == 0 || temp.get(0).intValue() == 0) {
                    return new ApiResultJson(ApiResultJson.CODE_202, "三违不存在", null);
                }

                JSONArray jsonArray = JSONArray.fromObject(fileContent);
                if (jsonArray !=null && jsonArray.size() > 0) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String fileExtend = object.getString("fileExtend");
                        String filebase64 = object.getString("filebase64");

                        if (StringUtils.isNotBlank(fileExtend) && StringUtils.isNotBlank(filebase64)) {
                            //重命名文件
                            String fileName = getNewFileName() + "." + fileExtend;

                            //创建文件夹
                            String dir = ResourceUtil.getConfigByName("mobileHiddenImgPath") + "//" + hiddenId;
                            CreateFileUtil.createDir(dir);

                            //保存文件到本地
                            Base64Util.decoderBase64File(filebase64, dir + "//" + fileName);

                            //图片压缩
                            File srcFile = new File(dir + "//" + fileName);
                            ReduceImgUtil.reduceImg(dir + "//" + fileName, dir + "//reduce_" + fileName, ReduceImgUtil.getReduceWidthAndHeight(100, srcFile)[0], ReduceImgUtil.getReduceWidthAndHeight(100, srcFile)[1], null);

                            //保存关联关系
                            TBThreeViolationsImgRelEntity rel = new TBThreeViolationsImgRelEntity();
                            rel.setMobileThreevioId(hiddenId);
                            rel.setImgPath(fileName);
                            systemService.save(rel);
                        }
                    }
                }
                return new ApiResultJson(ApiResultJson.CODE_200, "文件上传成功", null);
            } else {
                return new ApiResultJson(ApiResultJson.CODE_400, ApiResultJson.CODE_400_MSG, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResultJson(ApiResultJson.CODE_500, ApiResultJson.CODE_500_MSG+"："+e.getMessage(), null);
        }
    }


    private static String getNewFileName() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @RequestMapping("/getAllDeparts")
    @ResponseBody
    public ApiResultJson getAllDeparts(HttpServletRequest request, HttpServletResponse response){
        String sessionId = request.getParameter("sessionId");
        if (StringUtils.isBlank(sessionId)) {
            return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
        }
        TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
        if (user == null) {
            return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
        }
        String sql = "select id,departname from t_s_depart where delete_flag = '0'";
        List<Map<String,Object>> departs = systemService.findForJdbc(sql);
        if(departs==null || departs.size()==0){
            return new ApiResultJson(ApiResultJson.CODE_202,"没有找到部门信息",null);
        }
        return new ApiResultJson(departs);
    }

}