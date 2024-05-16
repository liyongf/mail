package com.sdzk.buss.web.accident.controller;
import com.sdzk.buss.web.accident.entity.TBAccidentEntity;
import com.sdzk.buss.web.accident.entity.TBAccidentLevelEntity;
import com.sdzk.buss.web.accident.service.TBAccidentServiceI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.util.*;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.context.annotation.Scope;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 事故
 * @author onlineGenerator
 * @date 2016-05-06 10:09:42
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBAccidentController")
public class TBAccidentController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBAccidentController.class);

	@Autowired
	private TBAccidentServiceI tBAccidentService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 事故列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
        List<TBAccidentLevelEntity> levelEntityList = systemService.getList(TBAccidentLevelEntity.class);
        request.setAttribute("levelEntityList",levelEntityList);
		return new ModelAndView("com/sdzk/buss/web/accident/tBAccidentList");
	}
    @RequestMapping(params = "goViewFile")
    public ModelAndView goViewFile(HttpServletRequest request){
        String busid =request.getParameter("busid");
        request.setAttribute("busid",busid);
        return new ModelAndView("com/sdzk/buss/web/accident/tBFineInfo-goViewFile");
    }

    @RequestMapping(params = "fileDatagrid")
    public void fileDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String busId = request.getParameter("busid");
        List<String> ids = this.systemService.findListbySql(" select tsa.id from t_s_attachment as tsa ,t_s_document as tsd where tsa.businesskey = '"+busId+"' and tsa.id = tsd.id and tsd.status = 1 ");
        if(ids != null && !ids.isEmpty()) {
            CriteriaQuery cq = new CriteriaQuery(TSAttachment.class, dataGrid);
            cq.in("id", ids.toArray());
            cq.eq("status", 1);
            cq.add();
            this.systemService.getDataGridReturn(cq, true);
        }
        TagUtil.datagrid(response, dataGrid);
    }
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

		CriteriaQuery cq = new CriteriaQuery(TBAccidentEntity.class, dataGrid);
		try{
		//自定义追加查询条件
            String accidentlevel = request.getParameter("accidentlevel");
            if(StringUtils.isNotBlank(accidentlevel)){
                cq.eq("accidentlevel.id",accidentlevel);
            }
            String accidentcode = request.getParameter("accidentcode");
            if(StringUtils.isNotBlank(accidentcode)){
                cq.like("accidentcode","%"+accidentcode+"%");
            }
            String accidentname = request.getParameter("accidentname");
            if(StringUtils.isNotBlank(accidentname)){
                cq.like("accidentname","%"+accidentname+"%" );
            }
            String happentimeBegin =request.getParameter("happentime_begin");
            if(StringUtils.isNotBlank(happentimeBegin)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.ge("happentime" ,sdf.parse(happentimeBegin));
            }
            String happentimeEnd =request.getParameter("happentime_end");
            if(StringUtils.isNotBlank(happentimeEnd)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.le("happentime",sdf.parse(happentimeEnd));
            }
            String happenaddress = request.getParameter("happenaddress");
            if(StringUtils.isNotBlank(happenaddress)){
                cq.like("happenaddress","%"+happenaddress+"%");
            }
            String accidenttype = request.getParameter("accidenttype");
            if(StringUtils.isNotBlank(accidenttype)){
                cq.eq("accidenttype",accidenttype);
            }
            try{
                String deathnumBegin =request.getParameter("deathnum_begin");
                if(StringUtils.isNotBlank(deathnumBegin)){
                    cq.ge("deathnum", Integer.parseInt(deathnumBegin));
                }
            }catch(NumberFormatException e){
            }
            try{
                String deathnumEnd = request.getParameter("deathnum_end");
                if(StringUtils.isNotBlank(deathnumEnd)){
                    cq.le("deathnum",Integer.parseInt(deathnumEnd));
                }
            }catch(NumberFormatException e){}
            try{
                String heavywoundnumBegin =request.getParameter("heavywoundnum_begin");
                if(StringUtils.isNotBlank(heavywoundnumBegin)){
                    cq.ge("heavywoundnum",Integer.parseInt(heavywoundnumBegin));
                }
            }catch (NumberFormatException e){}
            try{
                String heavywoundnumEnd = request.getParameter("heavywoundnum_end");
                if(StringUtils.isNotBlank(heavywoundnumEnd)){
                    cq.le("heavywoundnum",Integer.parseInt(heavywoundnumEnd));
                }
            }catch (NumberFormatException e){}
            try{
                String minorwoundnumBegin = request.getParameter("minorwoundnum_begin");
                if(StringUtils.isNotBlank(minorwoundnumBegin)){
                    cq.ge("minorwoundnum",Integer.parseInt(minorwoundnumBegin));
                }
            }catch(NumberFormatException e){}
            try{
                String minorwoundnumEnd = request.getParameter("minorwoundnum_end");
                if(StringUtils.isNotBlank(minorwoundnumEnd)){
                    cq.le("minorwoundnum",Integer.parseInt(minorwoundnumEnd));
                }
            }catch(NumberFormatException e){}
            String directdamageBegin = request.getParameter("directdamage_begin");
            if(StringUtils.isNotBlank(directdamageBegin)){
                int index = directdamageBegin.indexOf(".");
                if(index > 0){
                    while(directdamageBegin.endsWith("0")){
                        directdamageBegin = directdamageBegin.substring(0,directdamageBegin.lastIndexOf("0"));
                    }
                }
                if(directdamageBegin.endsWith(".")){
                    directdamageBegin = directdamageBegin.substring(0,index);
                }
                cq.ge("directdamage",directdamageBegin);
            }
            String directdamageEnd = request.getParameter("directdamage_end");
            if(StringUtils.isNotBlank(directdamageEnd)){
                int index = directdamageEnd.indexOf(".");
                if(index > 0){
                    while(directdamageEnd.endsWith("0")){
                        directdamageEnd = directdamageEnd.substring(0,directdamageEnd.lastIndexOf("0"));
                    }
                }
                if(directdamageEnd.endsWith(".")){
                    directdamageEnd = directdamageEnd.substring(0,index);
                }
                cq.le("directdamage",directdamageEnd);
            }
            String consequentiallossBegin = request.getParameter("consequentialloss_begin");
            if(StringUtils.isNotBlank(consequentiallossBegin)){
                int index = consequentiallossBegin.indexOf(".");
                if(index > 0){
                    while(consequentiallossBegin.endsWith("0")){
                        consequentiallossBegin = consequentiallossBegin.substring(0,consequentiallossBegin.lastIndexOf("0"));
                    }
                }
                if(consequentiallossBegin.endsWith(".")){
                    consequentiallossBegin = consequentiallossBegin.substring(0,index);
                }
                cq.ge("consequentialloss",consequentiallossBegin);
            }
            String consequentiallossEnd =request.getParameter("consequentialloss_end");
            if(StringUtils.isNotBlank(consequentiallossEnd)){
                int index = consequentiallossEnd.indexOf(".");
                if(index > 0){
                    while(consequentiallossEnd.endsWith("0")){
                        consequentiallossEnd = consequentiallossEnd.substring(0,consequentiallossEnd.lastIndexOf("0"));
                    }
                }
                if(consequentiallossEnd.endsWith(".")){
                    consequentiallossEnd = consequentiallossEnd.substring(0,index);
                }
                cq.le("consequentialloss",consequentiallossEnd);
            }

		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
        cq.addOrder("createDate", SortDirection.desc);
		cq.add();
		this.tBAccidentService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除事故
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBAccidentEntity tBAccident, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBAccident = systemService.getEntity(TBAccidentEntity.class, tBAccident.getId());
		message = "事故删除成功";
		try{
			tBAccidentService.delete(tBAccident);
			systemService.addLog("事故\""+tBAccident.getId()+"\"删除成功", Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		}catch(Exception e){
			e.printStackTrace();
			message = "事故删除失败";
            systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除事故
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "事故删除成功";
		try{
			for(String id:ids.split(",")){
				TBAccidentEntity tBAccident = systemService.getEntity(TBAccidentEntity.class,id);
				tBAccidentService.delete(tBAccident);
				systemService.addLog("事故\""+tBAccident.getId()+"\"删除成功", Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "事故删除失败";
            systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


    @RequestMapping(params = "doAddFiels")
    @ResponseBody
    public AjaxJson doAddFiels( HttpServletRequest request,TSDocument document) {
        message = "事故添加成功";
        //移除删除的附件
        String delAttachmentId = request.getParameter("delAttachmentId");
        if(StringUtils.isNotBlank(delAttachmentId)){
            message = "事故修改成功";
        }
        // 文件标题
        String documentTitle = oConvertUtils.getString(request.getParameter("documentTitle"));
        AjaxJson j = new AjaxJson();

        String bussId = request.getParameter("bussId");
        TSTypegroup tsTypegroup=systemService.getTypeGroup("fieltype","文档分类");
        TSType tsType = systemService.getType("emergencyFile","附件", tsTypegroup);
        // 文件ID
        String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));
        if(document != null){
            if (StringUtil.isNotEmpty(fileKey)) {
                document.setId(fileKey);
                document = systemService.getEntity(TSDocument.class, fileKey);
                document.setDocumentTitle(documentTitle);

            }
            document.setStatus(1);
            document.setSubclassname(MyClassLoader.getPackPath(document));
            document.setCreatedate(DateUtils.gettimestamp());
            document.setTSType(tsType);
            document.setBusinessKey(bussId);
            UploadFile uploadFile = new UploadFile(request, document);
            uploadFile.setCusPath("files");
            uploadFile.setSwfpath("swfpath");
            Map<String, Object> attributes = new HashMap<String, Object>();
            document = systemService.uploadFile(uploadFile);
            systemService.addLog("事故报告上传成功",Globals.Log_Leavel_INFO,Globals.Log_Type_UPLOAD);
            attributes.put("url", document.getRealpath());
            attributes.put("fileKey", document.getId());
            attributes.put("name", document.getAttachmenttitle());
            attributes.put("viewhref", "commonController.do?objfileList&fileKey=" + document.getId());
            attributes.put("delurl", "commonController.do?delObjFile&fileKey=" + document.getId());
        }
        j.setMsg(message);
        return j;
    }
	/**
	 * 添加事故
	 * 
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBAccidentEntity tBAccident, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        String deptId = request.getParameter("deptName");
        TSDepart tsd = systemService.getEntity(TSDepart.class,deptId);
        tBAccident.setDept(tsd);
		message = "事故添加成功";
		try{
			tBAccidentService.save(tBAccident);
            String id = tBAccident.getId();
            Map<String,Object> retMap = new HashMap<String,Object>();
            retMap.put("id",id);
            j.setAttributes(retMap);
			systemService.addLog("事故\"" + id + "\"添加成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}catch(Exception e){
			e.printStackTrace();
			message = "事故添加失败";
            systemService.addLog("事故添加失败："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_INSERT);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新事故
	 * 
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBAccidentEntity tBAccident, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "事故更新成功";
		TBAccidentEntity t = tBAccidentService.get(TBAccidentEntity.class, tBAccident.getId());
        String deptId = request.getParameter("deptName");
        TSDepart tsd = systemService.getEntity(TSDepart.class,deptId);
        tBAccident.setDept(tsd);
		try {
            String delAttachmentId = request.getParameter("delAttachmentId");
            if(StringUtils.isNotBlank(delAttachmentId)){
                String ids [] = delAttachmentId.split(",");
                if(ids != null && ids.length >0){
                    for(String idTemp : ids){
                        this.systemService.updateBySqlString(" update t_s_document set  status = 0 where id = '"+idTemp+"' ");
                        systemService.addLog("文件\""+idTemp+"\"状态更新成功",Globals.Log_Leavel_INFO,Globals.Log_Type_UPDATE);
                    }
                }
            }

			MyBeanUtils.copyBeanNotNull2Bean(tBAccident, t);
			tBAccidentService.saveOrUpdate(t);
			systemService.addLog("事故\"" + t.getId() + "\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			message = "事故更新失败";
            systemService.addLog("事故更新失败："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_UPDATE);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 事故新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBAccidentEntity tBAccident, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBAccident.getId())) {
			tBAccident = tBAccidentService.getEntity(TBAccidentEntity.class, tBAccident.getId());
			req.setAttribute("tBAccidentPage", tBAccident);
		}
        //查询事故等级
        List<TBAccidentLevelEntity> levelEntityList = systemService.getList(TBAccidentLevelEntity.class);
        req.setAttribute("levelEntityList",levelEntityList);
		return new ModelAndView("com/sdzk/buss/web/accident/tBAccident-add");
	}
	/**
	 * 事故编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBAccidentEntity tBAccident, HttpServletRequest req) {
        String load = req.getParameter("load");
        req.setAttribute("load",load);
		if (StringUtil.isNotEmpty(tBAccident.getId())) {
			tBAccident = tBAccidentService.getEntity(TBAccidentEntity.class, tBAccident.getId());
			req.setAttribute("tBAccidentPage", tBAccident);
            List<String> list = this.systemService.findListbySql("select tsa.id FROM t_s_document as document , t_s_attachment as tsa where document.id = tsa.id and document.`status` = '1' and businesskey = '"+tBAccident.getId()+"'");
            List<TSAttachment> tsaList = new ArrayList<TSAttachment>();
            StringBuffer attachmentIds = new StringBuffer();
            if(list != null && list.size() >0){
                for(String idTemp : list){
                    //判断文件是否可用
                    TSAttachment tsAttachment = this.systemService.getEntity(TSAttachment.class,idTemp);
                    tsaList.add(tsAttachment);
                    if(StringUtils.isNotBlank(attachmentIds.toString())){
                        attachmentIds.append(",");
                    }
                    attachmentIds.append(tBAccident.getId());
                }
            }
            req.setAttribute("attachmentIds",attachmentIds);
            req.setAttribute("list",tsaList);
		}
        List<TBAccidentLevelEntity> levelEntityList = systemService.getList(TBAccidentLevelEntity.class);
        req.setAttribute("levelEntityList",levelEntityList);
        if(StringUtils.isNotBlank(load)){
            tBAccident.setAccidentTypeTemp(DicUtil.getTypeNameByCode("accidentCate", tBAccident.getAccidenttype()));
            return new ModelAndView("com/sdzk/buss/web/accident/tBAccident-detail");
        }else{
            return new ModelAndView("com/sdzk/buss/web/accident/tBAccident-update");
        }
		/*return new ModelAndView("com/sdzk/buss/web/accident/tBAccident-update");*/
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBAccidentController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBAccidentEntity tBAccident,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBAccidentEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAccident, request.getParameterMap());
		List<TBAccidentEntity> tBAccidents = this.tBAccidentService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"事故");
		modelMap.put(NormalExcelConstants.CLASS,TBAccidentEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("事故列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBAccidents);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBAccidentEntity tBAccident,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"事故");
    	modelMap.put(NormalExcelConstants.CLASS,TBAccidentEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("事故列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 获取上传文件对象
			MultipartFile file = entity.getValue();
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TBAccidentEntity> listTBAccidentEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TBAccidentEntity.class,params);
				for (TBAccidentEntity tBAccident : listTBAccidentEntitys) {
					tBAccidentService.save(tBAccident);
                    systemService.addLog("事故\""+tBAccident.getId()+"\"导入成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
				}
				j.setMsg("文件导入成功！");
                systemService.addLog(j.getMsg(),Globals.Log_Leavel_INFO,Globals.Log_Type_UPLOAD);
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
                systemService.addLog(j.getMsg()+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_UPLOAD);
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}
}
