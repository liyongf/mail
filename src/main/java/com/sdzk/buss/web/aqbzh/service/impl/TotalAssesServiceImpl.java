/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年06月03日
 * 修改日期：2020年06月03日
 */
package com.sdzk.buss.web.aqbzh.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdzk.buss.web.aqbzh.dao.HistoricalRecordMapper;
import com.sdzk.buss.web.aqbzh.dao.QualityControlModuleMapper;
import com.sdzk.buss.web.aqbzh.dao.TotalAssesMapper;
import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlModule;
import com.sdzk.buss.web.aqbzh.pojo.bo.TotalAsses;
import com.sdzk.buss.web.aqbzh.pojo.dto.QualityControlTableIds;
import com.sdzk.buss.web.aqbzh.service.IQualityControlModuleService;
import com.sdzk.buss.web.aqbzh.service.ITotalAssesService;
import com.sdzk.buss.web.aqbzh.util.DateTimeUtil;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;
import com.sdzk.buss.web.aqbzh.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description ServiceImpl
 * @author：Tony
 * @version：V1.0
 * @since：2020年06月03日
 */
@Service
public class TotalAssesServiceImpl implements ITotalAssesService {

    @Autowired
    private TotalAssesMapper totalAssesMapper;
    @Autowired
    private QualityControlModuleMapper qualityControlModuleMapper;
    @Autowired
    private HistoricalRecordMapper historicalRecordMapper;
    @Autowired
    private IQualityControlModuleService qualityControlModuleService;

    /**
     * @Description 根据条件查询-分页
     */
    public List<TotalAsses> findByPage(PageQuery pageQuery, TotalAsses totalAsses, String dataduokuang, boolean duokuangFlag) {
        if (totalAsses != null && StringUtil.isBlank(totalAsses.getYear())) {
            totalAsses.setYear(DateTimeUtil.dateToStr(new Date(), "yyyy"));
        }
        List<TotalAsses> totalAssesList = totalAssesMapper.findByPage(pageQuery,totalAsses);
        //没有则创建
        if (totalAssesList.size() == 0) {
            //获取摸块id
            List<QualityControlModule> byList = qualityControlModuleMapper.findByList();
            List<QualityControlTableIds> byPageIds = qualityControlModuleMapper.findByPageIds();
            Map<Integer, String> map = new HashMap<Integer, String>();
            for(int i=0;i<byPageIds.size();i++) {
                map.put(byPageIds.get(i).getParentId(),byPageIds.get(i).getIds());
            }
            List<TotalAsses> list = new ArrayList<>();
            TotalAsses totalAsses1;
            if (byList.size() != 0) {
                for (int i = 1; i < 5; i++) {
                        if (duokuangFlag && StringUtil.isNotBlank(dataduokuang)){
                            JSONArray jsonArray = JSONObject.parseArray(dataduokuang);
                            for(int j=0;j<jsonArray.size();j++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                                String code = jsonObject.getString("code");
                                for (QualityControlModule qualityControlModule : byList) {
                                    totalAsses1 = new TotalAsses();
                                    BeanUtils.copyProperties(totalAsses, totalAsses1);
                                    totalAsses1.setModuleId(qualityControlModule.getId());
                                    totalAsses1.setA3(map.get(qualityControlModule.getId()));
                                    totalAsses1.setScore("0.00");
                                    totalAsses1.setPlayScore("0.00");
                                    if (i == 1) {
                                        totalAsses1.setMonth("一季度");
                                    } else if (i == 2) {
                                        totalAsses1.setMonth("二季度");
                                    } else if (i == 3) {
                                        totalAsses1.setMonth("三季度");
                                    } else if (i == 4) {
                                        totalAsses1.setMonth("四季度");
                                    }
                                    totalAsses1.setA1(i);
                                    totalAsses1.setA2(1);
                                    totalAsses1.setA4(code);
                                    list.add(totalAsses1);
                                }
                            }
                        }else {
                            for (QualityControlModule qualityControlModule : byList) {
                                totalAsses1 = new TotalAsses();
                                BeanUtils.copyProperties(totalAsses, totalAsses1);
                                totalAsses1.setModuleId(qualityControlModule.getId());
                                totalAsses1.setA3(map.get(qualityControlModule.getId()));
                                totalAsses1.setScore("0.00");
                                totalAsses1.setPlayScore("0.00");

                                if (i == 1) {
                                    totalAsses1.setMonth("一季度");
                                } else if (i == 2) {
                                    totalAsses1.setMonth("二季度");
                                } else if (i == 3) {
                                    totalAsses1.setMonth("三季度");
                                } else if (i == 4) {
                                    totalAsses1.setMonth("四季度");
                                }
                                totalAsses1.setA1(i);
                                totalAsses1.setA2(1);
                                list.add(totalAsses1);
                            }
                        }

                    }
                }
            totalAssesMapper.insertAll(list);
       }
        totalAssesList = totalAssesMapper.findByPage(pageQuery,totalAsses);
        // 排序
        Collections.sort(totalAssesList, totalAssesConparator);
        return totalAssesList;
    }


    public PageInfo<TotalAsses> findByPage2(PageQuery pageQuery, TotalAsses totalAsses, String dataduokuang, boolean duokuangFlag) {
        List<TotalAsses>    totalAssesList =   this.findByPage( pageQuery,  totalAsses,  dataduokuang,  duokuangFlag);
        PageInfo<TotalAsses> pageInfo = totalAssesMapper.findByPage2(pageQuery,totalAsses);
        pageInfo.setList(totalAssesList);
        return pageInfo;
    }













    public Comparator<TotalAsses> totalAssesConparator = new Comparator<TotalAsses>() {
        @Override
        public int compare(TotalAsses o1, TotalAsses o2) {
            return o1.getA1() - o2.getA1();
        }
    };
    /**
     * @Description 根据id查询
     */
    public TotalAsses findById(Integer id) {
        return totalAssesMapper.findById(id);
    }

    /**
     * @Description 添加数据
     */
    public void insert(TotalAsses totalAsses) {
         totalAssesMapper.insert(totalAsses);
    }

    /**
     * @Description 修改数据
     */
    public void update(TotalAsses totalAsses) {
         totalAssesMapper.update(totalAsses);
    }

    /**
     * @Description 根据id删除
     */
    public void deleteById(Integer id) {
         totalAssesMapper.deleteById(id);
    }

    /**
     * @Description 根据年月并且分数为0的删除
     */
    public void deleteByDate(String dataduokuang,boolean duokuangFlag) {
        //获取服务器时间的年份和月份
        String year = DateTimeUtil.dateToStr(new Date(), "yyyy");
        String month = DateTimeUtil.dateToStr(new Date(), "MM");
        Integer num = Integer.valueOf(month);
        String monthStr = null;
        Double byData = null;
        if (num >= 1 && num <= 3) {
            monthStr = "一季度";
        } else if (num >= 4 && num <= 6) {
            monthStr = "二季度";
        } else if (num >= 7 && num <= 9) {
            monthStr = "三季度";
        } else if (num >= 10 && num <= 12) {
            monthStr = "四季度";
        }
        if (StringUtil.isNotBlank(monthStr)) {
            byData = totalAssesMapper.findByData(year, monthStr);
        }
        List<String> monthList = new ArrayList<>();
        //获取模块id
        List<QualityControlModule> byList = qualityControlModuleMapper.findByList();
        List<TotalAsses> list = new ArrayList<>();
        TotalAsses totalAsses1;
        List<QualityControlTableIds> byPageIds = qualityControlModuleMapper.findByPageIds();
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (int i=0; i<byPageIds.size();i++) {
            map.put(byPageIds.get(i).getParentId(),byPageIds.get(i).getIds());
        }
        if (byData != null && byData > 0) {
            if (byList.size() != 0) {
                for (int i = num + 1; i < 13; i++) {
                    clacMonth(monthList, i);//当前月之后的一个月所属季度
                }
            }
        } else {
            for (int i = num; i < 13; i++) {
                clacMonth(monthList, i);//当前月份所属季度
            }
        }
        monthList =removeDuplicateWithOrder(monthList);
        for (int i = 0; i < monthList.size(); i++) {
                if (duokuangFlag && StringUtil.isNotBlank(dataduokuang)){
                    JSONArray jsonArray = JSONObject.parseArray(dataduokuang);
                    for(int j=0;j<jsonArray.size();j++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                        String code =  jsonObject.getString("code");
                        for (QualityControlModule qualityControlModule : byList) {
                            totalAsses1 = new TotalAsses();
                            totalAsses1.setYear(year);
                            totalAsses1.setModuleId(qualityControlModule.getId());
                            totalAsses1.setA3(map.get(qualityControlModule.getId()));
                            totalAsses1.setScore("0.00");
                            totalAsses1.setPlayScore("0.00");
                            totalAsses1.setMonth(monthList.get(i));
                            if (StringUtil.isNotBlank(totalAsses1.getMonth())) {
                                if (totalAsses1.getMonth().equals("一季度")) {
                                    totalAsses1.setA1(1);
                                } else if (totalAsses1.getMonth().equals("二季度")) {
                                    totalAsses1.setA1(2);
                                } else if (totalAsses1.getMonth().equals("三季度")) {
                                    totalAsses1.setA1(3);
                                } else if (totalAsses1.getMonth().equals("四季度")) {
                                    totalAsses1.setA1(4);
                                }
                            }
                            totalAsses1.setA2(1);
                            totalAsses1.setA4(code);
                            list.add(totalAsses1);
                        }

                    }
                }else {
                    for (QualityControlModule qualityControlModule : byList) {
                        totalAsses1 = new TotalAsses();
                        totalAsses1.setYear(year);
                        totalAsses1.setModuleId(qualityControlModule.getId());
                        totalAsses1.setA3(map.get(qualityControlModule.getId()));
                        totalAsses1.setScore("0.00");
                        totalAsses1.setPlayScore("0.00");
                        totalAsses1.setMonth(monthList.get(i));
                        if (StringUtil.isNotBlank(totalAsses1.getMonth())) {
                            if (totalAsses1.getMonth().equals("一季度")) {
                                totalAsses1.setA1(1);
                            } else if (totalAsses1.getMonth().equals("二季度")) {
                                totalAsses1.setA1(2);
                            } else if (totalAsses1.getMonth().equals("三季度")) {
                                totalAsses1.setA1(3);
                            } else if (totalAsses1.getMonth().equals("四季度")) {
                                totalAsses1.setA1(4);
                            }
                        }
                        totalAsses1.setA2(1);
                        list.add(totalAsses1);
                    }

                }

            }
        List<Integer> idLIst = totalAssesMapper.selectByDate(year, monthList);
        if (idLIst.size() > 0) {
            historicalRecordMapper.deleteByIds(idLIst);
        }
        totalAssesMapper.deleteByDate(year, monthList);
        totalAssesMapper.insertAll(list);

    }

    public List removeDuplicateWithOrder(List<String> list) {
        List<String> listNew = new ArrayList<String>();
        for (String str : list) {
            if(!listNew.contains(str)){
                listNew.add(str);
            }
        }
    return listNew ;
  }


    /**
     * 根据月份计算季度
     *
     * @param monthList
     * @param i
     */
    private List<String> clacMonth(List<String> monthList, int i) {
        if (i >= 1 && i <= 3) {
            monthList.add("一季度");
        } else if (i >= 4 && i <= 6) {
            monthList.add("二季度");
        } else if (i >= 7 && i <= 9) {
            monthList.add("三季度");
        } else if (i >= 10 && i <= 12) {
            monthList.add("四季度");
        }
        return monthList;
    }


}
