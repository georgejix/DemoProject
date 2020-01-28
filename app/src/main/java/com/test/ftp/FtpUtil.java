package com.test.ftp;

import android.util.Log;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamEvent;
import org.apache.commons.net.io.CopyStreamListener;
import java.io.FileInputStream;

public class FtpUtil implements CopyStreamListener {
    private static final String TAG = "FtpUtil";

    private FTPClient ftpClient = null; // FTP客户端
    private static Object lockObject = new Object();
    private static FtpUtil ftpUtil = null;
    private FtpTransferListener transferListener;

    public static FtpUtil getInstance(){
        if(null == ftpUtil){
            synchronized (lockObject){
                if(null == ftpUtil) {
                    ftpUtil = new FtpUtil();
                }
            }
        }
        return ftpUtil;
    }

    public void registerTransferListener(FtpTransferListener listener){
        transferListener = listener;
    }

    public void unregisterTRansferListener(){
        transferListener = null;
    }

    /**
     * 连接到FTP服务器
     *
     * @param host     ftp服务器域名
     * @param username 访问用户名
     * @param password 访问密码
     * @param port     端口
     * @return 是否连接成功
     */
    public boolean ftpConnect(String host, String username, String password, int port) {
        if(null == host || null == username || null == password){
            return false;
        }
        if(host.trim().isEmpty() || username.trim().isEmpty() || password.trim().isEmpty()){
            return false;
        }
        try {
            ftpClient = new FTPClient();
            Log.d(TAG, "connecting to the ftp server " + host + " ：" + port);
            ftpClient.connect(host, port);
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ftpClient.disconnect();
                ftpClient = null;
                return false;
            }
            Log.d(TAG, "login to the ftp server");
            boolean status = ftpClient.login(username, password);
            if(!status){
                ftpClient.logout();
                ftpClient.disconnect();
                ftpClient = null;
                Log.d(TAG, "login to the ftp server failed");
                return false;
            }
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setCopyStreamListener(this);
            ftpClient.setControlEncoding("UTF-8");
            Log.d(TAG, "login to the ftp server success");
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Error: could not connect to host " + host);
        }
        return false;
    }

    /**
     * 断开ftp服务器连接
     *
     * @return 断开结果
     */
    public boolean ftpDisconnect() {
        if (ftpClient == null) {
            return true;
        }
        try {
            ftpClient.logout();
            ftpClient.disconnect();
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error occurred while disconnecting from ftp server.");
        }
        return false;
    }

    /**
     * ftp 文件上传
     *
     * @param srcFilePath  源文件目录
     * @param desFileName  文件名称
     * @param desDirectory 目标文件
     * @return 文件上传结果
     */
    public boolean ftpUpload(String srcFilePath, String desFileName, String desDirectory) {
        if(null == srcFilePath || null == desFileName || null == desDirectory){
            return false;
        }
        if(srcFilePath.trim().isEmpty() || desFileName.trim().isEmpty() || desDirectory.trim().isEmpty()){
            return false;
        }
        boolean status = false;
        try {
            if(!ftpChangeDir(desDirectory)){
                return false;
            }
            FileInputStream srcFileStream = new FileInputStream(srcFilePath);
            status = ftpClient.storeFile(desFileName, srcFileStream);
            srcFileStream.close();
            Log.d(TAG, "upload: " + status);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "upload failed: " + e.getLocalizedMessage());
        }
        return status;
    }

    /**
     * ftp 更改目录
     *
     * @param path 更改的路径
     * @return 更改是否成功
     */
    public boolean ftpChangeDir(String path) {
        boolean status = false;
        if(null == path || path.trim().isEmpty()){
            return false;
        }
        try {
            status = ftpClient.changeWorkingDirectory(path);
            Log.d(TAG, "change directory " + status);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "change directory failed: " + e.getLocalizedMessage());
        }
        return status;
    }

    @Override
    public void bytesTransferred(CopyStreamEvent copyStreamEvent) {
        bytesTransferred(copyStreamEvent.getTotalBytesTransferred(), copyStreamEvent.getBytesTransferred(), copyStreamEvent.getStreamSize());
    }

    @Override
    public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
        if(null != transferListener){
            transferListener.bytesTransferred(totalBytesTransferred, bytesTransferred, streamSize);
        }
    }
}
