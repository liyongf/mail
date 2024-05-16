package com.sdzk.sys.synctocloud.service;

import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.dangersource.entity.TBDangerAddresstRelEntity;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.violations.entity.TBThreeViolationsEntity;
import org.jeecgframework.core.common.service.CommonService;

public interface SyncToCloudService extends CommonService {

    void addressInfoReport(TBAddressInfoEntity addressInfo);

    void dangerSourceReport(TBDangerSourceEntity dangerSource);
    void dangerSourceBatchReport(String ids);

    void addressRiskRelReport(String addressId);

    void hiddenDangerReport(TBHiddenDangerExamEntity hiddenDanger);
    void hiddenDangerBatchReport(String ids);

    void violationReport(TBThreeViolationsEntity violations);
    void violationBatchReport(String ids);
}
