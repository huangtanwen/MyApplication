package com.htw.main;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UseSocket {

    private static final String TAG = "UseSocket";
    private static final String HOST = "192.168.0.104";
    private static final int PORT = 9999;
    private ExecutorService mExecutorService = null;
    private PrintWriter mPrintWriter = null;
    private BufferedReader mIn = null;
    private String mReceiveMsg = "";
    private SocketListener mSocketListener = null;
    private boolean mIsConnected = false;

    public interface SocketListener {
        /**
         * 连接成功
         */
        public void onConnectSuccess(String msg);

        /**
         * 发送成功
         */
        public void onSendSuccess(String msg);

        /**
         * 关闭完成
         */
        public void onClosed();
    }

    public UseSocket(SocketListener socketListener) {
        mSocketListener = socketListener;
        init();
    }

    private void init() {
        mExecutorService = Executors.newCachedThreadPool();
        Log.e("huangtanwen", "init");
    }

    private void receiveMsg() {
        try {
            while (true) {                                      //步骤三
                if ((mReceiveMsg = mIn.readLine()) != null) {
                    Log.e(TAG, "receiveMsg:" + mReceiveMsg);
                    if(!mIsConnected) {
                        if (mSocketListener != null) {
                            mSocketListener.onConnectSuccess(mReceiveMsg);
                        }
                        mIsConnected = true;
                    } else {
                        if(mSocketListener != null) {
                            mSocketListener.onSendSuccess(mReceiveMsg);
                        }
                    }
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "receiveMsg failed!", e);
        }
    }


    /**
     * 连接
     */
    public void connect() {
        Log.e("huangtanwen", "connect");
        mExecutorService.execute(new connectService());  //在一个新的线程中请求 Socket 连接
    }

    /**
     * 发送信息
     */
    public void send(String sendMsg) {
        mExecutorService.execute(new sendService(sendMsg));
    }

    /**
     * 关闭连接
     */
    public void close() {
        mExecutorService.execute(new sendService("0"));
        if(mSocketListener != null) {
            mSocketListener.onClosed();
        }
    }

    private class connectService implements Runnable {
        @Override
        public void run() {//可以考虑在此处添加一个while循环，结合下面的catch语句，实现Socket对象获取失败后的超时重连，直到成功建立Socket连接
            try {
                Socket socket = new Socket(HOST, PORT);      //步骤一
                socket.setSoTimeout(60000);
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(   //步骤二
                        socket.getOutputStream(), "UTF-8")), true);
                mIn = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                receiveMsg();
            } catch (Exception e) {
                Log.e(TAG, ("connectService:" + e.getMessage()));   //如果Socket对象获取失败，即连接建立失败，会走到这段逻辑
            }
        }
    }

    private class sendService implements Runnable {
        private String msg;

        sendService(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            mPrintWriter.println(this.msg);
            Log.e("huangtanwen","sendService msg = " + msg);
        }
    }
}
