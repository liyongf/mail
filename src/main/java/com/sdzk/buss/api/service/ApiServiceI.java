package com.sdzk.buss.api.service;

import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import net.sf.json.JSONArray;
import org.jeecgframework.web.system.pojo.base.TSUser;

import java.util.List;
import java.util.Map;

public interface ApiServiceI {
	/**
	 * 登录信息认证
     * @return
     */
	public ApiResultJson mobileLogin(TSUser user);


	/**
	 * 获取矿井秘钥
	 * @return
     */
	public String getLocalToken();

    /**
     * 同步数据字典
     * @return
     * */
    public List<Map<String, Object>> mobileSyncDataDic();


	/**
	 * 同步地点
	 * @return
	 * */
	public List<Map<String, Object>> mobileSyncDataLocation();

	/**
	 * 同步岗位
	 * @return
	 * */
	public List<Map<String, Object>> mobileSyncDataPostName();

	/**
	 * 同步风险
	 * @return
	 * */
	public List<Map<String, Object>> mobileSyncDataRisk();

	/**
	 * 同步我的管控清单
	 * @return
	 * */
	public List<Map<String, Object>> mobileSyncDataRiskManage(TSUser user);

	/**
	 * 同步我的管控清单关联的危害因素
	 * @return
	 * */
	public List<Map<String, Object>> mobileSyncDataRiskManageHazardFactor(TSUser user);


	/**
	 * 同步我的管控任务
	 * @return
	 * */
	public List<Map<String, Object>> mobileSyncDataRiskManageTask(TSUser user);

	/**
	 * 同步我的管控任务关联的风险
	 * @return
	 * */
	public List<Map<String, Object>> mobileSyncDataRiskManageTaskRisk(TSUser user);

	/**
	 * 同步我的管控任务关联的风险的危害因素
	 * @return
	 * */
	public List<Map<String, Object>> mobileSyncDataRiskManageTaskHazardFactor(TSUser user);


	/**
	 * 同步人员
	 * @return
	 * */
	public List<Map<String, Object>> mobileSyncDataUser();
	/**
	 * 同步人员与部门之间的关系
	 * @return
	 * */
	public List<Map<String, Object>> mobileSyncDataUserOrg();

	/**
	 * 同步部门
	 * @return
	 * */
	public List<Map<String, Object>> mobileSyncDataDepart();


	/**
	 * 登出
	 * @param user
	 * @return
     */
	ApiResultJson mobileLogout(String sessionId);

	/**
	 * 修改用户密码
	 * @return
	 * */
	ApiResultJson changePassword(TSUser user, String newPassword);

	/**
	 * 整改
	 * @param entity
     */
	void rectify(TBHiddenDangerHandleEntity entity);

	/**
	 * 复查
	 * @param entity
	 * @param limitDate
     */
	void review(TBHiddenDangerHandleEntity entity, String limitDate);

    /**
     * 待办任务统计
     @param user
     */
    ApiResultJson hiddenDangerTaskCount(TSUser user);

    /**
	 *  获取集团推送的隐患
	 * */
    public JSONArray pushedHiddenDangerReceive(String mineCode, String reportContent);

	/**
	 * 同步隐患-我的任务列表
	 * @return
	 * */
	public List<Map<String, Object>> mobileSyncDataHiddenTask(TSUser user);

	/**
	 * 同步隐患-待上报隐患数据
	 * @param examIds
	 * @return
	 */
	public List<Map<String, Object>> mobileSyncDataNotUploadHiddenTask(TSUser user, String examIds);
}
