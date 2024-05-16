package org.jeecgframework.tag.core.easyui;

import org.jeecgframework.core.util.MutiLangUtil;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * 类描述：组合表头字段处理项目
 * 
 * hansf
 * @date： 日期：2016-03-4 时间：上午14:29:45
 * @version 1.0
 */
public class DataGridCombColumnTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	protected String title;
	protected String rowspan;
	protected String colspan;
    //扩展参数
	private String extendParams;
	private String langArg;


	public int doEndTag() throws JspTagException {
		title = MutiLangUtil.doMutiLang(title, langArg);
		
		Tag t = findAncestorWithClass(this, DataGridTag.class);
		DataGridTag parent = (DataGridTag) t;
		parent.setCombColumn(title,rowspan,colspan,extendParams);
		return EVAL_PAGE;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public void setRowspan(String rowspan) {
		this.rowspan = rowspan;
	}


	public void setColspan(String colspan) {
		this.colspan = colspan;
	}


	public void setExtendParams(String extendParams) {
		this.extendParams = extendParams;
	}


	public void setLangArg(String langArg) {
		this.langArg = langArg;
	}
	
	
	
}
