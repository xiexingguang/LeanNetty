package com.ec.xxg.server;

import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by ecuser on 2015/9/16.
 */
public class TimeClientHandle implements  Runnable{
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile  boolean stop = false;

    public TimeClientHandle(String host ,int Port) {
        this.host = host;
        this.port = Port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {

    }


}
