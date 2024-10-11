package org.example.server;

import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer {

    /**
     * 启动服务器
     * @param port
     */
    public void doStart(int port) {

        //创建Vert.x实例
        Vertx vertx = Vertx.vertx();

        //创建Http服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        //监听端口并处理请求
        // 这个地方到HttpServerHandler中有具体实现
        server.requestHandler(new HttpServerHandler());

        //启动Http服务器并监听指定端口
        server.listen(port, result->{
            if(result.succeeded()){
                System.out.println("Server is now listening on port:" + port);
            }else{
                System.err.println("Failed to start server:" + result.cause());
            }
        });
    }
}
