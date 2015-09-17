package com.ec.xxg.server;

/**
 * Created by ecuser on 2015/9/17.
 */
public class ServerStarp {
    public static void main(String[] args) {
       new Thread(new MultiplexerTimeServer(),"time-server").start();
    }
}
