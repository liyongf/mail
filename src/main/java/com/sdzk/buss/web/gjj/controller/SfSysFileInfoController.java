package com.sdzk.buss.web.gjj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.service.DataMapServiceI;
import com.sdzk.buss.web.gjj.entity.SfSysFileInfoEntity;
import com.sdzk.buss.web.gjj.service.SfSysFileInfoServiceI;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.LayDataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
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
 * @Description: 双重预防工作制度
 * @author gzy
 * @date 2023-10-31 13:32:39
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/sfSysFileInfoController")
public class SfSysFileInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SfSysFileInfoController.class);

	@Autowired
	private SfSysFileInfoServiceI sfSysFileInfoService;
	@Autowired
	private SystemService systemService;
	@Autowired
    private DataMapServiceI dataMapService;

	/**
	 * 双重预防工作制度列表 页面跳转
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
		return new ModelAndView("com/sdzk/buss/web/gjj/sfSysFileInfoList");
	}

	/**
	 * 双重预防工作制度列表
	 */
	@RequestMapping(params = "datagrid")
    @ResponseBody
	public LayDataGrid LayDataGrid(HttpServletRequest request){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String[]> obj = request.getParameterMap();
		Integer page =Integer.parseInt(obj.get("page")[0]) ;//页码
		Integer rows = Integer.parseInt(obj.get("limit")[0]);//每页行数
		CriteriaQuery cq = new CriteriaQuery(SfSysFileInfoEntity.class);
		//查询条件，如果页面没有，忽略该段代码，如果有，根据实际情况编写
		if(obj.get("searchParams") != null){
			String searchParams = obj.get("searchParams")[0];
			if(StringUtils.isNotEmpty(searchParams)){
				JSONObject json = JSON.parseObject(searchParams);
				String sysName = json.getString("sysName");//文本
				String upDate = json.getString("upDate");//日期
				if(StringUtils.isNotEmpty(sysName)){
					cq.like("sysName","%" + sysName + "%");
				}
				if(StringUtils.isNotEmpty(upDate)){
					try {
						cq.eq("upDate", sdf.parse(upDate));
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
		List<SfSysFileInfoEntity> list =  systemService.getListByCriteriaQuery(cq,true);
        LayDataGrid lay = new LayDataGrid(countList.size(), list);
        return lay;
	}

    /**
     * 批量删除双重预防工作制度
    */
    @RequestMapping(params = "batchDel")
    @ResponseBody
    public AjaxJson batchDel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        if(StringUtils.isNotBlank(ids)){
            String[] idArray = ids.split(",");
            for(String id : idArray){
                SfSysFileInfoEntity sfSysFileInfo = systemService.getEntity(SfSysFileInfoEntity.class,Integer.parseInt(id));
				if (Constants.GJJ_STATE_FLAG_0.equals(sfSysFileInfo.getStateFlag())){
					sfSysFileInfo.setIsDelete(Constants.IS_DELETE_Y);
					sfSysFileInfo.setStateFlag(Constants.GJJ_STATE_FLAG_3);//国家局上报标识
					systemService.updateEntitie(sfSysFileInfo);
				}else {
					sfSysFileInfo.setStateFlag(Constants.GJJ_STATE_FLAG_0);//国家局上报标识
					systemService.delete(sfSysFileInfo);
				}
//				sfSysFileInfo.setIsDelete(Constants.IS_DELETE_Y);
//                sfSysFileInfoService.saveOrUpdate(sfSysFileInfo);
            }
        }
        j.setMsg("删除成功");
        return j;
    }

	/**
	 * 添加双重预防工作制度
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(SfSysFileInfoEntity sfSysFileInfo, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(sfSysFileInfo.getId())) {
			message = "双重预防工作制度更新成功";
			SfSysFileInfoEntity t = sfSysFileInfoService.get(SfSysFileInfoEntity.class, sfSysFileInfo.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(sfSysFileInfo, t);
				if (Constants.GJJ_STATE_FLAG_0.equals(t.getStateFlag())){
					t.setStateFlag(Constants.GJJ_STATE_FLAG_2);//国家局上报标识
				}
				sfSysFileInfoService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "双重预防工作制度更新失败";
			}
		} else {
			message = "双重预防工作制度添加成功";
            sfSysFileInfo.setIsDelete(Constants.IS_DELETE_N);
			sfSysFileInfo.setStateFlag(Constants.GJJ_STATE_FLAG_1);
			sfSysFileInfoService.save(sfSysFileInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 双重预防工作制度列表页面跳转
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(SfSysFileInfoEntity sfSysFileInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(sfSysFileInfo.getId())) {
			sfSysFileInfo = sfSysFileInfoService.getEntity(SfSysFileInfoEntity.class, sfSysFileInfo.getId());
			req.setAttribute("sfSysFileInfoPage", sfSysFileInfo);
		}
		return new ModelAndView("com/sdzk/buss/web/gjj/sfSysFileInfo");
	}

}
