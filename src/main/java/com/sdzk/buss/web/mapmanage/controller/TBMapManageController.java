package com.sdzk.buss.web.mapmanage.controller;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.utils.MailUtil;
import com.sdzk.buss.web.dangersource.entity.TBDocumentSourceEntity;
import com.sdzk.buss.web.mapmanage.entity.TBMapManageEntity;
import com.sdzk.buss.web.mapmanage.service.TBMapManageServiceI;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDocument;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tbMapManageController")
public class TBMapManageController extends BaseController {

    private static final Logger logger = Logger.getLogger(TBMapManageController.class);

    @Autowired
    private SystemService systemService;
    @Autowired
    private TBMapManageServiceI tbMapManageService;

    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        request.setAttribute("uploadType","zip");
        return new ModelAndView("com/sdzk/buss/web/mapmanage/tbMapManageList");
    }

    @RequestMapping(params = "goUpload")
    public ModelAndView goUpload(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/mapmanage/tbMapManage-add");
    }

    @RequestMapping(params = "datagrid")
    public void datagrid(TBMapManageEntity mapManage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
        String uploadTime_begin = request.getParameter("uploadTime_begin");
        String uploadTime_end = request.getParameter("uploadTime_end");

        String uploadType = request.getParameter("uploadType");

        CriteriaQuery cq = new CriteriaQuery(TBMapManageEntity.class,dataGrid);
        cq.eq("isDelete", Constants.IS_DELETE_N);
        cq.eq("uploadType", uploadType);
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            if(StringUtils.isNotBlank(uploadTime_begin)){
                Date uploadTimeBegin = sdf.parse(uploadTime_begin);
                cq.ge("uploadTime",uploadTimeBegin);
            }

            if(StringUtils.isNotBlank(uploadTime_end)){
                Date uploadTimeEnd = sdf.parse(uploadTime_end);
                cq.le("uploadTime",uploadTimeEnd);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        cq.addOrder("uploadTime", SortDirection.desc);
        cq.add();
        tbMapManageService.getDataGridReturn(cq,true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "doUpload", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson doUpload(TBMapManageEntity mapManage,HttpServletRequest request, HttpServletResponse response, TSDocument document){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "矿图上传成功";

        try{
            String filePath = tbMapManageService.upload(request);

            TSUser user = ResourceUtil.getSessionUserName();

            String uploadType = "zip";
            mapManage = new TBMapManageEntity(filePath,user,uploadType);
            tbMapManageService.save(mapManage);
            systemService.addLog(message,Globals.Log_Type_UPLOAD,Globals.Log_Leavel_INFO);
        }catch(Exception e){
            e.printStackTrace();
            message = "矿图上传失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(String ids,HttpServletRequest request){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "矿图删除成功";
        try{
            for(String id:ids.split(",")){
                TBMapManageEntity mapManage = systemService.getEntity(TBMapManageEntity.class,id);
                tbMapManageService.delete(mapManage);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "矿图删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBMapManageEntity mapManage,HttpServletRequest request){
        String message = "设置成功";
        AjaxJson j = new AjaxJson();

        String id = request.getParameter("id");
        if(StringUtils.isNotBlank(id)){
            try{
                mapManage = tbMapManageService.getEntity(TBMapManageEntity.class,id);
                if(mapManage != null){
                    mapManage.setIsUsed("1");
                    tbMapManageService.saveOrUpdate(mapManage);
                }

                String sql = "update t_b_map_manage set is_used='0' where id !='" + id + "'";
                systemService.executeSql(sql);
            }catch (Exception e){
                e.printStackTrace();
                message = "设置失败";
            }
        }

        j.setMsg(message);
        return j;
    }

    /**********************************************上传dwg文件  start  *************************************************/
    @RequestMapping(params = "dwgList")
    public ModelAndView dwgList(HttpServletRequest request) {
        request.setAttribute("uploadType","dwg");
        return new ModelAndView("com/sdzk/buss/web/mapmanage/tbdwgMapManageList");
    }

    @RequestMapping(params = "goUploaddwg")
    public ModelAndView goUploaddwg(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/mapmanage/tbdwgMapManage-add");
    }


    @RequestMapping(params = "dodwgUpload", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson dodwgUpload(TBMapManageEntity mapManage,HttpServletRequest request, HttpServletResponse response, TSDocument document){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "矿图上传成功";

        try{
            TSUser user = ResourceUtil.getSessionUserName();

            String uploadType = "dwg";

            mapManage = new TBMapManageEntity(null,user,uploadType);

            mapManage.setStatus("0");

            tbMapManageService.save(mapManage);

            String sql="select departname from t_s_depart where parentdepartid is null";

            List<String> findDepartname = systemService.findListbySql(sql);

            tbMapManageService.dwgupload(request,findDepartname.get(0),mapManage.getId());

            MailUtil mailutil = new MailUtil();

            mailutil.sendMail(findDepartname.get(0)+"提醒:dwg矿图已上传,请及时处理并替换!");

            systemService.addLog(message,Globals.Log_Type_UPLOAD,Globals.Log_Leavel_INFO);
        }catch(Exception e){
            e.printStackTrace();
            message = "矿图上传失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    private void seCrossOrigint(HttpServletResponse response) {
        //添加跨域CORS
        response.addHeader("Access-Control-Max-Age", "1800");//30 min
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,Content-Type,token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
    }

    @RequestMapping(params = "changeStatus",method=RequestMethod.GET)
    @ResponseBody
    public AjaxJson changeStatus(TBMapManageEntity mapManage,HttpServletRequest request,HttpServletResponse response){
        seCrossOrigint(response);

        String message = "设置成功";
        AjaxJson j = new AjaxJson();

        String id = request.getParameter("id");
        String status = request.getParameter("status");

        if(StringUtils.isNotBlank(id)){
            try{
                mapManage = tbMapManageService.getEntity(TBMapManageEntity.class,id);
                if(mapManage != null){
                    mapManage.setStatus(status);
                    tbMapManageService.saveOrUpdate(mapManage);
                }

            }catch (Exception e){
                e.printStackTrace();
                message = "设置失败";
            }
        }
        j.setMsg(message);
        return j;
    }

    /**********************************************上传dwg文件   end   *************************************************/
}
