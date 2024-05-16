package com.sdzk.buss.web.common.manualnsertRisk;

import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sddb.buss.riskdata.entity.HazardFactorsEntity;
import com.sddb.buss.riskmanage.entity.RiskManageHazardFactorEntity;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.service.SfService;
import com.sdzk.buss.web.dangersource.entity.TbHazardManageEntity;
import com.sdzk.buss.web.gjj.entity.SFPictureInfoEntity;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.p3.core.util.MD5Util;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lenovo on 2018/12/5.
 */
@Controller
@RequestMapping("/riskHelpController")
public class RiskHelpController {

    private static final Logger logger = Logger.getLogger(RiskImportHelper.class);

    @Autowired
    private SystemService systemService;


    @Autowired
    private SfService sfService;

    /**
     * 导入功能跳转
     * @param req
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        String riskTaskId = req.getParameter("riskTaskId");
        req.setAttribute("controller_name","riskHelpController");
        req.setAttribute("function_name", "importExcelT&riskTaskId="+riskTaskId);
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    //导入风险
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "importExcelT", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcelT(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String riskTaskId = request.getParameter("riskTaskId");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        Map<String,String> departMap = new HashMap<>();
        List<TSDepart> departList = systemService.getList(TSDepart.class);
        for(TSDepart depart : departList){
            departMap.put(depart.getDepartname(),depart.getId());
        }

        Map<String,String> userMap = new HashMap<>();
        List<TSUser> userList = systemService.getList(TSUser.class);
        for(TSUser user : userList){
            userMap.put(user.getRealName(),user.getId());
        }
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setNeedSave(false);
            params.setNeedVerfiy(true);
            params.setVerifyHanlder(new RiskExcelVerifyHandler(userMap,departMap));
            try {
                ExcelImportResult<RiskImportHelper> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(), RiskImportHelper.class, params);
                if(result.isVerfiyFail()){
                    String uploadpathtemp = ResourceUtil.getConfigByName("uploadpathtemp");
                    String realPath = multipartRequest.getSession().getServletContext().getRealPath("/") + "/" + uploadpathtemp+"/";// 文件的硬盘真实路径
                    File fileTemp = new File(realPath);
                    if (!fileTemp.exists()) {
                        fileTemp.mkdirs();// 创建根目录
                    }
                    String name = DateUtils.getDataString(DateUtils.yyyymmddhhmmss)+".xls";
                    realPath+=name;
                    FileOutputStream fos = new FileOutputStream(realPath);
                    result.getWorkbook().write(fos);
                    fos.close();
                    Map<String, Object> attributes = new HashMap<String, Object>();
                    attributes.put("path", uploadpathtemp+"/"+name);
                    j.setAttributes(attributes);
                    j.setSuccess(false);
                    j.setMsg("导入数据校验失败");
                }else{
                    List<RiskImportHelper> riskImportHelperList = new ArrayList<>();
                    for (int i = 0; i < result.getList().size(); i++) {
                        RiskImportHelper riskImportHelper = result.getList().get(i);
                        riskImportHelper.setRiskTaskId(riskTaskId);
                        riskImportHelperList.add(riskImportHelper);
                    }
                    riskHelperSplitSave(riskImportHelperList);
                    j.setSuccess(true);
                    j.setMsg("文件导入成功！");
                    systemService.addLog(j.getMsg(), Globals.Log_Leavel_INFO, Globals.Log_Type_UPLOAD);
                }
            } catch (Exception e) {
                j.setSuccess(false);
                j.setMsg("文件导入失败！");
                systemService.addLog(j.getMsg()+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_UPLOAD);
                logger.error(ExceptionUtil.getExceptionMessage(e));
            }finally{
                try {
                    file.getInputStream().close();
                } catch (Exception e) {
                    LogUtil.error("风险导入失败", e);
                }
            }
        }
        return j;
    }

    private void riskHelperSplitSave(List<RiskImportHelper> riskImportHelperList){
        Map<String, String> addressMap = new HashMap<>();
        Map<String, String> riskMap = new HashMap<>();
        Map<String, String> hazardMap = new HashMap<>();
        Map<String, String> addrIdMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //基础信息
        Map<String,String> departMap = new HashMap<>();
        List<TSDepart> departList = systemService.getList(TSDepart.class);
        for(TSDepart depart : departList){
            departMap.put(depart.getDepartname(),depart.getId());
        }
        Map<String,String> userMap = new HashMap<>();
        List<TSUser> userList = systemService.getList(TSUser.class);
        for(TSUser user : userList){
            userMap.put(user.getRealName(),user.getId());
        }
        Map<String, String> postMap = new HashMap<>();
        List<TBPostManageEntity> postList = systemService.getList(TBPostManageEntity.class);
        for (TBPostManageEntity post : postList) {
            postMap.put(post.getPostName(), post.getId());
        }
        CriteriaQuery addressCq = new CriteriaQuery(TBAddressInfoEntity.class);
        addressCq.eq("isDelete","0");
        addressCq.add();
        List<TBAddressInfoEntity> addressList =systemService.getListByCriteriaQuery(addressCq,false);
        if (addressList != null && addressList.size()>0) {
            for (TBAddressInfoEntity entity : addressList) {
                addressMap.put(entity.getAddress().trim().replace(" ",""), entity.getId());
                addrIdMap.put(entity.getId(),entity.getAddress().trim().replace(" ",""));
            }
        }
        CriteriaQuery hazardFactorsCq = new CriteriaQuery(HazardFactorsEntity.class);
        hazardFactorsCq.eq("isDel","0");
        hazardFactorsCq.add();
        List<HazardFactorsEntity> hazardFactorsList =systemService.getListByCriteriaQuery(hazardFactorsCq,false);
        if (hazardFactorsList != null && hazardFactorsList.size()>0) {
            for (HazardFactorsEntity entity : hazardFactorsList) {
                StringBuffer hazardInfo = new StringBuffer();
                //危害因素信息组成
                String major = DicUtil.getTypeNameByCode("major", entity.getMajor());
                if (StringUtil.isNotEmpty(major)) {
                    hazardInfo.append(major);
                } else {
                    hazardInfo.append(entity.getMajor());
                }
                hazardInfo.append(entity.getHazardFactors());
                hazardInfo.append(entity.getManageMeasure());
                String riskType = DicUtil.getTypeNameByCode("risk_type",entity.getRiskType());
                if (StringUtil.isNotEmpty(riskType)) {
                    hazardInfo.append(riskType);
                } else {
                    hazardInfo.append(entity.getRiskType());
                }
                hazardInfo.append(entity.getPostName());
                String level = DicUtil.getTypeNameByCode("factors_level",entity.getRiskLevel());
                if (StringUtil.isNotEmpty(level)) {
                    hazardInfo.append(level);
                } else {
                    hazardInfo.append(entity.getRiskLevel());
                }
                String hazardInfoKey = MD5Util.MD5Encode(hazardInfo.toString(), "UTF-8");
                hazardMap.put(hazardInfoKey,entity.getId());
            }
        }
        CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class);
        cq.eq("isDel",com.sddb.common.Constants.RISK_IS_DEL_FALSE);
        cq.add();
        List<RiskIdentificationEntity> riskList =systemService.getListByCriteriaQuery(cq,false);
        //List<RiskIdentificationEntity> riskList = systemService.getList(RiskIdentificationEntity.class);
        if (riskList!=null && riskList.size()>0) {
            for (RiskIdentificationEntity entity : riskList) {
                StringBuffer riskInfo = new StringBuffer();
                //风险信息组成
                riskInfo.append(addrIdMap.get(entity.getAddress().getId()));
//                riskInfo.append(entity.getPost());
                String riskType = DicUtil.getTypeNameByCode("risk_type",entity.getRiskType());
                if (StringUtil.isNotEmpty(riskType)) {
                    riskInfo.append(riskType);
                } else {
                    riskInfo.append(entity.getRiskType());
                }

                String level = DicUtil.getTypeNameByCode("factors_level",entity.getRiskLevel());
                if (StringUtil.isNotEmpty(level)) {
                    riskInfo.append(level);
                } else {
                    riskInfo.append(entity.getRiskLevel());
                }
                riskInfo.append(entity.getRiskDesc());
                if(StringUtil.isNotEmpty(entity.getIdentifiDate())){
                    riskInfo.append(sdf.format(entity.getIdentifiDate()));
                }
                if(StringUtil.isNotEmpty(entity.getExpDate())){
                    riskInfo.append(sdf.format(entity.getExpDate()));
                }
                riskInfo.append(entity.getDutyManager());
                riskInfo.append(entity.getManageLevel());
                String riskInfoKey = MD5Util.MD5Encode(riskInfo.toString(), "UTF-8");
                riskMap.put(riskInfoKey, entity.getId());
            }
        }

        List<RiskImportHelper> list = new ArrayList<>();
        for(RiskImportHelper helper : riskImportHelperList){
            String controlUnit = helper.getControlUnit();
            String controller = helper.getController();
            String topControlLevel = helper.getTopControlLevel();
            String topController = helper.getTopController();

            if (controlUnit!=null && controlUnit.contains(",")
                    && controller!=null && controller.contains(",")
                    && controlUnit.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",").length == controller.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",").length) {
                for (int i=0; i<controlUnit.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",").length;i++) {
                    String unit = controlUnit.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",")[i];
                    String name = controller.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",")[i];
                    if (StringUtil.isEmpty(unit)) {
                        continue;
                    }
                    if (topController!=null && topController.contains(",")
                            && topControlLevel!=null && topControlLevel.contains(",")
                            && topController.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",").length == topControlLevel.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",").length){
                        for (int j=0; j<topController.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",").length;j++) {
                            String topName = topController.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",")[j];
                            String topLevel = topControlLevel.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",")[j];
                            if (StringUtil.isEmpty(topName)) {
                                continue;
                            }
                            RiskImportHelper newHelp = new RiskImportHelper();
                            BeanUtils.copyProperties(helper,newHelp);
                            newHelp.setController(name);
                            newHelp.setControlUnit(unit);
                            newHelp.setTopController(topName);
                            newHelp.setTopControlLevel(topLevel);
                            list.add(newHelp);
                        }
                    } else {
                        RiskImportHelper newHelp = new RiskImportHelper();
                        BeanUtils.copyProperties(helper,newHelp);
                        newHelp.setController(name);
                        newHelp.setControlUnit(unit);
                        list.add(newHelp);
                    }
                }
            } else if (topController!=null && topController.contains(",")
                    && topControlLevel!=null && topControlLevel.contains(",")
                    && topController.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",").length == topControlLevel.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",").length){
                for (int j=0; j<topController.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",").length;j++) {
                    String topName = topController.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",")[j];
                    String topLevel = topControlLevel.replace(",,",",").replace(" ","").replace("，",",").replace("\n","").split(",")[j];
                    if (StringUtil.isEmpty(topName)) {
                        continue;
                    }
                    RiskImportHelper newHelp = new RiskImportHelper();
                    BeanUtils.copyProperties(helper,newHelp);
                    newHelp.setTopController(topName);
                    newHelp.setTopControlLevel(topLevel);
                    list.add(newHelp);
                }
            } else {
                list.add(helper);
            }
        }

        int i = 0;
        for(RiskImportHelper helper : list){
            System.out.println("当前idx="+i++);
            //
            HazardFactorsEntity hazardFactorsEntity = new HazardFactorsEntity();
            String hazardId;
            RiskIdentificationEntityTmp riskEntity = new RiskIdentificationEntityTmp();
            String riskId;
            //危害因素相关联信息
            StringBuffer hazardInfo = new StringBuffer();
            //危害因素信息组成
            hazardInfo.append(helper.getProfession());
            hazardInfo.append(helper.getHazard());
            hazardInfo.append(helper.getControlMeasure());
            hazardInfo.append(helper.getRiskCate());
            hazardInfo.append(helper.gethPostName());
            hazardInfo.append(helper.gethRiskLevel());
            String hazardInfoKey = MD5Util.MD5Encode(hazardInfo.toString(), "UTF-8");
            if(!hazardMap.containsKey(hazardInfoKey)){
                hazardFactorsEntity.setRiskType(DicUtil.getTypeCodeByName("risk_type",helper.getRiskCate()));
                if (StringUtil.isEmpty(hazardFactorsEntity.getRiskType())) {
                    hazardFactorsEntity.setRiskType(helper.getRiskCate());
                }
                hazardFactorsEntity.setMajor(DicUtil.getTypeCodeByName("major", helper.getProfession()));
                if (hazardFactorsEntity.getMajor()==""||hazardFactorsEntity.getMajor()==null) {
                    hazardFactorsEntity.setMajor(helper.getProfession());
                }
                hazardFactorsEntity.setIsDel("0");
                hazardFactorsEntity.setFrom("1");
                hazardFactorsEntity.setStatus("3");
                hazardFactorsEntity.setHazardFactors(helper.getHazard());
                hazardFactorsEntity.setManageMeasure(helper.getControlMeasure());
                // 岗位
                hazardFactorsEntity.setPostName(helper.gethPostName());
                // 危险源等级
                hazardFactorsEntity.setRiskLevel(DicUtil.getTypeCodeByName("factors_level",helper.gethRiskLevel()));
                if (StringUtil.isEmpty(hazardFactorsEntity.getRiskLevel())) {
                    hazardFactorsEntity.setRiskLevel(helper.gethRiskLevel());
                }
                systemService.save(hazardFactorsEntity);
                hazardMap.put(hazardInfoKey,hazardFactorsEntity.getId());
                hazardId = hazardFactorsEntity.getId();
            }else{
                hazardId = hazardMap.get(hazardInfoKey);
            }
            //风险相关联信息
            StringBuffer riskInfo = new StringBuffer();
            //风险信息组成
            riskInfo.append(helper.getAddressName());
//            riskInfo.append(helper.getPostName());
            riskInfo.append(helper.getRiskCate());
            riskInfo.append(helper.getRiskLevel());
            riskInfo.append(helper.getRiskDescription());
            if(StringUtil.isNotEmpty(helper.getAssessDate())){
                riskInfo.append(sdf.format(helper.getAssessDate()));
            }
            if(StringUtil.isNotEmpty(helper.getTerminateDate())){
                riskInfo.append(sdf.format(helper.getTerminateDate()));
            }
            riskInfo.append(helper.getTopController());
            riskInfo.append(helper.getTopControlLevel());
            String riskInfoKey = MD5Util.MD5Encode(riskInfo.toString(), "UTF-8");
            if(!riskMap.containsKey(riskInfoKey)){
                // 关联地点
                if (StringUtil.isNotEmpty(helper.getAddressName())) {
                    //地点相关联信息
                    String addressName = helper.getAddressName().trim().replace(" ","");
                    String addresssId = addressMap.get(addressName);
                    if(!addressMap.containsKey(addressName)){
                        TBAddressInfoEntity addressInfoEntity = new TBAddressInfoEntity();
                        addressInfoEntity.setId(UUIDGenerator.generate());
                        addressInfoEntity.setAddress(addressName);
                        addressInfoEntity.setIsDelete(Constants.IS_DELETE_N);
                        addressInfoEntity.setIsshow("Y");
                        addressInfoEntity.setIsShowData(Constants.IS_SHOWDATA_Y);
                        addressInfoEntity.setLat("0");
                        addressInfoEntity.setLon("0");
                        //添加国家局标识
                        addressInfoEntity.setStateFlag(Constants.GJJ_STATE_FLAG_1);
                        //判断该图层是否有图形文件，没有则新增，有则保存id
                        String  sql="select id from sf_picture_info where 1=1 and file_type='1' and is_delete='0' limit 1";
                        Map<String, Object> oneForJdbc = systemService.findOneForJdbc(sql);
                        if (oneForJdbc!=null&&oneForJdbc.get("id")!=null){
                            addressInfoEntity.setPictureCode(Integer.parseInt(oneForJdbc.get("id").toString()));
                        }

                        systemService.save(addressInfoEntity);
                        addressMap.put(addressName,addressInfoEntity.getId());
                        addresssId = addressInfoEntity.getId();
                    }
                    riskEntity.setIdentificationType(DicUtil.getTypeCodeByName("identifi_from",helper.getInformationSource()));
                    riskEntity.setAddress(addresssId);
                } else {
                    continue;
                }
//                if (StringUtil.isNotEmpty(helper.getPostName())) {
//                    //地点相关联信息
//                    String postName = helper.getPostName();
//                    String postId = postMap.get(postName);
//                    if(!postMap.containsKey(postName)){
//                        TBPostManageEntity postManageEntity = new TBPostManageEntity();
//                        postManageEntity.setIsDelete(Constants.IS_DELETE_N);
//                        postManageEntity.setPostName(postName);
//                        systemService.save(postManageEntity);
//                        postMap.put(postName,postManageEntity.getId());
//                        postId = postManageEntity.getId();
//                    }
//                    riskEntity.setIdentificationType("4");
//                    riskEntity.setPost(postId);
//                }
                //非关联实体信息
                riskEntity.setRiskType(DicUtil.getTypeCodeByName("risk_type",helper.getRiskCate()));
                if (StringUtil.isEmpty(riskEntity.getRiskType())) {
                    riskEntity.setRiskType(helper.getRiskCate());
                }
                riskEntity.setRiskDesc(helper.getRiskDescription());
                riskEntity.setRiskLevel(DicUtil.getTypeCodeByName("factors_level",helper.getRiskLevel()));
                if (StringUtil.isEmpty(riskEntity.getRiskLevel())) {
                    riskEntity.setRiskLevel(helper.getRiskLevel());
                }
                riskEntity.setManageLevel(DicUtil.getTypeCodeByName("identifi_mange_level",helper.getTopControlLevel()));
                if (StringUtil.isEmpty(riskEntity.getManageLevel())) {
                    riskEntity.setManageLevel(helper.getTopControlLevel());
                }
                riskEntity.setDutyManager(helper.getTopController());
//                try{
//                    riskEntity.setIdentifiDate(DateUtils.date_sdf.parse(helper.getAssessDate()));
//                }catch (Exception e){
//
//                }
//                try{
//                    riskEntity.setExpDate(DateUtils.date_sdf.parse(helper.getTerminateDate()));
//                }catch (Exception e){
//
//                }
                riskEntity.setIdentifiDate(helper.getAssessDate());
                riskEntity.setExpDate(helper.getTerminateDate());
                riskEntity.setStatus("3");
                riskEntity.setIsDel("0");
                //添加国家局上报标识
                riskEntity.setStateFlag(Constants.GJJ_STATE_FLAG_1);
                //添加风险辨识关系
                riskEntity.setRiskTaskId(helper.getRiskTaskId());
                systemService.save(riskEntity);
                riskMap.put(riskInfoKey,riskEntity.getId());
                riskId = riskEntity.getId();
            }else{
                riskId = riskMap.get(riskInfoKey);
            }
            //危害因素风险关联表
            String sql = "select id from t_b_risk_factors_rel where hazard_factors_id=? and risk_identification_id=? limit 0,1";
            Map<String, Object> relMap = systemService.findOneForJdbc(sql, hazardId, riskId);
            if (relMap==null || relMap.get("id")==null || StringUtil.isEmpty(relMap.get("id").toString())) {
                System.out.println("插入当前idx="+i);
                RiskFactortsRelTmp riskFactortsRel = new RiskFactortsRelTmp();
                riskFactortsRel.setHazardFactorsEntity(hazardId);
                riskFactortsRel.setRiskIdentificationEntity(riskId);
                riskFactortsRel.setManageDepart(departMap.get(helper.getControlUnit()));
                if (StringUtil.isEmpty(riskFactortsRel.getManageDepart())) {
                    riskFactortsRel.setManageDepart(helper.getControlUnit());
                }
                riskFactortsRel.setManageUser(userMap.get(helper.getController()));
                if (StringUtil.isEmpty(riskFactortsRel.getManageUser())) {
                    riskFactortsRel.setManageUser(helper.getController());
                }
                riskFactortsRel.setHfManageMeasure(helper.getControlMeasure());
                riskFactortsRel.setHfLevel(DicUtil.getTypeCodeByName("factors_level",helper.gethRiskLevel()));
                if (StringUtil.isEmpty(riskFactortsRel.getHfLevel())) {
                    riskFactortsRel.setHfLevel(helper.gethRiskLevel());
                }
                systemService.save(riskFactortsRel);

                //保存上报国家局风险措施于风险关联表  审批后上传国家局
                RiskIdentificationEntity riskIdentification = systemService.getEntity(RiskIdentificationEntity.class, riskId);
                HazardFactorsEntity hazardFactors = systemService.getEntity(HazardFactorsEntity.class, hazardId);
                sfService.saveOrUpdateSfRiskMeasureEntity(riskId,riskIdentification.getAddress().getId(),riskIdentification.getDutyManager(), hazardId, hazardFactors.getManageMeasure(), hazardFactors.getPostName(),"0");

            } else {
                System.out.println("已存在当前idx="+i);
            }
        }
    }
}
