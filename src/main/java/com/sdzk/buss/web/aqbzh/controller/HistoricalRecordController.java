/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年06月10日
 * 修改日期：2020年06月10日
 */
package com.sdzk.buss.web.aqbzh.controller;

import com.sdzk.buss.web.aqbzh.pojo.bo.HistoricalRecord;
import com.sdzk.buss.web.aqbzh.service.IHistoricalRecordService;
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
 * @since：2020年06月10日
 */
@Controller
@RequestMapping("/historicalRecord")
public class HistoricalRecordController {

    @Autowired
    private IHistoricalRecordService historicalRecordServiceImpl;

    /**
     * @Description 根据条件查找-分页
     */
    @RequestMapping(value = "/findByPage", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse findByPage(PageQuery pageQuery, HistoricalRecord historicalRecord) {
        return GeneralResponse.createBySuccess(historicalRecordServiceImpl.findByPage2(pageQuery, historicalRecord));
    }

    /**
     * @Description 添加数据
     */
    @RequestMapping(value = "/insert",method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse insert(HistoricalRecord historicalRecord) {
        historicalRecordServiceImpl.insert(historicalRecord);
        return GeneralResponse.createBySuccessMessage("添加成功 ");

    }

    /**
     * @Description 修改数据
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse update(HistoricalRecord historicalRecord) {
        historicalRecordServiceImpl.update(historicalRecord);
        return GeneralResponse.createBySuccessMessage("修改成功 ");

    }

    /**
     * @Description 根据id删除
     */
    @RequestMapping(value = "/deleteById", method = {RequestMethod.POST})
    @ResponseBody
    public GeneralResponse deleteById(Integer id, String ids) {
        if (StringUtil.isNotBlank(id)) {
            historicalRecordServiceImpl.deleteById(id);
        } else if (StringUtil.isNotBlank(ids)) {
            List<Integer> idList = StringUtil.splitToListInt(ids);
            for (Integer delId : idList) {
                historicalRecordServiceImpl.deleteById(delId);
            }
        }
        return GeneralResponse.createBySuccessMessage("删除成功 ");
    }

}
