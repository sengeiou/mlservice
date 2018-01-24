package cn.ml_tech.mx.mlservice.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by zhongwang on 2018/1/22.
 */

public class OperateUtil {
    private Socket socket;
    private InputStream inputStream;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private String ipAddress;

    public OperateUtil(Socket socket) {
        this.socket = socket;
        ipAddress = this.socket.getInetAddress().getHostAddress();
        try {
            printWriter = new PrintWriter(this.socket.getOutputStream(),true);
            printWriter.println(MlConCommonUtil.CONNECTSUCESS);
            inputStream = socket.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    private OperateUtil() {
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void closeConnect() {
        if (printWriter != null) {
            printWriter.close();
            printWriter = null;
        }
        try {
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
            if (bufferedReader != null) {
                bufferedReader.close();
                bufferedReader = null;
            }
            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
