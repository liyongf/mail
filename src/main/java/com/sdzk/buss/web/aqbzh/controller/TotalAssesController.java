/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年06月03日
 * 修改日期：2020年06月03日
 */
package com.sdzk.buss.web.aqbzh.controller;


import com.sdzk.buss.web.aqbzh.pojo.bo.TotalAsses;
import com.sdzk.buss.web.aqbzh.service.ITotalAssesService;
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
 * @since：2020年06月03日
 */
@Controller
@RequestMapping("/totalAsses")
public class TotalAssesController {

    @Autowired
    private ITotalAssesService totalAssesServiceImpl;

    /**
     * @Description 根据条件查找-分页
     */
    @RequestMapping(value = "/findByPage", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse findByPage(PageQuery pageQuery, TotalAsses totalAsses, String dataduokuang, boolean duokuangFlag) {
        return GeneralResponse.createBySuccess(totalAssesServiceImpl.findByPage2(pageQuery, totalAsses,dataduokuang,duokuangFlag));
    }


    /**
     * @Description 根据id查询
     */
    @RequestMapping(value = "/findById", method = {RequestMethod.GET})
    @ResponseBody
    public GeneralResponse<TotalAsses> findById(Integer id) {
        return GeneralResponse.createBySuccess(totalAssesServiceImpl.findById(id));
    }

    /**
     * @Description 重置
     */
    @RequestMapping(value = "/deleteByDate", method = {RequestMethod.GET})
    @ResponseBody
    public GeneralResponse deleteByDate(String dataduokuang,boolean duokuangFlag) {
        totalAssesServiceImpl.deleteByDate(dataduokuang,duokuangFlag);
        return GeneralResponse.createBySuccessMessage("重置成功");
    }

    /**
     * @Description 添加数据
     */
    @RequestMapping(value = "/insert", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse insert(TotalAsses totalAsses) {
        totalAssesServiceImpl.insert(totalAsses);
        return GeneralResponse.createBySuccessMessage("添加成功 ");

    }

    /**
     * @Description 修改数据
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse update(TotalAsses totalAsses) {
        totalAssesServiceImpl.update(totalAsses);
        return GeneralResponse.createBySuccessMessage("修改成功 ");

    }

    /**
     * @Description 根据id删除
     */
    @RequestMapping(value = "/deleteById", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse deleteById(Integer id, String ids) {
        if (StringUtil.isNotBlank(id)) {
            totalAssesServiceImpl.deleteById(id);
        } else if (StringUtil.isNotBlank(ids)) {
            List<Integer> idList = StringUtil.splitToListInt(ids);
            for (Integer delId : idList) {
                totalAssesServiceImpl.deleteById(delId);
            }
        }
        return GeneralResponse.createBySuccessMessage("删除成功 ");
    }


}
