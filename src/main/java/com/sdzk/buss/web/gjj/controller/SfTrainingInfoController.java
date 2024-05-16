package com.sdzk.buss.web.gjj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.service.DataMapServiceI;
import com.sdzk.buss.web.gjj.entity.SfTrainingInfoEntity;
import com.sdzk.buss.web.gjj.service.SfTrainingInfoServiceI;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.LayDataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Title: Controller
 * @Description: 培训档案
 * @author gzy
 * @date 2023-10-31 15:59:43
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/sfTrainingInfoController")
public class SfTrainingInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SfTrainingInfoController.class);

	@Autowired
	private SfTrainingInfoServiceI sfTrainingInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
    private DataMapServiceI dataMapService;

	/**
	 * 培训档案列表 页面跳转
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
		Map<String, List<TSType>> allTypes = ResourceUtil.allTypes;
		request.setAttribute("trainingTypeList",allTypes.get("gjj_training_type"));
		return new ModelAndView("com/sdzk/buss/web/gjj/sfTrainingInfoList");
	}

	/**
	 * 培训档案列表
	 */
	@RequestMapping(params = "datagrid")
    @ResponseBody
	public LayDataGrid LayDataGrid(HttpServletRequest request){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String[]> obj = request.getParameterMap();
		Integer page =Integer.parseInt(obj.get("page")[0]) ;//页码
		Integer rows = Integer.parseInt(obj.get("limit")[0]);//每页行数
		CriteriaQuery cq = new CriteriaQuery(SfTrainingInfoEntity.class);
		//查询条件，如果页面没有，忽略该段代码，如果有，根据实际情况编写
		if(obj.get("searchParams") != null){
			String searchParams = obj.get("searchParams")[0];
			if(StringUtils.isNotEmpty(searchParams)){
				JSONObject json = JSON.parseObject(searchParams);
				String trainingTheme = json.getString("trainingTheme");//培训主题
				String trainingType = json.getString("trainingType");//培训类型
				String trainingDate = json.getString("trainingDate");//培训日期
				if(StringUtils.isNotEmpty(trainingTheme)){
					cq.like("trainingTheme","%" + trainingTheme + "%");
				}
				if(StringUtils.isNotEmpty(trainingType)){
					cq.eq("trainingType", trainingType);
				}
				if(StringUtils.isNotEmpty(trainingDate)){
					try {
						cq.eq("trainingDate", sdf.parse(trainingDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}
		cq.eq("isDelete", Constants.IS_DELETE_N);
        Map orderMap =  new LinkedHashMap();
        orderMap.put("createDate", SortDirection.desc);
        cq.setOrder(orderMap);
		cq.add();
		List countList =  systemService.getListByCriteriaQuery(cq,false);
		cq.setPageSize(rows);
		cq.setCurPage(page);
		cq.add();
		List<SfTrainingInfoEntity> list =  systemService.getListByCriteriaQuery(cq,true);
		systemService.getSession().clear();
		for (SfTrainingInfoEntity entity : list) {
			entity.setTrainingType(DicUtil.getTypeNameByCode("gjj_training_type",entity.getTrainingType()));
			entity.setTrainingDepart(ResourceUtil.departMapCache.get(entity.getTrainingDepart()));
			entity.setTrainingPerson(ResourceUtil.userMapCache.get(entity.getTrainingPerson()));
		}
        LayDataGrid lay = new LayDataGrid(countList.size(), list);
        return lay;
	}

    /**
     * 批量删除培训档案
    */
    @RequestMapping(params = "batchDel")
    @ResponseBody
    public AjaxJson batchDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        if(StringUtils.isNotBlank(ids)){
            String[] idArray = ids.split(",");
            for(String id : idArray){
                SfTrainingInfoEntity sfTrainingInfo = systemService.getEntity(SfTrainingInfoEntity.class,Integer.parseInt(id));
				if (Constants.GJJ_STATE_FLAG_0.equals(sfTrainingInfo.getStateFlag())){
					sfTrainingInfo.setIsDelete(Constants.IS_DELETE_Y);
					sfTrainingInfo.setStateFlag(Constants.GJJ_STATE_FLAG_3);//国家局上报标识
					systemService.updateEntitie(sfTrainingInfo);
				}else {
					sfTrainingInfo.setStateFlag(Constants.GJJ_STATE_FLAG_0);//国家局上报标识
					systemService.delete(sfTrainingInfo);
				}
//                sfTrainingInfo.setIsDelete(Constants.IS_DELETE_Y);
//                sfTrainingInfoService.saveOrUpdate(sfTrainingInfo);
            }
        }
        j.setMsg("删除成功");
        return j;
    }

	/**
	 * 添加培训档案
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SfTrainingInfoEntity sfTrainingInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(sfTrainingInfo.getId())) {
			message = "培训档案更新成功";
			SfTrainingInfoEntity t = sfTrainingInfoService.get(SfTrainingInfoEntity.class, sfTrainingInfo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(sfTrainingInfo, t);
				if (Constants.GJJ_STATE_FLAG_0.equals(t.getStateFlag())){
					t.setStateFlag(Constants.GJJ_STATE_FLAG_2);//国家局上报标识
				}
				sfTrainingInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "培训档案更新失败";
			}
		} else {
			message = "培训档案添加成功";
            sfTrainingInfo.setIsDelete(Constants.IS_DELETE_N);
			sfTrainingInfo.setStateFlag(Constants.GJJ_STATE_FLAG_1);
			sfTrainingInfoService.save(sfTrainingInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 培训档案列表页面跳转
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SfTrainingInfoEntity sfTrainingInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sfTrainingInfo.getId())) {
			sfTrainingInfo = sfTrainingInfoService.getEntity(SfTrainingInfoEntity.class, sfTrainingInfo.getId());
			req.setAttribute("sfTrainingInfoPage", sfTrainingInfo);
		}
		List<Map<String, Object>> allDepart = ResourceUtil.allDepart;
		req.setAttribute("allDepart",allDepart);
		List<Map<String, Object>> allUser = ResourceUtil.allUser;
		req.setAttribute("allUser",allUser);
		Map<String, List<TSType>> allTypes = ResourceUtil.allTypes;
		req.setAttribute("trainingTypeList",allTypes.get("gjj_training_type"));
		return new ModelAndView("com/sdzk/buss/web/gjj/sfTrainingInfo");
	}


}
