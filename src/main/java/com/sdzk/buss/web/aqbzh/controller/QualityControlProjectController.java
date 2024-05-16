/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年05月27日
 * 修改日期：2020年05月27日
 */
package com.sdzk.buss.web.aqbzh.controller;


import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlProject;
import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlRequirements;
import com.sdzk.buss.web.aqbzh.service.IQualityControlProjectService;
import com.sdzk.buss.web.aqbzh.service.IQualityControlRequirementsService;
import com.sdzk.buss.web.aqbzh.util.GeneralResponse;
import com.sdzk.buss.web.aqbzh.util.PageQuery;
import com.sdzk.buss.web.aqbzh.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description Controller
 * @author：Tony
 * @version：V1.0
 * @since：2020年05月27日
 */
@Controller
@RequestMapping("/qualityControlProject")
public class QualityControlProjectController {

    @Autowired
    private IQualityControlProjectService qualityControlProjectServiceImpl;
    @Autowired
    private IQualityControlRequirementsService qualityControlRequirementsServiceImpl;

    /**
     * @Description 渲染表格
     */
    @RequestMapping(value = "/findByList",method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse findByList(String moduleId, String moduleIds, Integer status, String url, Integer totalAssesId, String score, String theWeight) {
        return GeneralResponse.createBySuccess(qualityControlProjectServiceImpl.findByList(moduleId,moduleIds, status, url, totalAssesId, score, theWeight));
    }

    /**
     * @Description 根据条件查找-分页
     */
    @RequestMapping(value = "/findByPage",method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse findByPage(PageQuery pageQuery, QualityControlProject qualityControlProject) {
        return GeneralResponse.createBySuccess(qualityControlProjectServiceImpl.findByPage2(pageQuery, qualityControlProject));
    }

    /**
     * @Description 根据id查询
     */
    @RequestMapping(value = "/findById",method = {RequestMethod.GET})
    @ResponseBody
    public GeneralResponse<QualityControlProject> findById(Integer id) {
        return GeneralResponse.createBySuccess(qualityControlProjectServiceImpl.findById(id));
    }

    /**
     * @Description 添加数据
     */
    @RequestMapping(value = "/insert",method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse insert(QualityControlProject qualityControlProject) {
        qualityControlProjectServiceImpl.insert(qualityControlProject);
        return GeneralResponse.createBySuccessMessage("添加成功 ");

    }

    /**
     * @Description 修改数据
     */
    @RequestMapping(value = "/update",method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse update(QualityControlProject qualityControlProject) {
        qualityControlProjectServiceImpl.update(qualityControlProject);
        return GeneralResponse.createBySuccessMessage("修改成功 ");

    }

    /**
     * @Description 根据id删除
     */
    @RequestMapping(value = "/deleteById",method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse deleteById(Integer id) {
        QualityControlProject qualityControlProject = new QualityControlProject();
        QualityControlRequirements qualityControlRequirements = new QualityControlRequirements();
        qualityControlProject.setParentId(id);
        qualityControlRequirements.setA1(id);
        PageQuery pageQuery = new PageQuery();
        List<QualityControlProject> byPage = qualityControlProjectServiceImpl.findByPage(pageQuery, qualityControlProject);
        List<QualityControlRequirements> byPage1 = qualityControlRequirementsServiceImpl.findByPage(pageQuery, qualityControlRequirements);
        if(byPage.size() != 0 || byPage1.size() != 0){
            return GeneralResponse.createByError("该项目下有数据不可以删除");
        }else {
            if (StringUtil.isNotBlank(id)) {
                qualityControlProjectServiceImpl.deleteById(id);
            }
            return GeneralResponse.createBySuccessMessage("删除成功 ");
        }
    }
}
