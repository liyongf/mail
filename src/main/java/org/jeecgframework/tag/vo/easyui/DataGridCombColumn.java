package org.jeecgframework.tag.vo.easyui;
/**
 * 
 * 类描述：列表组合表头字段模型
 * 
 * @author:  hansf
 * @date： 日期：2016-03-04 时间：上午14:34:45
 * @version 1.0
 */
public class DataGridCombColumn {
    //表格列名
	protected String title;
    //跨列
	protected String rowspan;
    //跨行
	protected String colspan;
    //扩展参数,easyui有的,但是jeecg没有的参数进行扩展
	protected String extendParams;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRowspan() {
		return rowspan;
	}
	public void setRowspan(String rowspan) {
		this.rowspan = rowspan;
	}
	public String getColspan() {
		return colspan;
	}
	public void setColspan(String colspan) {
		this.colspan = colspan;
	}
	public String getExtendParams() {
		return extendParams;
	}
	public void setExtendParams(String extendParams) {
		this.extendParams = extendParams;
	}
	


	
}
