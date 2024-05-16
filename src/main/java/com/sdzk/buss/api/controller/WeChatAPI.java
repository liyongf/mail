package com.sdzk.buss.api.controller;

import com.sdzk.buss.api.entity.WeChartTextMessage;
import com.sdzk.buss.api.service.MessageUtil;
import com.sdzk.buss.api.service.WeChartServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/weChat")
public class WeChatAPI extends HttpServlet {
    @Autowired
    private SystemService systemService;
    @Autowired
    private WeChartServiceI weChartService;

    private Logger log =Logger.getLogger(this.getClass().getName());
    private static final long serialVersionUID = 1L;
    private  String Token = "zhangsaichao";
    private  String echostr;

    @RequestMapping(params = "selectMethod")
    public void selectMethod(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        if ("GET".equals(request.getMethod())){
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**  防止程序执行时间过长，首先返回给微信服务器success字符串，证明我们已收到消息，正在处理。如果超过10秒，则微信服务器报公众号故障 */
        PrintWriter out = response.getWriter();
        out.print("success");

        message(request,response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        connect(request,response);
    }

//    @Override
//    public void init() throws ServletException {
//        Token="zhangsaichao";
//    }

    private void message(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{

//        InputStream is = request.getInputStream();
//        // 取HTTP请求流长度
//        int size = request.getContentLength();
//        // 用于缓存每次读取的数据
//        byte[] buffer = new byte[size];
//        // 用于存放结果的数组
//        byte[] xmldataByte = new byte[size];
//        int count = 0;
//        int rbyte = 0;
//        // 循环读取
//        while (count < size) {
//            // 每次实际读取长度存于rbyte中
//            rbyte = is.read(buffer);
//            for(int i=0;i<rbyte;i++) {
//                xmldataByte[count + i] = buffer[i];
//            }
//            count += rbyte;
//        }
//        is.close();
//        String requestStr = new String(xmldataByte, "UTF-8");

        String requestStr = "";

        try{
            manageMessage(requestStr,request,response);
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    /**
     * @author haibing.xiao
     * @return
     * @exception ServletException, IOException
     * @param
     *
     * <p>业务转发组件</p>
     *
     */
    private void  manageMessage(String requestStr,HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException {
        System.out.println(requestStr);

        String responseStr;

        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            Map<String, String> message = MessageUtil.parseXml(request);

            String messageType = message.get("MsgType");

            if (messageType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                //接收的是文本消息

                //打印接收所有参数
                System.out.println("ToUserName：" + message.get("ToUserName"));
                System.out.println("FromUserName：" + message.get("FromUserName"));
                System.out.println("CreateTime：" + message.get("CreateTime"));
                System.out.println("MsgType：" + message.get("MsgType"));
                System.out.println("Content：" + message.get("Content"));
                System.out.println("MsgId：" + message.get("MsgId"));

                String req_content = message.get("Content");

                String res_content = "";

                //组装回复消息
                //接收内容：你好  回复：你好
                //接收内容：大家好  回复：大家好
                //接收内容：同志们好  回复：为人民服务
                if ("你好".equals(req_content)) {
                    res_content = "你好";
                } else if ("大家好".equals(req_content)) {
                    res_content = "大家好";
                } else if ("同志们好".equals(req_content)) {
                    res_content = "为人民服务";
                } else {
                    //否则调用图灵机器人
                    weChartService.tuLing(req_content);

                    res_content = req_content;
                }

                WeChartTextMessage textMessage = new WeChartTextMessage();
                textMessage.setToUserName(message.get("FromUserName"));   //这里的ToUserName  是刚才接收xml中的FromUserName
                textMessage.setFromUserName(message.get("ToUserName"));   //这里的FromUserName 是刚才接收xml中的ToUserName  这里一定要注意，否则会出错
                textMessage.setCreateTime(new Date().getTime());
                textMessage.setContent(res_content);
                textMessage.setMsgType(messageType);

                String xml = MessageUtil.entityMessageToXml(textMessage);

                System.out.println("回复消息：------------------------------------");
                System.out.println("xml:" +"\n"+ xml);

                PrintWriter out = response.getWriter();
                out.print(xml);
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*******************************************************get 方法请求 开始****************************************************/

    /**
     *@author haibing.xiao
     *@return
     *@exception
     *@param
     *
     * <p>接入连接生效验证</p>
     */
    private void connect(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        System.out.println("RemoteAddr: "+ request.getRemoteAddr());
        System.out.println("QueryString: "+ request.getQueryString());
        if(!accessing(request, response)){
            System.out.println("服务器接入失败.......");
            return ;
        }
        String echostr=getEchostr();
        if(echostr!=null && !"".equals(echostr)){
            System.out.println("服务器接入生效........");
            response.getWriter().append(echostr);//完成相互认证
        }
    }
    /**
     * @author haibing.xiao
     * Date 2013-05-29
     * @return boolean
     * @exception ServletException, IOException
     * @param
     *
     *<p>用来接收微信公众平台的验证</p>
     */
    private boolean accessing(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if( isEmpty(signature)){
            return false;
        }
        if(isEmpty(timestamp)){
            return false;
        }
        if(isEmpty(nonce)){
            return false;
        }
        if(isEmpty(echostr)){
            return false;
        }
        String[] ArrTmp = { Token, timestamp, nonce };
        Arrays.sort(ArrTmp);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ArrTmp.length; i++) {
            sb.append(ArrTmp[i]);
        }
        String pwd = Encrypt(sb.toString());

        System.out.println("signature:"+signature+"timestamp:"+timestamp+"nonce:"+nonce+"pwd:"+pwd+"echostr:"+echostr);

        if(trim(pwd).equals(trim(signature))){
            this.echostr =echostr;
            return true;
        }else{
            return false;
        }
    }
    private String Encrypt(String strSrc) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.update(bt);
            strDes = bytes2Hex(md.digest()); //to HexString
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Invalid algorithm.");
            return null;
        }
        return strDes;
    }

    public String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    public String getEchostr(){
        return echostr;
    }
    /**
     *@author haibing.xiao
     *@return
     *@exception ServletException, IOException
     *@param
     *
     * <p>XML组装组件</p>
     */

    /*****************************************************get 方法请求结束******************************************************/

    private boolean isEmpty(String str){
        return null ==str || "".equals(str) ? true :false;
    }
    private String trim(String str){
        return null !=str  ? str.trim() : str;
    }

}
