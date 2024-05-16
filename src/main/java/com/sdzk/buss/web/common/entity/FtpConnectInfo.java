package com.sdzk.buss.web.common.entity;

import java.io.Serializable;

/**
 * ftp 连接配置
 *
 * @author ljh
 * @version 1.0
 * @date 2023/11/3 11:03
 */
public class FtpConnectInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主机（ip）
     */
    private String host;

    /**
     * 端口
     */
    private int port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 远程文件夹路径（用于ftp）
     */
    private String remote;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }


    public FtpConnectInfo() {
    }

    public FtpConnectInfo(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
}
