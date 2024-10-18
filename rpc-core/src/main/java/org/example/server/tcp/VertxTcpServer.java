package org.example.server.tcp;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import org.example.server.HttpServer;

public class VertxTcpServer implements HttpServer {

    private byte[] handleRequest(byte[] requestData) {
        //在这里编写处理请求的逻辑，根据requstData构造响应数据并返回
        //这里只是示例，需要根据具体业务需求去实现具体逻辑
        return "Hello,client!".getBytes();
    }


    @Override
    public void doStart(int port) {
        //创建Vert.x实例
        Vertx vertx = Vertx.vertx();

        //创建TCP服务器
        NetServer server = vertx.createNetServer();

        //处理请求
        server.connectHandler(socket -> {
            //处理连接
            socket.handler(buffer -> {
                //处理接收到字节数组
                byte[] requestData = buffer.getBytes();
                //在这里进行自定义的字节数组处理逻辑，比如解析请求、调用服务、构造响应等
                byte[] responseData = handleRequest(requestData);
                //发送响应
                socket.write(Buffer.buffer(responseData));
            });
        });

        //启动TCP服务器并监听指定端口
        server.listen(port, result->{
            if(result.succeeded()){
                System.out.println("TCP server start on port :" + port);
            }else{
                System.out.println("Failed to start TCP server:" + result.cause());
            }
        });
    }

    public static void main(String[] args){
        new VertxTcpServer().doStart(8888);
    }
}
