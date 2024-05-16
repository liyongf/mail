package com.sdzk.buss.web.dangersource.controller;

import com.sdzk.buss.web.dangersource.entity.TBDocumentSourceEntity;
import com.sdzk.buss.web.dangersource.service.TBDocumentSourceServiceI;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**   
 * @Title: Controller
 * @Description: 文档来源分类
 * @author zhangdaihao
 * @date 2017-07-31 10:59:58
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBDocumentSourceController")
public class TBDocumentSourceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBDocumentSourceController.class);

	@Autowired
	private TBDocumentSourceServiceI tBDocumentSourceService;
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
	 * 文档来源分类列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/dangersource/tBDocumentSourceList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param
	 * @param
	 * @param
	 * @param
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TBDocumentSourceEntity tBDocumentSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBDocumentSourceEntity.class, dataGrid);
		/* 查询条件组装器 */
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDocumentSource, request.getParameterMap());
		this.tBDocumentSourceService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 文档来源分类列表，树形展示
	 * @param
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping(params = "docSourceGrid")
	@ResponseBody
	public List<TreeGrid> docSourceGrid(HttpServletRequest request,TreeGrid treegrid){

		CriteriaQuery cq = new CriteriaQuery(TBDocumentSourceEntity.class);
		String selfId = request.getParameter("selfId");
		if (selfId != null) {
			cq.notEq("id", selfId);
		}
		if (treegrid.getId() != null) {
			cq.eq("parentDocSource.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("parentDocSource");
		}
		cq.notEq("isDelete","1");
		cq.add();

		List<TBDocumentSourceEntity> docSourceList = systemService.getListByCriteriaQuery(cq,false);
        System.out.println(docSourceList.size());

		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setIdField("id");
		treeGridModel.setTextField("docSourceName");
		treeGridModel.setParentText("parentDocSource.docSourceName");
		treeGridModel.setParentId("parentDocSource.id");
		treeGridModel.setChildList("childDocSources");
		treeGridModel.setSrc("");

		treeGrids = systemService.treegrid(docSourceList, treeGridModel);

		MutiLangUtil.setMutiTree(treeGrids);
		return treeGrids;
	}



	/**
	 * 删除文档来源分类
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TBDocumentSourceEntity tBDocumentSource, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();

		message = "文档来源分类删除成功";
		TBDocumentSourceEntity t = tBDocumentSourceService.get(TBDocumentSourceEntity.class, tBDocumentSource.getId());
		t.setIsDelete("1");
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBDocumentSource, t);
			tBDocumentSourceService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "文档来源分类删除失败";
		}
		/*tBDocumentSource = systemService.getEntity(TBDocumentSourceEntity.class, tBDocumentSource.getId());
		message = "文档来源分类删除成功";
		tBDocumentSourceService.delete(tBDocumentSource);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);*/
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加文档来源分类
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(TBDocumentSourceEntity tBDocumentSource, HttpServletRequest request) {
		if(tBDocumentSource.getParentDocSource() != null && "".equals(tBDocumentSource.getParentDocSource().getId())){
			tBDocumentSource.setParentDocSource(null);
		}

		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(tBDocumentSource.getId())) {
			message = "文档来源分类更新成功";
			TBDocumentSourceEntity t = tBDocumentSourceService.get(TBDocumentSourceEntity.class, tBDocumentSource.getId());
			try {
				//t.setBelongMine(belongMine);
				MyBeanUtils.copyBeanNotNull2Bean(tBDocumentSource, t);
				tBDocumentSourceService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "文档来源分类更新失败";
			}
		} else {
			message = "文档来源分类添加成功";
			//tBDocumentSource.setBelongMine(belongMine);
			tBDocumentSourceService.save(tBDocumentSource);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 文档来源分类列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TBDocumentSourceEntity tBDocumentSource, HttpServletRequest req) {
		String parentDocSourceId = req.getParameter("parentDocSource.id");
		if(StringUtils.isNotBlank(parentDocSourceId)){
			req.setAttribute("pid", parentDocSourceId);
		}

		List<TBDocumentSourceEntity> docSourceList = systemService.getList(TBDocumentSourceEntity.class);
		req.setAttribute("docSourceList", docSourceList);

		if (StringUtil.isNotEmpty(tBDocumentSource.getId())) {
			tBDocumentSource = tBDocumentSourceService.getEntity(TBDocumentSourceEntity.class, tBDocumentSource.getId());
			req.setAttribute("tBDocumentSourcePage", tBDocumentSource);
		}
		return new ModelAndView("com/sdzk/buss/web/dangersource/tBDocumentSource");
	}

	/**
	 * 父级分类下拉菜单
	 */
	@RequestMapping(params = "setParentDocSource")
	@ResponseBody
	public List<ComboTree> setParentDocSource(HttpServletRequest request,
										ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TBDocumentSourceEntity.class);
		if (null != request.getParameter("selfId")) {
			cq.notEq("id", request.getParameter("selfId"));
		}
		if (comboTree.getId() != null) {
			cq.eq("parentDocSource.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("parentDocSource");
		}
		cq.notEq("isDelete","1");
		cq.add();
		List<TBDocumentSourceEntity> functionList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id","docSourceName", "childDocSources");
		comboTrees = systemService.ComboTree(functionList, comboTreeModel,null, false);
		MutiLangUtil.setMutiTree(comboTrees);
		return comboTrees;
	}

	/**
	 * 文档来源分类树
	 *
	 * @param
	 * @param request
	 * @param comboTree
	 * @return
	 */
	@RequestMapping(params = "docSourceComboTree")
	@ResponseBody
	public List<ComboTree> docSourceComboTree(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TBDocumentSourceEntity.class);
		if (comboTree.getId() != null) {
			cq.eq("parentDocSource.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("parentDocSource");
		}
		/*cq.add(Restrictions.or(Restrictions.isNull("belongMine"),Restrictions.eq("belongMine",""),Restrictions.eq("belongMine", belongMine)));*/
		cq.notEq("isDelete","1");
		cq.add();
		List<TBDocumentSourceEntity> functionList = systemService.getListByCriteriaQuery(cq, false);

		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id",
				"docSourceName", "childDocSources");
		comboTrees = systemService.ComboTree(functionList, comboTreeModel,
				null, false);

		MutiLangUtil.setMutiTree(comboTrees);
		return comboTrees;
	}


    @RequestMapping(params = "clauseDocManageList")
    public ModelAndView clauseDocManageList(HttpServletRequest req) {
        return new ModelAndView("com/sdzk/buss/web/dangersource/clauseDocManageList");
    }

}
