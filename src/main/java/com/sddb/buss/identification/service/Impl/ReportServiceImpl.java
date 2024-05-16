package com.sddb.buss.identification.service.Impl;

import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sddb.buss.identification.service.ReportServiceI;
import com.sddb.buss.riskdata.entity.HazardFactorsEntity;
import com.sddb.common.Constants;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lenovo on 2019/1/8.
 */
@Service("reportService")
public class ReportServiceImpl implements ReportServiceI {
    @Override
    public CriteriaQuery queryRiskReportList(HttpServletRequest request,DataGrid dataGrid){
        String address = request.getParameter("address.address");
        String postName = request.getParameter("post.postName");
        String riskType = request.getParameter("riskType");
        String riskLevel = request.getParameter("riskLevel");
        String manageLevel = request.getParameter("manageLevel");
        String dutyManager = request.getParameter("dutyManager");
        String addressId = request.getParameter("addressId");
        String postId = request.getParameter("postId");
        String identificationTypeArr[] = request.getParameterValues("identificationType");
        CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class, dataGrid);
        try {
            cq.eq("status", Constants.RISK_IDENTIFI_STATUS_REVIEW);
            cq.eq("isDel",Constants.RISK_IS_DEL_FALSE);
            if(StringUtils.isNotBlank(address)){
                cq.createAlias("address","address");
                cq.like("address.address","%"+address+"%");
            }
            if(StringUtils.isNotBlank(postName)){
                cq.createAlias("post","post");
                cq.like("post.postName","%"+postName+"%");
            }
            if(StringUtils.isNotBlank(riskType)){
                cq.eq("riskType",riskType);
            }
            if(StringUtils.isNotBlank(riskLevel)){
                cq.eq("riskLevel",riskLevel);
            }
            if(StringUtils.isNotBlank(manageLevel)){
                cq.eq("manageLevel",manageLevel);
            }
            if(StringUtils.isNotBlank(dutyManager)){
                cq.like("dutyManager","%"+dutyManager+"%");
            }

            if(StringUtils.isNotBlank(addressId)){
                cq.createAlias("address","address");
                cq.eq("address.id",addressId);
            }
            if(StringUtils.isNotBlank(postId)){
                cq.createAlias("post","post");
                cq.eq("post.id",postId);
            }
            if(identificationTypeArr.length>0){
                for(int i=0;i<identificationTypeArr.length;i++){
                    cq.eq("identificationType",identificationTypeArr[i]);
                }
            }
            //上报煤监状态
            String reportStatusProvince = request.getParameter("reportStatusProvince");
            if(StringUtil.isNotEmpty(reportStatusProvince)){
                if(reportStatusProvince.equals(com.sdzk.buss.web.common.taskProvince.Constants.REPORTED_NOT)){
                    cq.or(Restrictions.isNull("reportStatusProvince"),Restrictions.eq("reportStatusProvince",Integer.parseInt(com.sdzk.buss.web.common.taskProvince.Constants.REPORTED_NOT)));
                }else{
                    cq.eq("reportStatusProvince",Integer.parseInt(com.sdzk.buss.web.common.taskProvince.Constants.REPORTED_ALREADY));
                }
            }else{
                cq.or(Restrictions.isNull("reportStatusProvince"),Restrictions.eq("reportStatusProvince",Integer.parseInt(com.sdzk.buss.web.common.taskProvince.Constants.REPORTED_NOT)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        return cq;
    }

    @Override
    public CriteriaQuery queryHazardFactorReportList(HttpServletRequest request,DataGrid dataGrid){
        String riskType = request.getParameter("riskType");
        String major = request.getParameter("major");
        String riskLevel = request.getParameter("riskLevel");
        CriteriaQuery cq = new CriteriaQuery(HazardFactorsEntity.class, dataGrid);
        try{
            cq.eq("isDel",Constants.HAZARDFACTORS_IS_DEL_FALSE);
            if(StringUtils.isNotBlank(riskType)){
                cq.eq("riskType",riskType);
            }
            if(StringUtils.isNotBlank(major)){
                cq.eq("major",major);
            }
            if(StringUtils.isNotBlank(riskLevel)){
                cq.eq("riskLevel",riskLevel);
            }
            //内置条件审批通过的危害因素
            cq.eq("status",Constants.HAZARDFACTORS_STATUS_REVIEW);
            //上报煤监状态
            String reportStatusProvince = request.getParameter("reportStatusProvince");
            if(StringUtil.isNotEmpty(reportStatusProvince)){
                if(reportStatusProvince.equals(com.sdzk.buss.web.common.taskProvince.Constants.REPORTED_NOT)){
                    cq.or(Restrictions.isNull("reportStatusProvince"),Restrictions.eq("reportStatusProvince",Integer.parseInt(com.sdzk.buss.web.common.taskProvince.Constants.REPORTED_NOT)));
                }else{
                    cq.eq("reportStatusProvince",Integer.parseInt(com.sdzk.buss.web.common.taskProvince.Constants.REPORTED_ALREADY));
                }
            }else{
                cq.or(Restrictions.isNull("reportStatusProvince"),Restrictions.eq("reportStatusProvince",Integer.parseInt(com.sdzk.buss.web.common.taskProvince.Constants.REPORTED_NOT)));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        return cq;
    };

}
