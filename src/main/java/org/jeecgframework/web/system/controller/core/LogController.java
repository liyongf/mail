package org.jeecgframework.web.system.controller.core;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.lang3.StringUtils;
import org.apache.fop.svg.PDFTranscoder;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.Highchart;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSLog;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.LogService;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.sdzk.buss.web.system.entity.LoginCountVO;


/**
 * 日志处理类
 * 
 * @author 张代浩
 * 
 */
@Controller
@RequestMapping("/logController")
public class LogController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LogController.class);

    //用户浏览器统计分析的国际化KEY
    private static final String USER_BROWSER_ANALYSIS = "user.browser.analysis";
	private SystemService systemService;
	
	private LogService logService;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
	@Autowired
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	/**
	 * 日志列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "log")
	public ModelAndView log() {
		return new ModelAndView("system/log/logList");
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSLog.class, dataGrid);
		
		//日志级别查询条件
		String loglevel = request.getParameter("loglevel");
		if (loglevel != null && !"0".equals(loglevel)) {
			cq.eq("loglevel", oConvertUtils.getShort(loglevel));
			cq.add();
		}
		//时间范围查询条件
        String operatetime_begin = request.getParameter("operatetime_begin");
        String operatetime_end = request.getParameter("operatetime_end");
        if(oConvertUtils.isNotEmpty(operatetime_begin)){
        	try {
				cq.ge("operatetime", DateUtils.parseDate(operatetime_begin, "yyyy-MM-dd hh:mm:ss"));
			} catch (ParseException e) {
				logger.error(e);
			}
        	cq.add();
        }
        if(oConvertUtils.isNotEmpty(operatetime_end)){
        	try {
				cq.le("operatetime", DateUtils.parseDate(operatetime_end, "yyyy-MM-dd hh:mm:ss"));
			} catch (ParseException e) {
				logger.error(e);
			}
        	cq.add();
        }
        this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 获取日志详情
	 * @param tsLog
	 * @param request
	 * @return
	 * @Author fangwenrong
	 * @Date 2015-05-10
	 */
	@RequestMapping(params = "logDetail")
	public ModelAndView logDetail(TSLog tsLog,HttpServletRequest request){
		if (StringUtil.isNotEmpty(tsLog.getId())) {
			tsLog = logService.getEntity(TSLog.class, tsLog.getId());
			request.setAttribute("tsLog", tsLog);
		}
		return new ModelAndView("system/log/logDetail");
		
	}
	
	/**
	 * @RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TSTimeTaskEntity timeTask, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(timeTask.getId())) {
			timeTask = timeTaskService.getEntity(TSTimeTaskEntity.class, timeTask.getId());
			req.setAttribute("timeTaskPage", timeTask);
		}
		return new ModelAndView("system/timetask/timeTask");
	}
	 */
	
	
	/**
	 * 统计集合页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "statisticTabs")
	public ModelAndView statisticTabs(HttpServletRequest request) {
		return new ModelAndView("system/log/statisticTabs");
	}
	/**
	 * 用户浏览器使用统计图
	 * 
	 * @return
	 */
	@RequestMapping(params = "userBroswer")
	public ModelAndView userBroswer(String reportType, HttpServletRequest request) {
		request.setAttribute("reportType", reportType);
		if("pie".equals(reportType)){
			return new ModelAndView("system/log/userBroswerPie");
		}else if("line".equals(reportType)) {
			return new ModelAndView("system/log/userBroswerLine");
		}
		return new ModelAndView("system/log/userBroswer");
	}

	/**
	 * 报表数据生成
	 * 
	 * @return
	 */
	@RequestMapping(params = "getBroswerBar")
	@ResponseBody
	public List<Highchart> getBroswerBar(HttpServletRequest request,String reportType, HttpServletResponse response) {
		List<Highchart> list = new ArrayList<Highchart>();
		Highchart hc = new Highchart();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT broswer ,count(broswer) FROM TSLog group by broswer");
		List userBroswerList = systemService.findByQueryString(sb.toString());
		Long count = systemService.getCountForJdbc("SELECT COUNT(1) FROM T_S_Log WHERE 1=1");
		List lt = new ArrayList();
		hc = new Highchart();
		hc.setName(MutiLangUtil.getMutiLangInstance().getLang(USER_BROWSER_ANALYSIS));
		hc.setType(reportType);
		Map<String, Object> map;
		if (userBroswerList.size() > 0) {
			for (Object object : userBroswerList) {
				map = new HashMap<String, Object>();
				Object[] obj = (Object[]) object;
				map.put("name", obj[0]);
				map.put("y", obj[1]);
				Long groupCount = (Long) obj[1];
				Double  percentage = 0.0;
				if (count != null && count.intValue() != 0) {
					percentage = new Double(groupCount)/count;
				}
				map.put("percentage", percentage*100);
				lt.add(map);
			}
		}
		hc.setData(lt);
		list.add(hc);
		return list;
	}

	/**
	 * hightchart导出图片
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(params = "export")
	public void export(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String type = request.getParameter("type");
		String svg = request.getParameter("svg");
		String filename = request.getParameter("filename");

		filename = filename == null ? "chart" : filename;
		ServletOutputStream out = response.getOutputStream();
		try {
			if (null != type && null != svg) {
				svg = svg.replaceAll(":rect", "rect");
				String ext = "";
				Transcoder t = null;
				if (type.equals("image/png")) {
					ext = "png";
					t = new PNGTranscoder();
				} else if (type.equals("image/jpeg")) {
					ext = "jpg";
					t = new JPEGTranscoder();
				} else if (type.equals("application/pdf")) {
					ext = "pdf";
					t = (Transcoder) new PDFTranscoder();
				} else if (type.equals("image/svg+xml"))
					ext = "svg";
				response.addHeader("Content-Disposition",
						"attachment; filename=" + new String(filename.getBytes("GBK"),"ISO-8859-1") + "." + ext);
				response.addHeader("Content-Type", type);

				if (null != t) {
					TranscoderInput input = new TranscoderInput(
							new StringReader(svg));
					TranscoderOutput output = new TranscoderOutput(out);

					try {
						t.transcode(input, output);
					} catch (TranscoderException e) {
						out.print("Problem transcoding stream. See the web logs for more details.");
						e.printStackTrace();
					}
				} else if (ext.equals("svg")) {
					// out.print(svg);
					OutputStreamWriter writer = new OutputStreamWriter(out,
							"UTF-8");
					writer.append(svg);
					writer.close();
				} else
					out.print("Invalid type: " + type);
			} else {
				response.addHeader("Content-Type", "text/html");
				out
						.println("Usage:\n\tParameter [svg]: The DOM Element to be converted."
								+ "\n\tParameter [type]: The destination MIME type for the elment to be transcoded.");
			}
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}


    @RequestMapping(params = "logintime")
    public ModelAndView logintime(HttpServletRequest req) {
        String longyun = ResourceUtil.getConfigByName("longyun");
        req.setAttribute("longyun",longyun);
		return new ModelAndView("system/log/loginCount");
    }

    @RequestMapping(params = "loginCountDatagrid")
    public void hiddenCheckNumberDataGrid(HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid) {

        String username = request.getParameter("username");
        String operatetime_begin = request.getParameter("operatetime_begin");
        String operatetime_end = request.getParameter("operatetime_end");
        String departname = request.getParameter("departname");
        String querySql = "";
        String addsql = " and 1=1 ";
        StringBuffer qp = new StringBuffer(" where 1=1 and bsuser.status = 1 and bsuser.delete_flag = 0 ");
        if(StringUtil.isNotEmpty(username)){
            TSUser t = systemService.findUniqueByProperty(TSUser.class,"userName",username);
            String id = t.getId();
            addsql = addsql + " and userid= '" + id + "' ";

            qp.append(" and bsuser.id = ").append("'").append(id).append("' ");
        }
        if(StringUtil.isNotEmpty(operatetime_begin)){
            addsql = addsql + " and DATE(operatetime)>= '" + operatetime_begin + "' ";
        }
        if(StringUtil.isNotEmpty(operatetime_end)){
            addsql = addsql + " and DATE(operatetime)<= '" + operatetime_end + "' ";
        }
        if(StringUtil.isNotEmpty(departname)){
            addsql = addsql + " and userid in(select user_id from t_s_user_org where org_id = '"+departname+"') ";
            qp.append(" and bsuser.id in(select user_id from t_s_user_org where org_id = '"+departname+"') ");
        }

        if(addsql == " and 1=1 "){
            querySql = "select userid,count(IF(broswer = 'app', TRUE, NULL)) appLogintime, count(IF(broswer != 'app' OR broswer IS NULL, TRUE, NULL)) pcLogintime,count(1) logintime ,count(distinct DATE(operatetime)) daytime from t_s_log where operatetype='1' and loglevel = '1' group by userid ";
        }else{

            querySql = "select userid,count(IF(broswer = 'app', TRUE, NULL)) appLogintime, count(IF(broswer != 'app' OR broswer IS NULL, TRUE, NULL)) pcLogintime,count(1) logintime ,count(distinct DATE(operatetime)) daytime from t_s_log where operatetype='1' and loglevel = '1' "+ addsql + " group by userid ";
        }

        StringBuffer sb = new StringBuffer();
        sb.append("select bsuser.id userid,ifnull(t.appLogintime, 0) appLogintime, ifnull(t.pcLogintime, 0) pcLogintime,ifnull(t.logintime,0) logintime,ifnull(t.daytime,0) daytime from t_s_base_user bsuser  LEFT JOIN ( ");
        sb.append(querySql);
        sb.append(" ) t on bsuser.id = t.userid  ");
        String orderByStr="";
        if (org.apache.commons.lang.StringUtils.isNotBlank(dataGrid.getSort())) {
            String []sortArr = dataGrid.getSort().split(",");
            String []orderArr = dataGrid.getOrder().split(",");
            if(sortArr.length==orderArr.length){
                for(int i=0;i<sortArr.length;i++){
                    if(i==sortArr.length-1){
                        orderByStr=orderByStr+sortArr[i]+" "+orderArr[i];
                    }else{
                        orderByStr=orderByStr+sortArr[i]+" "+orderArr[i]+" ,";
                    }
                }
            }
        }
        sb.append(qp.toString());
       if(orderByStr.equals("")){
           sb.append(" ORDER BY logintime desc ,daytime desc ");
       }else{
           sb.append(" ORDER BY "+orderByStr);
       }

//        List<Map<String, Object>> maplist = systemService.findForJdbc(querySql, null);
        List<Map<String, Object>> maplist = systemService.findForJdbc(sb.toString(), dataGrid.getPage(),dataGrid.getRows());
        if(maplist!=null&&maplist.size()>0){
            for (Map map : maplist) {

                // 根据单位ID获取单位名称
                if(StringUtil.isNotEmpty(map.get("userid"))){
                    TSUser t = systemService.getEntity(TSUser.class,String.valueOf(map.get("userid")));
                    map.put("username",t.getUserName());
                    map.put("realname",t.getRealName());
                }
            }
        }
        dataGrid.setResults(maplist);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "exportXlsFineSummary")
    public String exportXlsFineSummary(HttpServletRequest request, DataGrid dataGrid,ModelMap modelMap) {
        String username = request.getParameter("username");
        String operatetime_begin = request.getParameter("operatetime_begin");
        String operatetime_end = request.getParameter("operatetime_end");
        String departname = request.getParameter("departname");
        String querySql = "";
        String addsql = " and 1=1 ";
        StringBuffer qp = new StringBuffer(" where 1=1 and bsuser.status = 1 and bsuser.delete_flag = 0 ");
        if(StringUtil.isNotEmpty(username)){
            TSUser t = systemService.findUniqueByProperty(TSUser.class,"userName",username);
            String id = t.getId();
            addsql = addsql + " and userid= '" + id + "' ";

            qp.append(" and bsuser.id = ").append("'").append(id).append("' ");
        }
        if(StringUtil.isNotEmpty(operatetime_begin)){
            addsql = addsql + " and DATE(operatetime)>= '" + operatetime_begin + "' ";
        }
        if(StringUtil.isNotEmpty(operatetime_end)){
            addsql = addsql + " and DATE(operatetime)<= '" + operatetime_end + "' ";
        }
        if(StringUtil.isNotEmpty(departname)){
            addsql = addsql + " and userid in(select user_id from t_s_user_org where org_id = '"+departname+"') ";
            qp.append(" and bsuser.id in(select user_id from t_s_user_org where org_id = '"+departname+"') ");
        }

        if(addsql == " and 1=1 "){
            querySql = "select userid,count(1) logintime ,count(distinct DATE(operatetime)) daytime from t_s_log where operatetype='1' and loglevel = '1' group by userid ";
        }else{

            querySql = "select userid,count(1) logintime ,count(distinct DATE(operatetime)) daytime from t_s_log where operatetype='1' and loglevel = '1' "+ addsql + " group by userid ";
        }

        StringBuffer sb = new StringBuffer();
        sb.append("select bsuser.id userid,ifnull(t.logintime,0) logintime,ifnull(t.daytime,0) daytime from t_s_base_user bsuser LEFT JOIN ( ");
        sb.append(querySql);
        sb.append(" ) t on bsuser.id = t.userid  ");
        sb.append(qp.toString());
        sb.append(" ORDER BY logintime desc ,daytime desc ");
        List<Map<String, Object>> maplist = systemService.findForJdbc(sb.toString(),null);
        if(maplist!=null&&maplist.size()>0){
            for (Map map : maplist) {
                // 根据单位ID获取单位名称
                if(StringUtil.isNotEmpty(map.get("userid"))){
                    TSUser t = systemService.getEntity(TSUser.class,String.valueOf(map.get("userid")));
                    map.put("username",t.getUserName());
                    map.put("realname",t.getRealName());
                }
            }
        }

        List<LoginCountVO> retList = new ArrayList<LoginCountVO>();
        if(!maplist.isEmpty()){
            for (Map map : maplist) {
                LoginCountVO vo = new LoginCountVO();
                vo.setUserName(String.valueOf(map.get("username")));
                vo.setRealName(String.valueOf(map.get("realname")));
                vo.setYemoday(String.valueOf(map.get("daytime")));
                vo.setCount(String.valueOf(map.get("logintime")));
                retList.add(vo);
            }
        }
        dataGrid.setResults(retList);

        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_logincount.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", retList);
        modelMap.put(NormalExcelConstants.FILE_NAME,"登录情况导出");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

}
