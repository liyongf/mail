package com.sdzk.buss.web.common.excelverify;

import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DepartExcelVerifyHandler implements IExcelVerifyHandler<TSDepart> {
    private SystemService systemService = (SystemService) ApplicationContextUtil.getContext().getBean("systemService");
    public static final String company = "公司";
    public static final String position = "岗位";
    public static final String department = "组织机构";
    public static final String companyCode = "1";
    public static final String positionCode = "3";
    public static final String departmentCode = "2";

    private Map<String, TSDepart> departMap = new HashMap<>();

    public Map<String, TSDepart> getDepartMap() {
        return departMap;
    }

    public void setDepartMap(Map<String, TSDepart> departMap) {
        this.departMap = departMap;
    }

    private Set<String> departNames;

    public Set<String> getDepartNames() {
        return departNames;
    }

    public void setDepartNames(Set<String> departNames) {
        this.departNames = departNames;
    }

    @Override
	public ExcelVerifyHanlderResult verifyHandler(TSDepart obj) {
		StringBuilder builder = new StringBuilder();
		boolean success = true;

		if(StringUtil.isEmpty(obj.getDepartname())){
			builder.append("部门名称不能为空。");
		}
		if(StringUtil.isEmpty(obj.getSuperiorDepart())){
            if (!company.equals(obj.getOrgTypeTemp())) {
                builder.append("非一级组织机构上级部门不能为空。");
            }
		} else {
            if (!departNames.contains(obj.getSuperiorDepart())){
                List<TSDepart> departs = systemService.findByProperty(TSDepart.class, "departname", obj.getSuperiorDepart());
                if (departs == null || departs.size() == 0) {
                    builder.append("上级部门["+obj.getSuperiorDepart()+"]不存在。");
                } else {
                    obj.setTSPDepart(departs.get(0));
                }
            }
        }
		if(StringUtil.isEmpty(obj.getOrgTypeTemp())){
			builder.append("机构类型不能为空。");
		} else {
            if (StringUtil.isEmpty(obj.getSuperiorDepart())){
                if (!company.equals(obj.getOrgTypeTemp())) {
                    builder.append("一级组织机构的机构类型必须为公司。");
                } else {
                    obj.setOrgType(companyCode);
                }
            } else {
                if (!department.equals(obj.getOrgTypeTemp()) && !position.equals(obj.getOrgTypeTemp())) {
                    builder.append("非一级组织机构的机构类型为组织机构或岗位。");
                } else if (department.equals(obj.getOrgTypeTemp())){
                    obj.setOrgType(departmentCode);
                } else if (position.equals(obj.getOrgTypeTemp())){
                    obj.setOrgType(positionCode);
                }
            }
        }
		if(StringUtil.isNotEmpty(builder.toString())){
			success = false;
		} else {
            TSDepart map = departMap.get(obj.getDepartname());
            if (map == null) {
                departMap.put(obj.getDepartname(), obj);
            }
        }
		return new ExcelVerifyHanlderResult(success,builder.toString());
	}



}
