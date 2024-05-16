package com.sdzk.sys.synctocloud.aop;

import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.violations.entity.TBThreeViolationsEntity;
import com.sdzk.sys.synctocloud.service.SyncToCloudService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

@Aspect
public class SyncToCloudAOP {
    @Autowired
    private SyncToCloudService syncToCloudService;

    /**
     * 风险点同步
     *
     * @param point
     * @param returnValue
     */
    @AfterReturning(pointcut = "(execution(* com.sdzk.buss.web.address.service.TBAddressInfoServiceI.save(..))) || (execution(* com.sdzk.buss.web.address.service.TBAddressInfoServiceI.updateEntitie(..))) || (execution(* com.sdzk.buss.web.address.service.TBAddressInfoServiceI.saveOrUpdate(..)))", returning = "returnValue")
    public void addressInfoReport(JoinPoint point, Object returnValue) {
        Object[] args = point.getArgs();
        if (args[0] instanceof TBAddressInfoEntity) {
            TBAddressInfoEntity bean = (TBAddressInfoEntity) args[0];
            if (StringUtils.isNotBlank(bean.getId())) {
                syncToCloudService.addressInfoReport(bean);
                syncToCloudService.addressRiskRelReport(bean.getId());
            }
        }
    }

    /**
     * 风险同步
     *
     * @param point
     * @param returnValue
     */
    @AfterReturning(pointcut = "(execution(* com.sdzk.buss.web.dangersource.service.TBDangerSourceServiceI.save(..))) || (execution(* com.sdzk.buss.web.dangersource.service.TBDangerSourceServiceI.saveOrUpdate(..)))", returning = "returnValue")
    public void dangerSourceReport(JoinPoint point, Object returnValue) {
        Object[] args = point.getArgs();
        if (args[0] instanceof TBDangerSourceEntity) {
            TBDangerSourceEntity bean = (TBDangerSourceEntity) args[0];
            if (StringUtils.isNotBlank(bean.getId())) {
                syncToCloudService.dangerSourceReport(bean);
            }
        }
    }

    /**
     * 风险点-风险关联信息同步
     *
     * @param point
     * @param returnValue
     */
    @AfterReturning(pointcut = "execution(* com.sdzk.buss.web.dangersource.controller.TBDangerSourceController.saveAddressChooseDanger(..))", returning = "returnValue")
    public void addressRiskRelReport(JoinPoint point, Object returnValue) {
        Object[] args = point.getArgs();
        if (args[0] instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) args[0];
            String addressId = request.getParameter("addressId");

            if (StringUtils.isNotBlank(addressId)) {
                syncToCloudService.addressRiskRelReport(addressId);
            }
        }
    }

    /**
     * 风险点-风险关联信息同步（全部关联操作）
     *
     * @param point
     * @param returnValue
     */
    @AfterReturning(pointcut = "execution(* com.sdzk.buss.web.dangersource.controller.TBDangerSourceController.relAll(..))", returning = "returnValue")
    public void addressRiskRelAllReport(JoinPoint point, Object returnValue) {
        Object[] args = point.getArgs();
        if (args[1] instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) args[1];
            String addressId = request.getParameter("addressId");

            if (StringUtils.isNotBlank(addressId)) {
                syncToCloudService.addressRiskRelReport(addressId);
            }
        }
    }

    /**
     * 隐患同步
     *
     * @param point
     * @param returnValue
     */
    @AfterReturning(pointcut = "(execution(* com.sdzk.buss.web.hiddendanger.service.TBHiddenDangerExamServiceI.save(..))) || (execution(* com.sdzk.buss.web.hiddendanger.service.TBHiddenDangerExamServiceI.saveOrUpdate(..))) || (execution(* com.sdzk.buss.web.hiddendanger.service.TBHiddenDangerHandleServiceI.saveOrUpdate(..)))", returning = "returnValue")
    public void hiddenDangerReport(JoinPoint point, Object returnValue) {
        Object[] args = point.getArgs();
        if (args[0] instanceof TBHiddenDangerExamEntity) {
            TBHiddenDangerExamEntity bean = (TBHiddenDangerExamEntity) args[0];
            if (StringUtils.isNotBlank(bean.getId())) {
                syncToCloudService.hiddenDangerReport(bean);
            }
        }
        if (args[0] instanceof TBHiddenDangerHandleEntity) {
            TBHiddenDangerHandleEntity bean = (TBHiddenDangerHandleEntity) args[0];
            if (StringUtils.isNotBlank(bean.getHiddenDanger().getId())) {
                syncToCloudService.hiddenDangerReport(bean.getHiddenDanger());
            }
        }
    }

    /**
     * 隐患批量同步（延期申请等操作）
     * @param point
     * @param returnValue
     */
    @AfterReturning(pointcut = "execution(* com.sdzk.buss.web.hiddendanger.controller.TBHiddenDangerHandleController.saveAllApplyIssues(..))", returning = "returnValue")
    public void hiddenDangerBatchReport(JoinPoint point, Object returnValue) {
        Object[] args = point.getArgs();
        if (args[1] instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) args[1];
            String ids = request.getParameter("ids");
            if(StringUtils.isNotBlank(ids)){
                syncToCloudService.hiddenDangerBatchReport(ids);
            }
        }
    }

    /**
     * 三违同步
     *
     * @param point
     * @param returnValue
     */
    @AfterReturning(pointcut = "(execution(* com.sdzk.buss.web.violations.service.TBThreeViolationsServiceI.save(..))) || (execution(* com.sdzk.buss.web.violations.service.TBThreeViolationsServiceI.saveOrUpdate(..)))", returning = "returnValue")
    public void violationReport(JoinPoint point, Object returnValue) {
        Object[] args = point.getArgs();
        if (args[0] instanceof TBThreeViolationsEntity) {
            TBThreeViolationsEntity bean = (TBThreeViolationsEntity) args[0];
            if (StringUtils.isNotBlank(bean.getId())) {
                syncToCloudService.violationReport(bean);
            }
        }
    }
}
