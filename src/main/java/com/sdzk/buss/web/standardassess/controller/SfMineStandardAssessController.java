package com.sdzk.buss.web.standardassess.controller;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.standardassess.entity.SfMineStandardAssessEntity;
import com.sdzk.buss.web.standardassess.entity.SfMineStandardAssessScoreEntity;
import com.sdzk.buss.web.standardassess.service.SfMineStandardAssessServiceI;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**   
 * @Title: Controller  
 * @Description: 安全生产标准化
 * @author onlineGenerator
 * @date 2017-04-22 09:11:14
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/sfMineStandardAssessController")
public class SfMineStandardAssessController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SfMineStandardAssessController.class);

	@Autowired
	private SfMineStandardAssessServiceI sfMineStandardAssessService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;

    /**
     * 煤矿考核自评汇总
     *
     * @return
     */
    @RequestMapping(params = "sfMineStandardAssessScoreList")
    public ModelAndView sfMineStandardAssessScoreList(HttpServletRequest request) {
        //煤矿类型： 1：井工煤矿   2：露天煤矿
        String mineType = request.getParameter("mineType");
        Map<String,String> paraMap = new HashMap<String,String>();
        paraMap.put("mineType", mineType);
        return new ModelAndView("com/sdzk/buss/web/standardassess/sfMineStandardAssessScoreList",paraMap);
    }

    /**
     * 矿级编辑上报考核数据 列表
     *
     * @return
     * @throws ParseException
     */
    @RequestMapping(params = "goMineAssessList")
    public ModelAndView goMineAssessList(SfMineStandardAssessEntity sfMineStandardAssess, HttpServletRequest req) throws ParseException {
        String mineType = req.getParameter("mineType");
        String ssasMonth = req.getParameter("ssasMonth");
        Map<String,String> paraMap = new HashMap<String,String>();
        paraMap.put("mineType", mineType);
        paraMap.put("ssasMonth", ssasMonth);
        String id = req.getParameter("id");
        SfMineStandardAssessScoreEntity entity = systemService.getEntity(SfMineStandardAssessScoreEntity.class,id);
        req.setAttribute("entity",entity);
        String load = req.getParameter("load");
        req.setAttribute("load",load);
        return new ModelAndView("com/sdzk/buss/web/standardassess/mineAssessList",paraMap);
    }

    /**
     * 查询SfMineStandardAssessScore信息
     *
     * @return
     */
    @RequestMapping(params = "getSfMineStandardAssessScore")
    @ResponseBody
    public AjaxJson getSfMineStandardAssessScore(HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        SfMineStandardAssessScoreEntity entity = systemService.getEntity(SfMineStandardAssessScoreEntity.class, request.getParameter("id"));
        j.setObj(entity);
        return j;
    }

    /**
     * 矿级 月份考核详情
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "mineAssessListDatagrid")
    public void mineAssessListDatagrid(SfMineStandardAssessEntity sfMineStandardAssess, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(SfMineStandardAssessEntity.class, dataGrid);
        //查询条件组装器
        String mineType = request.getParameter("mineType");
        String ssasMonth = request.getParameter("ssasMonth");
        try{
            //自定义追加查询条件
            cq.eq("ssaMineType", mineType);
            cq.eq("ssaMonth",ssasMonth);
            //查询已提交数据
            cq.eq("ssaCurrentStatus", Constants.SF_MINE_STANDARD_ASSESS_STATUS_2);
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.sfMineStandardAssessService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

	/**
	 * 安全生产标准化列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		String mineType = request.getParameter("mineType");
		String assessType = request.getParameter("assessType");
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("mineType", mineType);
		paraMap.put("assessType", assessType);
		return new ModelAndView("com/sdzk/buss/web/standardassess/"+assessType+"_sfMineStandardAssessList",paraMap);
	}

    /**
     * 煤矿考核自评汇总 提交
     *
     * @return
     */
    @RequestMapping(params = "doCommitMineStandarAssess")
    @ResponseBody
    public AjaxJson doCommitMineStandarAssess(SfMineStandardAssessScoreEntity entity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        entity = systemService.getEntity(SfMineStandardAssessScoreEntity.class, entity.getId());
        message = "煤矿考核自评汇总提交成功";
        try{
            entity.setSsasCurrentStatus(Constants.SF_MINE_STANDARD_ASSESS_SCORE_SSASCURRENTSTATUS_COMMIT);
            systemService.saveOrUpdate(entity);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        }catch(Exception e){
            e.printStackTrace();
            message = "煤矿考核自评汇总提交失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 煤矿考核自评汇总 数据查询
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "sfMineStandardAssessScoreDatagrid")
    public void sfMineStandardAssessScoreDatagrid(SfMineStandardAssessScoreEntity sfMineStandardAssessScoreEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(SfMineStandardAssessScoreEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, sfMineStandardAssessScoreEntity, request.getParameterMap());
        String mineType = request.getParameter("mineType");
        String queryHandleStatus = request.getParameter("queryHandleStatus");
        try{
            //自定义追加查询条件
            cq.eq("ssasMineType",mineType);
            if("true".equals(queryHandleStatus)){
                cq.eq("ssasCurrentStatus", Constants.SF_MINE_STANDARD_ASSESS_STATUS_2);
                cq.eq("ssasCurrentStatus", Constants.SF_MINE_STANDARD_ASSESS_STATUS_1);
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.sfMineStandardAssessService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(SfMineStandardAssessEntity sfMineStandardAssess, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(SfMineStandardAssessEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, sfMineStandardAssess, request.getParameterMap());
		String mineType = request.getParameter("mineType");
		String assessType = request.getParameter("assessType");
		try{
		//自定义追加查询条件
			cq.eq("ssaMineType", mineType);
			cq.eq("ssaAssessType", assessType);
			String queryHandleStatus = request.getParameter("queryHandleStatus");
			if("true".equals(queryHandleStatus)){
				cq.eq("ssaCurrentStatus", Constants.SF_MINE_STANDARD_ASSESS_STATUS_2);
			}else{
				cq.eq("ssaCurrentStatus", Constants.SF_MINE_STANDARD_ASSESS_STATUS_1);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.sfMineStandardAssessService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除安全生产标准化
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(SfMineStandardAssessEntity sfMineStandardAssess, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		sfMineStandardAssess = systemService.getEntity(SfMineStandardAssessEntity.class, sfMineStandardAssess.getId());
		message = "安全生产标准化删除成功";
		try{
			sfMineStandardAssessService.delete(sfMineStandardAssess);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "安全生产标准化删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除安全生产标准化
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "安全生产标准化删除成功";
		try{
			for(String id:ids.split(",")){
				SfMineStandardAssessEntity sfMineStandardAssess = systemService.getEntity(SfMineStandardAssessEntity.class,
				id
				);
				sfMineStandardAssessService.delete(sfMineStandardAssess);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "安全生产标准化删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	private boolean assessExist(SfMineStandardAssessEntity sfMineStandardAssess){
		boolean result = true;
		CriteriaQuery cq = new CriteriaQuery(SfMineStandardAssessEntity.class);
		cq.add(Restrictions.eq("ssaMonth",sfMineStandardAssess.getSsaMonth()));
		cq.add(Restrictions.eq("ssaMineType",sfMineStandardAssess.getSsaMineType()));
		cq.add(Restrictions.eq("ssaAssessType",sfMineStandardAssess.getSsaAssessType()));
		List<SfMineStandardAssessEntity> assessList = systemService.getListByCriteriaQuery(cq, false);
        if (assessList == null || assessList.size() == 0 || assessList.get(0).getId().equals(sfMineStandardAssess.getId())){
			result = false;
		}
		return result;
	}
	/**
	 * 验证该月份安全生产标准化是否已存在
	 */
	@RequestMapping(params = "assessExist")
	@ResponseBody
	public Map<String, String> assessExist(SfMineStandardAssessEntity sfMineStandardAssess, HttpServletRequest request){
		Map<String, String> result = new HashedMap();
		sfMineStandardAssess.setSsaMonth(request.getParameter("param"));
		if (assessExist(sfMineStandardAssess)){
			result.put("status", "n");
			result.put("info","此月份评估已存在！");
		} else {
			result.put("status", "y");
			result.put("info","通过信息验证！");
		}
		return result;
	}

	/**
	 * 添加安全生产标准化
	 * 
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(SfMineStandardAssessEntity sfMineStandardAssess, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "保存成功";
        Map<String,Object> attrMap = new HashMap<String,Object>();
        try{
            if (this.assessExist(sfMineStandardAssess)){
                j.setMsg("此月份已评估，请执行编辑操作。");
                j.setSuccess(false);
                return j;
            }
            if(StringUtil.isNotEmpty(sfMineStandardAssess.getId())){//更新
                SfMineStandardAssessEntity t = sfMineStandardAssessService.get(SfMineStandardAssessEntity.class, sfMineStandardAssess.getId());
                MyBeanUtils.copyBeanNotNull2Bean(sfMineStandardAssess, t);
                t.setSsaScoreDetail(stringToClob(JSONHelper.map2json(JSONHelper.getParameterMap(request))));
                sfMineStandardAssessService.saveOrUpdate(t);
                systemService.addLog("安全生产标准化"+sfMineStandardAssess.getId()+"更新成功", Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            }else{//新增
                sfMineStandardAssess.setSsaScoreDetail(stringToClob(JSONHelper.map2json(JSONHelper.getParameterMap(request))));
                sfMineStandardAssessService.save(sfMineStandardAssess);
                systemService.addLog("安全生产标准化"+sfMineStandardAssess.getId()+"添加成功", Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            }
            //待上报状态时加入汇总表
            if (Constants.SF_MINE_STANDARD_ASSESS_STATUS_2.equals(sfMineStandardAssess.getSsaCurrentStatus())) {
                //根据权值计算总得分
                String month = sfMineStandardAssess.getSsaMonth();
                String mineType = sfMineStandardAssess.getSsaMineType();
                CriteriaQuery cq = new CriteriaQuery(SfMineStandardAssessEntity.class);
                cq.eq("ssaMonth",month);
                cq.eq("ssaMineType",mineType);
                cq.eq("ssaCurrentStatus", Constants.SF_MINE_STANDARD_ASSESS_STATUS_2);
                cq.add();
                List<SfMineStandardAssessEntity> assessList = systemService.getListByCriteriaQuery(cq, false);
                BigDecimal sum =  sfMineStandardAssess.getSsaSumScore().multiply(new BigDecimal( Constants.SSA_ASSESS_WEIGHT.get(sfMineStandardAssess.getSsaAssessType())));
                for (SfMineStandardAssessEntity entity : assessList) {
                    if (!entity.getId().equals(sfMineStandardAssess.getId())) {
                        sum = sum.add(entity.getSsaSumScore().multiply(new BigDecimal( Constants.SSA_ASSESS_WEIGHT.get(entity.getSsaAssessType()))));
                    }
                }
                //更新或保存汇总记录
                SfMineStandardAssessScoreEntity scoreEntity = null;
                CriteriaQuery scoreCQ = new CriteriaQuery(SfMineStandardAssessScoreEntity.class);
                scoreCQ.add(Restrictions.eq("ssasMonth",month));
                scoreCQ.add(Restrictions.eq("ssasMineType",mineType));
                List<SfMineStandardAssessScoreEntity> scoreList = systemService.getListByCriteriaQuery(scoreCQ, false);
                //TODO 等级判断预留
                if (scoreList != null && scoreList.size() > 0) {
                    scoreEntity = scoreList.get(0);
                    scoreEntity.setSsasSumScore(sum);
                    systemService.updateEntitie(scoreEntity);
                } else {
                    scoreEntity = new SfMineStandardAssessScoreEntity();
                    scoreEntity.setSsasMonth(sfMineStandardAssess.getSsaMonth());
                    scoreEntity.setSsasSumScore(sum);
                    scoreEntity.setSsasCurrentStatus(Constants.SF_MINE_STANDARD_ASSESS_SCORE_STATUS_1);
                    scoreEntity.setSsasMineType(mineType);
                    systemService.save(scoreEntity);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "安全生产标准化添加失败";
            throw new BusinessException(e.getMessage());
        }
        attrMap.put("assessId", sfMineStandardAssess.getId());
        j.setAttributes(attrMap);
        j.setMsg(message);
        return j;
	}
	/**
	 * 更新安全生产标准化
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(SfMineStandardAssessEntity sfMineStandardAssess, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "安全生产标准化更新成功";
		SfMineStandardAssessEntity t = sfMineStandardAssessService.get(SfMineStandardAssessEntity.class, sfMineStandardAssess.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(sfMineStandardAssess, t);
			sfMineStandardAssessService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "安全生产标准化更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 安全生产标准化新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(SfMineStandardAssessEntity sfMineStandardAssess, HttpServletRequest req) throws Exception{
		String mineType = req.getParameter("mineType");
		String assessType = req.getParameter("assessType");
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("mineType", mineType);
		paraMap.put("assessType", assessType);
		if (StringUtil.isNotEmpty(sfMineStandardAssess.getId())) {
			sfMineStandardAssess = sfMineStandardAssessService.getEntity(SfMineStandardAssessEntity.class, sfMineStandardAssess.getId());
		}
        /**
         * 获取当前时间传到前台
         * */
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        String nowDate = dateFormat.format(currentDate);
        sfMineStandardAssess.setSsaMonthDate(DateUtils.parseDate(nowDate, "yyyy-MM"));

        req.setAttribute("sfMineStandardAssessPage", sfMineStandardAssess);
		return new ModelAndView("com/sdzk/buss/web/standardassess/"+assessType+"_sfMineStandardAssess",paraMap);
	}

	/**
	 * 安全生产标准化编辑页面跳转
	 * 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(SfMineStandardAssessEntity sfMineStandardAssess, HttpServletRequest req) throws ParseException {
		String mineType = req.getParameter("mineType");
		String assessType = req.getParameter("assessType");
		Map<String,String> paraMap = new HashMap<String,String>();
		paraMap.put("mineType", mineType);
		paraMap.put("assessType", assessType);
		if (StringUtil.isNotEmpty(sfMineStandardAssess.getId())) {
			sfMineStandardAssess = sfMineStandardAssessService.getEntity(SfMineStandardAssessEntity.class, sfMineStandardAssess.getId());
			if(sfMineStandardAssess.getSsaMonth()!=null){
				sfMineStandardAssess.setSsaMonthDate(DateUtils.parseDate(sfMineStandardAssess.getSsaMonth(), "yyyy-MM"));
			}

			req.setAttribute("sfMineStandardAssessPage", sfMineStandardAssess);
			try {
				req.setAttribute("scoreDetail", JSONHelper.jsonstr2json(sfMineStandardAssess.getSsaScoreDetail().getSubString(1, (int) sfMineStandardAssess.getSsaScoreDetail().length())));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        String from = req.getParameter("from");
        req.setAttribute("from",from);
        String masterId = req.getParameter("masterId");
        req.setAttribute("masterId",masterId);
        String load = req.getParameter("load");
        req.setAttribute("load",load);
		return new ModelAndView("com/sdzk/buss/web/standardassess/"+assessType+"_sfMineStandardAssess",paraMap);
	}

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","sfMineStandardAssessController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(SfMineStandardAssessEntity sfMineStandardAssess, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"安全生产标准化");
    	modelMap.put(NormalExcelConstants.CLASS,SfMineStandardAssessEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("安全生产标准化列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		SfMineStandardAssessEntity task = sfMineStandardAssessService.get(SfMineStandardAssessEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody SfMineStandardAssessEntity sfMineStandardAssess, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<SfMineStandardAssessEntity>> failures = validator.validate(sfMineStandardAssess);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			sfMineStandardAssessService.save(sfMineStandardAssess);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = sfMineStandardAssess.getId();
		URI uri = uriBuilder.path("/rest/sfMineStandardAssessController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody SfMineStandardAssessEntity sfMineStandardAssess) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<SfMineStandardAssessEntity>> failures = validator.validate(sfMineStandardAssess);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			sfMineStandardAssessService.saveOrUpdate(sfMineStandardAssess);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		sfMineStandardAssessService.deleteEntityById(SfMineStandardAssessEntity.class, id);
	}
	
	// String类型转换成Clob类型  
	 private Clob stringToClob(String str) {
        if (null == str)
         return null;
        else {
            try {
                 Clob c = new javax.sql.rowset.serial.SerialClob(str
                         .toCharArray());
                 return c;
             } catch (Exception e) {
                 return null;
            }
        }
    }

	@RequestMapping(params = "checkConfig")
	public ModelAndView checkConfig(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/assessmentscore/checkConfig");
	}
}
