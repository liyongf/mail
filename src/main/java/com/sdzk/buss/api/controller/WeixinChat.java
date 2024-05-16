package com.sdzk.buss.api.controller;

import com.alibaba.fastjson.JSONArray;
import com.sdzk.buss.api.entity.WexinOpenId;
import com.sdzk.buss.api.service.WeChartGetToken;
import com.sdzk.buss.api.service.WexinOpenServiceI;
import com.sdzk.buss.web.common.Constants;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.HttpClientUtils;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/WeixinChat")
public class WeixinChat extends HttpServlet {
    @Autowired
    private SystemService systemService;
    @Autowired
    private WeChartGetToken weChartGetToken;
    @Autowired
    private WexinOpenServiceI wexinOpenServiceI;



  /**获取token*/
    @RequestMapping(params = "getAccessToken")
    @ResponseBody
    public String getAccessToken(String appid, String appsecret) throws IOException {
        appid = ResourceUtil.getConfigByName("appID");
        appsecret = ResourceUtil.getConfigByName("appsecret");
        return weChartGetToken.getAccessToken(appid, appsecret);
  }

 /**获取关注微信公众号用户列表*/
    @RequestMapping(params = "getUserList")
    @ResponseBody
    public JSONObject getUserList(){

        String access_token= weChartGetToken.getToken();
        String url = "https://api.weixin.qq.com/cgi-bin/user/get";

        JSONObject jo = new JSONObject();

        Map<String, String> params = new HashMap<>();
        params.put("access_token", access_token);
        params.put("next_openid", null);

        try {
            String returnData = HttpClientUtils.post(url, params, "UTF-8");
               jo = JSONObject.fromObject(returnData);
            net.sf.json.JSONArray openIds = jo.getJSONObject("data").getJSONArray("openid");
             WexinOpenId wexinOpenId ;
            List<String > list=new ArrayList<>();
            for(int i=0;i<openIds.size();i++){
               String opid= openIds.get(i).toString();
                   if(StringUtils.isNotBlank(opid)){
                        list.add(openIds.get(i).toString());
                    }
            }
            for( String openId :list){
                String sql="select count(1) from t_b_wexinopenid where openId='"+openId+"'";
                Long count = systemService.getCountForJdbc(sql);
                if(count==0){
                    wexinOpenId=new WexinOpenId();
                    wexinOpenId.setOpenId(openId);
                    wexinOpenId.setWexinStatus("0");
                    wexinOpenServiceI.save(wexinOpenId);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo;

    }
 /**根据关注用户openId获取用户详细信息   获取用户最大值为100   需要判断数据是否要分批处理*/
    @RequestMapping(params = "postUserList")
    @ResponseBody
    public void postUserList() {
        String openIdsql = "select openId from t_b_WexinOpenId where nickname is NULL;";
        List<String> dataList = systemService.findListbySql(openIdsql);

        if (null != dataList && dataList.size() > 0) {
            //限制条数，最大99
            int pointsDataLimit = 99;
            Integer size = dataList.size();
            //如果数据大于99，需要分批处理
            if (pointsDataLimit < size) {
                int part = size / pointsDataLimit;//分批条数
                //分批处理
                for (int i = 0; i < part; i++) {
                    List<String> listPage = dataList.subList(0, pointsDataLimit);
                    postList(listPage);
                    dataList.subList(0, pointsDataLimit).clear();
                }
                //分批之后剩下的数据
                if (!dataList.isEmpty()) {
                    postList(dataList);
                }
               //不用分批
            } else {
                postList(dataList);
            }

        }
    }

    public void postList(List <String> dataList){
        String access_token= weChartGetToken.getToken();
        JSONObject jo = new JSONObject();
        JSONArray userListJA = new JSONArray();

        for( String openId : dataList){
            JSONObject u=new JSONObject();
            u.put("openid",openId);
            u.put("lang","zh_CN");
            userListJA.add(u);
        }
        try {

            String url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token="+access_token;
            JSONObject userlist=new JSONObject();
            userlist.put("user_list",userListJA);
            String returnData = HttpClientUtils.post(url, userlist.toString());
            String json = new String(returnData.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println(json);
            jo = JSONObject.fromObject(json);
            net.sf.json.JSONArray arraylist=jo.getJSONArray("user_info_list");
            if(arraylist.size()>0) {
                for (int i = 0; i < arraylist.size(); i++) {
                    // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                    JSONObject job = arraylist.getJSONObject(i);
                    // 得到 每个对象中的属性值
                    String openid= job.get("openid").toString();
                    String nickname= job.get("nickname").toString();
//                    String sql="select count(1) from t_b_wexinopenid where nickname='"+nickname+"'";
//                    Long count = systemService.getCountForJdbc(sql);
//                    if(count==0){
                        String updatesql="update t_b_wexinopenid set nickname='"+nickname+"' where openId='"+openid+"'";
                        systemService.updateBySqlString(updatesql);
//                    }
                }
            }

        }catch (Exception e){

            e.printStackTrace();
        }

    }

    /**
     *双防新录入隐患通知
     *
     *【双防平台】通知:
     *
     *尊敬的双防用户，您好,{{first.DATA}}发布了一条新的隐患：
     * 时间：{{keyword1.DATA}} ,
     * 地点：{{keyword2.DATA}},
     * 内容：{{keyword3.DATA}},
     * 限期日期为: {{remark.DATA}}.
     *
     */
    /** 发送消息*/
    @RequestMapping(params = "postAddTemplate")
    @ResponseBody
    public JSONObject postAddTemplate(String openid,String createName,String createTime,String addressName,String problemDesc,String limitDate) throws IOException {
        JSONObject jo = new JSONObject();
        String access_token = "";
        String sql = "select accessToken from t_b_token";
        List<String> tmpList = systemService.findListbySql(sql);
        if (!tmpList.isEmpty() && tmpList.size() > 0) {
            access_token = tmpList.get(0);
        } else {
            String appid = ResourceUtil.getConfigByName("appID");
            String appsecret = ResourceUtil.getConfigByName("appsecret");
            access_token = weChartGetToken.getAccessToken(appid, appsecret);

        }
        String templateAdd_id=ResourceUtil.getConfigByName("templateAdd_id");
        JSONObject postJO = new JSONObject();
        JSONObject text=new JSONObject();
        JSONObject first=new JSONObject();
        JSONObject keyword1=new JSONObject();
        JSONObject keyword2=new JSONObject();
        JSONObject keyword3=new JSONObject();
        JSONObject remark=new JSONObject();

        openid="oHNqj1Az4-kNsxgpen5FT8CtvR0M";
        createName="习近平";
        createTime="2018年3月32日";
        addressName="A515";
        problemDesc="殴打小朋友";
        limitDate="1995年";

        postJO.put("touser",openid);
        postJO.put("template_id",templateAdd_id);
        first.put("value",createName);
        first.put("color","#007f80");
        keyword1.put("value",createTime);
        keyword1.put("color","#007f80");
        keyword2.put("value",addressName);
        keyword2.put("color","#007f80");
        keyword3.put("value",problemDesc);
        keyword3.put("color","#007f80");
        remark.put("value",limitDate);
        remark.put("color","#007f80");

        text.put("first",first);
        text.put("keyword1",keyword1);
        text.put("keyword2",keyword2);
        text.put("keyword3",keyword3);
        text.put("remark",remark);
        postJO.put("data",text);

        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token;

        try {
            String returnData = HttpClientUtils.post(url, postJO.toString());
            jo = JSONObject.fromObject(returnData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo;
    }


    /**
     *双防隐患整改通知
     *
     * 【双防平台】通知:
     *
     * 尊敬的双防用户，您好！您于{{first.DATA}}发布的隐患已整改完成 .
     * 地点: {{keyword1.DATA}};
     * 内容: {{keyword2.DATA}};
     * 限期日期为：{{keyword3.DATA}};
     * 整改人为：{{keyword4.DATA}};
     * 整改内容为: {{keyword5.DATA}};
     * 整改完成时间为：{{remark.DATA}}.
     */
    /** 发送消息*/
    @RequestMapping(params = "postCheckTemplate")
    @ResponseBody
    public JSONObject postCheckTemplate(String openid,String createTime,String addressName,String problemDesc,
                                        String limitDate,String modifyManName,String rectMeasures,String modifyDate) throws IOException {
        //   测试地址 http://liuran.tunnel.qydev.com/WeixinChat.do?postCheckTemplate
        //   记得判断如果id被关联多个时 遍历一下
        JSONObject jo = new JSONObject();
        String access_token = "";
        String sql = "select accessToken from t_b_token";
        List<String> tmpList = systemService.findListbySql(sql);
        if (!tmpList.isEmpty() && tmpList.size() > 0) {
            access_token = tmpList.get(0);
        } else {
            String appid = ResourceUtil.getConfigByName("appID");
            String appsecret = ResourceUtil.getConfigByName("appsecret");
            access_token = weChartGetToken.getAccessToken(appid, appsecret);
        }
        String templateCheck_id=ResourceUtil.getConfigByName("templateCheck_id");
        JSONObject postJO = new JSONObject();
        JSONObject text=new JSONObject();
        JSONObject first=new JSONObject();
        JSONObject keyword1=new JSONObject();
        JSONObject keyword2=new JSONObject();
        JSONObject keyword3=new JSONObject();
        JSONObject keyword4=new JSONObject();
        JSONObject keyword5=new JSONObject();
        JSONObject remark=new JSONObject();

        openid="oHNqj1Az4-kNsxgpen5FT8CtvR0M";
        createTime="2018年3月32日";
        addressName="A515";
        problemDesc="殴打小朋友";
        limitDate="1995年";
        modifyManName="习近平";
        rectMeasures="打哭就行";
        modifyDate="1997年";

        postJO.put("touser",openid);
        postJO.put("template_id",templateCheck_id);
        first.put("value",createTime);
        first.put("color","#007f80");
        keyword1.put("value",addressName);
        keyword1.put("color","#007f80");
        keyword2.put("value",problemDesc);
        keyword2.put("color","#007f80");
        keyword3.put("value",limitDate);
        keyword3.put("color","#007f80");
        keyword4.put("value",modifyManName);
        keyword4.put("color","#007f80");
        keyword5.put("value",rectMeasures);
        keyword5.put("color","#007f80");
        remark.put("value",modifyDate);
        remark.put("color","#007f80");

        text.put("first",first);
        text.put("keyword1",keyword1);
        text.put("keyword2",keyword2);
        text.put("keyword3",keyword3);
        text.put("keyword4",keyword4);
        text.put("keyword5",keyword5);
        text.put("remark",remark);
        postJO.put("data",text);

        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token;

        try {
            String returnData = HttpClientUtils.post(url, postJO.toString());
            jo = JSONObject.fromObject(returnData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo;
    }

    /**
     * 双防隐患升级通知
     【双防平台】通知：

     尊敬的双防用户，您好！
     {{first.DATA}}发布的隐患因未及时整改，等级上升为：{{keyword1.DATA}}，
     限期日期为：{{keyword2.DATA}}，
     整改责任人为：{{remark.DATA}}.

     */
    @RequestMapping(params = "postUpTemplate")
    @ResponseBody
    public JSONObject postUpTemplate(String openid,String createName,String hiddenLevelName,String limitDateStr,String dutyMan) throws IOException {
        JSONObject jo = new JSONObject();
        String access_token = "";
        String sql = "select accessToken from t_b_token";
        List<String> tmpList = systemService.findListbySql(sql);
        if (!tmpList.isEmpty() && tmpList.size() > 0) {
            access_token = tmpList.get(0);
        } else {
            String appid = ResourceUtil.getConfigByName("appID");
            String appsecret = ResourceUtil.getConfigByName("appsecret");
            access_token = weChartGetToken.getAccessToken(appid, appsecret);

        }
        String templateUp_id=ResourceUtil.getConfigByName("templateUp_id");
        JSONObject postJO = new JSONObject();
        JSONObject text=new JSONObject();
        JSONObject first=new JSONObject();
        JSONObject keyword1=new JSONObject();
        JSONObject keyword2=new JSONObject();

        JSONObject remark=new JSONObject();

        openid="oHNqj1Az4-kNsxgpen5FT8CtvR0M";
        createName="习近平";
        limitDateStr="1997年";
        dutyMan="薄熙来";

        postJO.put("touser",openid);
        postJO.put("template_id",templateUp_id);
        first.put("value",createName);
        first.put("color","#007f80");
        keyword1.put("value",hiddenLevelName);
        keyword1.put("color","#007f80");
        keyword2.put("value",limitDateStr);
        keyword2.put("color","#007f80");
        remark.put("value",dutyMan);
        remark.put("color","#007f80");

        text.put("first",first);
        text.put("keyword1",keyword1);
        text.put("keyword2",keyword2);
        text.put("remark",remark);
        postJO.put("data",text);

        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token;

        try {
            String returnData = HttpClientUtils.post(url, postJO.toString());
            jo = JSONObject.fromObject(returnData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo;
    }
    /***
     * 微信用户信息页面
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/weixin/tBWexinUserList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(WexinOpenId wexinOpenId, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(WexinOpenId.class, dataGrid);

        try{
            String WexinStatus=request.getParameter("wexinStatus");
            if (StringUtils.isNotBlank(WexinStatus)){
                 if(Constants.WEIXIN_RELATION.equals(WexinStatus) || Constants.WEIXIN_UNRELATED.equals(WexinStatus)) {
                        cq.eq("wexinStatus", WexinStatus);

                   }else if("all".equals(WexinStatus)){
                        cq.notEq("wexinStatus", Constants.HANDELSTATUS_DRAFT);
                        }
            }else{
                cq.eq("wexinStatus", Constants.WEIXIN_UNRELATED);
            }
          String nickname=request.getParameter("nickname");
          if(StringUtils.isNotBlank(nickname)) {
              cq.like("nickname", "%" + nickname + "%");

          }
        } catch (Exception e){
            e.printStackTrace();
            throw new org.jeecgframework.core.common.exception.BusinessException(e.getMessage());
        }
        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;

        TSUser user = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunCommonUser").equals(userRole)){
                isSunRole = true;
            }
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        if (isSunRole){
            String sunSql = "select column_id from t_b_sunshine where table_name='t_b_address_info'";
            List<String> sunList = systemService.findListbySql(sunSql);
            if (sunList!=null && sunList.size()>0){
                String[] sunString = new String[sunList.size()];
                for (int i=0; i<sunList.size(); i++){
                    sunString[i] = sunList.get(i);
                }
                cq.notIn("id", sunString);
            }
        }
        /*************************************************************/

        cq.add();
        this.wexinOpenServiceI.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null) {
            if (dataGrid.getResults().size() > 0) {
                List<WexinOpenId> list = dataGrid.getResults();
                for (WexinOpenId w : list) {
                    String userid= w.getUser();
                    if(StringUtils.isNotBlank(userid)) {
                        TSUser usertemp = systemService.getEntity(TSUser.class, userid);
                        w.setUserTemp(usertemp.getRealName());
                    }
                }

            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

  @RequestMapping(params = "goAddUser")
   public String  goAddUser (HttpServletRequest request){
    String id=request.getParameter("id");
    if(StringUtils.isNotBlank(id)) {
        WexinOpenId wexinOpenId = wexinOpenServiceI.getEntity(WexinOpenId.class, id);

        request.setAttribute("WexinOpenId",wexinOpenId);
    }
        return "com/sdzk/buss/web/weixin/goAddUser-add";
   }

    /**
     * 微信用户关联
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(WexinOpenId wexinOpenId, HttpServletRequest request) {
        AjaxJson j=new AjaxJson();
        String message = "微信用户关联成功";
        try {
            WexinOpenId wexinOpenIdTo = wexinOpenServiceI.getEntity(wexinOpenId.getClass(), wexinOpenId.getId());
            MyBeanUtils.copyBeanNotNull2Bean(wexinOpenId,wexinOpenIdTo);
            wexinOpenServiceI.saveOrUpdate(wexinOpenIdTo);
        }catch (Exception e){
            e.printStackTrace();
            message = "微信用户关联失败";

        }
        j.setMsg(message);
        return j;
    }
}
