package com.sdzk.buss.web.common.utils;

import java.io.*;
import java.net.URLEncoder;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * 解压Zip文件工具类
 *
 * @author zhangyongbo
 */
public class ZipUtil {
    private static final int buffer = 2048;

    /**
     * 解压Zip文件
     *
     * @param path     文件目录
     * @param savepath 解压路径
     */
    public static String getUnZipPath(String path, String savepath) {
        int count = -1;
        String resultPath = "";

        File file = null;
        InputStream is = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        new File(savepath).mkdir(); //创建保存目录
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(path, "UTF-8"); //解决中文乱码问题
            Enumeration<?> entries = zipFile.getEntries();
            boolean flag = true;
            while (entries.hasMoreElements()) {
                byte buf[] = new byte[buffer];

                ZipEntry entry = (ZipEntry) entries.nextElement();

                if (flag) {
                    String entryName = entry.getName();
                    resultPath = entryName.substring(0, entryName.indexOf("/"));
                    flag = false;
                }

                String filename = entry.getName();
                boolean ismkdir = false;
                if (filename.lastIndexOf("/") != -1) { //检查此文件是否带有文件夹
                    ismkdir = true;
                }
                filename = savepath + filename;

                if (entry.isDirectory()) { //如果是文件夹先创建
                    file = new File(filename);
                    file.mkdirs();
                    continue;
                }
                file = new File(filename);
                if (!file.exists()) { //如果是目录先创建
                    if (ismkdir) {
                        String folderPath = filename.substring(0, filename.lastIndexOf("/"));
                        new File(folderPath).mkdirs(); //目录先创建
                    }
                }
                file.createNewFile(); //创建文件

                is = zipFile.getInputStream(entry);
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos, buffer);

                while ((count = is.read(buf)) > -1) {
                    bos.write(buf, 0, count);
                }
                bos.flush();
                bos.close();
                fos.close();

                is.close();
            }

            zipFile.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultPath;
    }

    public static String getUnZipPath(String path) {
        String savepath = path.substring(0, path.lastIndexOf("\\")) + File.separator;
        return getUnZipPath(path, savepath);
    }

    public static void main(String[] args) {
        //unZip("F:\\ziptest\\zipPath\\test.zip","F:\\20171226\\");
        //getUnZipPath("F:\\ziptest\\zipPath\\test.zip");
        getUnZipPath("C:\\Users\\Administrator\\Desktop\\gucheng123.zip");
    }
}