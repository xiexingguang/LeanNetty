package com.ec.xxg.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by ecuser on 2015/9/21.
 *
 * 测试TCP/T沾包特性
 * 即客户端发送100次请求查询时间请求
 */
public class TimeClientHandler2 extends ChannelHandlerAdapter {

    private int counter;
    private byte[] bytes;

    public TimeClientHandler2() {
        String req = "QUERY TIME ORDER" + System.getProperty("line.separator");
        System.out.println("the query order :"+req);
        bytes = req.getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(bytes.length);
            message.writeBytes(bytes);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws  Exception {
        String body = (String) msg;
        System.out.println("Now si : " + body + "; the counter is :" + ++counter);


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();

    }
}
