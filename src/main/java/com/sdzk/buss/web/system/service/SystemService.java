package com.sdzk.buss.web.system.service;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.*;

import java.util.List;
import java.util.Set;

/**
 *
 * @author  张代浩
 *
 */
public interface SystemService extends CommonService {
	/**
 	 * 方法描述:  查询数据字典
 	 * 作    者： yiming.zhang
 	 * 日    期： 2014年5月11日-下午4:22:42
 	 * @param dicTable
 	 * @param dicCode
 	 * @param dicText
 	 * @return
 	 * 返回类型： List<DictEntity>
 	 */
 	List<DictEntity> queryDict(String dicTable, String dicCode, String dicText);

	/**
	 * 登陆用户检查
	 * @param user
	 * @return
	 * @throws Exception
	 */
	TSUser checkUserExits(TSUser user) throws Exception;
	/**
	 * 日志添加
	 * @param logContent 内容
	 * @param logLevel 级别
	 * @param operateType 类型
	 */
	void addLog(String logContent, Short logLevel, Short operateType);
	/**
	 * 根据类型编码和类型名称获取Type,如果为空则创建一个
	 * @param typecode
	 * @param typename
	 * @return
	 */
	TSType getType(String typecode, String typename, TSTypegroup tsTypegroup);
	/**
	 * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
	 * @param typecode
	 * @param typename
	 * @return
	 */
	TSTypegroup getTypeGroup(String typegroupcode, String typgroupename);
	/**
	 * 根据用户ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * @param roleId
	 * @param functionId
	 * @return
	 */
	 Set<String> getOperationCodesByUserIdAndFunctionId(String userId, String functionId);
	/**
	 * 根据角色ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * @param roleId
	 * @param functionId
	 * @return
	 */
	 Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId, String functionId);
	/**
	 * 根据编码获取字典组
	 *
	 * @param typegroupCode
	 * @return
	 */
	TSTypegroup getTypeGroupByCode(String typegroupCode);
	/**
	 * 对数据字典进行缓存
	 */
	void initAllTypeGroups();

	/**
	 * 刷新字典缓存
	 * @param type
	 */
	void refleshTypesCach(TSType type);
	/**
	 * 刷新字典分组缓存
	 */
	void refleshTypeGroupCach();
	/**
	 * 刷新菜单
	 *
	 * @param id
	 */
	void flushRoleFunciton(String id, TSFunction newFunciton);

    /**
     * 生成组织机构编码
     * @param id 组织机构主键
     * @param pid 组织机构的父级主键
     * @return 组织机构编码
     */
	String generateOrgCode(String id, String pid);

	/**
	 *
	  * getOperationCodesByRoleIdAndruleDataId
	  * 根据角色id 和 菜单Id 获取 具有操作权限的数据规则
	  *
	  * @Title: getOperationCodesByRoleIdAndruleDataId
	  * @Description: TODO
	  * @param @param roleId
	  * @param @param functionId
	  * @param @return    设定文件
	  * @return Set<String>    返回类型
	  * @throws
	 */

	 Set<String> getOperationCodesByRoleIdAndruleDataId(String roleId, String functionId);

	 Set<String> getOperationCodesByUserIdAndDataId(String userId, String functionId);

	/**
	 * 加载所有图标
	 * @return
	 */
	 void initAllTSIcons();

	/**
	 * 更新图标
	 * @param icon
	 */
	 void upTSIcons(TSIcon icon);
	/**
	 * 删除图标
	 * @param icon
	 */
	 void delTSIcons(TSIcon icon);

	/**
	 * 添加数据日志
	 * @param tableName		操作表名
	 * @param dataId		数据ID
	 * @param dataContent	内容(JSON格式)
	 */
	void addDataLog(String tableName, String dataId, String dataContent);
}
