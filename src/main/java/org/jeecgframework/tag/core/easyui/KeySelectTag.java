package org.jeecgframework.tag.core.easyui;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MutiLangUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 
 * 选择关键字
 * 
 * @author:
 * @date：
 * @version 1.0
 */
public class KeySelectTag extends TagSupport {
	private static final long serialVersionUID = 1;
	private String typeGroupCode; // 数据字典类型
	
	@Autowired
	private static SystemService systemService;


	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		JspWriter out = null;
		try {
			out = this.pageContext.getOut();
			out.print(end().toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.clear();
				out.close();
				end().setLength(0);
			} catch (Exception e2) {
			}
		}
		return EVAL_PAGE;
	}

	public StringBuffer end() {
		
		StringBuffer sb = new StringBuffer();
        TSTypegroup typeGroup = ResourceUtil.allTypeGroups.get(this.typeGroupCode.toLowerCase());
        List<TSType> types = ResourceUtil.allTypes.get(this.typeGroupCode.toLowerCase());
        if(types!=null&&types.size()>0){
            for(TSType tSType:types){
                sb.append(" <a value=\"-1\" title=\""+tSType.getTypename()+"\" href=\"javascript:void(0);\"><span>"+tSType.getTypename()+"</span><em></em></a>");
            }
        }
		/*if (StringUtils.isBlank(divClass)) {
			divClass = "form"; // 默认form样式
		}
		if (StringUtils.isBlank(labelClass)) {
			labelClass = "Validform_label"; // 默认label样式
		}
		if (dictTable != null) {
			List<Map<String, Object>> list = queryDic();
			if ("radio".equals(type)) {
				for (Map<String, Object> map : list) {
					radio(map.get("text").toString(), map.get("field")
							.toString(), sb);
				}
			} else if ("checkbox".equals(type)) {
				for (Map<String, Object> map : list) {
					checkbox(map.get("text").toString(), map.get("field")
							.toString(), sb);
				}
			} else if("text".equals(type)){
				for (Map<String, Object> map : list) {
					text(map.get("text").toString(), map.get("field")
							.toString(), sb);
				}
			}else {
				sb.append("<select name=\"" + field + "\"");

				this.readonly(sb);

				
				//增加扩展属性
				if (!StringUtils.isBlank(this.extendJson)) {
					Gson gson = new Gson();
					Map<String, String> mp = gson.fromJson(extendJson, Map.class);
					for(Map.Entry<String, String> entry: mp.entrySet()) { 
						sb.append(entry.getKey()+"=\"" + entry.getValue() + "\"");
						} 
				}
				if (!StringUtils.isBlank(this.id)) {
					sb.append(" id=\"" + id + "\"");
				}
				sb.append(">");
				select("common.please.select", "", sb);
				for (Map<String, Object> map : list) {
					select(map.get("text").toString(), map.get("field").toString(), sb);
				}
				sb.append("</select>");
			}
		} else {
			TSTypegroup typeGroup = ResourceUtil.allTypeGroups.get(this.typeGroupCode.toLowerCase());
			List<TSType> types = ResourceUtil.allTypes.get(this.typeGroupCode.toLowerCase());
			if (hasLabel) {
				sb.append("<div class=\"" + divClass + "\">");
				sb.append("<label class=\"" + labelClass + "\" >");
			}
			if (typeGroup != null) {
				if (hasLabel) {
					if (StringUtils.isBlank(this.title)) {
						this.title = MutiLangUtil.getMutiLangInstance().getLang(typeGroup.getTypegroupname());
					}
					sb.append(this.title + ":");
					sb.append("</label>");
				}
				if ("radio".equals(type)) {
					for (TSType type : types) {
						radio(type.getTypename(), type.getTypecode(), sb);
					}
				} else if ("checkbox".equals(type)) {
					for (TSType type : types) {
						checkbox(type.getTypename(), type.getTypecode(), sb);
					}
				}else if ("text".equals(type)) {
					for (TSType type : types) {
						text(type.getTypename(), type.getTypecode(), sb);
					}
				} else {
					sb.append("<select name=\"" + field + "\"");

					this.readonly(sb);

					
					//增加扩展属性
					if (!StringUtils.isBlank(this.extendJson)) {
						Gson gson = new Gson();
						Map<String, String> mp = gson.fromJson(extendJson, Map.class);
						for(Map.Entry<String, String> entry: mp.entrySet()) { 
							sb.append(" "+entry.getKey()+"=\"" + entry.getValue() + "\"");
							} 
					}
					if (!StringUtils.isBlank(this.id)) {
						sb.append(" id=\"" + id + "\"");
					}
					this.datatype(sb);
					sb.append(">");
					select("common.please.select", "", sb);
					for (TSType type : types) {
						select(type.getTypename(), type.getTypecode(), sb);
					}
					sb.append("</select>");
				}
				if (hasLabel) {
					sb.append("</div>");
				}
			}
		}*/

		return sb;
	}

	public String getTypeGroupCode() {
		return typeGroupCode;
	}

	public void setTypeGroupCode(String typeGroupCode) {
		this.typeGroupCode = typeGroupCode;
	}


}
