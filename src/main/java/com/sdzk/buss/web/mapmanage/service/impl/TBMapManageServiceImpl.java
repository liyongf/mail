package com.sdzk.buss.web.mapmanage.service.impl;

import com.sdzk.buss.web.common.utils.ZipUtil;
import com.sdzk.buss.web.mapmanage.entity.TBMapManageEntity;
import com.sdzk.buss.web.mapmanage.service.TBMapManageServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("tBMapManageService")
@Transactional
public class TBMapManageServiceImpl extends CommonServiceImpl implements TBMapManageServiceI {

    public void delete(TBMapManageEntity entity) throws Exception {
        super.delete(entity);
        //执行删除操作增强业务
        this.doDelBus(entity);
    }

    public Serializable save(TBMapManageEntity entity) throws Exception {
        Serializable t = super.save(entity);
        //执行新增操作增强业务
        this.doAddBus(entity);
        return t;
    }

    public void saveOrUpdate(TBMapManageEntity entity) throws Exception {
        super.saveOrUpdate(entity);
        //执行更新操作增强业务
        this.doUpdateBus(entity);
    }

    /**
     * 新增操作增强业务
     *
     * @param t
     * @return
     */
    private void doAddBus(TBMapManageEntity t) throws Exception {
    }

    /**
     * 更新操作增强业务
     *
     * @param t
     * @return
     */
    private void doUpdateBus(TBMapManageEntity t) throws Exception {
    }

    /**
     * 删除操作增强业务
     *
     * @param t
     * @return
     */
    private void doDelBus(TBMapManageEntity t) throws Exception {
    }

    public String upload(HttpServletRequest request) throws Exception {
        String filePath = "";
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile multipartFile = entity.getValue();// 获取上传文件对象

            File f = null;
            try {
                f = File.createTempFile("tmp", null);
                multipartFile.transferTo(f);

                String savePath = request.getSession().getServletContext().getRealPath("/plug-in/baidumap/");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String folderName = sdf.format(new Date());
                savePath = savePath + "/" + folderName + "/";

                String childFolder = ZipUtil.getUnZipPath(f.getPath(),savePath);
                filePath = "/plug-in/baidumap/" + folderName + "/" + childFolder + "/";

                f.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

    public String getCurrentMapPath(){
        String currentMapPath = "/plug-in/baidumap/tiles/";
        String sql = "select file_path from t_b_map_manage where is_delete='0' and is_used='1'";
        List<String> pathList = commonDao.findListbySql(sql);
        if(!pathList.isEmpty() && pathList.size()>0){
            currentMapPath = pathList.get(0);
        }
        return currentMapPath;
    }

    // dwg文件上传
    public void dwgupload(HttpServletRequest request,String departName, String mapId) throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {

            MultipartFile multipartFile = entity.getValue();// 获取上传文件对象

            String fileName = multipartFile.getOriginalFilename();

            InputStream is = multipartFile.getInputStream();

            HashMap<String, InputStream> files = new HashMap<String, InputStream>();

            files.put(fileName, is);

            String ipAddress = getIpAddr(request);

            Integer portInt = request.getLocalPort();

            String port = Integer.toString(portInt);

            String projectName = request.getServletContext().getContextPath();

            Map<String, String> mapData = new HashMap<>();

            mapData.put("departName",departName);

            mapData.put("mapId",mapId);

            mapData.put("ipAddress",ipAddress);
            mapData.put("port",port);
            mapData.put("projectName",projectName);

            uploadToFarService(files,mapData);

            is.close();
        }
    }

    public void uploadToFarService(HashMap<String, InputStream> files,Map<String, String> mapData) {
        try {

            String FAR_SERVICE_DIR = "http://118.190.148.69:8080/minemanage/tBMapManageController.do?receive";//远程服务器接受文件的路由

            String boundary = "Boundary-b1ed-4060-99b9-fca7ff59c113"; //Could be any string

            final String PREFIX = "--"; // 固定的前缀

            String Enter = "\r\n";

            String CHARSET = "UTF-8";

            URL url = new URL(FAR_SERVICE_DIR);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            if (mapData != null && !mapData.isEmpty()) {
                for (Map.Entry<String, String> entry : mapData.entrySet()) {
                    String key = entry.getKey(); // 键，相当于上面分析的请求中的username
                    String value = mapData.get(key); // 值，相当于上面分析的请求中的sdafdsa
                    dos.writeBytes(PREFIX + boundary + Enter); // 像请求体中写分割线，就是前缀+分界线+换行
                    dos.writeBytes("Content-Disposition: form-data; "
                            + "name=\"" + key + "\"" + Enter); // 拼接参数名，格式就是Content-Disposition: form-data; name="key" 其中key就是当前循环的键值对的键，别忘了最后的换行
                    dos.writeBytes(Enter);
                    dos.write(value.toString().getBytes(CHARSET));
                    dos.writeBytes(Enter); // 换行
                }
            }

            byte[] end_data = (Enter+ PREFIX + boundary +PREFIX+ Enter).getBytes();// 定义最后数据分隔线
            Iterator iter = files.entrySet().iterator();
            int i=0;
            while (iter.hasNext()) {
                i++;
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                InputStream val = (InputStream) entry.getValue();
                String fname = key;
                File file = new File(fname);
                StringBuilder sb = new StringBuilder();
                sb.append("--");
                sb.append(boundary);
                sb.append("\r\n");
                sb.append("Content-Disposition: form-data;name=\"file" + i + "\";filename=\"" + key + "\"\r\n");
                sb.append("Content-Type:application/octet-stream\r\n\r\n");

                byte[] data = sb.toString().getBytes();
                dos.write(data);
                DataInputStream in = new DataInputStream(val);
                int bytes = 0;
                byte[] bufferOut = new byte[1024];
                while ((bytes = in.read(bufferOut)) != -1) {
                    dos.write(bufferOut, 0, bytes);
                }
                dos.write("\r\n".getBytes()); // 多个文件时，二个文件之间加入这个
                in.close();
            }
            dos.write(end_data);
            dos.flush();
            dos.close();

            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     *
     * @return ip
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            System.out.println("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            System.out.println("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            System.out.println("getRemoteAddr ip: " + ip);
        }
        System.out.println("获取客户端ip: " + ip);
        return ip;
    }
}
