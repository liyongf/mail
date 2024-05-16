package com.sdzk.buss.web.gjj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.gjj.entity.SFReportInfoEntity;
import com.sdzk.buss.web.gjj.service.SFReportInfoServiceI;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.LayDataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.pojo.base.TSAttachment;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Title: Controller
 * @Description: 报告文件
 * @author zhanglong
 * @date 2023-10-27 13:36:21
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/sFReportInfoController")
public class SFReportInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SFReportInfoController.class);

	@Autowired
	private SFReportInfoServiceI sFReportInfoService;
	@Autowired
	private SystemService systemService;

	/**
	 * 列表页面跳转
	 */
	@RequestMapping(params = "list")
	public ModelAndView listLayui(HttpServletRequest request) {
		TSUser sessionUserName = ResourceUtil.getSessionUserName();
		//权限
		if(systemService.isSearchRole(sessionUserName,"admin")){
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

		String fileTypeSql="SELECT typecode,typename FROM t_s_type type LEFT JOIN t_s_typegroup typegroup ON type.typegroupid = typegroup.ID WHERE typegroup.typegroupcode='gjj_bg_type'";
		List<Map<String, Object>> fileTypeList = systemService.findForJdbc(fileTypeSql);
		request.setAttribute("fileTypeList",fileTypeList);

		return new ModelAndView("com/sdzk/buss/web/gjj/sFReportInfoList");//四色图
	}


	/**
	 * 列表数据
	 */
	@RequestMapping(params = "data")
	@ResponseBody
	public LayDataGrid data(HttpServletRequest request) {
		String type = request.getParameter("type");
		Map<String, String[]> obj = request.getParameterMap();
		Integer page =Integer.parseInt(obj.get("page")[0]) ;//页码
		Integer rows = Integer.parseInt(obj.get("limit")[0]);//每页行数
		CriteriaQuery cq = new CriteriaQuery(SFReportInfoEntity.class);
		cq.notEq("isDelete", Constants.IS_DELETE_Y);

		if(obj.get("searchParams") != null){
			String searchParams = obj.get("searchParams")[0];
			if(StringUtils.isNotEmpty(searchParams)){
				JSONObject json = JSON.parseObject(searchParams);
				String reportType = json.getString("reportType");
				if(StringUtils.isNotEmpty(reportType)){
					cq.eq("reportType",reportType);
				}
			}
		}
		cq.add();
		List countList =  systemService.getListByCriteriaQuery(cq,false);
		cq.setPageSize(rows);
		cq.setCurPage(page);
		cq.add();

		List<SFReportInfoEntity> list =  systemService.getListByCriteriaQuery(cq,true);
		systemService.getSession().clear();
		if(list!=null && list.size()>0){
			for(SFReportInfoEntity entity:list){
				TSAttachment attachment = systemService.getEntity(TSAttachment.class,entity.getFileId());
				if(attachment!=null){
					entity.setFileRealPath(attachment.getRealpath());
				}
				if (StringUtils.isNotBlank(entity.getReportType())){
					entity.setReportTypeName(DicUtil.getTypeNameByCode("gjj_bg_type",entity.getReportType()));
				}
			}
		}
		LayDataGrid lay = new LayDataGrid(countList.size(),list);
		return lay;
	}

	/**
	 * 删除报告
	 *
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(SFReportInfoEntity sfReportInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		sfReportInfo = systemService.getEntity(SFReportInfoEntity.class, sfReportInfo.getId());
		message = "报告删除成功";
		sfReportInfo.setIsDelete(Constants.IS_DELETE_Y);
		//删除操作，如果stateflag 不是1 ---删除数据--- stateflag 变成3，isdelete变为1
		//删除操作，如果stateflag 是1 ---删除数据---  stateflag 变为0，isdelete变为1
		if ("1".equals(sfReportInfo.getStateFlag())){
			sfReportInfo.setStateFlag(Constants.GJJ_STATE_FLAG_0);//国家局上报标识
		}else {
			sfReportInfo.setStateFlag(Constants.GJJ_STATE_FLAG_3);//国家局上报标识
		}
		sFReportInfoService.updateEntitie(sfReportInfo);

		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除报告
	 *
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "删除成功";
		try{
			for(String id:ids.split(",")){
				SFReportInfoEntity sfReportInfo = systemService.getEntity(SFReportInfoEntity.class,
						Integer.valueOf(id)
				);
				sfReportInfo.setIsDelete(Constants.IS_DELETE_Y);
				//删除操作，如果stateflag 为1 时候，删除  stateflag更新为0，isdelete更新为1；
				//如果stateflag 不为1 时候，删除  stateflag更新为3，isdelete更新为1.
				if ("1".equals(sfReportInfo.getStateFlag())){
					sfReportInfo.setStateFlag(Constants.GJJ_STATE_FLAG_0);
				}else {
					sfReportInfo.setStateFlag(Constants.GJJ_STATE_FLAG_3);
				}
				sFReportInfoService.updateEntitie(sfReportInfo);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加报告
	 *
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SFReportInfoEntity sfReportInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(sfReportInfo.getId())) {
			message = "报告更新成功";
			SFReportInfoEntity t = sFReportInfoService.get(SFReportInfoEntity.class, sfReportInfo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(sfReportInfo, t);
				if ("0".equals(t.getStateFlag())){
					t.setStateFlag(Constants.GJJ_STATE_FLAG_2);
				}
				TSUser sessionUserName = ResourceUtil.getSessionUserName();
				t.setUpdateBy(sessionUserName.getUserName());
				t.setUpdateName(sessionUserName.getRealName());
				t.setUpdateDate(new Date());
				sFReportInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "报告更新失败";
			}
		} else {
			message = "报告添加成功";
			sfReportInfo.setStateFlag(Constants.GJJ_STATE_FLAG_1);
			sfReportInfo.setIsDelete(Constants.IS_DELETE_N);
			sFReportInfoService.save(sfReportInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 报告文件列表页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "addorupdateLayui")
	public ModelAndView addorupdateLayui(SFReportInfoEntity sfReportInfo, HttpServletRequest req) {

		List<Object> imgList = new ArrayList<>();
		if (StringUtil.isNotEmpty(sfReportInfo.getId())) {
			sfReportInfo = sFReportInfoService.getEntity(SFReportInfoEntity.class, sfReportInfo.getId());

			if (StringUtil.isNotEmpty(sfReportInfo.getFileId())) {
				TSAttachment attachment = systemService.get(TSAttachment.class, sfReportInfo.getFileId());
				Map<String, Object> imgMap = new HashMap<>();
				if (attachment != null) {
					imgMap.put("id", attachment.getId());
					imgMap.put("path", attachment.getRealpath());
					imgList.add(imgMap);
				}
			}
			req.setAttribute("sfReportInfoPage", sfReportInfo);
			req.setAttribute("imgList", imgList);
		}

		req.setAttribute("imgNum", imgList.size());

		String fileTypeSql="SELECT typecode,typename FROM t_s_type type LEFT JOIN t_s_typegroup typegroup ON type.typegroupid = typegroup.ID WHERE typegroup.typegroupcode='gjj_bg_type'";
		List<Map<String, Object>> fileTypeList = systemService.findForJdbc(fileTypeSql);
		req.setAttribute("fileTypeList",fileTypeList);

		return new ModelAndView("com/sdzk/buss/web/gjj/sFReportInfoAdd");
	}




	@RequestMapping(params = "uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson uploadFile(HttpServletRequest request, TSAttachment attachment) {
		String message = "上传成功";
		AjaxJson j = new AjaxJson();
		try {
			String fileKey = oConvertUtils.getString(request.getParameter("fileId"));// 文件ID
			attachment.setSubclassname(MyClassLoader.getPackPath(attachment));
			attachment.setCreatedate(DateUtils.gettimestamp());
			attachment.setTSUser(ResourceUtil.getSessionUserName());
			UploadFile uploadFile = new UploadFile(request, attachment);
			uploadFile.setCusPath("files");
			uploadFile.setSwfpath("swfpath");
			attachment = systemService.uploadFile(uploadFile);
			Map<String, Object> map = new HashMap<>();
			map.put("id", attachment.getId());
			map.put("path", attachment.getRealpath());
			map.put("name", attachment.getAttachmenttitle()+'.'+attachment.getExtend());
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
