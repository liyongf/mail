package com.liyf.boot.mail.util;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.util.IOUtils;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
/**
 * @author: Seven.wk
 * @description: 辅助工具类
 * @create: 2018/07/04
 */
public class HttpUtils {
    /**
     * 向目的URL发送post请求
     * @param url       目的url
     * @param params    发送的参数
     * @return  ResultVO
     */
    public static String post(String url, HttpMethod method, JSONObject json) throws IOException {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10*1000);
        requestFactory.setReadTimeout(10*1000);
        RestTemplate client = new RestTemplate(requestFactory);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8);
        HttpEntity<String> requestEntity = new HttpEntity<String>(json.toString(), headers);
        //  执行HTTP请求
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
        return response.getBody();
    }
    public static void main(String[] args) throws IOException {
        // 创建Workbook对象
        Workbook workbook = new XSSFWorkbook();

        // 创建Sheet表
        Sheet sheet = workbook.createSheet("Sheet2");

        // 加载图片文件
        InputStream inputStream = new FileInputStream("C:\\Users\\admin\\Pictures\\1.jpeg");
        byte[] imageBytes = IOUtils.toByteArray(inputStream);

        // 创建Drawing对象
        Drawing<?> drawing = sheet.createDrawingPatriarch();

        // 创建锚点
        ClientAnchor anchor = workbook.getCreationHelper().createClientAnchor();
        anchor.setCol1(1);
        anchor.setRow1(1);
        anchor.setCol2(4);
        anchor.setRow2(4);

        // 插入图片
        int pictureIndex = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_JPEG);
        Picture picture = drawing.createPicture(anchor, pictureIndex);

        // 保存Excel文件
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\admin\\Pictures\\output.xlsx");
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }
}