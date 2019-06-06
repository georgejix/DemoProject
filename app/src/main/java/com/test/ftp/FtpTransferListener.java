package com.test.ftp;

public interface FtpTransferListener {
    public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize);
}
