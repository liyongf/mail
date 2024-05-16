/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年06月05日
 * 修改日期：2020年06月05日
 */
package com.sdzk.buss.web.aqbzh.controller;


import com.sdzk.buss.web.aqbzh.pojo.bo.DetailsAsses;
import com.sdzk.buss.web.aqbzh.service.IDetailsAssesService;
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
 * @since：2020年06月05日
 */
@Controller
@RequestMapping("/detailsAsses")
public class DetailsAssesController {

    @Autowired
    private IDetailsAssesService detailsAssesServiceImpl;

    /**
     * @Description 根据条件查找-分页
     */
    @RequestMapping(value = "/findByPage", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse findByPage(PageQuery pageQuery,DetailsAsses detailsAsses) {
        return GeneralResponse.createBySuccess(detailsAssesServiceImpl.findByPage2(pageQuery,detailsAsses));
    }

    /**
     * @Description 根据id查询
     */
    @RequestMapping(value = "/findById", method = {RequestMethod.GET})
    @ResponseBody
    public GeneralResponse<DetailsAsses> findById(Integer id) {
        return GeneralResponse.createBySuccess(detailsAssesServiceImpl.findById(id));
    }

    /**
     * @Description 添加数据
     */
    @RequestMapping(value = "/insert", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse insert(DetailsAsses detailsAsses) {
        int insert = detailsAssesServiceImpl.insert(detailsAsses);
        return GeneralResponse.createBySuccess(insert);

    }

    /**
     * @Description 修改数据
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse update(DetailsAsses detailsAsses) {
        detailsAssesServiceImpl.update(detailsAsses);
        return GeneralResponse.createBySuccessMessage("修改成功 ");
    }

    /**
     * @Description 修改数据
     */
    @RequestMapping(value = "/updateByIDetailsAsses", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse updateByIDetailsAsses(DetailsAsses detailsAsses) {
        detailsAssesServiceImpl.updateByIDetailsAsses(detailsAsses);
        return GeneralResponse.createBySuccessMessage("修改成功 ");
    }

    /**
     * @Description 根据id删除
     */
    @RequestMapping(value = "/deleteById", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse deleteById(Integer id, String ids) {
        if (StringUtil.isNotBlank(id)) {
            detailsAssesServiceImpl.deleteById(id);
        } else if (StringUtil.isNotBlank(ids)) {
            List<Integer> idList = StringUtil.splitToListInt(ids);
            for (Integer delId : idList) {
                detailsAssesServiceImpl.deleteById(delId);
            }
        }
        return GeneralResponse.createBySuccessMessage("删除成功 ");
    }

    /**
     * @Description 计算分数
     */
    @RequestMapping(value = "/calculateScore", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse calculateScore(Integer totalAssesId,String moduleId) {
        detailsAssesServiceImpl.calculateScore(totalAssesId,moduleId);
        return GeneralResponse.createBySuccess();
    }


}
