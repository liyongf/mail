package org.jeecgframework.core.util;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.system.service.SystemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author hansf
 * 
 */
public class DicUtil {
	private static Logger log = Logger.getLogger(DicUtil.class);

    private static org.jeecgframework.web.system.service.SystemService systemService = (org.jeecgframework.web.system.service.SystemService) ApplicationContextUtil.getContext().getBean("systemService");

    /**
	 * 根据字典编码获取字典文本值
	 * @param groupCode
	 * @param typeCode
	 * @return
	 */
	public static String getTypeNameByCode(String groupCode, String typeCode) {
		String typeName="";
		if(StringUtil.isNotEmpty(typeCode)){
			if (StringUtils.isBlank(groupCode)) {
				return typeCode;
			}
			List<TSType> types = ResourceUtil.allTypes.get(groupCode.toLowerCase());
			if (types!=null && types.size()>0) {
				for (TSType tSType : types) {
					if (tSType.getTypecode().equals(typeCode)) {
						typeName = tSType.getTypename();
						break;
					}
				}
			}
		}
		
		return typeName;
	}
	
	/**
	 * 根据字典名称获取字典编码
	 * @param groupCode
	 * @param typeName
	 * @return
	 */
	public static String getTypeCodeByName(String groupCode, String typeName) {
		String typeCode="";
		if(StringUtil.isNotEmpty(typeName)){
			if (StringUtils.isBlank(groupCode)) {
				return typeCode;
			}
			List<TSType> types = ResourceUtil.allTypes.get(groupCode.toLowerCase());
			if (types!=null && types.size()>0) {
				for (TSType tSType : types) {
					if (tSType.getTypename().equals(typeName.trim())) {
						typeCode = tSType.getTypecode();
						break;
					}
				}
			}
		}
		
		return typeCode;
	}
    /**
     * 判断用户角色是不是隐患复查角色
     * @param userId
     * @return
     */
    public static boolean reviewPower(String userId){
        boolean flag = false;
        if(roleCheck(userId,Constants.HIDDEN_DANGER_REVIEW_ROLE)){
            flag = true;
        }
        return flag;
    }
    /**
     * 判断用户是不是隐患整改角色
     * @param userId
     * @return
     */
    public static boolean modifyPower(String userId){
        boolean flag = false;
        if(roleCheck(userId,Constants.HIDDEN_DANGER_MODIFY_ROLE)){
            flag = true;
        }
        return flag;
    }
    /**
     * 判断用户角色是否对应
     * @param userId
     * @param roleCode
     * @return
     */
    private static boolean roleCheck(String userId,String roleCode){
        boolean flag = false;
        List<String> roleCodeList = getRoleByUserId(userId);
        if(roleCodeList.size()>0&&roleCodeList!=null){
            for(String roleCodeTemp : roleCodeList){
                if(roleCode.equals(roleCodeTemp)){
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
    /**
     * 获取对应用户角色Code  返回List
     * @param userId
     * @return
     */
    private static List<String> getRoleByUserId(String userId){
        List<String> roleCodeList = new ArrayList<>();
        if(StringUtil.isNotEmpty(userId)){
            String sqlToRole = "select tr.roleCode from t_s_role_user tru left join t_s_role tr on tr.id = tru.roleId  where  tru.userId = '"+userId+"'";
            roleCodeList = systemService.findListbySql(sqlToRole);
        }
        return roleCodeList;
    }
}
