/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年05月27日
 * 修改日期：2020年05月27日
 */
package com.sdzk.buss.web.aqbzh.service.impl;



import com.sdzk.buss.web.aqbzh.dao.QualityControlModuleMapper;
import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlModule;
import com.sdzk.buss.web.aqbzh.pojo.dto.QualityControlModuleDTO;
import com.sdzk.buss.web.aqbzh.service.IQualityControlModuleService;
import com.sdzk.buss.web.aqbzh.util.LevelUtil;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;
import com.sdzk.buss.web.aqbzh.util.StringUtil;
import com.sdzk.buss.web.system.service.SystemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * @Description ServiceImpl
 * @author：Tony
 * @version：V1.0
 * @since：2020年05月27日
 */
@Service(value = "qualityControlModuleServiceImpl")
public class QualityControlModuleServiceImpl implements IQualityControlModuleService {

    @Autowired
    private QualityControlModuleMapper qualityControlModuleMapper;

    @Autowired
    private org.jeecgframework.web.system.service.SystemService systemService;

    /**
     * @Description 根据条件查询-分页
     */
    public List<QualityControlModuleDTO> findByList(String year, String month, String duokuangCode) {
        //根据年月查询最底层模块
        List<QualityControlModule> byListIds = qualityControlModuleMapper.findByListIds(year, month, null,duokuangCode);
        QualityControlModule qualityControlModule = new QualityControlModule();
        //查询所有模块
        qualityControlModule.setA2(1);
        List<QualityControlModule> qualityControlModuleList1 = qualityControlModuleMapper.findByPage(null,qualityControlModule);
        Map<Integer, QualityControlModule> byPage1Map = new HashMap<Integer, QualityControlModule>();
        for(int i=0;i<qualityControlModuleList1.size();i++){
            byPage1Map.put(qualityControlModuleList1.get(i).getId(),qualityControlModuleList1.get(i));
        }
        QualityControlModuleDTO qualityControlModuleDTO;
        List<QualityControlModuleDTO> listArr = new ArrayList<>();
        StringBuffer stringBuffer;
        String sb = "1";//拼接排序
        for (QualityControlModule qualityControlModuleDTO1 : byListIds) {
            sb = String.valueOf(qualityControlModuleDTO1.getSort());
            qualityControlModuleDTO = new QualityControlModuleDTO();
            BeanUtils.copyProperties(qualityControlModuleDTO1, qualityControlModuleDTO);
            //根据parentId去上一层数据
            QualityControlModule qualityControlModule1 = byPage1Map.get(qualityControlModuleDTO1.getParentId());
            if (qualityControlModule1 != null && StringUtil.isNotBlank(qualityControlModule1.getModuleName())) {
                sb += String.valueOf(qualityControlModule1.getSort());
                qualityControlModuleDTO.setNum(qualityControlModule1.getSort());
                qualityControlModuleDTO.setModuleName1(qualityControlModule1.getModuleName());
            } else {
                sb += String.valueOf(qualityControlModuleDTO1.getSort());
                qualityControlModuleDTO.setModuleName1(qualityControlModuleDTO1.getModuleName());
                qualityControlModuleDTO.setNum(qualityControlModuleDTO1.getSort());
            }
            stringBuffer = new StringBuffer(sb);
            stringBuffer.reverse();
            qualityControlModuleDTO.setSort(Integer.valueOf(stringBuffer.toString()));
            listArr.add(qualityControlModuleDTO);
        }
        // 排序
        Collections.sort(listArr, qualityControlModuleDTOConparator);
        return listArr;
    }

    public Comparator<QualityControlModuleDTO> qualityControlModuleDTOConparator = new Comparator<QualityControlModuleDTO>() {
        @Override
        public int compare(QualityControlModuleDTO o1, QualityControlModuleDTO o2) {
            return o1.getSort() - o2.getSort();
        }
    };

    /**
     * @Description 根据条件查询-分页
     */
    public List<QualityControlModule> findByPage(PageQuery pageQuery, QualityControlModule qualityControlModule) {
        List<QualityControlModule> qualityControlModuleList = qualityControlModuleMapper.findByPage(pageQuery,qualityControlModule);
        return qualityControlModuleList;
    }
    /**
     * @Description 根据条件查询-分页
     */
    public PageInfo<QualityControlModule> findByPage2(PageQuery pageQuery, QualityControlModule qualityControlModule) {
        PageInfo<QualityControlModule> qualityControlModuleList = qualityControlModuleMapper.findByPage2(pageQuery,qualityControlModule);
        return qualityControlModuleList;
    }
    /**
     * @Description 根据id查询
     */
    public QualityControlModule findById(Integer id) {
        return qualityControlModuleMapper.findById(id);
    }

    /**
     * @Description 根据id查询
     */
    public List<QualityControlModule> findByIdList(List<Integer> idList) {
        return qualityControlModuleMapper.findByIdList(idList);
    }

    /**
     * @Description 添加数据
     */
    public void insert(QualityControlModule qualityControlModule) {
        if (StringUtil.isBlank(qualityControlModule.getParentId())) {
            qualityControlModule.setParentId(null);
        }
        qualityControlModule.setA1(LevelUtil.calculateLevel(getLevel(qualityControlModule.getParentId()), qualityControlModule.getParentId()));
         qualityControlModuleMapper.insert(qualityControlModule);
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
        QualityControlModule byId = qualityControlModuleMapper.findById(orgId);
        if (StringUtil.isBlank(byId)) {
            return null;
        }
        return byId.getA1();
    }

    /**
     * @Description 修改数据
     */
    public void update(QualityControlModule qualityControlModule) {
        qualityControlModuleMapper.updateByParentId(qualityControlModule.getId(), qualityControlModule.getIsAsses());
        qualityControlModuleMapper.update(qualityControlModule);
    }

    /**
     * @Description 根据id删除
     */
    public void deleteById(Integer id) {
         qualityControlModuleMapper.deleteById(id);
    }

}
