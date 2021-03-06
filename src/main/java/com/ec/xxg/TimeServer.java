package com.ec.xxg;

import java.net.ServerSocket;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by xxg on 2015/9/15.
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8080;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.print("The server is start at the 8080");
            serverSocket.accept(); //等待接受请求
        } catch (Exception e) {

        }
    }

    static class MultiplexerTimerServer implements Runnable {

        private Selector selector;
        private ServerSocketChannel serverSocketChannel;
        private volatile boolean stop;

        @Override
        public void run() {

        }

        /**
         * @param port
         */
        public MultiplexerTimerServer(int port) {
            try {
                selector = Selector.open();
                selector.select(1000);
            } catch (Exception e) {

            }

        }
    }
}

