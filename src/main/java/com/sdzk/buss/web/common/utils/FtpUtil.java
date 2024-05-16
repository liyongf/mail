package com.sdzk.buss.web.common.utils;

import com.sdzk.buss.web.common.entity.FtpConnectInfo;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * ftp 工具类
 *
 * @author ljh
 * @version 1.0
 * @date 2023/9/25 17:34
 */
public class FtpUtil {
    Logger logger = LoggerFactory.getLogger("【FTP】");
    /**
     * 本地字符编码
     */
    private static String LOCAL_CHARSET = "ISO-8859-1";
    private final FTPClient ftpClient;

    public FtpUtil() {
        ftpClient = new FTPClient();
    }

    /**
     * 连接ftp
     *
     * @param connectInfo ftp连接信息
     * @return 是否连接成功
     * @throws IOException 异常
     */
    public boolean connect(FtpConnectInfo connectInfo) throws IOException {
        ftpClient.setConnectTimeout(1000 * 60);
        // 设置 FTP 控制连接的编码方式，通常应该设置为 UTF-8，以确保正确处理中文文件名等特殊字符。必须在connect之前设置,否则不生效，此时为默认ISO-8859-1。
        ftpClient.setControlEncoding(LOCAL_CHARSET);
        // FTP 客户端是否自动检测服务器的 UTF-8 支持。当设置为 true 时，FTP 客户端会自动检测服务器的 UTF-8 支持情况，并在需要的时候使用 UTF-8 编码进行通信。
        ftpClient.setAutodetectUTF8(true);
        ftpClient.connect(connectInfo.getHost(), connectInfo.getPort());
        int replyCode = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            ftpClient.disconnect();
            logger.info("FTP服务器拒绝连接。");
            return false;
        }
        // 发送自定义的FTP命令"OPTS UTF8 ON"来启用UTF-8编码。
        if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
            LOCAL_CHARSET = "UTF-8";
        }

        boolean success = ftpClient.login(connectInfo.getUsername(), connectInfo.getPassword());
        if (!success) {
            logger.info("用户名或者密码错误！");
            ftpClient.disconnect();
            return false;
        }

        // 设置FTP客户端的数据传输模式为被动模式
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.setSoTimeout(Integer.MAX_VALUE);
        return true;
    }

    /**
     * 关闭ftp连接
     *
     * @throws IOException 异常
     */
    public void disconnect() throws IOException {
        if (ftpClient.isConnected()) {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }

    /**
     * 上传文件
     *
     * @param localFile      本地文件
     * @param remoteFilePath ftp服务器文件相对路径
     * @throws IOException 异常
     */
    public boolean uploadFile(File localFile, String remoteFilePath) throws IOException {
        boolean success;
        try (InputStream inputStream = Files.newInputStream(localFile.toPath())) {
            success = ftpClient.storeFile(new String(remoteFilePath.getBytes(StandardCharsets.UTF_8), LOCAL_CHARSET), inputStream);
        }
        return success;
    }

    /**
     * 上传文件
     *
     * @param localFilePath  本地文件路径
     * @param remoteFilePath ftp服务器文件相对路径
     * @throws IOException 异常
     */
    public boolean uploadFile(String localFilePath, String remoteFilePath) throws IOException {
        boolean success;
        File localFile = new File(localFilePath);
        try (InputStream inputStream = Files.newInputStream(localFile.toPath())) {
            success = ftpClient.storeFile(remoteFilePath, inputStream);
        }
        return success;
    }

    /**
     * 下载文件
     *
     * @param remoteFilePath ftp文件路径
     * @param localFilePath  本地保存文件路径
     * @throws IOException 异常
     */
    public void downloadFile(String remoteFilePath, String localFilePath) throws IOException {
        File localFile = new File(localFilePath);
        try (OutputStream outputStream = Files.newOutputStream(localFile.toPath())) {
            boolean success = ftpClient.retrieveFile(remoteFilePath, outputStream);
            if (!success) {
                throw new IOException("Could not download file from the FTP server.");
            }
        }
    }

    public void deleteFile(String remoteFilePath) throws IOException {
        boolean success = ftpClient.deleteFile(remoteFilePath);
        if (!success) {
            throw new IOException("Could not delete file from the FTP server.");
        }
    }

    /**
     * 远程创建文件夹
     *
     * @param remote ftp服务器路径
     * @return 是否成功
     * @throws IOException 异常
     */
    public boolean createDirectory(String remote) throws IOException {
        // 先检查是否存在目录，如果不存在再创建目录
        boolean check = ftpClient.changeWorkingDirectory(remote);
        if (!check) {
            // 初始化一下工作文件夹，防止进来不是根目录
            ftpClient.changeWorkingDirectory("/");
            String[] directories = remote.split("/");
            for (String directory : directories) {
                if (!directory.isEmpty()) {
                    /*
                     * makeDirectory：
                     * 1.makeDirectory("a"),会在当前工作文件夹下创建文件夹a
                     * 2.makeDirectory("/a"),会在根目录下创建文件夹a
                     * 3.makeDirectory("a/b"),如果当前工作文件件下存在a文件夹，则在a里面创建b，否则创建失败返回false
                     * 4.makeDirectory("/a/b"),如果根目录下有文件夹a，则在a里面创建b，否则创建失败返回false
                     * changeWorkingDirectory：
                     * 1.changeWorkingDirectory("a")、changeWorkingDirectory("/a")
                     *      如果当前工作目录下、根目录下存在文件夹a，则改变当前工作目录为a，否则更改失败，当前工作目录不变
                     * 2.changeWorkingDirectory("a/b")changeWorkingDirectory("/a/b")
                     *      如果当前目录下存在a并且a目录下存在b、根目录下存在a并且a目录下存在b，则更改当前工作目录到a下的b，否则更改失败
                     */
                    // 初始进入ftp是根目录（"/"）,这里一级一级更改当前目录，如果有进不去的目录则进行创建
                    boolean check2 = ftpClient.changeWorkingDirectory(directory);
                    if (!check2) {
                        boolean success = ftpClient.makeDirectory(directory);
                        // 创建成功，再次更改当前目录
                        if (success) {
                            ftpClient.changeWorkingDirectory(directory);
                        } else {
                            // 创建失败，不继续创建，直接返回结果
                            return false;
                        }
                    }
                }
            }
            ftpClient.changeToParentDirectory();
        }
        return true;
    }

    public void deleteDirectory(String remoteDirectoryPath) throws IOException {
        boolean success = ftpClient.removeDirectory(remoteDirectoryPath);
        if (!success) {
            throw new IOException("Could not delete directory from the FTP server.");
        }
    }

    public FTPFile[] listFiles(String remoteDirectoryPath) throws IOException {
        return ftpClient.listFiles(remoteDirectoryPath);
    }
}
