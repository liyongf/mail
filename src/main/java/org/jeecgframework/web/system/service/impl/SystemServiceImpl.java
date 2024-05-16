package org.jeecgframework.web.system.service.impl;

import java.io.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.utils.SemanticSimilarityUtil;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.dao.JeecgDictDao;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("systemService")
@Transactional
public class SystemServiceImpl extends CommonServiceImpl implements SystemService {
	@Autowired
	private JeecgDictDao jeecgDictDao;

	public TSUser checkUserExits(TSUser user) throws Exception {
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}

	public List<DictEntity> queryDict(String dicTable, String dicCode,String dicText){
		List<DictEntity> dictList = null;
		//step.1 如果没有字典表则使用系统字典表
		if(StringUtil.isEmpty(dicTable)){
			dictList = jeecgDictDao.querySystemDict(dicCode);
			for(DictEntity t:dictList){
				t.setTypename(MutiLangUtil.getMutiLangInstance().getLang(t.getTypename()));
			}
		}else {
			dicText = StringUtil.isEmpty(dicText, dicCode);
			dictList = jeecgDictDao.queryCustomDict(dicTable, dicCode, dicText);
		}
		return dictList;
	}

	/**
	 * 添加日志
	 */
	public void addLog(String logcontent, Short loglevel, Short operatetype) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String broswer = BrowserUtils.checkBrowse(request);
		TSLog log = new TSLog();
		log.setLogcontent(logcontent);
		log.setLoglevel(loglevel);
		log.setOperatetype(operatetype);
		log.setNote(oConvertUtils.getIp());
		log.setBroswer(broswer);
		/*start dangzhenghui 201703016TASK #1784 【online bug】Online 表单保存的时候，报错*/
		log.setOperatetime(new Date());
		/* end dangzhenghui 201703016TASK #1784 【online bug】Online 表单保存的时候，报错*/
		//登录日志添加时矿端数据操作标识stateFlag设为1(未上报)
		if("1".equals(loglevel.toString()) && "1".equals(operatetype.toString())){
			log.setStateFlag(Constants.GJJ_STATE_FLAG_1);
		}
		log.setTSUser(ResourceUtil.getSessionUserName());
		commonDao.save(log);
	}


	public void addLogAppLogin(String logcontent, Short loglevel, Short operatetype,String userId) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String broswer = "app";
		TSLog log = new TSLog();
		log.setLogcontent(logcontent);
		log.setLoglevel(loglevel);
		log.setOperatetype(operatetype);
		log.setNote(oConvertUtils.getIp());
		log.setBroswer(broswer);
		/*start dangzhenghui 201703016TASK #1784 【online bug】Online 表单保存的时候，报错*/
		log.setOperatetime(new Date());
		/* end dangzhenghui 201703016TASK #1784 【online bug】Online 表单保存的时候，报错*/
		TSUser user = commonDao.getEntity(TSUser.class,userId);
		log.setTSUser(user);
		//登录日志添加时矿端数据操作标识stateFlag设为1(未上报)
		if("1".equals(loglevel.toString()) && "1".equals(operatetype.toString())){
			log.setStateFlag(Constants.GJJ_STATE_FLAG_1);
		}
		commonDao.save(log);
	}
	/**
	 * 根据类型编码和类型名称获取Type,如果为空则创建一个
	 *
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public TSType getType(String typecode, String typename, TSTypegroup tsTypegroup) {
		//TSType actType = commonDao.findUniqueByProperty(TSType.class, "typecode", typecode,tsTypegroup.getId());
		List<TSType> ls = commonDao.findHql("from TSType where typecode = ? and typegroupid = ?",typecode,tsTypegroup.getId());
		TSType actType = null;
		if (ls == null || ls.size()==0) {
			actType = new TSType();
			actType.setTypecode(typecode);
			actType.setTypename(typename);
			actType.setTSTypegroup(tsTypegroup);
			commonDao.save(actType);
		}else{
			actType = ls.get(0);
		}
		return actType;

	}

	/**
	 * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
	 *
	 * @param typegroupcode
	 * @param typgroupename
	 * @return
	 */
	public TSTypegroup getTypeGroup(String typegroupcode, String typgroupename) {
		TSTypegroup tsTypegroup = commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode", typegroupcode);
		if (tsTypegroup == null) {
			tsTypegroup = new TSTypegroup();
			tsTypegroup.setTypegroupcode(typegroupcode);
			tsTypegroup.setTypegroupname(typgroupename);
			commonDao.save(tsTypegroup);
		}
		return tsTypegroup;
	}


	public TSTypegroup getTypeGroupByCode(String typegroupCode) {
		TSTypegroup tsTypegroup = commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode", typegroupCode);
		return tsTypegroup;
	}


	public void initAllTypeGroups() {
		List<TSTypegroup> typeGroups = this.commonDao.loadAll(TSTypegroup.class);
		ResourceUtil.dicMapCache = new HashMap<>();
		List<Map<String,Object>> tempList = new ArrayList<>();
		Map<String,Object> tempMap = new HashMap<>();
		for (TSTypegroup tsTypegroup : typeGroups) {
			ResourceUtil.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
			List<TSType> types = this.commonDao.findHql("from TSType where TSTypegroup.id='"+tsTypegroup.getId()+"' ")/*.findByProperty(TSType.class, "TSTypegroup.id", tsTypegroup.getId())*/;
			ResourceUtil.allTypes.put(tsTypegroup.getTypegroupcode().toLowerCase(), types);

			tempList = new ArrayList<>();
			for (int i = 0; i < types.size(); i++) {
				tempMap = new HashMap<>();
				tempMap.put(types.get(i).getTypecode(),types.get(i).getTypename());
				tempList.add(tempMap);
			}
			ResourceUtil.dicMapCache.put(tsTypegroup.getTypegroupcode().toLowerCase(),tempList);

			TSTypegroup.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
			TSTypegroup.allTypes.put(tsTypegroup.getTypegroupcode().toLowerCase(), types);


		}
	}


	@Override
	public void initAllDepartCach() {
		List<TSDepart> departs = this.commonDao.findHql(" from TSDepart where deleteFlag = '0' order by departOrder asc");
		ResourceUtil.allDepart = new ArrayList<>();
		ResourceUtil.departMapCache = new HashMap<>();
		Map<String, Object> tempMap = new HashMap<>();
		for (int i = 0; i <departs.size() ; i++) {
			tempMap = new HashMap<>();
			tempMap.put("value",departs.get(i).getId());
			tempMap.put("name",departs.get(i).getDepartname());
			ResourceUtil.allDepart.add(tempMap);
			ResourceUtil.departMapCache.put(departs.get(i).getId(),departs.get(i).getDepartname());
		}
	}

	@Override
	public void initAllUserCach() {
		List<TSBaseUser> users = this.commonDao.findHql(" from TSBaseUser where deleteFlag = '0'");
		ResourceUtil.allUser = new ArrayList<>();
		ResourceUtil.userMapCache = new HashMap<>();
		Map<String, Object> tempMap = new HashMap<>();
		for (int i = 0; i <users.size() ; i++) {
			tempMap = new HashMap<>();
			tempMap.put("value",users.get(i).getId());
			tempMap.put("name",users.get(i).getRealName());
			ResourceUtil.allUser.add(tempMap);
			ResourceUtil.userMapCache.put(users.get(i).getId(),users.get(i).getRealName());
		}
	}


	public void refleshTypesCach(TSType type) {
		TSTypegroup tsTypegroup = type.getTSTypegroup();
		TSTypegroup typeGroupEntity = this.commonDao.get(TSTypegroup.class, tsTypegroup.getId());
		List<TSType> types = this.commonDao.findByProperty(TSType.class, "TSTypegroup.id", tsTypegroup.getId());
		ResourceUtil.allTypes.put(typeGroupEntity.getTypegroupcode().toLowerCase(), types);
	}

	public void refleshTypesCach(String typeGroupCode) {
		List<TSType> types = this.commonDao.findHql("from TSType where TSTypegroup.typegroupcode=?  ", typeGroupCode);
		ResourceUtil.allTypes.put(typeGroupCode.toLowerCase(), types);
	}


	public void refleshTypeGroupCach() {
		ResourceUtil.allTypeGroups.clear();
		List<TSTypegroup> typeGroups = this.commonDao.loadAll(TSTypegroup.class);
		for (TSTypegroup tsTypegroup : typeGroups) {
			ResourceUtil.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
		}
	}

	public  void refleshTypeGroupCachDeep(){
		ResourceUtil.allTypeGroups.clear();
		List<TSTypegroup> typeGroups = this.commonDao.loadAll(TSTypegroup.class);
		for (TSTypegroup tsTypegroup : typeGroups) {
			ResourceUtil.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
			TSTypegroup typeGroupEntity = this.commonDao.get(TSTypegroup.class, tsTypegroup.getId());
			List<TSType> types = this.commonDao.findByProperty(TSType.class, "TSTypegroup.id", tsTypegroup.getId());
			ResourceUtil.allTypes.put(typeGroupEntity.getTypegroupcode().toLowerCase(), types);
		}

	}


	/**
	 * 根据角色ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * @param roleId
	 * @param functionId
	 * @return
	 */
	public Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId, String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		TSRole role = commonDao.get(TSRole.class, roleId);
		CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
		cq1.eq("TSRole.id", role.getId());
		cq1.eq("TSFunction.id", functionId);
		cq1.add();
		List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
		if (null != rFunctions && rFunctions.size() > 0) {
			TSRoleFunction tsRoleFunction = rFunctions.get(0);
			if (null != tsRoleFunction.getOperation()) {
				String[] operationArry = tsRoleFunction.getOperation().split(",");
				for (int i = 0; i < operationArry.length; i++) {
					operationCodes.add(operationArry[i]);
				}
			}
		}
		return operationCodes;
	}

	/**
	 * 根据用户ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * @param userId
	 * @param functionId
	 * @return
	 */
	public Set<String> getOperationCodesByUserIdAndFunctionId(String userId, String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		List<TSRoleUser> rUsers = findByProperty(TSRoleUser.class, "TSUser.id", userId);
		for (TSRoleUser ru : rUsers) {
			TSRole role = ru.getTSRole();
			CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
			cq1.eq("TSRole.id", role.getId());
			cq1.eq("TSFunction.id", functionId);
			cq1.add();
			List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
			if (null != rFunctions && rFunctions.size() > 0) {
				TSRoleFunction tsRoleFunction = rFunctions.get(0);
				if (null != tsRoleFunction.getOperation()) {
					String[] operationArry = tsRoleFunction.getOperation().split(",");
					for (int i = 0; i < operationArry.length; i++) {
						operationCodes.add(operationArry[i]);
					}
				}
			}
		}
		return operationCodes;
	}
	/**
	 * 获取页面控件权限控制的
	 * JS片段
	 */
	public String getAuthFilterJS() {
		StringBuilder out = new StringBuilder();
		out.append("<script type=\"text/javascript\">");
		out.append("$(document).ready(function(){");
		if(ResourceUtil.getSessionUserName().getUserName().equals("admin")|| !Globals.BUTTON_AUTHORITY_CHECK){
			return "";
		}else{
			HttpServletRequest request = ContextHolderUtils.getRequest();
			Set<String> operationCodes = (Set<String>) request.getAttribute(Globals.OPERATIONCODES);
			if (null!=operationCodes) {
				for (String MyoperationCode : operationCodes) {
					if (oConvertUtils.isEmpty(MyoperationCode))
						break;
					TSOperation operation = this.getEntity(TSOperation.class, MyoperationCode);
					if (operation.getOperationcode().startsWith(".") || operation.getOperationcode().startsWith("#")){
						if (operation.getOperationType().intValue()==Globals.OPERATION_TYPE_HIDE){
							//out.append("$(\""+name+"\").find(\"#"+operation.getOperationcode().replaceAll(" ", "")+"\").hide();");
							out.append("$(\""+operation.getOperationcode().replaceAll(" ", "")+"\").hide();");
						}else {
							//out.append("$(\""+name+"\").find(\"#"+operation.getOperationcode().replaceAll(" ", "")+"\").find(\":input\").attr(\"disabled\",\"disabled\");");
							out.append("$(\""+operation.getOperationcode().replaceAll(" ", "")+"\").attr(\"disabled\",\"disabled\");");
							out.append("$(\""+operation.getOperationcode().replaceAll(" ", "")+"\").find(\":input\").attr(\"disabled\",\"disabled\");");
						}
					}
				}
			}else{
				return "";
			}

		}
		out.append("});");
		out.append("</script>");
		return out.toString();
	}

	public void flushRoleFunciton(String id, TSFunction newFunction) {
		TSFunction functionEntity = this.getEntity(TSFunction.class, id);
		if (functionEntity.getTSIcon() == null || !StringUtil.isNotEmpty(functionEntity.getTSIcon().getId())) {
			return;
		}
		TSIcon oldIcon = this.getEntity(TSIcon.class, functionEntity.getTSIcon().getId());
		if (!oldIcon.getIconClas().equals(newFunction.getTSIcon().getIconClas())) {
			// 刷新缓存
			HttpSession session = ContextHolderUtils.getSession();
			TSUser user = ResourceUtil.getSessionUserName();
			List<TSRoleUser> rUsers = this.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				session.removeAttribute(role.getId());
			}
		}
	}

	public String generateOrgCode(String id, String pid) {

		int orgCodeLength = 2; // 默认编码长度
		if ("3".equals(ResourceUtil.getOrgCodeLengthType())) { // 类型2-编码长度为3，如001
			orgCodeLength = 3;
		}


		String  newOrgCode = "";
		if(!StringUtils.hasText(pid)) { // 第一级编码
			String sql = "select max(t.org_code) orgCode from t_s_depart t where t.parentdepartid is null";
			Map<String, Object> pOrgCodeMap = commonDao.findOneForJdbc(sql);
			if(pOrgCodeMap.get("orgCode") != null) {
				String curOrgCode = pOrgCodeMap.get("orgCode").toString();
				newOrgCode = String.format("%0" + orgCodeLength + "d", Integer.valueOf(curOrgCode) + 1);
			} else {
				newOrgCode = String.format("%0" + orgCodeLength + "d", 1);
			}
		} else { // 下级编码
			String sql = "select max(t.org_code) orgCode from t_s_depart t where t.parentdepartid = ?";
			Map<String, Object> orgCodeMap = commonDao.findOneForJdbc(sql, pid);
			if(orgCodeMap.get("orgCode") != null) { // 当前基本有编码时
				String curOrgCode = orgCodeMap.get("orgCode").toString();
				String pOrgCode = curOrgCode.substring(0, curOrgCode.length() - orgCodeLength);
				String subOrgCode = curOrgCode.substring(curOrgCode.length() - orgCodeLength, curOrgCode.length());
				newOrgCode = pOrgCode + String.format("%0" + orgCodeLength + "d", Integer.valueOf(subOrgCode) + 1);
			} else { // 当前级别没有编码时
				String pOrgCodeSql = "select max(t.org_code) orgCode from t_s_depart t where t.id = ?";
				Map<String, Object> pOrgCodeMap = commonDao.findOneForJdbc(pOrgCodeSql, pid);
				String curOrgCode = pOrgCodeMap.get("orgCode").toString();
				newOrgCode = curOrgCode + String.format("%0" + orgCodeLength + "d", 1);
			}
		}

		return newOrgCode;
	}

	public Set<String> getOperationCodesByRoleIdAndruleDataId(String roleId,
															  String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		TSRole role = commonDao.get(TSRole.class, roleId);
		CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
		cq1.eq("TSRole.id", role.getId());
		cq1.eq("TSFunction.id", functionId);
		cq1.add();
		List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
		if (null != rFunctions && rFunctions.size() > 0) {
			TSRoleFunction tsRoleFunction = rFunctions.get(0);
			if (null != tsRoleFunction.getDataRule()) {
				String[] operationArry = tsRoleFunction.getDataRule().split(",");
				for (int i = 0; i < operationArry.length; i++) {
					operationCodes.add(operationArry[i]);
				}
			}
		}
		return operationCodes;
	}

	public Set<String> getOperationCodesByUserIdAndDataId(String userId,
														  String functionId) {
		// TODO Auto-generated method stub
		Set<String> dataRulecodes = new HashSet<String>();
		List<TSRoleUser> rUsers = findByProperty(TSRoleUser.class, "TSUser.id", userId);
		for (TSRoleUser ru : rUsers) {
			TSRole role = ru.getTSRole();
			CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
			cq1.eq("TSRole.id", role.getId());
			cq1.eq("TSFunction.id", functionId);
			cq1.add();
			List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
			if (null != rFunctions && rFunctions.size() > 0) {
				TSRoleFunction tsRoleFunction = rFunctions.get(0);
				if (null != tsRoleFunction.getDataRule()) {
					String[] operationArry = tsRoleFunction.getDataRule().split(",");
					for (int i = 0; i < operationArry.length; i++) {
						dataRulecodes.add(operationArry[i]);
					}
				}
			}
		}
		return dataRulecodes;
	}
	/**
	 * 加载所有图标
	 * @return
	 */
	public  void initAllTSIcons() {
		List<TSIcon> list = this.loadAll(TSIcon.class);
		for (TSIcon tsIcon : list) {
			ResourceUtil.allTSIcons.put(tsIcon.getId(), tsIcon);
		}
	}
	/**
	 * 所有风险中的隐患描述进行分词
	 * @return
	 */
	public  void initAllDSParts() {
		Thread thread = new Thread(){
			public void run(){
				System.out.println("风险描述分词线程开始");
				SemanticSimilarityUtil.setDSDartDone(false);
				SemanticSimilarityUtil.clearDSPartMap();
				String sql = "select ds.id id,ds.ye_mhazard_desc description from t_b_danger_source ds where ds.is_delete='0' and ds.audit_status='4'";
				List<Map<String, Object>> dsList = findForJdbc(sql);
				for(int i=0;i<dsList.size();i++){
					String id = (String)dsList.get(i).get("id");
					String hdDesc = (String)dsList.get(i).get("description");
					if(StringUtil.isNotEmpty(hdDesc)) {
						SemanticSimilarityUtil.addDSPartMap(id, hdDesc);
					}
				}
				SemanticSimilarityUtil.setDSDartDone(true);
				System.out.println("风险描述分词线程结束");

			}
		};
		thread.start();
	}
	/**
	 * 更新图标
	 * @param icon
	 */
	public  void upTSIcons(TSIcon icon) {
		ResourceUtil.allTSIcons.put(icon.getId(), icon);
	}
	/**
	 * 更新图标
	 * @param icon
	 */
	public  void delTSIcons(TSIcon icon) {
		ResourceUtil.allTSIcons.remove(icon.getId());
	}

	@Override
	public void addDataLog(String tableName, String dataId, String dataContent) {

		int versionNumber = 0;

		Integer integer = commonDao.singleResult("select max(versionNumber) from TSDatalogEntity where tableName = '" + tableName + "' and dataId = '" + dataId + "'");
		if (integer != null) {
			versionNumber = integer.intValue();
		}

		TSDatalogEntity tsDatalogEntity = new TSDatalogEntity();
		tsDatalogEntity.setTableName(tableName);
		tsDatalogEntity.setDataId(dataId);
		tsDatalogEntity.setDataContent(dataContent);
		tsDatalogEntity.setVersionNumber(versionNumber + 1);
		commonDao.save(tsDatalogEntity);
	}


	public void initServerCert(){
		String serverCert = "";
		String serverConfigDirStr = ResourceUtil.getConfigByName("serverConfigDir");
		String serverCertFileStr = ResourceUtil.getConfigByName("serverCertFile");

		File serverConfigDir = new File(serverConfigDirStr);
		if(!serverConfigDir.exists()){
			serverConfigDir.mkdirs();
		}
		File serverCertFile = new File(serverConfigDirStr + "/" + serverCertFileStr);
		if(!serverCertFile.exists()){
			serverCert = UUIDGenerator.generate();
			FileOutputStream fos  = null;
			OutputStreamWriter osw = null;
			try{
				fos = new FileOutputStream(serverConfigDirStr + "/" + serverCertFileStr);
				osw = new OutputStreamWriter(fos, "UTF-8"); //指定以UTF-8编码输出
				osw.write(serverCert);
				osw.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		} else {
			try {
				FileInputStream fis = new FileInputStream(serverCertFile);
				InputStreamReader isr = new InputStreamReader(fis,"UTF-8"); //指定以UTF-8编码读入
				BufferedReader br = new BufferedReader(isr);
				String line = "";
				StringBuffer sb=new StringBuffer();
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

				serverCert = sb.toString();
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		ResourceUtil.setServerCert(serverCert);

	}

	@Override
	public boolean isSearchRole(TSUser user, String roleCode) {
		boolean flag = false;
		try{
			if(StringUtil.isNotEmpty(roleCode)){
				List<TSRoleUser> roleList = findByProperty(TSRoleUser.class,"TSUser",user);
				if(roleList!=null && roleList.size()>0){
					for(TSRoleUser roleUser:roleList){
						if(roleUser.getTSRole()!=null && StringUtil.isNotEmpty(roleUser.getTSRole().getRoleCode())){
							if(roleCode.equals(roleUser.getTSRole().getRoleCode())){
								flag = true;
							}
						}
					}
				}
			}
		}catch (Exception e){

		}finally {
			return flag;
		}
	}

	@Override
	public String getConfigFromDb(String key) {
		String value = "";
		try {
			List<String>  list = commonDao.findListbySql("select `value` from t_b_config where `name` = '"+key+"'");
			if (list!=null&&list.size()>0){
				value=list.get(0);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return value;
	}

}
