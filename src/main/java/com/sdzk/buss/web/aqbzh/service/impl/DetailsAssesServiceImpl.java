/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年06月05日
 * 修改日期：2020年06月05日
 */
package com.sdzk.buss.web.aqbzh.service.impl;


import com.sdzk.buss.web.aqbzh.dao.DetailsAssesMapper;
import com.sdzk.buss.web.aqbzh.dao.HistoricalRecordMapper;
import com.sdzk.buss.web.aqbzh.dao.TotalAssesMapper;
import com.sdzk.buss.web.aqbzh.pojo.bo.DetailsAsses;
import com.sdzk.buss.web.aqbzh.pojo.bo.HistoricalRecord;
import com.sdzk.buss.web.aqbzh.pojo.bo.TotalAsses;
import com.sdzk.buss.web.aqbzh.service.IDetailsAssesService;
import com.sdzk.buss.web.aqbzh.util.DateTimeUtil;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;
import com.sdzk.buss.web.aqbzh.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Description ServiceImpl
 * @author：Tony
 * @version：V1.0
 * @since：2020年06月05日
 */
@Service("detailsAssesServiceImpl")
public class DetailsAssesServiceImpl implements IDetailsAssesService {

    @Autowired
    private DetailsAssesMapper detailsAssesMapper;
    @Autowired
    private HistoricalRecordMapper historicalRecordMapper;
    @Autowired
    private TotalAssesMapper totalAssesMapper;

    /**
     * @Description 根据条件查询-分页
     */
    public List<DetailsAsses> findByPage(PageQuery pageQuery,DetailsAsses detailsAsses) {
        List<DetailsAsses> detailsAssesList = detailsAssesMapper.findByPage(pageQuery,detailsAsses);
        return detailsAssesList;
    }
    public PageInfo<DetailsAsses> findByPage2(PageQuery pageQuery, DetailsAsses detailsAsses) {
        PageInfo<DetailsAsses> detailsAssesList = detailsAssesMapper.findByPage2(pageQuery,detailsAsses);
        return detailsAssesList;
    }
    /**
     * @Description 根据id查询
     */
    public DetailsAsses findById(Integer id) {
        return detailsAssesMapper.findById(id);
    }

    /**
     * @Description 添加数据
     */
    public int insert(DetailsAsses detailsAsses) {
        detailsAsses.setCreateTime(new Date());
        if (detailsAsses != null && detailsAsses.getId() != null) {
            DetailsAsses byId = detailsAssesMapper.findById(detailsAsses.getId());
            if (byId != null && byId.getTotalAssesId().equals(detailsAsses.getTotalAssesId()) && byId.getModuleId().equals(detailsAsses.getModuleId())) {
                //判断打分点是否为空 为空直接修改 否则判断和当前打分点是否一致
                if (StringUtil.isNotBlank(byId.getPlaceCode())) {
                    //打分点相同则修改  否则添加
                    if (byId.getPlaceCode().equals(detailsAsses.getPlaceCode())) {
                        this.update(detailsAsses);
                    } else {
                        detailsAssesMapper.insert(detailsAsses);
                    }
                } else {
                    this.update(detailsAsses);
                }
            } else {
                detailsAssesMapper.insert(detailsAsses);
            }
        } else {
            detailsAssesMapper.insert(detailsAsses);
        }
        return detailsAsses.getId();
    }

    /**
     * @Description 修改数据
     */
    public void update(DetailsAsses detailsAsses) {
        detailsAsses.setUpdateTime(new Date());
         detailsAssesMapper.update(detailsAsses);
    }

    /**
     * @Description 修改数据
     */
    public void updateByIDetailsAsses(DetailsAsses detailsAsses) {
         detailsAssesMapper.updateByIDetailsAsses(detailsAsses);
    }

    /**
     * @Description 根据id删除
     */
    public void deleteById(Integer id) {
         detailsAssesMapper.deleteById(id);
    }

    /**
     * @Description 计算分数
     */
    public void calculateScore(Integer totalAssesId, String moduleId) {
        DecimalFormat df = new DecimalFormat("0.00");
        List<Integer> list1 = StringUtil.splitToListInt(moduleId);
        //根据汇总表id查询某一大项的标准分值  应打分分值  权重
        List<HistoricalRecord> byId = historicalRecordMapper.findById(totalAssesId, list1, 1);
        Map<String, HistoricalRecord> mapHistoricalRecord = new HashMap<String, HistoricalRecord> ();
        for(int i=0;i<byId.size();i++){
            mapHistoricalRecord.put(byId.get(i).getModuleId(),byId.get(i));
        }
        Integer clacRule = 3; //1最低 2最高  3权重
        if (CollectionUtils.isNotEmpty(byId)) {
            if ("-999999".equals(byId.get(0).getA1())) {//最低
                clacRule = 1;
            } else if ("999999".equals(byId.get(0).getA1())) {//最高
                clacRule = 2;
            }
        }

        //根据汇总表的id查询所有的打分明细
        DetailsAsses detailsAsses = new DetailsAsses();
        detailsAsses.setTotalAssesId(totalAssesId);
        List<DetailsAsses> list = detailsAssesMapper.findByPage(null,detailsAsses);
	
        String totalScore = "0.00";//得分
        //判断是都有打分记录
        if (list.size() > 0) {
            // 分组 按 模块ID
            Map<Integer, List<DetailsAsses>> detailsAssesgroupByModuleId = new HashMap<Integer, List<DetailsAsses>>();

            for(int i =0;i<list.size();i++) {
                DetailsAsses detailsAsses1 = list.get(i);
                if (detailsAssesgroupByModuleId.containsKey(detailsAsses1.getModuleId())) {
                    List<DetailsAsses> detailsAssesList  = detailsAssesgroupByModuleId.get(detailsAsses1.getModuleId());
                    detailsAssesList.add(detailsAsses1);
                    detailsAssesgroupByModuleId.put(detailsAsses1.getModuleId(),detailsAssesList);
                } else {
                    List<DetailsAsses> detailsAssesList  = new ArrayList<DetailsAsses>();
                    detailsAssesList.add(detailsAsses1);
                    detailsAssesgroupByModuleId.put(detailsAsses1.getModuleId(),detailsAssesList);
                }
            }
            if (MapUtils.isNotEmpty(detailsAssesgroupByModuleId)) {
                List<Map<String, Object>> sumByModuleId = new ArrayList<>();
                for (Integer key : detailsAssesgroupByModuleId.keySet()) {
                    List<DetailsAsses> detailsAssesList = detailsAssesgroupByModuleId.get(key);
                    if (CollectionUtils.isNotEmpty(detailsAssesList)) {
                        Map<String, Integer> singleDoubleRulesMap = new HashMap<>();// key  项id value 1单 2双
                        Map<String, Integer> scoringMethodMap = new HashMap<>();// key  项id value 1最低 2最高  3平均
                        String totalModuleScore = "0.00";// 模块得分
                        List<Integer> moduleIdList =new ArrayList<>();
                        moduleIdList.add(key);
                        //循环匹配规则
                        for (DetailsAsses detailsAsses1 : detailsAssesList) {
                            singleDoubleRulesMap.put(detailsAsses1.getProjectTreeId(), detailsAsses1.getA1());
                            scoringMethodMap.put(detailsAsses1.getProjectTreeId(), detailsAsses1.getA2());
                        }
                        //计算单点实得分数的和  否则根据多点规则计算
                        for (String projectTreeId : singleDoubleRulesMap.keySet()) {//keySet获取map集合key的集合  然后在遍历key即可
                            Integer value = singleDoubleRulesMap.get(projectTreeId);
                            if (value != null && value == 1) {
                                totalModuleScore = new BigDecimal(totalModuleScore).add(new BigDecimal(clacSinglePoint(totalAssesId, moduleIdList, mapHistoricalRecord, projectTreeId, null))).toString();
                            }
                            if (value != null && value == 2) {
                                Integer type = scoringMethodMap.get(projectTreeId);
                                totalModuleScore = new BigDecimal(totalModuleScore).add(new BigDecimal(clacMultipoint(type, totalAssesId, moduleIdList, projectTreeId,null))).toString();
                            }
                        }
                        Map map = new HashMap();
                        map.put("module_id", key);//表id
                        map.put("sumScore", totalModuleScore);//最低分数
                        sumByModuleId.add(map);
                    }
                }
                //判断 是否 是多模块
                if (CollectionUtils.isNotEmpty(sumByModuleId)) {
                    if (sumByModuleId.size() > 1) {//如果是多模块 在计算模块
                        if (clacRule == 3) {//权重
                            totalScore = clacSinglePoint(totalAssesId, list1, mapHistoricalRecord, null, sumByModuleId);
                        } else {
                            totalScore = clacMultipoint(clacRule, totalAssesId, list1, null,sumByModuleId);
                        }

                        System.out.println(totalScore);
                    } else {
                        totalScore = String.valueOf(sumByModuleId.get(0).get("sumScore"));
                    }
                }
            }
        }
		
        if (StringUtil.isNotBlank(totalScore)) {
            TotalAsses totalAsses = new TotalAsses();
            if (byId.size() > 0) {
                //计算后的最终打分结果
                double parseDouble1 = 100;
                double parseDouble = Double.parseDouble(String.valueOf(totalScore));
                //不能超过标准分值
                String methodScore = byId.get(0).getMethodScore();
                if (StringUtil.isNotBlank(methodScore)) {
                    parseDouble1 = Double.parseDouble(String.valueOf(methodScore));
                }
                if (parseDouble > parseDouble1) {
                    totalAsses.setPlayScore(df.format(Double.parseDouble("100")));
                } else {
                    totalAsses.setPlayScore(df.format(parseDouble));
                }
                String theWeight = byId.get(0).getTheWeight();
                String str = new BigDecimal(totalAsses.getPlayScore()).multiply(new BigDecimal(theWeight)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                totalAsses.setScore(str);
                totalAsses.setId(totalAssesId);
                totalAssesMapper.update(totalAsses);
            }
        }
    }
	
	
	    /**
     *
     * @param type  1 最低 2  最高 3  平均
     * @param totalAssesId  表id
     * @param list1  模块id
     * @param projectTreeId  项目 id
     * @return
     */
    public String clacMultipoint(Integer type, Integer totalAssesId, List<Integer> list1,String projectTreeId,List<Map<String, Object>> sumByModuleId) {
        String totalScore = "0.00";//得分
        DecimalFormat df = new DecimalFormat("0.00");
           if (CollectionUtils.isEmpty(sumByModuleId)) {
            sumByModuleId = new ArrayList<>();
            Map map;
            //取最小值
            if (type != null && type == 1) {
                //按打分点分组并根据分数排序取最低  局部通风
                List<Map<String, Object>> sumSmallByModuleId = detailsAssesMapper.findSumSmallByModuleId(totalAssesId, projectTreeId);
                if (sumSmallByModuleId.size() > 0) {
                    //实际打分分数 获取第一个  最低分 分数
                    for (Map<String, Object> mapSum : sumSmallByModuleId) {
                        map = new HashMap();
                        map.put("module_id", String.valueOf(mapSum.get("module_id")));//表id
                        map.put("sumScore", String.valueOf(mapSum.get("scoreDetail")));//最低分数
                        sumByModuleId.add(map);
                    }
                }
            }
            //取最大值
            if (type != null && type == 2) {
                //按打分点分组并根据分数排序取最低  局部通风
                List<Map<String, Object>> sumSmallByModuleId = detailsAssesMapper.findSumSmallByModuleIdMax(totalAssesId, projectTreeId);
                if (sumSmallByModuleId.size() > 0) {
                    //实际打分分数 获取第一个  最高分 分数
                    for (Map<String, Object> mapSum : sumSmallByModuleId) {
                        map = new HashMap();
                        map.put("module_id", String.valueOf(mapSum.get("module_id")));//表id
                        map.put("sumScore", String.valueOf(mapSum.get("scoreDetail")));//最低分数
                        sumByModuleId.add(map);
                    }
                }
            }
            if (type != null && type == 3) { //平均
                //按打分点分组求平均数 通风设施
                List<Map<String, Object>> sumAvgByModuleId = detailsAssesMapper.findSumAvgByModuleId(totalAssesId, projectTreeId);
                for (Map<String, Object> mapAvg : sumAvgByModuleId) {
                    map = new HashMap();
                    map.put("module_id", String.valueOf(mapAvg.get("module_id")));//表id
                    map.put("sumScore", String.valueOf(mapAvg.get("sumAvgScore")));//平均分数
                    sumByModuleId.add(map);
                }
            }
        }
        String sumCount = "0.00";
        Integer val = null;
        List<Integer> listRemoveId = new ArrayList<>();
        for (int i = list1.size() - 1; i >= 0; i--) {
            for (Map<String, Object> calcMap : sumByModuleId) {
                Integer mIdNum = list1.get(i);
                String module_id = String.valueOf(calcMap.get("module_id"));
                if (StringUtil.isNotBlank(module_id)) {
                    val = Integer.valueOf(module_id);
                }
                if (mIdNum != null && val != null && val == mIdNum) {
                    listRemoveId.add(mIdNum);
                    if (StringUtil.isNotBlank(sumCount) && sumCount.equals("0.00")) {
                        sumCount = String.valueOf(calcMap.get("sumScore"));
                    } else {
                        //已有最低/最高分数 减去 当前分数值
                        String score = new BigDecimal(sumCount).subtract(new BigDecimal(String.valueOf(calcMap.get("sumScore")))).toString();
                        if (type == 1) {//最低
                            if (Double.valueOf(score) > 0) {//取最低
                                sumCount = String.valueOf(calcMap.get("sumScore"));
                            }
                        } else if (type == 2) {//最高
                            if (Double.valueOf(score) < 0) {//取最高
                                sumCount = String.valueOf(calcMap.get("sumScore"));
                            }
                        }
                    }
                }
            }
        }
        HashSet hs1 = new HashSet(list1);
        HashSet hs2 = new HashSet(listRemoveId);
        hs1.removeAll(hs2);
        List<Integer> listC = new ArrayList<>();
        listC.addAll(hs1);
        if (listC.size() > 0) {
            sumCount = "0.00";
        }
        totalScore = df.format(Double.parseDouble(String.valueOf(sumCount)));
        return totalScore;
    }

    /**
     *
     *
     * @param totalAssesId  统计表 id
     * @param list1  模块id
     * @param mapHistoricalRecord 根据汇总表id查询某一大项的标准分值  应打分分值  权重
     * @param projectTreeId  项目 id
     * @return
     */
    public String clacSinglePoint(Integer totalAssesId, List<Integer> list1,Map<String, HistoricalRecord> mapHistoricalRecord,String projectTreeId, List<Map<String, Object>> sumByModuleId) {
        String totalScore = "0.00";//得分
        DecimalFormat df = new DecimalFormat("0.00");
        //单点求和按表分组

        if (CollectionUtils.isEmpty(sumByModuleId)) {
            sumByModuleId = detailsAssesMapper.findSumByModuleId(totalAssesId, projectTreeId);
        }
 
        if (sumByModuleId.size() > 0) {
            //统计表个数
            String count = null;
            Integer flag = null;
            if (list1.size() > 0) {
                Integer mId = list1.get(list1.size() - 1);
                if (mId != null) {
                    HistoricalRecord historicalRecord = mapHistoricalRecord.get(String.valueOf(mId));
                    count = historicalRecord.getA4();
                    flag = Integer.valueOf(historicalRecord.getA3());
                }
            }
            if (flag != null && flag == 3 && StringUtil.isNotBlank(count)) {
                Integer num = Integer.valueOf(count);
                if (num > 1) {
                    //只有1个则不循环  否则循环根据规则运算
                    BigDecimal multiply = null;
                    for (Map<String, Object> mapId : sumByModuleId) {
                        //获取表id
                        String module_id = String.valueOf(mapId.get("module_id"));
                        //根据表id获取对应的权重和应打分值
                        HistoricalRecord historicalRecord1 = mapHistoricalRecord.get(module_id);
                        if (historicalRecord1 != null) {
                            //判断是否为露天煤矿
                            if (StringUtil.isNotBlank(historicalRecord1.getA3())) {
                                //获取表格类型 3位露天煤矿
                                flag = Integer.valueOf(historicalRecord1.getA3());
                                if (flag == 3) {
                                    //缺项处理
                                    totalScore = df.format(Double.parseDouble(String.valueOf(getHistory(historicalRecord1, String.valueOf(mapId.get("sumScore"))))));
                                    //判断是否为第一次进来
                                    if (multiply == null) {
                                        multiply = new BigDecimal(totalScore);
                                    } else {
                                        BigDecimal multiply1 = new BigDecimal(totalScore);
                                        multiply = multiply.add(multiply1);//统计所有表总得分
                                    }
                                }
                            }
                        }
                    }
                    //露天煤矿算平均
                    if (multiply != null) {
                        totalScore = df.format(Double.parseDouble(String.valueOf(multiply.divide(new BigDecimal(count)))));
                    }
                } else {
                    //实际打分的和
                    totalScore = df.format(Double.parseDouble(String.valueOf(sumByModuleId.get(0).get("sumScore"))));
                    HistoricalRecord historicalRecord = mapHistoricalRecord.get(sumByModuleId.get(0).get("module_id"));
                    totalScore = df.format(Double.parseDouble(String.valueOf(getHistory(historicalRecord, totalScore))));
                }
            } else {
                //只有1个则不循环  否则循环根据规则运算
                if (sumByModuleId.size() == 1) {
                    //实际打分的和
                    totalScore = df.format(Double.parseDouble(String.valueOf(sumByModuleId.get(0).get("sumScore"))));
                    HistoricalRecord historicalRecord = mapHistoricalRecord.get(sumByModuleId.get(0).get("module_id"));
                    totalScore = df.format(Double.parseDouble(String.valueOf(getHistory(historicalRecord, totalScore))));
                } else {
                    BigDecimal multiply = null;
                    for (Map<String, Object> mapId : sumByModuleId) {
                        //获取表id
                        String module_id = String.valueOf(mapId.get("module_id"));
                        //根据表id获取对应的权重和应打分值
                        HistoricalRecord historicalRecord1 = mapHistoricalRecord.get(module_id);
                        if (historicalRecord1 != null) {
                            //判断是否有权重  存在根据权重计算并求和
                            if (StringUtil.isNotBlank(historicalRecord1.getA1())) {
                                //实际打分的和
                                totalScore = df.format(Double.parseDouble(String.valueOf(getHistory(historicalRecord1, String.valueOf(mapId.get("sumScore"))))));
                                if (multiply == null) {
                                    multiply = new BigDecimal(totalScore).multiply(new BigDecimal(historicalRecord1.getA1()));
                                } else {
                                    BigDecimal multiply1 = new BigDecimal(totalScore).multiply(new BigDecimal(historicalRecord1.getA1()));
                                    multiply = multiply.add(multiply1);
                                }
                            }
                        }
                    }
                    if (multiply != null) {
                        totalScore = df.format(Double.parseDouble(String.valueOf(multiply)));
                    }
                }
            }
        }

        return totalScore;
    }


	
	
	
	
	
	
	
	
	
	

    /**
     * 判断是否缺项 缺项则根据公式计算
     *
     * @param historicalRecord
     * @param totalScore
     * @return
     */
    private String getHistory(HistoricalRecord historicalRecord, String totalScore) {
        if (historicalRecord != null && StringUtil.isNotBlank(historicalRecord.getScore())
                && StringUtil.isNotBlank(historicalRecord.getMethodScore())) {
            if (Integer.valueOf(historicalRecord.getScore()) < Integer.valueOf(historicalRecord.getMethodScore())) {
                totalScore = new BigDecimal(historicalRecord.getMethodScore()).
                        divide(new BigDecimal(historicalRecord.getScore())).
                        multiply(new BigDecimal(totalScore)).toString();
            }
        }
        return totalScore;
    }
}
