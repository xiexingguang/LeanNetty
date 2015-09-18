package com.ec.xxg.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by xxg on 2015/9/16.
 */
public class MultiplexerTimeServer implements  Runnable{

     private Selector selector;
     private ServerSocketChannel serverSocketChannel;
     private volatile boolean stop;

    public MultiplexerTimeServer() {
         try{
             selector = Selector.open();
             serverSocketChannel = ServerSocketChannel.open();
             serverSocketChannel.configureBlocking(false);
             serverSocketChannel.socket().bind(new InetSocketAddress(7000), 1024);
             serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT); //注册是否有客户端连接事件
             System.out.print("The time server is  start in port : 7000");
         }catch (Exception e){
             System.out.println(e);
             System.exit(8);
         }
    }


    public  void  stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                selector.select(1000);

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                 t.printStackTrace();
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
               if(key.isAcceptable()){
                  ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
                  SocketChannel socketChannel = serverSocketChannel.accept();
                  socketChannel.configureBlocking(false);
                  socketChannel.register(selector,SelectionKey.OP_READ);   //listen the read event of the client
             }
            if (key.isReadable()) {
                SocketChannel sc = (SocketChannel)key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int readBytes = 0;
                if (sc != null) {
                    readBytes  = sc.read(byteBuffer);
                }

                if (readBytes > 0) {
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()]; //Returns the number of elements between the current position and the limit
                    byteBuffer.get(bytes);                                      //将byteBuffer数据读取到bytes直接数组中去
                    String body = new String(bytes,"UTF-8");
                    System.out.println("the server has been recieve  oreder:" + body);
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
                    doWrite(sc, currentTime);
                }else if (readBytes < 0) {
                     key.cancel();
                     sc.close();
                }else
                    ;

        }
        }
    }

    private  void doWrite(SocketChannel socketChannel,String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeByteBuffer = ByteBuffer.allocate(bytes.length);
            writeByteBuffer.put(bytes);
            writeByteBuffer.flip();
            socketChannel.write(writeByteBuffer);
        }
    }

    public void testGi() {

    }
}
