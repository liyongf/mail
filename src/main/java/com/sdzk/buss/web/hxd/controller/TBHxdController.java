package com.sdzk.buss.web.hxd.controller;

import com.sdzk.buss.web.common.excelverify.FineListExcelVerifyHandler;
import com.sdzk.buss.web.dangersource.entity.TBDangerAddresstRelEntity;
import com.sdzk.buss.web.fine.entity.TBFineEntity;
import com.sdzk.buss.web.fine.service.TBFineServiceI;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.violations.entity.TBThreeViolationsEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**   
 * @Title: Controller  
 * @Description: 重大隐患
 * @author onlineGenerator
 * @date 2017-09-26 10:21:57
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tBHxdController")
public class TBHxdController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBHxdController.class);

	@Autowired
	private TBFineServiceI tbFineService;
    @Autowired
    private UserService userService;

	@Autowired
	private SystemService systemService;

	@RequestMapping(params = "list1")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/hxd/tBHxdList1");
	}

    @RequestMapping(params = "datagrid1")
    public void datagrid1(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        List<Map<String,String>> list = new ArrayList<>();
        String [] colNames = {"id", "dept","tjr","tjrq","xq","clrq","status"};
        String [][] rows = {
                {"1","安监处","李方","2018-01-1","修改详情","2018-01-2","待审核"},
                {"2","机电科","孙玉柏","2018-02-1","修改详情","2018-02-2","待审核"},
                {"3","通防处","孔冲","2018-03-1","修改详情","2018-03-2","待审核"},
                {"4","运转工区","侯成国","2018-04-1","修改详情","2018-04-2","待审核"},
                {"5","综采工区","仇金阁","2018-05-1","修改详情","2018-05-2","待审核"},
                {"6","掘二工区","林建国","2018-06-1","修改详情","2018-06-2","待审核"}
        };
        for(int i=0;i<rows.length;i++){
            Map<String,String> map = new HashMap<>();
            for(int k=0;k<rows[i].length;k++){
                map.put(colNames[k],rows[i][k]);
            }
            list.add(map);
        }

        int page = dataGrid.getPage();
        int rowsPerPage = dataGrid.getRows();
        int beginIndex = rowsPerPage*(page-1);
        int endIndex = rowsPerPage*page>list.size()?list.size():rowsPerPage*page;
        dataGrid.setTotal(list.size());
        dataGrid.setResults(list.subList(beginIndex,endIndex));
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goUpdate1")
    public ModelAndView goUpdate1(HttpServletRequest request) {

        return new ModelAndView("com/sdzk/buss/web/hxd/tBHxd-update1");
    }

 }
