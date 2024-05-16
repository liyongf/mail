package com.sdzk.buss.web.servlet;

import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerImgRelEntity;
import com.sdzk.buss.web.violations.entity.TBThreeViolationsImgRelEntity;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

@WebServlet("/UploadAppImageServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ApplicationContext applicationContext;
    @Autowired
    private SystemService systemService;
    // 上传文件存储目录
    //private static final String UPLOAD_DIRECTORY = "upload";
 
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 80; // 80MB

    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    /**
     * 上传数据及保存文件
     */
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
        // 检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }

        JSONObject json = new JSONObject();
        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        String dir = ResourceUtil.getConfigByName("mobileHiddenImgPath");
        factory.setRepository(new File(dir));
        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 中文处理
        upload.setHeaderEncoding("UTF-8");

        // 构造临时路径来存储上传的文件
        List<String> idList = new ArrayList<String>();
        try {
            // 解析请求的内容提取文件数据
            List<FileItem> formItems = upload.parseRequest(request);
            String type = "";   //risk: 风险；hiddenDanger:隐患；vio: 违章
            String id = "";
            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理表单中的字段,获得路径名称
                    if ("type".equals(item.getFieldName())) {
                        System.out.println(item.getString("UTF-8"));
                        type = item.getString("UTF-8");
                        continue;
                    }
                    if ("id".equals(item.getFieldName())) {
                        System.out.println(item.getString("UTF-8"));
                        id = item.getString("UTF-8");
                        dir = ResourceUtil.getConfigByName("mobileHiddenImgPath") + "//" + id;
                        idList.add(id);
                        continue;
                    }
                    String uploadPath = dir + File.separator;
                    // 如果目录不存在则创建
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        //System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        request.setAttribute("message",
                                "文件上传成功!");

                         if (Constants.MOBILE_FILE_TYPE_HD.equals(type)) {
                            TBHiddenDangerImgRelEntity rel = new TBHiddenDangerImgRelEntity();
                            rel.setMobileHiddenId(id);
                            rel.setImgPath(fileName);
                            systemService.save(rel);
                        } else if (Constants.MOBILE_FILE_TYPE_VIO.equals(type)) {
                            TBThreeViolationsImgRelEntity rel = new TBThreeViolationsImgRelEntity();
                            rel.setMobileThreevioId(id);
                            rel.setImgPath(fileName);
                            systemService.save(rel);
                        }
                    }
                }
            }

        } catch(Exception ex){
            ex.printStackTrace();
            json.put("code", ApiResultJson.CODE_500);
            json.put("message", ApiResultJson.CODE_500_MSG);
            json.put("data", ex);
            response.getWriter().print(json);
            return;
        }
        // 返回json
        json.put("code", ApiResultJson.CODE_200);
        json.put("message", ApiResultJson.CODE_200_MSG);
        json.put("data", idList);
        response.getWriter().print(json);
    }

    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

}
