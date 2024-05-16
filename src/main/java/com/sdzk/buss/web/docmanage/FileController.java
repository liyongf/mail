package com.sdzk.buss.web.docmanage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.pojo.base.TSAttachment;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangchen
 * @version V1.0
 * @Title: Controller
 * @Description: 文档管理
 * @date 2023-10-31
 */
@Controller
@RequestMapping("/fileController")
public class FileController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FileController.class);

	@Autowired
	private SystemService systemService;


	/**
	 * 文件上传
	 * @param request
	 * @param attachment
	 * @return
	 */
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

	/**
	 * 附件下载
	 */
	@RequestMapping(params = "downloadFile")
	public void viewFile(HttpServletRequest request, HttpServletResponse response) {
		String fileId = request.getParameter("fileId");
		if (StringUtil.isNotEmpty(fileId)) {
			List<String> attachIdList = systemService.findListbySql("select id from t_s_attachment where id = '" + fileId + "' order by createdate desc ");
			if (attachIdList != null && attachIdList.size() > 0) {
				TSAttachment fileobj = systemService.get(TSAttachment.class, attachIdList.get(0));
				UploadFile uploadFile = new UploadFile(request, response);
				String path = fileobj.getRealpath();
				String extend = fileobj.getExtend();
				String attachmenttitle = fileobj.getAttachmenttitle();
				uploadFile.setExtend(extend);
				uploadFile.setTitleField(attachmenttitle);
				uploadFile.setRealPath(path);
				systemService.viewOrDownloadFile(uploadFile);
			}
		}
	}

	/**
	 * 附件预览
	 */
	@RequestMapping(params = "previewFile")
	public ModelAndView previewFile(HttpServletRequest request, HttpServletResponse response) {
		String fileId = request.getParameter("fileId");
		String subclassname = oConvertUtils.getString(request.getParameter("subclassname"), "org.jeecgframework.web.system.pojo.base.TSAttachment");
		String contentfield = oConvertUtils.getString(request.getParameter("contentfield"));
		if(StringUtil.isNotEmpty(fileId)){
			List<String> attachIdList = systemService.findListbySql("select id from t_s_attachment where id = '"+fileId+"'");
			if (attachIdList!=null&&attachIdList.size()==1){
				TSAttachment fileobj = systemService.get(TSAttachment.class,attachIdList.get(0));
				ReflectHelper reflectHelper = new ReflectHelper(fileobj);
				String extend = oConvertUtils.getString(reflectHelper.getMethodValue("extend"));
				if (FileUtils.isPicture(extend)) {
					String realpath = oConvertUtils.getString(reflectHelper.getMethodValue("realpath"));
					request.setAttribute("realpath", realpath);
					request.setAttribute("fileId", fileId);
					request.setAttribute("subclassname", subclassname);
					request.setAttribute("contentfield", contentfield);
					return new ModelAndView("common/upload/imageView");
				} else {
					String swfpath = oConvertUtils.getString(reflectHelper.getMethodValue("swfpath"));
					swfpath=swfpath.replace("\\","/");
					request.setAttribute("swfpath", swfpath);
					request.setAttribute("fileId", fileId);
					return new ModelAndView("common/upload/pdfView");
				}
			}
		}
		return new ModelAndView();
	}

	/**
	 * 转化pdf
	 */
	@RequestMapping(params = "preFilePdf")
	@ResponseBody
	public AjaxJson preFile(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String docManageId = request.getParameter("id");
		String basePath = request.getServletContext().getContextPath();
		String path = "";
		String extend = "";
		if (StringUtils.isBlank(docManageId)) {
			j.setMsg("参数不正确");
			j.setSuccess(false);
			return j;
		}

		List<String> attachIdList = systemService.findListbySql("select id from t_s_attachment where id = '" + docManageId + "' order by createdate desc ");
		if (attachIdList != null && attachIdList.size() > 0) {
			TSAttachment fileobj = systemService.get(TSAttachment.class, attachIdList.get(0));
			path = fileobj.getRealpath();
			extend = fileobj.getExtend();
			if(StringUtil.isNotEmpty(extend)){
				if (extend.equals("xls") || extend.equals("xlsx") || extend.equals("doc") || extend.equals("docx") || extend.equals("txt")
						||extend.toUpperCase().equals("JPG") ||extend.toUpperCase().equals("JPEG")||extend.toUpperCase().equals("PNG")) {
					path = path.replace("." + extend, ".pdf");
					extend = "pdf";
				}
			}

		}
		if (!"pdf".equals(extend)){
			j.setMsg("暂不支持." + extend+"格式文件预览");
			j.setSuccess(false);
			return j;
		}
		if(StringUtils.isNotBlank(basePath)){
			path = basePath.replace("/","")+"/"+path;
		}
		j.setObj(path);
		j.setMsg("请求成功");
		return j;
	}

}
