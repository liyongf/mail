package com.sdzk.buss.web.common.service;

import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.gjj.entity.*;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSLog;

import java.util.List;
import java.util.Map;

/**
 * 上报省局
 *
 * @author ljh
 * @version 1.0
 * @date 2023/10/26 9:25
 */
public interface ReportToProvinceService extends CommonService {
    /**
     * 1.风险辨识任务上报
     *
     * @param list 辨识任务
     * @return 上报条数
     */
    AjaxJson reportRiskIdentTask(List<Map<String, Object>> list);

    /**
     * 2.图片上报
     *
     * @param list 数据
     * @return 结果
     */
    AjaxJson reportPicture(List<SFPictureInfoEntity> list);

    /**
     * 2.图片上报
     *
     * @param list 数据
     * @return 结果
     */

    AjaxJson reportRiskControl(List<SfRiskControlEntity> list);

    /**
     * 2.图片上报
     *
     * @param list 数据
     * @return 结果
     */

    AjaxJson reportRiskMeasure(List<SfRiskMeasureEntity> list);

    /**
     * 2.图片上报
     *
     * @param list 数据
     * @return 结果
     */

    AjaxJson reportLoginLog(List<TSLog> list, String userId);

    /**
     * 2.图片上报
     *
     * @param list 数据
     * @return 结果
     */

    AjaxJson reportReportInfo(List<SFReportInfoEntity> list);

    /**
     * 2.图片上报
     *
     * @param list 数据
     * @return 结果
     */


    AjaxJson sfArchiveInfo(List<SfArchiveInfoEntity> list);

    /**
     * 2.图片上报
     *
     * @param list 数据
     * @return 结果
     */


    AjaxJson sfPlanInfo(List<SfPlanInfoEntity> list);

    /**
     * 2.图片上报
     *
     * @param list 数据
     * @return 结果
     */

    AjaxJson sfSysFileInfo(List<SfSysFileInfoEntity> list);

    /**
     * 2.图片上报
     *
     * @param list 数据
     * @return 结果
     */

    AjaxJson sfTrainingInfo(List<SfTrainingInfoEntity> list);

    /**
     * 11.风险点上报
     *
     * @param list 数据
     * @return 结果
     */
    AjaxJson reportRiskPoint(List<TBAddressInfoEntity> list);

    /**
     * 12.风险清单上报
     *
     * @param list 数据
     * @return 结果
     */
    AjaxJson reportRisk(List<RiskIdentificationEntity> list);

    /**
     * 13.三违上报
     *
     * @param list 数据
     * @return 结果
     */
    AjaxJson reportThreeVio(List<Map<String, Object>> list);
    /**
     * 14.隐患上报
     *
     * @param list 数据
     * @return 结果
     */
    AjaxJson reportHidden(List<Map<String, Object>> list);


}
