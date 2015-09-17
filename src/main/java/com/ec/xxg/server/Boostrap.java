package com.ec.xxg.server;

/**
 * Created by ecuser on 2015/9/17.
 */
public class Boostrap {

    public static void main(String[] args) {
        System.out.println("before client begin to connnect...");
        new Thread(new TimeClientHandle("localhost",7000)).start();
    }
}
