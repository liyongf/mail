package org.jeecgframework.core.common.model.json;

/**
 * $.ajax后需要接受的JSON
 * 
 * @author
 * 
 */
public class LayDataGrid {

	private int code = 0;
	private String msg = "";
	private int count = 0;
	private Object data = null;

	public LayDataGrid(){

	}

	public LayDataGrid(int count, Object data){
		this.count = count;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
