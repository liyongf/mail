package com.sdzk.buss.web.gjj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.service.DataMapServiceI;
import com.sdzk.buss.web.gjj.entity.SfPlanInfoEntity;
import com.sdzk.buss.web.gjj.service.SfPlanInfoServiceI;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.LayDataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSAttachment;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Title: Controller
 * @Description: 重大风险管控方案
 * @author gzy
 * @date 2023-10-30 17:02:45
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/sfPlanInfoController")
public class SfPlanInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SfPlanInfoController.class);

	@Autowired
	private SfPlanInfoServiceI sfPlanInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
    private DataMapServiceI dataMapService;

	/**
	 * 重大风险管控方案列表 页面跳转
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		String loginName = ResourceUtil.getSessionUserName().getUserName();
		if("admin".equals(loginName)){
			request.setAttribute("roleOpt","");
		}else{
			String opt = "";
			//获取用户菜单按钮权限
			Set<String> optList = (Set<String>) request.getAttribute(Globals.OPERATIONCODESTRS);
            if(optList != null && optList.size() > 0){
                for(String str : optList){
                    if("".equals(opt)){
                        opt = str;
                    }else{
                        opt += "," + str;
                    }
                }
            }
			request.setAttribute("roleOpt",opt);
		}
        //以下代码根据需要使用
        /*
        Map<String, TSUser> userMap = systemService.getSunshineUserMap();//获取全部用户
        Map<String, TSDepart> departMap = systemService.getSunshineDepartMap();//获取全部部门
        Map<String, TbRiskPointEntity> riskPointMap = systemService.getSunshinePointMap();//获取全部风险点
        Map<String, List<TSType>> types = ResourceUtil.allTypes;//获取全部字典
        List<TSType> dicList = types.get("");//传入字典CODE
        request.setAttribute("userMap",userMap);
        request.setAttribute("departMap",departMap);
        request.setAttribute("riskPointMap",riskPointMap);
        request.setAttribute("dicList",dicList);
        */

		String fileTypeSql="SELECT typecode,typename FROM t_s_type type LEFT JOIN t_s_typegroup typegroup ON type.typegroupid = typegroup.ID WHERE typegroup.typegroupcode='risk_type'";
		List<Map<String, Object>> riskTypeList = systemService.findForJdbc(fileTypeSql);
		request.setAttribute("riskTypeList",riskTypeList);

		return new ModelAndView("com/sdzk/buss/web/gjj/sfPlanInfoList");
	}

	/**
	 * 重大风险管控方案列表
	 */
	@RequestMapping(params = "datagrid")
    @ResponseBody
	public LayDataGrid LayDataGrid(HttpServletRequest request){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String[]> obj = request.getParameterMap();
		Integer page =Integer.parseInt(obj.get("page")[0]) ;//页码
		Integer rows = Integer.parseInt(obj.get("limit")[0]);//每页行数
		CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class);
		//查询条件
		if(obj.get("searchParams") != null){
			String searchParams = obj.get("searchParams")[0];
			if(StringUtils.isNotEmpty(searchParams)){
				JSONObject json = JSON.parseObject(searchParams);
				String riskType = json.getString("riskType");
				String riskDesc = json.getString("riskDesc");
				if(StringUtils.isNotEmpty(riskType)){
					cq.eq("riskType",riskType);
				}
				if(StringUtils.isNotEmpty(riskDesc)){
					cq.like("riskDesc","%" + riskDesc + "%");
				}
			}
		}
		cq.eq("isDel", Constants.IS_DELETE_N);
		//重大风险
		cq.eq("riskLevel", Constants.FACTORS_LEVEL_1);
		//未超期：解除日期大于当前日期
		cq.add(Restrictions.sqlRestriction("this_.id in (select id from  t_b_risk_identification  where  identifi_date <= now()  and (exp_date >= now() or exp_date is null))"));
		//风险辨识：审批通过
		cq.eq("status", com.sddb.common.Constants.RISK_IDENTIFI_STATUS_REVIEW);
        Map orderMap =  new LinkedHashMap();
        orderMap.put("createDate", SortDirection.desc);
        cq.setOrder(orderMap);
		cq.add();
		List countList =  systemService.getListByCriteriaQuery(cq,false);
		cq.setPageSize(rows);
		cq.setCurPage(page);
		cq.add();
		List<RiskIdentificationEntity> list =  systemService.getListByCriteriaQuery(cq,true);
		List<Map<String,Object>> result=new ArrayList<>();
		for (RiskIdentificationEntity entity : list) {
			Map<String,Object> resultMap=new HashMap<>();
			resultMap.put("id",entity.getId());
			Map<String, Object> oneForJdbc = systemService.findOneForJdbc("select id,file_name fileName,file_id fileId from sf_plan_info where 1=1 and is_delete='"+ Constants.IS_DELETE_N+"' and risk_code='" + entity.getId() + "' limit 1");
			if (oneForJdbc!=null){
				if (StringUtils.isNotBlank(oneForJdbc.get("fileId").toString())){
					resultMap.put("status","已上传");
					resultMap.put("fileName",oneForJdbc.get("fileName"));
				}else {
					resultMap.put("status","未上传");
				}
				resultMap.put("sfPlanInfoId",oneForJdbc.get("id"));//上传文档id
				resultMap.put("fileId",oneForJdbc.get("fileId"));//文档id
			}else {
				resultMap.put("sfPlanInfoId","");
				resultMap.put("status","未上传");
			}

			resultMap.put("addressName",entity.getAddress().getAddress());
			resultMap.put("riskDesc",entity.getRiskDesc());
			resultMap.put("riskType", DicUtil.getTypeNameByCode("risk_type",entity.getRiskType()));
			resultMap.put("riskLevel", DicUtil.getTypeNameByCode("factors_level",entity.getRiskLevel()));
			resultMap.put("identifiDate",entity.getIdentifiDate());
			resultMap.put("expDate",entity.getExpDate());
			result.add(resultMap);
		}
        LayDataGrid lay = new LayDataGrid(countList.size(), result);
        return lay;
	}

    /**
     * 批量删除重大风险管控方案
    */
    @RequestMapping(params = "batchDel")
    @ResponseBody
    public AjaxJson batchDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        if(StringUtils.isNotBlank(ids)){
            String[] idArray = ids.split(",");
            for(String id : idArray){
                SfPlanInfoEntity sfPlanInfo = systemService.getEntity(SfPlanInfoEntity.class,Integer.parseInt(id));
				if (Constants.GJJ_STATE_FLAG_0.equals(sfPlanInfo.getStateFlag())){
					sfPlanInfo.setIsDelete(Constants.IS_DELETE_Y);
					sfPlanInfo.setStateFlag(Constants.GJJ_STATE_FLAG_3);//国家局上报标识
					systemService.updateEntitie(sfPlanInfo);
				}else {
					sfPlanInfo.setStateFlag(Constants.GJJ_STATE_FLAG_0);//国家局上报标识
					systemService.delete(sfPlanInfo);
				}
//                sfPlanInfo.setIsDelete(Constants.IS_DELETE_Y);
//                sfPlanInfoService.saveOrUpdate(sfPlanInfo);
            }
        }
        j.setMsg("删除成功");
        return j;
    }

	/**
	 * 添加重大风险管控方案
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SfPlanInfoEntity sfPlanInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(sfPlanInfo.getId())) {
			message = "重大风险管控方案更新成功";
			SfPlanInfoEntity t = sfPlanInfoService.get(SfPlanInfoEntity.class, sfPlanInfo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(sfPlanInfo, t);
				if (Constants.GJJ_STATE_FLAG_0.equals(t.getStateFlag())){
					t.setStateFlag(Constants.GJJ_STATE_FLAG_2);//国家局上报标识
				}
				sfPlanInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "重大风险管控方案更新失败";
			}
		} else {
			message = "重大风险管控方案添加成功";
            sfPlanInfo.setIsDelete(Constants.IS_DELETE_N);
			sfPlanInfo.setStateFlag(Constants.GJJ_STATE_FLAG_1);
			sfPlanInfoService.save(sfPlanInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 重大风险管控方案列表页面跳转
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SfPlanInfoEntity sfPlanInfo, HttpServletRequest req) {
//		String riskId = req.getParameter("riskId");
//		req.setAttribute("riskId",riskId);
		if (StringUtil.isNotEmpty(sfPlanInfo.getId())) {
			sfPlanInfo = sfPlanInfoService.getEntity(SfPlanInfoEntity.class, sfPlanInfo.getId());
		}
		req.setAttribute("sfPlanInfoPage", sfPlanInfo);
		return new ModelAndView("com/sdzk/buss/web/gjj/sfPlanInfo");
	}


	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson uploadFile(HttpServletRequest request, TSAttachment attachment) {
		String message = "上传成功";
		AjaxJson j = new AjaxJson();
		try {
			attachment.setSubclassname(MyClassLoader.getPackPath(attachment));
			attachment.setCreatedate(DateUtils.gettimestamp());
			attachment.setTSUser(ResourceUtil.getSessionUserName());
			UploadFile uploadFile = new UploadFile(request, attachment);
			uploadFile.setCusPath("files");
			uploadFile.setSwfpath("swfpath");
			attachment = systemService.uploadFile(uploadFile);
			Map<String ,Object> map=new HashMap<>();
			map.put("id",attachment.getId());
			map.put("fileName",attachment.getAttachmenttitle()+"."+attachment.getExtend());
			j.setObj(map);
		} catch (Exception e) {
			message = "上传失败";
			j.setSuccess(false);
			LogUtil.error(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
}
