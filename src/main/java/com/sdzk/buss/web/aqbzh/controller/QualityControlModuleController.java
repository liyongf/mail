/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年05月27日
 * 修改日期：2020年05月27日
 */
package com.sdzk.buss.web.aqbzh.controller;

import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlModule;
import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlProject;
import com.sdzk.buss.web.aqbzh.service.IQualityControlModuleService;
import com.sdzk.buss.web.aqbzh.service.IQualityControlProjectService;
import com.sdzk.buss.web.aqbzh.util.GeneralResponse;
import com.sdzk.buss.web.aqbzh.util.PageQuery;
import com.sdzk.buss.web.aqbzh.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description Controller
 * @author：Tony
 * @version：V1.0
 * @since：2020年05月27日
 */
@Controller
@RequestMapping("/qualityControlModule")
public class QualityControlModuleController {

    @Autowired
    private IQualityControlModuleService qualityControlModuleServiceImpl;
    @Autowired
    private IQualityControlProjectService qualityControlProjectServiceImpl;

    /**
     * @Description 渲染表格
     */
    @RequestMapping(value = "/findByList",method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse findByList(String year ,String month,String duokuangCode) {
        return GeneralResponse.createBySuccess(qualityControlModuleServiceImpl.findByList(year, month,duokuangCode));
    }

    /**
     * @Description 根据条件查找-分页
     */

    @RequestMapping(value = "/findByPage",method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse findByPage(PageQuery pageQuery, QualityControlModule qualityControlModule) {
        return GeneralResponse.createBySuccess(qualityControlModuleServiceImpl.findByPage2(pageQuery, qualityControlModule));
    }

    /**
     * @Description 根据id查询
     */
    @RequestMapping(value = "/findById",method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse<QualityControlModule> findById(Integer id) {
        return GeneralResponse.createBySuccess(qualityControlModuleServiceImpl.findById(id));
    }

    /**
     * @Description 根据id查询
     */
    @RequestMapping(value = "/findByIdList",method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse<List> findByIdList(String idList) {
        List<Integer> str = StringUtil.splitToListInt(idList);
        return GeneralResponse.createBySuccess(qualityControlModuleServiceImpl.findByIdList(str));
    }

    /**
     * @Description 添加数据
     */
    @RequestMapping(value = "/insert",method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse insert(QualityControlModule qualityControlModule) {
        qualityControlModuleServiceImpl.insert(qualityControlModule);
        return GeneralResponse.createBySuccessMessage("添加成功 ");

    }

    /**
     * @Description 修改数据
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse update(QualityControlModule qualityControlModule) {
        qualityControlModuleServiceImpl.update(qualityControlModule);
        return GeneralResponse.createBySuccessMessage("修改成功 ");
    }

    /**
     * @Description 根据id删除 根据ids 批量删除 ids 逗号分隔
     */

    @RequestMapping(value = "/deleteById",method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse deleteById(Integer id) {
        QualityControlProject qualityControlProject = new QualityControlProject();
        qualityControlProject.setModuleId(id);
        PageQuery pageQuery = new PageQuery();
        QualityControlModule qualityControlModule = new QualityControlModule();
        qualityControlModule.setParentId(id);
        List<QualityControlModule> byPage1 = qualityControlModuleServiceImpl.findByPage(pageQuery, qualityControlModule);
        List<QualityControlProject> byPage = qualityControlProjectServiceImpl.findByPage(pageQuery, qualityControlProject);
        if (byPage.size() != 0 || byPage1.size() != 0) {
            return GeneralResponse.createByError("该模块下有数据不可以删除");
        } else {
            if (StringUtil.isNotBlank(id)) {
                qualityControlModuleServiceImpl.deleteById(id);
            }
            return GeneralResponse.createBySuccessMessage("删除成功");
        }
    }



    /**
     * @Description 跳转
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/quality/qualityControlModule");
    }


}
