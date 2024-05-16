package com.sdzk.buss.web.gjj.controller;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.LayDataGrid;
import org.jeecgframework.core.util.*;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSAttachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.web.system.service.SystemService;
import com.sdzk.buss.web.gjj.entity.SfArchiveInfoEntity;
import com.sdzk.buss.web.gjj.service.SfArchiveInfoServiceI;
import com.sdzk.buss.web.common.service.DataMapServiceI;

/**
 * @Title: Controller
 * @Description: 重大隐患档案信息
 * @author gzy
 * @date 2023-10-30 17:03:31
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/sfArchiveInfoController")
public class SfArchiveInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SfArchiveInfoController.class);

	@Autowired
	private SfArchiveInfoServiceI sfArchiveInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private DataMapServiceI dataMapService;

	/**
	 * 重大隐患档案信息列表 页面跳转
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
		List<Map<String, Object>> hiddenTypeList = systemService.findForJdbc(fileTypeSql);
		request.setAttribute("hiddenTypeList",hiddenTypeList);

		return new ModelAndView("com/sdzk/buss/web/gjj/sfArchiveInfoList");
	}

	/**
	 * 重大隐患档案信息列表
	 */
	@RequestMapping(params = "datagrid")
	@ResponseBody
	public LayDataGrid LayDataGrid(HttpServletRequest request){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String[]> obj = request.getParameterMap();
		Integer page =Integer.parseInt(obj.get("page")[0]) ;//页码
		Integer rows = Integer.parseInt(obj.get("limit")[0]);//每页行数
		CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class);
		cq.createAlias("hiddenDanger","hiddenDanger");
		//查询条件
		if(obj.get("searchParams") != null){
			String searchParams = obj.get("searchParams")[0];
			if(StringUtils.isNotEmpty(searchParams)){
				JSONObject json = JSON.parseObject(searchParams);
				String riskType = json.getString("riskType");
				String problemDesc = json.getString("problemDesc");
				if(StringUtils.isNotEmpty(riskType)){
					cq.eq("hiddenDanger.riskType",riskType);
				}
				if(StringUtils.isNotEmpty(problemDesc)){
					cq.like("hiddenDanger.problemDesc","%" + problemDesc + "%");
				}
			}
		}
//		cq.eq("isDelete",Constants.IS_DELETE_N);
		cq.eq("handlelStatus",Constants.REVIEWSTATUS_PASS);//复查通过
		cq.eq("hiddenDanger.hiddenNature",Constants.HIDDEN_LEVEL_1);
		Map orderMap =  new LinkedHashMap();
		orderMap.put("createDate", SortDirection.desc);
		cq.setOrder(orderMap);
		cq.add();
		List countList =  systemService.getListByCriteriaQuery(cq,false);
		cq.setPageSize(rows);
		cq.setCurPage(page);
		cq.add();
		List<TBHiddenDangerHandleEntity> list =  systemService.getListByCriteriaQuery(cq,true);
		List<Map<String,Object>> result=new ArrayList<>();
		for (TBHiddenDangerHandleEntity entity : list) {
			Map<String,Object> resultMap=new HashMap<>();
			resultMap.put("id",entity.getHiddenDanger().getId());
			Map<String, Object> oneForJdbc = systemService.findOneForJdbc("select id,file_name fileName,count(1) count,file_id fileId from sf_archive_info where 1=1 and is_delete='"+Constants.IS_DELETE_N+"' and hd_code='" + entity.getHiddenDanger().getId() + "' limit 1");
			if (oneForJdbc!=null){
				if (!"0".equals(oneForJdbc.get("count").toString())){
					resultMap.put("status","已上传");
				}else {
					resultMap.put("status","未上传");
				}
				resultMap.put("fileId",oneForJdbc.get("fileId"));//文档id
				resultMap.put("fileName",oneForJdbc.get("fileName"));
				resultMap.put("sfArchiveInfoId",oneForJdbc.get("id"));//上传文档id
			}else {
				resultMap.put("status","未上传");
			}

			resultMap.put("addressName",entity.getHiddenDanger().getAddress().getAddress());
			resultMap.put("examDate",entity.getHiddenDanger().getExamDate());
			resultMap.put("shift",DicUtil.getTypeNameByCode("workShift",entity.getHiddenDanger().getShift()));
			resultMap.put("dutyUnit",entity.getHiddenDanger().getDutyUnit().getDepartname());
			resultMap.put("dutyMan",entity.getHiddenDanger().getDutyMan());
			resultMap.put("riskType",DicUtil.getTypeNameByCode("risk_type",entity.getHiddenDanger().getRiskType()));
			resultMap.put("hiddenNature",DicUtil.getTypeNameByCode("hiddenLevel",entity.getHiddenDanger().getHiddenNature()));
			resultMap.put("problemDesc",entity.getHiddenDanger().getProblemDesc());
			result.add(resultMap);
		}
		LayDataGrid lay = new LayDataGrid(countList.size(), result);
		return lay;
	}


	/**
	 * 批量删除重大隐患档案信息
	 */
	@RequestMapping(params = "batchDel")
	@ResponseBody
	public AjaxJson batchDel(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String ids = request.getParameter("ids");
		if(StringUtils.isNotBlank(ids)){
			String[] idArray = ids.split(",");
			for(String id : idArray){
				SfArchiveInfoEntity sfArchiveInfo = systemService.getEntity(SfArchiveInfoEntity.class,Integer.parseInt(id));
				if (Constants.GJJ_STATE_FLAG_0.equals(sfArchiveInfo.getStateFlag())){
					sfArchiveInfo.setIsDelete(Constants.IS_DELETE_Y);
					sfArchiveInfo.setStateFlag(Constants.GJJ_STATE_FLAG_3);//国家局上报标识
					systemService.updateEntitie(sfArchiveInfo);
				}else {
					sfArchiveInfo.setStateFlag(Constants.GJJ_STATE_FLAG_0);//国家局上报标识
					systemService.delete(sfArchiveInfo);
				}
//                sfArchiveInfo.setIsDelete(Constants.IS_DELETE_Y);
//                sfArchiveInfoService.saveOrUpdate(sfArchiveInfo);
			}
		}
		j.setMsg("删除成功");
		return j;
	}

	/**
	 * 添加重大隐患档案信息
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SfArchiveInfoEntity sfArchiveInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(sfArchiveInfo.getId())) {
			message = "重大隐患档案信息更新成功";
			SfArchiveInfoEntity t = sfArchiveInfoService.get(SfArchiveInfoEntity.class, sfArchiveInfo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(sfArchiveInfo, t);
				if (Constants.GJJ_STATE_FLAG_0.equals(t.getStateFlag())){
					t.setStateFlag(Constants.GJJ_STATE_FLAG_2);//国家局上报标识
				}
				sfArchiveInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "重大隐患档案信息更新失败";
			}
		} else {
			message = "重大隐患档案信息添加成功";
			sfArchiveInfo.setIsDelete(Constants.IS_DELETE_N);
			sfArchiveInfo.setStateFlag(Constants.GJJ_STATE_FLAG_1);
			sfArchiveInfoService.save(sfArchiveInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 重大隐患档案信息列表页面跳转
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SfArchiveInfoEntity sfArchiveInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sfArchiveInfo.getId())) {
			sfArchiveInfo = sfArchiveInfoService.getEntity(SfArchiveInfoEntity.class, sfArchiveInfo.getId());
//			req.setAttribute("sfArchiveInfoPage", sfArchiveInfo);
		}
		req.setAttribute("sfArchiveInfoPage", sfArchiveInfo);
		return new ModelAndView("com/sdzk/buss/web/gjj/sfArchiveInfo");
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