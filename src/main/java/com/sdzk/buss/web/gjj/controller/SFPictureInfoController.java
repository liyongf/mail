package com.sdzk.buss.web.gjj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.gjj.entity.SFPictureInfoEntity;
import com.sdzk.buss.web.gjj.service.SFPictureInfoServiceI;
import com.sdzk.buss.web.layer.entity.TBLayerEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
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
import javax.validation.Validator;
import java.util.*;

/**   
 * @Title: Controller
 * @Description: 图形文件
 * @author zhangdaihao
 * @date 2023-10-25 17:15:21
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/sFPictureInfoController")
public class SFPictureInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SFPictureInfoController.class);

	@Autowired
	private SFPictureInfoServiceI sFPictureInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 列表页面跳转
	 */
	@RequestMapping(params = "listLayui")
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

		String layerListSql="select id id,layer_detail_name layerName from t_b_layer ";
		List<Map<String, Object>> layerList = systemService.findForJdbc(layerListSql);
		request.setAttribute("layerList",layerList);

		return new ModelAndView("com/sdzk/buss/web/gjj/graphic_list1");//四色图
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
		CriteriaQuery cq = new CriteriaQuery(SFPictureInfoEntity.class);
		cq.notEq("isDelete", Constants.IS_DELETE_Y);

		if(obj.get("searchParams") != null){
			String searchParams = obj.get("searchParams")[0];
			if(StringUtils.isNotEmpty(searchParams)){
				JSONObject json = JSON.parseObject(searchParams);
				String layerId = json.getString("layerId");
				if(StringUtils.isNotEmpty(layerId)){
					cq.eq("layerId",layerId);
				}
			}
		}
		cq.add();
		List countList =  systemService.getListByCriteriaQuery(cq,false);
		cq.setPageSize(rows);
		cq.setCurPage(page);
		cq.add();
		List<SFPictureInfoEntity> list =  systemService.getListByCriteriaQuery(cq,true);
		systemService.getSession().clear();
		if(list!=null && list.size()>0){
			for(SFPictureInfoEntity entity:list){
				if(StringUtils.isNotBlank(entity.getLayerId())){
					TBLayerEntity layer = systemService.getEntity(TBLayerEntity.class,entity.getLayerId());
					if(layer!=null){
						entity.setLayerName(layer.getLayerDetailName());
					}
				}
				if (StringUtils.isNotEmpty(entity.getFileId())){
					TSAttachment attachment = systemService.getEntity(TSAttachment.class,entity.getFileId());
					if(attachment!=null){
						entity.setFileRealPath(attachment.getRealpath());
					}
				}

				if (StringUtils.isNotBlank(entity.getFileType())){
					entity.setFileTypeName(DicUtil.getTypeNameByCode("gjj_file_type",entity.getFileType()));
				}
			}
		}
		LayDataGrid lay = new LayDataGrid(countList.size(),list);
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
				SFPictureInfoEntity sFPictureInfo = systemService.getEntity(SFPictureInfoEntity.class, Integer.parseInt(id));
				//删除操作，如果已上报则更改为删除标识  否则直接删除。
				if (Constants.GJJ_STATE_FLAG_0.equals(sFPictureInfo.getStateFlag())){
					sFPictureInfo.setIsDelete(Constants.IS_DELETE_Y);
					sFPictureInfo.setStateFlag(Constants.GJJ_STATE_FLAG_3);//国家局上报标识
					sFPictureInfoService.updateEntitie(sFPictureInfo);
				}else {
					sFPictureInfo.setStateFlag(Constants.GJJ_STATE_FLAG_0);//国家局上报标识
					sFPictureInfoService.delete(sFPictureInfo);
				}
			}
		}
		j.setMsg("删除成功");
		return j;
	}


	/**
	 * 添加图形文件
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SFPictureInfoEntity sFPictureInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(sFPictureInfo.getId())) {
			message = "图形文件更新成功";
			SFPictureInfoEntity t = sFPictureInfoService.get(SFPictureInfoEntity.class, sFPictureInfo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(sFPictureInfo, t);
				if (Constants.GJJ_STATE_FLAG_0.equals(t.getStateFlag())){
					t.setStateFlag(Constants.GJJ_STATE_FLAG_2);//国家局上报标识
				}
				TSUser sessionUserName = ResourceUtil.getSessionUserName();
				t.setUpdateBy(sessionUserName.getUserName());
				t.setUpdateName(sessionUserName.getRealName());
				t.setUpdateDate(new Date());
				sFPictureInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "图形文件更新失败";
			}
		} else {
			message = "图形文件添加成功";
			sFPictureInfo.setStateFlag(Constants.GJJ_STATE_FLAG_1);//国家局上报标识
			sFPictureInfo.setIsDelete(Constants.IS_DELETE_N);//国家局上报标识
			sFPictureInfoService.save(sFPictureInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 图形文件列表页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "addorupdateLayui")
	public ModelAndView addorupdateLayui(SFPictureInfoEntity sFPictureInfo, HttpServletRequest req) {

		List<Object> imgList = new ArrayList<>();
		if (StringUtil.isNotEmpty(sFPictureInfo.getId())) {
			sFPictureInfo = sFPictureInfoService.getEntity(SFPictureInfoEntity.class, sFPictureInfo.getId());
			req.setAttribute("sFPictureInfoPage", sFPictureInfo);

			if (StringUtil.isNotEmpty(sFPictureInfo.getFileId())) {
				TSAttachment attachment = systemService.get(TSAttachment.class, sFPictureInfo.getFileId());
				Map<String, Object> imgMap = new HashMap<>();
				if (attachment != null) {
					imgMap.put("id", attachment.getId());
					imgMap.put("path", attachment.getRealpath());
					imgList.add(imgMap);
				}
			}
			req.setAttribute("imgList", imgList);
		}

		req.setAttribute("imgNum", imgList.size());
		String layerListSql="select id id,layer_detail_name layerName from t_b_layer ";
		List<Map<String, Object>> layerList = systemService.findForJdbc(layerListSql);
		req.setAttribute("layerList",layerList);

		String fileTypeSql="SELECT typecode,typename FROM t_s_type type LEFT JOIN t_s_typegroup typegroup ON type.typegroupid = typegroup.ID WHERE typegroup.typegroupcode='gjj_file_type'";
		List<Map<String, Object>> fileTypeList = systemService.findForJdbc(fileTypeSql);
		req.setAttribute("fileTypeList",fileTypeList);

		return new ModelAndView("com/sdzk/buss/web/gjj/graphic_add1");
	}

	@RequestMapping(params = "uploadFile", method = RequestMethod.POST)
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
			Map<String, Object> map = new HashMap<>();
			map.put("id", attachment.getId());
			map.put("path", attachment.getRealpath());
			map.put("name", attachment.getAttachmenttitle()+"."+attachment.getExtend());
			j.setObj(map);
		} catch (Exception e) {
			message = "上传失败";
			j.setSuccess(false);
			LogUtil.error(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 属性唯一性校验(根据实际情况使用)
	 */
	@RequestMapping(params = "propertyUnique")
	@ResponseBody
	public AjaxJson propertyUnique(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String layerId = request.getParameter("layerId");
		String fileType = request.getParameter("fileType");
		if (oConvertUtils.isNotEmpty(layerId)&& oConvertUtils.isNotEmpty(fileType)){
			String sql="select count(1) count from sf_picture_info where 1=1 and layer_id='"+layerId+"' and file_type='"+fileType+"' and is_delete='0'";
			Map<String, Object> oneForJdbc = systemService.findOneForJdbc(sql);
			if (oneForJdbc==null||Integer.parseInt(oneForJdbc.get("count").toString())>=1){
				j.setSuccess(false);
				j.setMsg("该类型下存在相同图层");
			}else {
				j.setMsg("成功");
				j.setSuccess(true);
			}


		}else {
			j.setSuccess(false);
			j.setMsg("参数不能为空");
		}
		return j;
	}

}
