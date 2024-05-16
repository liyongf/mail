/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年05月27日
 * 修改日期：2020年05月27日
 */
package com.sdzk.buss.web.aqbzh.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sdzk.buss.web.aqbzh.dao.HistoricalRecordMapper;
import com.sdzk.buss.web.aqbzh.dao.QualityControlModuleMapper;
import com.sdzk.buss.web.aqbzh.dao.QualityControlProjectMapper;
import com.sdzk.buss.web.aqbzh.pojo.bo.HistoricalRecord;
import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlModule;
import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlProject;
import com.sdzk.buss.web.aqbzh.pojo.dto.QualityControlProjectDTO;
import com.sdzk.buss.web.aqbzh.service.IQualityControlProjectService;
import com.sdzk.buss.web.aqbzh.util.LevelUtil;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;
import com.sdzk.buss.web.aqbzh.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Description ServiceImpl
 * @author：Tony
 * @version：V1.0
 * @since：2020年05月27日
 */
@Service(value = "qualityControlProjectServiceImpl")
public class QualityControlProjectServiceImpl implements IQualityControlProjectService {

    @Autowired
    private QualityControlProjectMapper qualityControlProjectMapper;
    @Autowired
    private HistoricalRecordMapper historicalRecordMapper;
    @Autowired
    private QualityControlModuleMapper qualityControlModuleMapper;

    /**
     * 渲染表格
     *
     * @param moduleId
     * @return
     */
    public List<QualityControlProjectDTO> findByList(String moduleId, String moduleIds, Integer status, String url, Integer totalAssesId, String score, String theWeight) {
        List<Integer> list1 = StringUtil.splitToListInt(moduleId);
        List<Integer> listCount = StringUtil.splitToListInt(moduleIds);
        QualityControlModule byId1 = qualityControlModuleMapper.findById(list1.get(0));
        List<HistoricalRecord> byId = historicalRecordMapper.findById(totalAssesId, list1, status);
        List<QualityControlProjectDTO> list = new ArrayList<>();
        if (byId.size() > 0) {
            String detailsListDto = byId.get(0).getDetailsListDto();
            list = JSONArray.parseArray(detailsListDto, QualityControlProjectDTO.class);
        } else {
            //三表联查所有行数据
            List<QualityControlProjectDTO> qualityControlProjectDTOList = qualityControlProjectMapper.findByList(list1.get(0), 1);
            QualityControlProject qualityControlProject = new QualityControlProject();
            qualityControlProject.setType(1);
            //查询第一级项目
            List<QualityControlProject> byPage1 = qualityControlProjectMapper.findByPage(null,qualityControlProject);
            Map<Integer, QualityControlProject> byPage1Map = new HashMap<Integer, QualityControlProject>();
            for (int i=0;i<byPage1.size();i++){
                QualityControlProject qualityControlProject1 =  byPage1.get(i);
                byPage1Map.put(qualityControlProject1.getId(),qualityControlProject1);
            }

            qualityControlProject.setType(2);
            //查询所有项目内容
            List<QualityControlProject> byPage2 = qualityControlProjectMapper.findByPage(null,qualityControlProject);
            Map<Integer, QualityControlProject> byPage2Map = new HashMap<Integer, QualityControlProject>();
            for (int i=0;i<byPage2.size();i++){
                QualityControlProject qualityControlProject1 =  byPage2.get(i);
                byPage2Map.put(qualityControlProject1.getId(),qualityControlProject1);
            }

            QualityControlProjectDTO qualityControlProjectDTO1;
            List<QualityControlProjectDTO> listDto2 = new ArrayList<>();
            String sb = "1";//拼接排序
            StringBuffer stringBuffer;
            //根据第三级取第二级
            for (QualityControlProjectDTO qualityControlProjectDTO : qualityControlProjectDTOList) {
                if (qualityControlProjectDTO.getA2() < 10) {
                    sb = qualityControlProjectDTO.getA2() + "0";
                } else {
                    sb = new StringBuffer(String.valueOf(qualityControlProjectDTO.getA2())).reverse().toString();
                }
                if (qualityControlProjectDTO.getSort() < 10) {
                    sb += qualityControlProjectDTO.getSort() + "0";
                } else {
                    sb += new StringBuffer(String.valueOf(qualityControlProjectDTO.getSort())).reverse().toString();
                }
                if(StringUtil.isBlank(qualityControlProjectDTO.getRequirements().trim()) ||
                        qualityControlProjectDTO.getRequirements().trim().equals("") ||
                        qualityControlProjectDTO.getRequirements().trim().equals("<p>&nbsp;</p>") ||
                        qualityControlProjectDTO.getRequirements().trim().equals("&nbsp;")){
                    qualityControlProjectDTO.setRequirements(qualityControlProjectDTO.getProjectName());
                }
                qualityControlProjectDTO1 = new QualityControlProjectDTO();
                BeanUtils.copyProperties(qualityControlProjectDTO, qualityControlProjectDTO1);
                QualityControlProject qualityControlProject1 = byPage2Map.get(qualityControlProjectDTO.getParentId());
                if (qualityControlProject1 != null && StringUtil.isNotBlank(qualityControlProject1.getProjectName())) {
                    if (qualityControlProject1.getSort() < 10) {
                        sb += qualityControlProject1.getSort() + "0";
                    } else {
                        sb += new StringBuffer(String.valueOf(qualityControlProject1.getSort())).reverse().toString();
                    }
                    qualityControlProjectDTO1.setProjectName2(qualityControlProject1.getProjectName());
                    qualityControlProjectDTO1.setParentId(qualityControlProject1.getParentId());
                    if (StringUtil.isBlank(qualityControlProjectDTO.getRequirements())) {
                        qualityControlProjectDTO1.setRequirements(qualityControlProjectDTO.getProjectName());
                    }
                } else {
                    if (qualityControlProjectDTO.getSort() < 10) {
                        sb += qualityControlProjectDTO.getSort() + "0";
                    } else {
                        sb += new StringBuffer(String.valueOf(qualityControlProjectDTO.getSort())).reverse().toString();
                    }
                    qualityControlProjectDTO1.setProjectName2(qualityControlProjectDTO.getProjectName());
                }
				qualityControlProjectDTO1.setTotalSort1(sb);
                qualityControlProjectDTO1.setStatus(status);
                qualityControlProjectDTO1.setUrl(url);
                qualityControlProjectDTO1.setTotalAssesId(totalAssesId);
                listDto2.add(qualityControlProjectDTO1);
            }
            String sumScore = null;
            String num = null;
            for (QualityControlProjectDTO qualityControlProjectDTO2 : listDto2) {
				sb = qualityControlProjectDTO2.getTotalSort1();
                if (StringUtil.isNotBlank(qualityControlProjectDTO2.getStandardScore()) && StringUtil.isBlank(qualityControlProjectDTO2.getA4())) {
                    if (StringUtil.isBlank(sumScore)) {
                        sumScore = qualityControlProjectDTO2.getStandardScore();
                    } else {
                        BigDecimal add = null;
                        if (StringUtil.isBlank(qualityControlProjectDTO2.getA5())) {
                            add = new BigDecimal(sumScore).add(new BigDecimal(qualityControlProjectDTO2.getStandardScore().trim()));
                        } else {
                            if (StringUtil.isBlank(num)) {
                                num = qualityControlProjectDTO2.getA5();
                                add = new BigDecimal(sumScore).add(new BigDecimal(qualityControlProjectDTO2.getStandardScore()));
                            } else {
                                if (!num.equals(qualityControlProjectDTO2.getA5())) {
                                    num = qualityControlProjectDTO2.getA5();
                                    add = new BigDecimal(sumScore).add(new BigDecimal(qualityControlProjectDTO2.getStandardScore()));
                                }
                            }
                        }
                        if (add != null) {
                            sumScore = add.toString();
                        }
                    }
                }
                qualityControlProjectDTO1 = new QualityControlProjectDTO();
                BeanUtils.copyProperties(qualityControlProjectDTO2, qualityControlProjectDTO1);
                QualityControlProject qualityControlProject2 = byPage1Map.get(qualityControlProjectDTO2.getParentId());
                if (qualityControlProject2 != null && StringUtil.isNotBlank(qualityControlProject2.getProjectName())) {
                    sb += String.valueOf(qualityControlProject2.getSort());
                    qualityControlProjectDTO1.setProjectName1(qualityControlProject2.getProjectName() + "(" + qualityControlProject2.getTotalScore() + "分)");
                }
                stringBuffer = new StringBuffer(sb);
                stringBuffer.reverse();
                qualityControlProjectDTO1.setTotalSort(Integer.valueOf(stringBuffer.toString()));
                list.add(qualityControlProjectDTO1);
            }

            // 排序
            Collections.sort(list, qualityControlProjectDTOSeqConparator);
            String jsonString = JSON.toJSONString(list);
            HistoricalRecord historicalRecord = new HistoricalRecord();
            historicalRecord.setTotalAssesId(totalAssesId);
            historicalRecord.setDetailsListDto(jsonString);
            historicalRecord.setScore(sumScore);
            historicalRecord.setMethodScore(score);
            historicalRecord.setTheWeight(theWeight);
            historicalRecord.setModuleId(moduleId);
            historicalRecord.setA1(byId1.getTheWeight());
            historicalRecord.setA2(status);
            if (qualityControlProjectDTOList.size() > 0) {
                historicalRecord.setA3(qualityControlProjectDTOList.get(0).getTypeCode());
            }
            historicalRecord.setA4(String.valueOf(listCount.size()));
            historicalRecordMapper.insert(historicalRecord);
        }
        return list;
    }
    public Comparator<QualityControlProjectDTO> qualityControlProjectDTOSeqConparator = new Comparator<QualityControlProjectDTO>() {
        @Override
        public int compare(QualityControlProjectDTO o1, QualityControlProjectDTO o2) {
            return o1.getTotalSort() - o2.getTotalSort();
        }
    };


    /**
     * @Description 根据条件查询-分页
     */
    public List<QualityControlProject> findByPage(PageQuery pageQuery, QualityControlProject qualityControlProject) {
        List<QualityControlProject> qualityControlProjectList = qualityControlProjectMapper.findByPage(pageQuery,qualityControlProject);
        return qualityControlProjectList;
    }
    /**
     * @Description 根据条件查询-分页
     */
    public PageInfo<QualityControlProject> findByPage2(PageQuery pageQuery, QualityControlProject qualityControlProject) {
        PageInfo<QualityControlProject> qualityControlProjectList = qualityControlProjectMapper.findByPage2(pageQuery,qualityControlProject);
        return qualityControlProjectList;
    }
    /**
     * @Description 根据id查询
     */
    public QualityControlProject findById(Integer id) {
        return qualityControlProjectMapper.findById(id);
    }

    /**
     * @Description 添加数据
     */
    public void insert(QualityControlProject qualityControlProject) {
        if (StringUtil.isBlank(qualityControlProject.getParentId())) {
            qualityControlProject.setParentId(null);
        }
        qualityControlProject.setStatus(1);
        qualityControlProject.setTreeId(LevelUtil.calculateLevel(getLevel(qualityControlProject.getParentId()), qualityControlProject.getParentId()));
         qualityControlProjectMapper.insert(qualityControlProject);
    }

    /**
     * 计算treeId
     *
     * @param orgId
     * @return
     */
    private String getLevel(Integer orgId) {
        if (StringUtil.isBlank(orgId)) {
            return null;
        }
        QualityControlProject byId = qualityControlProjectMapper.findById(orgId);
        if (StringUtil.isBlank(byId)) {
            return null;
        }
        return byId.getTreeId();
    }

    /**
     * @Description 修改数据
     */
    public void update(QualityControlProject qualityControlProject) {
        qualityControlProjectMapper.updateByParentId(qualityControlProject.getId(), qualityControlProject.getStatus(),
                qualityControlProject.getA1(), qualityControlProject.getA2());
         qualityControlProjectMapper.update(qualityControlProject);
    }

    /**
     * @Description 根据id删除
     */
    public void deleteById(Integer id) {
         qualityControlProjectMapper.deleteById(id);
    }

}
