package com.sdzk.sys.hidedata.controller;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.sys.childwindow.controller.TSChildWindowsController;
import com.sdzk.sys.hidedata.entity.TBHideDataEntity;
import com.sdzk.sys.hidedata.service.HideDataServiceI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  这是隐藏数据的控制方法
 *  可以用来隐藏风险、隐患、三违
 *  目前，这里仅用来隐藏隐患
 * */
@Controller
@RequestMapping("/tBHideDataController")
public class HideDataController extends BaseController{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TSChildWindowsController.class);

    @Autowired
    private SystemService systemService;
    @Autowired
    private HideDataServiceI hideDataService;


    /**
     * 个性化配置    隐藏数据页面
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
        /**查询整个隐藏数据页面配置表(t_b_hide_data)，看看哪些显示哪些隐藏，并以 jsonarray 格式返回*/
        List<TBHideDataEntity> hideDataEntityList = systemService.getList(TBHideDataEntity.class);
        JSONArray ja = new JSONArray();
        for (TBHideDataEntity e : hideDataEntityList){
            JSONObject jo = new JSONObject();
            jo = e.entityToString();

            ja.add(jo);
        }
        /**获取 typegroupecode 为 hiddenLevel 的数据字典*/
        JSONArray hiddenLevelArray = hideDataService.getDicArray(Constants.TYPE_GROUP_CODE.HIDDEN_LEVEL);
        JSONArray riskLevelArray = hideDataService.getDicArray(Constants.TYPE_GROUP_CODE.RISK_LEVEL);
        JSONArray threeVioLevelArray = hideDataService.getDicArray(Constants.TYPE_GROUP_CODE.VIO_LEVEL);


        request.setAttribute("ja", ja);
        request.setAttribute("hiddenLevelList", hiddenLevelArray);
        request.setAttribute("riskLevelList", riskLevelArray);
        request.setAttribute("threeVioLevelList", threeVioLevelArray);
        return new ModelAndView("com/sdzk/sys/hidedata/tBHideDataList");
    }


    /**
     *  隐藏所选等级的所有的隐患
     * */
    @RequestMapping(params = "hideHiddenDangerByLevel")
    @ResponseBody
    public AjaxJson hideHiddenDangerByLevel(HttpServletResponse response, HttpServletRequest request){
        return hideByLevel(request, Constants.TYPE_GROUP_CODE.HIDDEN_LEVEL);
    }

    /**
     * 隐藏三违数据
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(params = "hideThreeVioByLevel")
    @ResponseBody
    public AjaxJson hideThreeVioByLevel(HttpServletResponse response, HttpServletRequest request){
        return hideByLevel(request, Constants.TYPE_GROUP_CODE.VIO_LEVEL);
    }

    /**
     * 隐藏风险数据
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(params = "hideRiskByLevel")
    @ResponseBody
    public AjaxJson hideRiskByLevel(HttpServletResponse response, HttpServletRequest request){
        return hideByLevel(request, Constants.TYPE_GROUP_CODE.RISK_LEVEL);
    }

    private AjaxJson hideByLevel (HttpServletRequest request, String typeGroupCode){
        AjaxJson jo = new AjaxJson();

        String id = request.getParameter("id");
        String isHide = request.getParameter("isHide");

        String sql = "update t_s_type set is_hide = ? where id = ?";
        try {
            systemService.executeSql(sql, isHide, id);
            systemService.refleshTypesCach(typeGroupCode);
            jo.setSuccess(true);
        } catch (Exception e) {
            jo.setSuccess(false);
        }
        return jo;
    }


}
