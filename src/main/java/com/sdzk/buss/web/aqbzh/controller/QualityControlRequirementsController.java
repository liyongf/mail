/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年05月27日
 * 修改日期：2020年05月27日
 */
package com.sdzk.buss.web.aqbzh.controller;

import com.alibaba.fastjson.JSONObject;
import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlRequirements;
import com.sdzk.buss.web.aqbzh.service.IQualityControlRequirementsService;
import com.sdzk.buss.web.aqbzh.util.GeneralResponse;
import com.sdzk.buss.web.aqbzh.util.PageQuery;
import com.sdzk.buss.web.aqbzh.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description Controller
 * @author：Tony
 * @version：V1.0
 * @since：2020年05月27日
 */
@Controller
@RequestMapping("/qualityControlRequirements")
public class QualityControlRequirementsController {

    @Autowired
    private IQualityControlRequirementsService qualityControlRequirementsServiceImpl;
    @Autowired
    private SystemService systemService;

    /**
     * @Description 根据条件查找-分页
     */
    @RequestMapping(value = "/findByPage", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse findByPage(PageQuery pageQuery, QualityControlRequirements qualityControlRequirements) {
        return GeneralResponse.createBySuccess(qualityControlRequirementsServiceImpl.findByPage2(pageQuery, qualityControlRequirements));
    }

    /**
     * @Description 根据id查询
     */
    @RequestMapping(value = "/findById", method = {RequestMethod.GET})
    @ResponseBody
    public GeneralResponse<QualityControlRequirements> findById(Integer id) {
        return GeneralResponse.createBySuccess(qualityControlRequirementsServiceImpl.findById(id));
    }

    /**
     * @Description 添加数据
     */
    @RequestMapping(value = "/insert", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse insert(QualityControlRequirements qualityControlRequirements) {
        qualityControlRequirementsServiceImpl.insert(qualityControlRequirements);
        return GeneralResponse.createBySuccessMessage("添加成功 ");

    }

    /**
     * @Description 修改数据
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse update(QualityControlRequirements qualityControlRequirements) {
        qualityControlRequirementsServiceImpl.update(qualityControlRequirements);
        return GeneralResponse.createBySuccessMessage("修改成功 ");
    }

    /**
     * @Description 修改数据
     */
    @RequestMapping(value = "/updateByIds", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse updateByIds(String ids,Integer a3) {
        qualityControlRequirementsServiceImpl.updateByIds(ids,a3);
        return GeneralResponse.createBySuccessMessage("修改成功 ");
    }

    /**
     * @Description 修改数据
     */
    @RequestMapping(value = "/updateA4ByIds", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse updateA4ByIds(String ids, String a4) {
        qualityControlRequirementsServiceImpl.updateA4ByIds(ids,a4);
        return GeneralResponse.createBySuccessMessage("修改成功 ");
    }

    /**
     * @Description 修改数据
     */
    @RequestMapping(value = "/updateA5ByIds", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse updateA5ByIds(String ids, String a5) {
        qualityControlRequirementsServiceImpl.updateA5ByIds(ids,a5);
        return GeneralResponse.createBySuccessMessage("修改成功 ");
    }

    /**
     * @Description 根据id删除
     */
    @RequestMapping(value = "/deleteById", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse deleteById(Integer id, String ids) {
        if (StringUtil.isNotBlank(id)) {
            qualityControlRequirementsServiceImpl.deleteById(id);
        } else if (StringUtil.isNotBlank(ids)) {
            List<Integer> idList = StringUtil.splitToListInt(ids);
            for (Integer delId : idList) {
                qualityControlRequirementsServiceImpl.deleteById(delId);
            }
        }
        return GeneralResponse.createBySuccessMessage("删除成功 ");
    }

    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        String totalAssesId = request.getParameter("totalAssesId");
        if(StringUtil.isNotEmpty(totalAssesId)) {
            String sql = "select year,a1 from total_asses where id='" + totalAssesId + "'";
            List<Map<String, Object>> list = systemService.findForJdbc(sql);
            if (list.size() > 0) {
                request.setAttribute("year", list.get(0).get("year"));
                if(StringUtil.isNotBlank(list.get(0).get("a1"))){
                    Integer a1 = (Integer)list.get(0).get("a1");
                    request.setAttribute("jdTemp", a1-1);
                }else{
                    request.setAttribute("jdTemp",0);
                }
            }
        }else {
            request.setAttribute("jdTemp",0);
        }

        return new ModelAndView("com/sdzk/buss/web/quality/qualityControlRequirements1");
    }


    @RequestMapping(params = "goCheck")
    public ModelAndView goCheck(HttpServletRequest request) {
        String moduleIds="'"+request.getParameter("moduleIds")+"'";
        String totalAssesId=request.getParameter("totalAssesId");
        String status=request.getParameter("status");
        String theWeight=request.getParameter("theWeight");
        String score=request.getParameter("score");
        request.setAttribute("moduleIds", moduleIds);
        request.setAttribute("totalAssesId", totalAssesId);
        request.setAttribute("status", status);
        request.setAttribute("theWeight", theWeight);
        request.setAttribute("score", score);
        if(StringUtil.isNotEmpty(totalAssesId)){
            String sql="select a4 from total_asses where id='"+totalAssesId+"'";
            List<Map<String,Object>> list=systemService.findForJdbc(sql);
            if(list.size()>0){
                if(StringUtil.isNotBlank(list.get(0).get("a4"))){
                    request.setAttribute("mineCode", JSONObject.toJSONString(list.get(0).get("a4").toString()));
                }
            }
        }
        StringBuffer data3029 = new StringBuffer();
        String codeTemp = "";
        Map<String, String> map =  new HashMap<>();
        TSTypegroup group = systemService.getTypeGroup("bzhfxd","标准化风险点");
        if (group != null && group.getTSTypes() != null && group.getTSTypes().size() > 0) {
            data3029.append("[");
            for (TSType type : group.getTSTypes()) {
                String code = type.getTypecode();
                if(StringUtil.isBlank(codeTemp)){
                    codeTemp = code;
                }
                String codDesc = type.getTypename();
                data3029.append("{\"codDesc\":\""+codDesc+"\",\"code\":\""+code+"\"},");
            }
            data3029.substring(0,data3029.length() - 1);
            data3029.append("]");
        }else{
            data3029.append("[]");
        }
        request.setAttribute("data3029",data3029);
        request.setAttribute("codeTemp",codeTemp);
        return new ModelAndView("com/sdzk/buss/web/quality/qualityControlRequirements");
    }
}
